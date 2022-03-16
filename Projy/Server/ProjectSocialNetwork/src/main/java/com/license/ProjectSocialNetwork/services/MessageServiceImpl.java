package com.license.ProjectSocialNetwork.services;

import com.license.ProjectSocialNetwork.model.Message;
import com.license.ProjectSocialNetwork.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageRepository messageRepository;
}
