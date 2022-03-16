import 'package:flutter/material.dart';
import 'package:projyproject/data/http_helper.dart';
import 'package:projyproject/data/websocket_helper.dart';
import 'package:projyproject/model/user.dart';
import 'package:projyproject/screens/intro_screen.dart';
import 'package:projyproject/screens/login_screen.dart';
import 'package:projyproject/screens/register_screen.dart';
import 'package:projyproject/screens/update_screen.dart';
import 'package:projyproject/ui_screens/projyapp/generatedhomescreenwidget/GeneratedHomescreenWidget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedloginscreenwidget/GeneratedLoginscreenWidget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedpersonalpagewidget/GeneratedPersonalPageWidget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedregisterscreenwidget/GeneratedRegisterscreenWidget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedupdateprofilescreenwidget/GeneratedUpdateprofilescreenWidget.dart';
import 'package:projyproject/view_model/bloc.dart';
import 'package:provider/provider.dart';

import 'package:web_socket_channel/web_socket_channel.dart';
import 'package:flutter/material.dart';

void main() => runApp(const ProjyApp());

class ProjyApp extends StatelessWidget {
  const ProjyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    late WebSocketHelper ws;
    return Provider<Bloc>(
        create: (_) {
          Bloc bloc = Bloc();
          ws = WebSocketHelper(bloc);
          return bloc;
        },
        dispose: (_, bloc) => {bloc.close(), ws.dispose()},
        //providers: [ChangeNotifierProvider.value(value: UserListViewModel())],
        child: MaterialApp(
          title: 'Projy',
          routes: {
            '/': (context) => GeneratedLoginscreenWidget(),
            '/intro': (context) => GeneratedHomescreenWidget(),
            '/update': (context) => GeneratedUpdateprofilescreenWidget(),
            '/register': (context) => GeneratedRegisterscreenWidget(),
            '/GeneratedUpdateprofilescreenWidget': (context) =>
                GeneratedUpdateprofilescreenWidget(),
            '/personalpage': (context) => GeneratedPersonalPageWidget(),
          },
          initialRoute: '/',
        ));
  }
}
