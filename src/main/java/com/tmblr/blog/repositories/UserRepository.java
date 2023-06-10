package com.tmblr.blog.repositories;

import com.tmblr.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);

}
