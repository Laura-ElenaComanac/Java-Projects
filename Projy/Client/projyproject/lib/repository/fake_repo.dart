// import 'package:flutter/material.dart';
// import 'package:projyproject/model/user.dart';
// import 'package:rxdart/rxdart.dart';
// import 'dart:async';

// class FakeRepo {
//   static List<User> users = [
//     const User(
//         id: "1",
//         username: "Jane",
//         firstname: "Jane",
//         lastname: "JaneD",
//         password: "pass1",
//         gender: "female"),
//     const User(
//         id: "2",
//         username: "John",
//         firstname: "Smith",
//         lastname: "JohnS",
//         password: "pass2",
//         gender: "male"),
//     const User(
//         id: "3",
//         username: "Ellen",
//         firstname: "James",
//         lastname: "EllenJ",
//         password: "pass3",
//         gender: "female")
//   ];

//   static List<User> getUsers() {
//     return users;
//   }

//   static List<String> getStringUsers() {
//     List<String> strusers = [];
//     users.forEach((user) {
//       strusers.add(user.toString());
//     });
//     return strusers;
//   }

//   static void save(User user) {
//     users.add(user);
//   }

//   static void delete(String id) {
//     var position = users.indexWhere((u) => u.id == id);
//     users.remove(users[position]);
//   }

//   static void update(User user) {
//     var position = users.indexWhere((u) => u.id == user.id);
//     users[position] = user;
//   }
// }
