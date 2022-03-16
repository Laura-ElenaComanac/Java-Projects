import 'package:f_logs/model/flog/flog.dart';
import 'package:flutter/material.dart';
import 'package:logger/logger.dart';
import 'package:projyproject/repository/database.dart';
import 'package:projyproject/model/user.dart';
import 'package:projyproject/repository/fake_repo.dart';
import 'package:projyproject/shared/menu_bottom.dart';
import 'package:projyproject/shared/menu_drawer.dart';
import 'package:projyproject/view_model/bloc.dart';
import 'package:projyproject/view_model/user_list_view_model.dart';
import 'package:provider/provider.dart';

class RegisterScreen extends StatefulWidget {
  RegisterScreen({Key? key}) : super(key: key);

  @override
  State<RegisterScreen> createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  Bloc get bloc => Provider.of<Bloc>(context, listen: false);

  final TextEditingController txtUsername = TextEditingController();
  final TextEditingController txtPassword = TextEditingController();
  final TextEditingController txtFirstName = TextEditingController();
  final TextEditingController txtLastName = TextEditingController();
  final TextEditingController txtGender = TextEditingController();

  String usernameMessage = 'Username';
  String passwordMessage = 'Password';
  String firstNameMessage = 'First Name';
  String lastNameMessage = 'Last Name';
  String genderMessage = 'Gender';

  @override
  Widget build(BuildContext context) {
    Logger logger = Logger();

    return Scaffold(
      appBar: AppBar(
        title: const Text('Registration'),
        backgroundColor: Colors.purple,
      ),
      drawer: MenuDrawer(),
      //bottomNavigationBar: HomeBottom(),
      body: Column(children: [
        TextField(
          controller: txtUsername,
          keyboardType: TextInputType.text,
          decoration: InputDecoration(hintText: usernameMessage),
        ),
        TextField(
          controller: txtPassword,
          keyboardType: TextInputType.text,
          decoration: InputDecoration(hintText: passwordMessage),
        ),
        TextField(
          controller: txtFirstName,
          keyboardType: TextInputType.text,
          decoration: InputDecoration(hintText: firstNameMessage),
        ),
        TextField(
          controller: txtLastName,
          keyboardType: TextInputType.text,
          decoration: InputDecoration(hintText: lastNameMessage),
        ),
        TextField(
          controller: txtGender,
          keyboardType: TextInputType.text,
          decoration: InputDecoration(hintText: genderMessage),
        ),
        ElevatedButton(
            style: ElevatedButton.styleFrom(
              primary: Colors.purple,
            ),
            onPressed: () {
              if (txtUsername.text == '' ||
                  txtPassword.text == '' ||
                  txtFirstName.text == '' ||
                  txtLastName.text == '' ||
                  txtGender.text == '') {
                Widget okButton = TextButton(
                  child: Text("OK"),
                  onPressed: () {
                    return Navigator.of(context, rootNavigator: true)
                        .pop(); // dismisses only the dialog and returns nothing
                  },
                );
                AlertDialog alert = AlertDialog(
                  title: Text("Warning!"),
                  content: Text("No empty fields allowed!"),
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
              } else {
                final user = LocalUsersCompanion.insert(
                    id: '',
                    username: txtUsername.text,
                    password: txtPassword.text,
                    firstname: txtFirstName.text,
                    lastname: txtLastName.text,
                    gender: txtGender.text);

                bloc.addEntry(user).then((value) {
                  logger.d("addEntry val: " + value.toString());

                  if (!value) {
                    Widget okButton = TextButton(
                      child: Text("OK"),
                      onPressed: () {
                        Navigator.pop(context);
                        return Navigator.of(context, rootNavigator: true).pop();
                      },
                    );
                    AlertDialog alert = AlertDialog(
                      title: Text("Warning!"),
                      content: Text("No internet connection!"),
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
                  else{
                    Navigator.pop(context);
                  }
                });
              }
            },
            child: const Text(
              'REGISTER',
              style: TextStyle(fontSize: 18),
            )),
      ]),
    );
  }
}
