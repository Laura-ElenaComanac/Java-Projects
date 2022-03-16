package com.license.ProjectSocialNetwork.repository;

import com.license.ProjectSocialNetwork.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
}
