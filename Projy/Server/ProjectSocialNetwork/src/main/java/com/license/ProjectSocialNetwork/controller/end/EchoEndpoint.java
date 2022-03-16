//package com.license.ProjectSocialNetwork.controller;
//
//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//
//@ServerEndpoint(value = "/users")
//public class EchoEndpoint {
//    @OnOpen
//    public void onOpen(Session session, EndpointConfig endpointConfig){
//        RemoteEndpoint.Basic remoteEndpoint = session.getBasicRemote();
//        session.addMessageHandler(new EchoMessageHandler(remoteEndpoint));
//    }
//
//    private static class EchoMessageHandler implements MessageHandler.Whole<String>{
//        private final RemoteEndpoint.Basic remoteEndpoint;
//
//        public EchoMessageHandler(RemoteEndpoint.Basic remoteEndpoint) {
//            this.remoteEndpoint = remoteEndpoint;
//        }
//
//        @Override
//        public void onMessage(String message) {
//            try{
//                if(remoteEndpoint != null){
//                    remoteEndpoint.sendText(message);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
