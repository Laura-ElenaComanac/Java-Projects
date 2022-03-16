package com.license.ProjectSocialNetwork.services;

import com.license.ProjectSocialNetwork.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;
}
