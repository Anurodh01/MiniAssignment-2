package com.nagarro.miniassignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.miniassignment.entity.User;

@Repository
public interface UserRespository extends JpaRepository<User, Integer> {

}
