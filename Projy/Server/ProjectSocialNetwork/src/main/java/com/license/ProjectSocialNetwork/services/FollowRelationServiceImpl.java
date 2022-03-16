package com.license.ProjectSocialNetwork.services;

import com.license.ProjectSocialNetwork.repository.FollowRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowRelationServiceImpl implements FollowRelationService{
    @Autowired
    private FollowRelationRepository followRelationRepository;
}
