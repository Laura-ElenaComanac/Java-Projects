import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

@immutable
class User {
  String _id;
  String get id => _id;

  String _username;
  String get username => _username;

  String _password;
  String get password => _password;

  String _firstname;
  String get firstname => _firstname;

  String _lastname;
  String get lastname => _lastname;

  String _gender;
  String get gender => _gender;

  factory User.fromJson(Map<String, dynamic> usersMap) {
    return User(
      id: usersMap['id'].toString() as String,
      username: usersMap['username'] as String,
      password: usersMap['password'] as String,
      firstname: usersMap['firstName'] as String,
      lastname: usersMap['lastName'] as String,
      gender: usersMap['gender'] as String,
    );
  }

  Map<String, dynamic> toJson() {
    Map<String, dynamic> usersMap = {
      'id': _id,
      'username': _username,
      'password': _password,
      'firstName': _firstname,
      'lastName': _lastname,
      'gender': _gender
    };
    return usersMap;
  }

  User({
    required String id,
    required String username,
    required String password,
    required String firstname,
    required String lastname,
    required String gender,
  })  : _id = id,
        _username = username,
        _password = password,
        _firstname = firstname,
        _lastname = lastname,
        _gender = gender,
        assert(id != null),
        assert(username != null),
        assert(password != null),
        assert(firstname != null),
        assert(lastname != null),
        assert(gender != null);

  // User copyWith({
  //   required String id,
  //   required String username,
  //   required String password,
  //   required String firstname,
  //   required String lastname,
  //   required String gender,
  // }) =>
  //     User(
  //         id: id ?? _id,
  //         username: username ?? _username,
  //         password: password ?? _password);

  @override
  bool operator ==(Object other) => other is User && other._id == _id;

  @override
  int get hashCode => hashValues(_id, _username);

  @override
  String toString() {
    return "id: " +
        _id +
        ", username: " +
        _username +
        ", password: " +
        _password +
        ", firstname:" +
        _firstname +
        ", lastname: " +
        _lastname +
        ", gender: " +
        _gender;
  }
}
