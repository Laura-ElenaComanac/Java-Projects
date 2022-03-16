import 'package:flutter/material.dart';
import 'package:projyproject/screens/intro_screen.dart';
import 'package:projyproject/screens/login_screen.dart';
import 'package:projyproject/data/websocket_helper_widget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedhomescreenwidget/GeneratedHomescreenWidget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedloginscreenwidget/GeneratedLoginscreenWidget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedpostprojectwidget/GeneratedPostProjectWidget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedregisterscreenwidget/GeneratedRegisterscreenWidget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedupdateprofilescreenwidget/GeneratedUpdateprofilescreenWidget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedupdateprofilescreenwidget/generated/GeneratedGroup13Widget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedupdateprofilescreenwidget/generated/GeneratedGroup9Widget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedupdateprofilescreenwidget/generated/GeneratedUpdateProfileWidget.dart';

class MenuDrawer extends StatelessWidget {
  const MenuDrawer({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Widget screen = Container();
    return Drawer(
      backgroundColor: Color.fromARGB(255, 135, 134, 231),
      child: ListView(padding: EdgeInsets.zero, children: <Widget>[
        DrawerHeader(
          child: FloatingActionButton(
            backgroundColor: Colors.transparent,
            elevation: 0,
            onPressed: () => Navigator.of(context).pushNamed('/personalpage'),
            child: Container(
              alignment: Alignment.centerLeft,
              child: GeneratedGroup13Widget(),
            ),
          ),
          decoration: BoxDecoration(
            color: Color.fromARGB(255, 102, 59, 223),
          ),
        ),
        ListTile(
            leading: Icon(
              Icons.home,
              size: 30,
              color: Colors.white,
            ),
            title: Text('Home',
                style: TextStyle(color: Colors.white, fontSize: 25)),
            onTap: () {
              screen = GeneratedHomescreenWidget();
              Navigator.of(context)
                  .push(MaterialPageRoute(builder: (context) => screen));
            }),
        ListTile(
            leading: Icon(
              Icons.lightbulb,
              size: 30,
              color: Colors.white,
            ),
            title: Text('Post Project',
                style: TextStyle(color: Colors.white, fontSize: 25)),
            onTap: () {
              screen = GeneratedPostProjectWidget();
              Navigator.of(context)
                  .push(MaterialPageRoute(builder: (context) => screen));
            }),
        ListTile(
            leading: Icon(
              Icons.edit_rounded,
              size: 30,
              color: Colors.white,
            ),
            title: Text('Edit Profile',
                style: TextStyle(color: Colors.white, fontSize: 25)),
            onTap: () {
              screen = GeneratedUpdateprofilescreenWidget();
              Navigator.of(context)
                  .push(MaterialPageRoute(builder: (context) => screen));
            }),
        ListTile(
            leading: Icon(
              Icons.switch_account,
              size: 30,
              color: Colors.white,
            ),
            title: Text('Switch Account',
                style: TextStyle(color: Colors.white, fontSize: 25)),
            onTap: () {
              screen = GeneratedLoginscreenWidget();
              Navigator.of(context)
                  .push(MaterialPageRoute(builder: (context) => screen));
            }),
      ]),
    );
  }

  // List<Widget> buildMenuItems(BuildContext context) {
  //   final List<String> menuTitles = ['Home', 'Register'];
  //   List<Widget> menuItems = [];
  //   menuItems.add(const DrawerHeader(
  //       decoration: BoxDecoration(
  //         color: Color.fromARGB(255, 102, 59, 223),
  //       ),
  //       child:
  //           Text('Menu', style: TextStyle(color: Colors.white, fontSize: 28))));

  //   menuTitles.forEach((String element) {
  //     Widget screen = Container();
  //     menuItems.add(ListTile(
  //         title: Text(element,
  //             style: TextStyle(color: Colors.white, fontSize: 20)),
  //         onTap: () {
  //           switch (element) {
  //             case 'Home':
  //               screen = GeneratedHomescreenWidget();
  //               break;
  //             case 'Register':
  //               screen = GeneratedRegisterscreenWidget();
  //               break;
  //           }
  //           Navigator.of(context)
  //               .push(MaterialPageRoute(builder: (context) => screen));
  //         }));
  //   });
  //   return menuItems;
  // }
}
