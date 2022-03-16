import 'package:f_logs/model/flog/flog.dart';
import 'package:flutter/material.dart';
import 'package:logger/logger.dart';
import 'package:projyproject/repository/database.dart';
import 'package:projyproject/model/user.dart';
import 'package:projyproject/repository/fake_repo.dart';
import 'package:projyproject/shared/menu_bottom.dart';
import 'package:projyproject/shared/menu_drawer.dart';
import 'package:projyproject/main.dart';
import 'package:projyproject/view_model/bloc.dart';
import 'package:projyproject/view_model/user_list_view_model.dart';
import 'package:projyproject/widgets/users_list_widget.dart';
import 'package:rxdart/rxdart.dart';
import 'dart:async';
import 'package:provider/provider.dart';

class IntroScreen extends StatefulWidget {
  const IntroScreen({Key? key}) : super(key: key);

  @override
  State<IntroScreen> createState() => _IntroScreenState();
}

class _IntroScreenState extends State<IntroScreen> {
  Bloc get bloc => Provider.of<Bloc>(context, listen: false);

  @override
  Widget build(BuildContext context) {
    Logger logger = Logger();

    // List<UserEntry> users = [];
    // Provider.of<UserListViewModel>(context).users.then((value) {
    //   setState(() {
    //     users = value;
    //   });
    // });

    return Scaffold(
        appBar: AppBar(
          title: const Text('Projy'),
          backgroundColor: Colors.purple,
        ),
        drawer: MenuDrawer(),
        bottomNavigationBar: MenuBottom(),
        // body: UsersListWidget(users, (int index) {
        //   Provider.of<UserListViewModel>(context, listen: false)
        //       .deleteUser(users[index]);
        // })

        body: StreamBuilder<List<UserEntry>>(
            stream: bloc.homeScreenEntries,
            builder: (context, snapshot) {
              if (!snapshot.hasData) {
                return const Align(
                  alignment: Alignment.center,
                  child: CircularProgressIndicator(),
                );
              }

              if (snapshot.hasError) logger.d('snapshot error: ' + snapshot.error.toString());

              final users = snapshot.data!;

              return UsersListWidget(users, (int index) {
                try {
                  bloc.deleteEntry(users[index].id);
                } on Failure catch (f) {
                  FLog.error(
                      className: "IntroScreen",
                      methodName: "deleteEntry",
                      text: f.message);

                  Widget okButton = TextButton(
                    child: Text("OK"),
                    onPressed: () {
                      return Navigator.of(context, rootNavigator: true).pop();
                    },
                  );
                  AlertDialog alert = AlertDialog(
                    title: Text("Warning!"),
                    content: Text(f.message),
                    actions: [
                      okButton,
                    ],
                  );
                  showDialog(
                    context: context,
                    builder: (BuildContext context) {
                      return alert;
                    },
                  );
                }
              });
            }));
  }
}
