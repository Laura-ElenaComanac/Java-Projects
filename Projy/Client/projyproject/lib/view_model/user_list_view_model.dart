// import 'package:flutter/cupertino.dart';
// import 'package:projyproject/model/database.dart';
// import 'package:projyproject/model/user.dart';
// import 'package:projyproject/repository/fake_repo.dart';
// import 'package:projyproject/repository/reository.dart';
// import 'package:projyproject/repository/reository.dart';

// class UserListViewModel with ChangeNotifier {
//   final Future<List<UserEntry>> _users = Repository.getEntries();

//   LocalUsersCompanion? _user;

//   LocalUsersCompanion? get user {
//     return _user;
//   }

//   void updateUser(LocalUsersCompanion user) {
//     //FakeRepo.update(user);
//     Repository.updateEntry(user);
//     notifyListeners();
//   }

//   void setUser(LocalUsersCompanion user) {
//     _user = user;
//     notifyListeners();
//   }

//   Future<List<UserEntry>> get users {
//     return _users;
//   }

//   void addUser(LocalUsersCompanion user) {
//     //FakeRepo.save(user);
//     Repository.addEntry(user);
//     notifyListeners();
//   }

//   void deleteUser(UserEntry userEntry) {
//     //FakeRepo.delete(index);
//     Repository.deleteEntry(userEntry);
//     notifyListeners();
//   }
// }
