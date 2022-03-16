import 'dart:async';
import 'package:moor_flutter/moor_flutter.dart';
import 'package:projyproject/model/user.dart';

part 'database.g.dart';

@DataClassName('UserEntry')
class LocalUsers extends Table {
  //IntColumn get id => integer().autoIncrement()();
  TextColumn get id => text().withLength(min: 1, max: 100)();
  TextColumn get username => text().withLength(min: 1, max: 100)();
  TextColumn get password => text().withLength(min: 1, max: 100)();
  TextColumn get firstname => text().withLength(min: 1, max: 100)();
  TextColumn get lastname => text().withLength(min: 1, max: 100)();
  TextColumn get gender => text().withLength(min: 1, max: 100)();

  @override
  Set<Column> get primaryKey => {id};
}

@UseMoor(tables: [LocalUsers])
class Database extends _$Database {
  Database()
      : super(FlutterQueryExecutor.inDatabaseFolder(
            path: 'database.sqlite', logStatements: true));

  @override
  int get schemaVersion => 1;

  Future<List<UserEntry>> getUsers() => select(localUsers).get();

  Stream<List<UserEntry>> watchUsers() => select(localUsers).watch();

  Stream<UserEntry> userById(String id) {
    return (select(localUsers)..where((t) => t.id.equals(id))).watchSingle();
  }

  SingleSelectable<UserEntry> entryById(String id) {
    return select(localUsers)..where((t) => t.id.equals(id));
  }

  SingleSelectable<UserEntry> entry(String id) {
    return select(localUsers)..where((t) => t.id.equals(id));
  }

  Future<void> insertUser(LocalUsersCompanion user) =>
      into(localUsers).insert(user);

  Future<void> insertUserEntry(UserEntry user) => into(localUsers).insert(user);

  Future<void> updateUser(Insertable<UserEntry> user) =>
      update(localUsers).replace(user);

  Future<void> deleteUser(UserEntry user) => delete(localUsers).delete(user);

  Future<void> deleteUserById(String id) {
    return (delete(localUsers)..where((t) => t.id.equals(id))).go();
  }

  Future<void> updateUserById(UserEntry user) {
    return (update(localUsers)..where((t) => t.id.equals(user.id))).write(user);
  }
}
