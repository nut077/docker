package com.github.nut077.docker.repository;

import com.github.nut077.docker.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
}
