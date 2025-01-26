package com.chentong.myblog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chentong.myblog.model.User;

/**
 * 操作User Table表格的接口 **********
 *
 * JpaRepository<User, Long> 操作的对象，和主键的类型 JPA是Java Persistence API的简称:
 * Java持久层API DAO层：使用JPA直接操作数据库，增删改查 注意命名的规则 ： find update remove & delete ....
 */
public interface UserRepository extends JpaRepository<User, Long> {

	// 直接查询数据库中的数据; 并返回一条数据对象
	User findByUsernameAndPassword(String username, String password);

	// 自动关联到数据库的查询
	User findByUsername(String username);
}
