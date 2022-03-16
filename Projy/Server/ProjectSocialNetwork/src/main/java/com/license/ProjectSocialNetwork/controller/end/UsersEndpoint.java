//package com.license.ProjectSocialNetwork.controller.end;
//
//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//@ServerEndpoint("/users")
//public class UsersEndpoint {
//
//    static List<UsersEndpoint> clients = new CopyOnWriteArrayList<UsersEndpoint>();
//    Session session;
//
//    @OnOpen
//    public void onOpen(Session session, EndpointConfig _){
//        this.session = session;
//        clients.add(this);
//    }
//
//    @OnClose
//    public void onClose(Session session, CloseReason closeReason){
//        System.out.println("socket closed: - " + closeReason.getReasonPhrase() );
//        clients.remove(this);
//    }
//
//    @OnMessage
//    public void onMessage(String message){
//        broadcast(message);
//    }
//
//    private void broadcast(String message){
//        for(UsersEndpoint client : clients){
//            try {
//                client.session.getBasicRemote().sendText(message);
//            }
//            catch (IOException e){
//                clients.remove(this);
//                try {
//                    client.session.close();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }
//    }
//}
