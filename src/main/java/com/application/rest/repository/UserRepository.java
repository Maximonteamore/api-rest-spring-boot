package com.application.rest.repository;

import com.application.rest.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo,Integer> {
    UserInfo findByUsername(String username);
}
