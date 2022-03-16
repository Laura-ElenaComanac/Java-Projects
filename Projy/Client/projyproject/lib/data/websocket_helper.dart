import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:logger/logger.dart';
import 'package:projyproject/model/user.dart';
import 'package:projyproject/repository/database.dart';
import 'package:projyproject/view_model/bloc.dart';
import 'package:provider/provider.dart';
import 'package:stomp_dart_client/stomp.dart';
import 'package:stomp_dart_client/stomp_config.dart';
import 'package:stomp_dart_client/stomp_frame.dart';

class WebSocketHelper {
  var logger = Logger();
  late StompClient? stompClient = null;
  final socketUrl = 'http://192.168.0.118:8080/ws-message';
  //final Database db = Database();
  Bloc bloc;

  WebSocketHelper(this.bloc) {
    initState();
  }

  void onConnect(StompFrame frame) {
    // subscribing to the /topic/message to receive data in real time

    stompClient!.subscribe(
        destination: '/topic/add',
        callback: (StompFrame frame) {
          if (frame.body != null) {
            logger.d("started websocket callback on add");
            var data = json.decode(frame.body!);
            logger.d("received " + frame.body!);
            User user = User.fromJson(data);

            try {
              bloc.addDBEntry(LocalUsersCompanion.insert(
                  id: user.id,
                  username: user.username,
                  password: user.password,
                  firstname: user.firstname,
                  lastname: user.lastname,
                  gender: user.gender));
              logger.d('Added user in local db: ' + user.toString());
            } catch (error) {
              logger.d('Websocket error add: ' + user.toString());
              throw Failure(error.toString());
            }
          }
        });

    stompClient!.subscribe(
        destination: '/topic/update',
        callback: (StompFrame frame) {
          if (frame.body != null) {
            logger.d("started websocket callback on update");
            var data = json.decode(frame.body!);
            logger.d("received " + frame.body!);
            User user = User.fromJson(data);

            try {
              bloc.updateDBEntry(LocalUsersCompanion.insert(
                  id: user.id,
                  username: user.username,
                  password: user.password,
                  firstname: user.firstname,
                  lastname: user.lastname,
                  gender: user.gender));
              logger.d('Updated user in local db: ' + user.toString());
            } catch (error) {
              logger.d('Websocket error update: ' + user.toString());
              throw Failure(error.toString());
            }
          }
        });

    stompClient!.subscribe(
        destination: '/topic/delete',
        callback: (StompFrame frame) {
          if (frame.body != null) {
            logger.d("started websocket callback on delete");
            //var data = json.decode(frame.body!);
            logger.d("received " + frame.body!);

            try {
              bloc.deleteDBEntry(frame.body!);
              logger.d('Deleted user in local db: ' + frame.body!);
            } catch (error) {
              logger.d('Websocket error delete: ' + frame.body!);
              throw Failure(error.toString());
            }
          }
        });

    // sending a message to /app/hello
    //stompClient!.send(destination: '/app/hello', body: "hello!");
  }

  void initState() {
    // connecting to the web socket
    if (stompClient == null) {
      stompClient = StompClient(
          config: StompConfig.SockJS(
        url: socketUrl,
        onConnect: onConnect,
        onWebSocketError: (dynamic error) => print(error.toString()),
      ));
      stompClient!.activate();
      logger.d("Web Socket connected!");
    }
  }

  void dispose() {
    if (stompClient != null) {
      stompClient!.deactivate();
    }
    logger.d("Web Socket deactivated!");
  }
}
