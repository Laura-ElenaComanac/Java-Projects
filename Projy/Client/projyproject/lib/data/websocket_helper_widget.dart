// import 'dart:convert';
// import 'dart:ffi';

// import 'package:flutter/material.dart';
// import 'package:logger/logger.dart';
// import 'package:projyproject/model/user.dart';
// import 'package:projyproject/view_model/bloc.dart';
// import 'package:projyproject/widgets/users_list_widget.dart';
// import 'package:stomp_dart_client/stomp.dart';
// import 'package:stomp_dart_client/stomp_config.dart';
// import 'package:stomp_dart_client/stomp_frame.dart';
// import 'package:web_socket_channel/io.dart';
// import 'package:web_socket_channel/web_socket_channel.dart';
// import 'http_helper.dart';

// class WebSocketHelperWidget extends StatefulWidget {
//   const WebSocketHelperWidget({Key? key}) : super(key: key);

//   @override
//   _WebSocketHelperWidgetState createState() => _WebSocketHelperWidgetState();
// }

// var logger = Logger();

// class _WebSocketHelperWidgetState extends State<WebSocketHelperWidget> {
//   // final WebSocketChannel channel = IOWebSocketChannel.connect(
//   //   Uri(scheme: "ws", host: "10.0.2.2", port: 8080, path: "/socket"),
//   //   //Uri.parse('ws://10.0.2.2:8080/socket'),
//   // );

//   late StompClient? stompClient = null;
//   final socketUrl = 'http://localhost:8080/ws-message';
//   String message = '';
//   late List<User> usersList;
//   late Bloc bloc;

//   void onConnect(StompFrame frame) {
//     // subscribing to the /topic/message to receive data in real time
//     stompClient!.subscribe(
//         destination: '/topic/message',
//         callback: (StompFrame frame) {
//           if (frame.body != null) {
//             logger.d("started websocket callback");
//             var data = json.decode(frame.body!) as List;
//             logger.d(data);
//             return;
//             List<User> users = data.map((e) => User.fromJson(e)).toList();
//             bloc.setUsers(users); // updating the db
//             setState(() => usersList = users);
//             logger.d("Web Socket connected! - updated the db with " +
//                 users.toString());
//           }
//         });
//     // sending a message to /app/hello
//     stompClient!.send(destination: '/app/hello', body: "hello!");
//   }

//   @override
//   void initState() {
//     // connecting to the web socket
//     super.initState();
//     if (stompClient == null) {
//       stompClient = StompClient(
//           config: StompConfig.SockJS(
//         url: socketUrl,
//         onConnect: onConnect,
//         onWebSocketError: (dynamic error) => print(error.toString()),
//       ));
//       stompClient!.activate();
//       logger.d("Web Socket connected!");
//     }
//   }

//   @override
//   void dispose() {
//     if (stompClient != null) {
//       stompClient!.deactivate();
//     }
//     super.dispose();
//     logger.d("Web Socket deactivated!");
//   }

//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//         appBar: AppBar(
//           title: Text('Test'),
//         ),
//         body: null);
//   }
// }
