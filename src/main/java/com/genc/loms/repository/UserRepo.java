package com.genc.loms.repository;

import com.genc.loms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
  
	  User findByUserName(String userName);
}
