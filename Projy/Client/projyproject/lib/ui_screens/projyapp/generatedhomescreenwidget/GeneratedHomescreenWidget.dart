import 'package:flutter/material.dart';
import 'package:projyproject/shared/menu_drawer.dart';
import 'package:projyproject/ui_screens/helpers/transform/transform.dart';
import 'package:projyproject/ui_screens/projyapp/generatedhomescreenwidget/generated/GeneratedEllipse10Widget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedhomescreenwidget/generated/GeneratedEllipse11Widget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedhomescreenwidget/generated/GeneratedEllipse8Widget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedhomescreenwidget/generated/GeneratedEllipse9Widget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedhomescreenwidget/generated/GeneratedLine3Widget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedhomescreenwidget/generated/GeneratedLine4Widget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedhomescreenwidget/generated/GeneratedLine5Widget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedhomescreenwidget/generated/GeneratedLine6Widget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedhomescreenwidget/generated/GeneratedProjyWidget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedhomescreenwidget/generated/GeneratedRectangle4Widget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedhomescreenwidget/generated/GeneratedRectangle5Widget.dart';
import 'package:projyproject/ui_screens/projyapp/generatedhomescreenwidget/generated/GeneratedRectangle6Widget.dart';

/* Component homescreen
    Autogenerated by FlutLab FTF Generator
  */
class GeneratedHomescreenWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var scaffoldKey = GlobalKey<ScaffoldState>();

    return Scaffold(
        drawer: ClipRRect(
          borderRadius: BorderRadius.only(
              topRight: Radius.circular(1000),
              bottomRight: Radius.circular(10)),
          child: MenuDrawer(),
        ),
        appBar: AppBar(
            key: scaffoldKey,
            leading: Builder(
              builder: (context) => IconButton(
                icon: Icon(Icons.menu_rounded),
                iconSize: 40,
                onPressed: () => Scaffold.of(context).openDrawer(),
              ),
            ),
            flexibleSpace: Container(
              decoration: new BoxDecoration(
                gradient: new LinearGradient(
                    colors: [
                      Color.fromARGB(255, 102, 59, 223),
                      Color.fromARGB(255, 135, 134, 231),
                    ],
                    begin: const FractionalOffset(0.0, 0.0),
                    end: const FractionalOffset(1.0, 0.0),
                    stops: [0.0, 1.0],
                    tileMode: TileMode.clamp),
              ),
              child: Stack(
                  fit: StackFit.expand,
                  alignment: Alignment.center,
                  overflow: Overflow.visible,
                  children: [
                    ClipRRect(
                      borderRadius: BorderRadius.zero,
                    ),
                    Positioned(
                      left: null,
                      top: -110,
                      right: -68.0,
                      bottom: null,
                      width: 192.0,
                      height: 207.0,
                      child: GeneratedEllipse8Widget(),
                    ),
                    Positioned(
                      left: null,
                      top: 40,
                      right: -4.8552703857421875,
                      bottom: null,
                      width: 96.85527038574219,
                      height: 44.0,
                      child: GeneratedProjyWidget(),
                    ),
                  ]),
            )),
        body: Material(
            child: ClipRRect(
          borderRadius: BorderRadius.zero,
          child: LayoutBuilder(
              builder: (BuildContext context, BoxConstraints constraints) {
            return SingleChildScrollView(
              scrollDirection: Axis.vertical,
              child: Container(
                  height: 1286.0,
                  child: Stack(children: [
                    Container(
                        width: constraints.maxWidth,
                        child: Container(
                          width: 414.0,
                          height: 896.0,
                          child: Stack(
                              fit: StackFit.expand,
                              alignment: Alignment.center,
                              overflow: Overflow.visible,
                              children: [
                                ClipRRect(
                                  borderRadius: BorderRadius.zero,
                                  child: Container(
                                    color: Color.fromARGB(255, 135, 134, 231),
                                  ),
                                ),
                                Positioned(
                                  left: 0.0,
                                  top: 0.0,
                                  right: 0.0,
                                  bottom: 0.0,
                                  width: null,
                                  height: null,
                                  child: LayoutBuilder(builder:
                                      (BuildContext context,
                                          BoxConstraints constraints) {
                                    final double width =
                                        constraints.maxWidth - 34.0;
                                    final double scaleX =
                                        width <= 0 ? 0 : (width / 380.0);

                                    final double height =
                                        constraints.maxHeight - -236.0;
                                    final double scaleY =
                                        height <= 0 ? 0 : (height / 1132.0);

                                    final Widget child =
                                        GeneratedRectangle6Widget();
                                    return Stack(children: [
                                      Transform(
                                        transform: Matrix4(
                                            scaleX,
                                            0,
                                            0,
                                            0,
                                            0,
                                            scaleY,
                                            0,
                                            0,
                                            0,
                                            0,
                                            1,
                                            0,
                                            17.0 * scaleX,
                                            115.0 * scaleY,
                                            0,
                                            1),
                                        alignment: Alignment.topLeft,
                                        child: SizedBox.expand(child: child),
                                      )
                                    ]);
                                  }),
                                ),
                                Positioned(
                                  left: 0.0,
                                  top: null,
                                  right: 0.0,
                                  bottom: null,
                                  width: null,
                                  height: 43.0,
                                  child: TransformHelper.translate(
                                      x: 0.00,
                                      y: -480,
                                      z: 0,
                                      child: LayoutBuilder(builder:
                                          (BuildContext context,
                                              BoxConstraints constraints) {
                                        final double width =
                                            constraints.maxWidth - 98.0;
                                        final double scaleX =
                                            width <= 0 ? 0 : (width / 316.0);

                                        final Widget child =
                                            GeneratedRectangle4Widget();
                                        return Stack(children: [
                                          Transform(
                                            transform: Matrix4(
                                                scaleX,
                                                0,
                                                0,
                                                0,
                                                0,
                                                1,
                                                0,
                                                0,
                                                0,
                                                0,
                                                1,
                                                0,
                                                28.0 * scaleX,
                                                89.0,
                                                0,
                                                1),
                                            alignment: Alignment.topLeft,
                                            child:
                                                SizedBox.expand(child: child),
                                          )
                                        ]);
                                      })),
                                ),
                                Positioned(
                                  left: null,
                                  top: null,
                                  right: 17.0,
                                  bottom: null,
                                  width: 47.0,
                                  height: 46.0,
                                  child: TransformHelper.translate(
                                      x: 0.00,
                                      y: -390,
                                      z: 0,
                                      child: GeneratedEllipse10Widget()),
                                ),
                                Positioned(
                                  left: null,
                                  top: null,
                                  right: 23.384471893310547,
                                  bottom: null,
                                  width: 20.615528106689453,
                                  height: 5.0,
                                  child: TransformHelper.translate(
                                      x: 0.00,
                                      y: -390,
                                      z: 0,
                                      child: GeneratedLine3Widget()),
                                ),
                                Positioned(
                                  left: null,
                                  top: null,
                                  right: 33.0,
                                  bottom: null,
                                  width: 22.0,
                                  height: 21.0,
                                  child: TransformHelper.translate(
                                      x: 0.00,
                                      y: -395,
                                      z: 0,
                                      child: GeneratedEllipse9Widget()),
                                ),
                                Positioned(
                                  left: 28.0,
                                  top: 115,
                                  right: 28.0,
                                  bottom: -374.0,
                                  width: null,
                                  height: null,
                                  child: GeneratedRectangle5Widget(),
                                )
                              ]),
                        ))
                  ])),
            );
          }),
        )));
  }
}