package com.license.ProjectSocialNetwork.services;

import com.license.ProjectSocialNetwork.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaServiceImpl implements MediaService{
    @Autowired
    private MediaRepository mediaRepository;
}
