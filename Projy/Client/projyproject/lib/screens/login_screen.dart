import 'package:flutter/material.dart';
import 'package:projyproject/screens/intro_screen.dart';

class LoginScreen extends StatelessWidget {
  const LoginScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    TextEditingController txtUsername = TextEditingController();
    TextEditingController txtPassword = TextEditingController();

    return Scaffold(
        appBar: AppBar(
          title: const Text('Projy'),
          backgroundColor: Colors.purple,
        ),
        body: SingleChildScrollView(
            child: Form(
                key: GlobalKey<FormState>(),
                child: Column(children: <Widget>[
                  TextFormField(
                      controller: txtUsername,
                      key: const Key('username'),
                      decoration: const InputDecoration(
                        //labelText: 'Username',
                        hintText: 'Username',
                      )),
                  TextFormField(
                      controller: txtPassword,
                      obscureText: true,
                      key: const Key('password'),
                      decoration: InputDecoration(
                        hintText: 'Password',
                        //labelText: 'Password',
                      )),
                  Column(children: <Widget>[
                    ClipRRect(
                        borderRadius: BorderRadius.circular(25),
                        child: TextButton(
                          key: Key('Login'),
                          onPressed: () {
                            if (txtUsername.text == "" ||
                                txtPassword.text == "") {
                              Widget okButton = TextButton(
                                child: Text("OK"),
                                onPressed: () {
                                  return Navigator.of(context,
                                          rootNavigator: true)
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
                              Navigator.of(context).pushNamed('/intro');
                            }
                          },
                          child: Text(
                            "Login",
                            style: TextStyle(color: Colors.purple),
                          ),
                        ))
                  ])
                ]))));
  }
}
