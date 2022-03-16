package com.license.ProjectSocialNetwork.repository;

import com.license.ProjectSocialNetwork.model.Message;
import com.license.ProjectSocialNetwork.model.MessageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, MessageId> {
}
