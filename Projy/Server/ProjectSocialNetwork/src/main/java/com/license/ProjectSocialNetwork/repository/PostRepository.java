package com.license.ProjectSocialNetwork.repository;

import com.license.ProjectSocialNetwork.model.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends SocialRepository<Post, Long>{
}
