package com.authservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authservices.modal.User;

public interface UserRepository extends JpaRepository<User , Integer>{

	Optional<User> findByUserName(String username);

}
