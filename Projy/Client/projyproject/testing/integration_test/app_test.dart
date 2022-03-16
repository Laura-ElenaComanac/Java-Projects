import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';
import 'package:projyproject/main.dart' as app;
import 'package:projyproject/screens/intro_screen.dart';
import 'package:projyproject/screens/login_screen.dart';
import 'package:flutter/material.dart';

void main() {
  IntegrationTestWidgetsFlutterBinding.ensureInitialized();

  testWidgets(
      "Login screen -> complete Username, Password fields -> press Login Button -> Intro screen ",
      (WidgetTester tester) async {
    app.main();

    await tester.pump();
    expect(find.byType(LoginScreen), findsOneWidget);

    Finder usernameField = find.byKey(Key('username'));
    expect(usernameField, findsOneWidget);
    await tester.enterText(usernameField, 'username');

    Finder passwordField = find.byKey(Key('password'));
    expect(passwordField, findsOneWidget);
    await tester.enterText(passwordField, 'password');

    final loginButton = find.byKey(const Key('Login'));
    expect(loginButton, findsOneWidget);
    await tester.tap(loginButton);
    await tester.pumpAndSettle(Duration(seconds: 5));

    expect(find.byType(IntroScreen), findsOneWidget);
  });
}
