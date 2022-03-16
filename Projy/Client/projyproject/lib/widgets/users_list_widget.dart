import 'package:flutter/material.dart';
import 'package:projyproject/data/websocket_helper.dart';
import 'package:projyproject/repository/database.dart';
import 'package:projyproject/model/user.dart';
import 'package:projyproject/view_model/bloc.dart';
import 'package:projyproject/view_model/user_list_view_model.dart';
import 'package:provider/provider.dart';

class UsersListWidget extends StatefulWidget {
  //final UserEntry entry;
  final List<UserEntry> users;
  final Function deleteFunction;
  const UsersListWidget(this.users, this.deleteFunction, {Key? key})
      : super(key: key);

  @override
  _UsersListWidgetState createState() => _UsersListWidgetState();
}

class _UsersListWidgetState extends State<UsersListWidget> {
  Bloc get bloc => Provider.of<Bloc>(context, listen: false);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
        itemCount: widget.users.length,
        itemBuilder: (context, index) {
          final user = UserEntry(
              id: widget.users[index].id,
              username: widget.users[index].username,
              password: widget.users[index].password,
              firstname: widget.users[index].firstname,
              lastname: widget.users[index].lastname,
              gender: widget.users[index].gender);

          final myuser = User(
              id: widget.users[index].id.toString(),
              username: widget.users[index].username,
              password: widget.users[index].password,
              firstname: widget.users[index].firstname,
              lastname: widget.users[index].lastname,
              gender: widget.users[index].gender);

          final struser = myuser.toString();

          return Dismissible(
            key: UniqueKey(),
            confirmDismiss: (DismissDirection direction) async {
              return await showDialog(
                context: context,
                builder: (BuildContext context) {
                  return AlertDialog(
                    title: const Text("Confirm"),
                    content: const Text(
                        "Are you sure you wish to delete this item?"),
                    actions: <Widget>[
                      TextButton(
                          onPressed: () => Navigator.of(context).pop(true),
                          child: const Text("DELETE")),
                      TextButton(
                        onPressed: () => Navigator.of(context).pop(false),
                        child: const Text("CANCEL"),
                      ),
                    ],
                  );
                },
              );
            },
            onDismissed: (direction) {
              widget.deleteFunction(index);
            },
            background: Container(color: Colors.red),
            child: StreamBuilder(builder: (context, snapshot) {
              return Column(children: [
                ListTile(
                  onTap: () {
                    bloc.setUser(user);
                  },
                  title: Text(struser),
                ),
              ]);
            }),
          );
        });
  }
}
