import 'dart:async';
import 'dart:io';
import 'dart:math';

import 'package:connectivity/connectivity.dart';
import 'package:flutter/services.dart';
import 'package:logger/logger.dart';
import 'package:projyproject/data/http_helper.dart';
import 'package:projyproject/data/websocket_helper_widget.dart';
import 'package:projyproject/repository/database.dart';
import 'package:projyproject/model/user.dart';

class Bloc {
  late final Stream<List<UserEntry>> _users;
  Stream<List<UserEntry>> get homeScreenEntries => _users;
  late UserEntry _user;

  //String _connectionStatus = 'Unknown';
  final Connectivity _connectivity = Connectivity();
  late StreamSubscription<ConnectivityResult> _connectivitySubscription;

  late HttpHelper h = HttpHelper();
  var logger = Logger();
  final Database db;

  Bloc() : db = Database() {
    _users = db.watchUsers();
    updateUsersDB();
  }

  Future<void> _updateConnectionStatus(ConnectivityResult result, LocalUsersCompanion user) async {
    switch (result) {
      case ConnectivityResult.wifi:
      case ConnectivityResult.mobile:{
      logger.d('status: online... adding: ' + user.toString());
      User myuser = User(
          id: '',
          username: user.username.value,
          password: user.password.value,
          firstname: user.firstname.value,
          lastname: user.lastname.value,
          gender: user.gender.value);

      h.addUser(myuser).then((value) {
        if (value != null)
          {
            logger.d('Added user to server: ' + myuser.toString());
            db.deleteUser(UserEntry(id: user.id.value, username: user.username.value, password: user.password.value, firstname: user.firstname.value, lastname: user.lastname.value, gender: user.gender.value));
            db.insertUser(LocalUsersCompanion.insert(
                id: value.id,
                username: value.username,
                password: value.password,
                firstname: value.firstname,
                lastname: value.lastname,
                gender: value.gender));
          }
        else
          {
          logger.d('failed to send user to server');
          }
      });
        break;
      }
      case ConnectivityResult.none:{
        logger.d('status: offline');
        break;}
      default:
        logger.d('Failed to get connectivity.');
        break;
    }
  }

  void setUsers(List<User> users) async {
    List<UserEntry> localUsers = await db.getUsers();

    for (var user in users) {
      bool found = false;

      for (var localuser in localUsers) {
        if (user.id == localuser.id) found = true;
      }

      if (!found) {
        logger.d('Getting user: ' + user.toString());

        final userC = LocalUsersCompanion.insert(
            id: user.id,
            username: user.username,
            password: user.password,
            firstname: user.firstname,
            lastname: user.lastname,
            gender: user.gender);

        db.insertUser(userC);

        logger.d('Inserted user: ' + userC.toString());
      }
    }
  }

  void updateUsersDB() async {
    logger.d("updateUsersDB");

    List<User> users = await h.getUsers();
    List<UserEntry> localUsers = await db.getUsers();

    for (var user in users) {
      bool found = false;

      for (var localuser in localUsers) {
        if (user.id == localuser.id) found = true;
      }

      if (!found) {
        logger.d('Getting user: ' + user.toString());

        final userC = LocalUsersCompanion.insert(
            id: user.id,
            username: user.username,
            password: user.password,
            firstname: user.firstname,
            lastname: user.lastname,
            gender: user.gender);

        db.insertUser(userC);

        logger.d('Inserted user: ' + userC.toString());
      }
    }
  }

  UserEntry get user {
    return _user;
  }

  void setUser(UserEntry user) {
    _user = user;
  }

  Future<bool> addEntry(LocalUsersCompanion user) async {
    try {
      logger.d('Adding given user: ' + user.toString());

      User myuser = User(
          id: '',
          username: user.username.value,
          password: user.password.value,
          firstname: user.firstname.value,
          lastname: user.lastname.value,
          gender: user.gender.value);

      LocalUsersCompanion userr;

      h.addUser(myuser).then((value) => {
            if (value != null)
              {
                logger.d('Added user to server: ' + myuser.toString()),
                //print(_connectionStatus)
                // db.insertUser(LocalUsersCompanion.insert(
                //     id: value.id,
                //     username: value.username,
                //     password: value.password,
                //     firstname: value.firstname,
                //     lastname: value.lastname,
                //     gender: value.gender)),
                //
                //logger.d('Added user in local db: ' + value.toString())
              }
            else
              {
                userr = LocalUsersCompanion.insert(
                    id: 'str' + Random().nextInt(1000000).toString(),
                    username: user.username.value,
                    password: user.password.value,
                    firstname: user.firstname.value,
                    lastname: user.lastname.value,
                    gender: user.gender.value),
                db.insertUser(userr),
                logger.d('Added fake user in local db: ' + userr.toString()),

                _connectivitySubscription = _connectivity.onConnectivityChanged.listen(
                    (ConnectivityResult result) {
                      _updateConnectionStatus(result, userr);
                    }
                ),
              }
          });
    } catch (error) {
      logger.d('Error Adding given user: ' + error.toString());
      throw Failure(error.toString());
    }

    var connectivityResult = await (Connectivity().checkConnectivity());
    logger.d('Connectivity: ' + connectivityResult.toString());
    if (connectivityResult == ConnectivityResult.none) {
      return false;
    }
    else {
      return true;
    }
  }

  void addDBEntry(LocalUsersCompanion user) {
    try {
      db.insertUser(user);
      logger.d('Added user in local db: ' + user.toString());
    } catch (error) {
      logger.d('Error Adding db user: ' + error.toString());
      throw Failure(error.toString());
    }
  }

  void updateEntry(UserEntry entry) async {
    try {
      logger.d('Updating given user: ' + entry.toString());

      User myuser = User(
          id: entry.id.toString(),
          username: entry.username,
          password: entry.password,
          firstname: entry.firstname,
          lastname: entry.lastname,
          gender: entry.gender);

      h.updateUser(myuser).then((value) => {
            logger.d('Updated user to server: ' + myuser.toString()),
            db.updateUserById(UserEntry(
                id: value.id,
                username: value.username,
                password: value.password,
                firstname: value.firstname,
                lastname: value.lastname,
                gender: value.gender)),
            logger.d('Updated user in local db: ' + value.toString())
          });
    } catch (error) {
      logger.d('Error Updating given user: ' + error.toString());
      throw Failure(error.toString());
      //print(error);
    }
  }

  void updateDBEntry(LocalUsersCompanion user) {
    try {
      db.updateUser(user);
      logger.d('Updated user in local db: ' + user.toString());
    } catch (error) {
      logger.d('Error Updating db user: ' + error.toString());
      throw Failure(error.toString());
    }
  }

  void deleteEntry(String id) {
    try {
      logger.d('Deleting user with the given id: ' + id);

      h.deleteUser(id).then((value) => {
            db.deleteUserById(value),
            logger.d('Deleted user with the given id: ' + value)
          });
      // db.deleteUserById(id);
    } catch (error) {
      logger.d('Error Deleting given user: ' + error.toString());
      throw Failure(error.toString());
    }
  }

  void deleteDBEntry(String id) {
    try {
      logger.d('Deleting user with the given id: ' + id);
      db.deleteUserById(id);
    } catch (error) {
      logger.d('Error Deleting db user: ' + error.toString());
      throw Failure(error.toString());
    }
  }

  Future<List<UserEntry>> getEntries() {
    return db.getUsers();
  }

  void close() {
    db.close();
    _connectivitySubscription.cancel();
  }
}

class Failure extends IOException {
  final String message;

  Failure(this.message);

  @override
  String toString() => message;
}
