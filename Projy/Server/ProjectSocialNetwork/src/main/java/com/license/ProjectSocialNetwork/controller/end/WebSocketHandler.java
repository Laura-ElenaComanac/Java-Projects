//package com.license.ProjectSocialNetwork.controller;
//
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.AbstractWebSocketHandler;
//
//public class WebSocketHandler extends AbstractWebSocketHandler {
//
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        System.out.println("New Simple Message Received");
//        session.sendMessage(message);
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        System.out.println("New Text Message Received "+message.getPayload());
//        session.sendMessage(new TextMessage("got this for you: "+message.getPayload()));
//    }
//}