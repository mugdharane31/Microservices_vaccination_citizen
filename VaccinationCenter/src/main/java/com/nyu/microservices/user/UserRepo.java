package com.nyu.microservices.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nyu.microservices.user.Entity.UserBean;

@Repository
public interface UserRepo extends JpaRepository<UserBean, Integer>{

	List<UserBean> findByEmail(String email);
	long deleteByEmail(String email);
}
