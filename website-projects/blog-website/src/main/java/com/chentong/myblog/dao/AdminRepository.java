package com.chentong.myblog.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.chentong.myblog.model.Blog;

import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;

/**
 * 需要传递对象和table表格的主键的类型
 * JpaSpecificationExecutor 用于组合之间的查询功能
 */

/**
 * Java 中通过接口实现多重继承的功能
 * java一个类只能继承extends一个类(class)，但是可以实现implements多个接口(interface);
 * 一个接口(interface)能够继承extends多个接口(interface)
 */
public interface AdminRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

	/**
	 * 在接口中可以定义变量和方法
	 */
	// Get all the blog to recommend
	@Query("select b from Blog b where b.recommend = true")
	List<Blog> findTop(Pageable pageable);

	/**
	 * 自定义的查询语句，通过传入的参数位置进行like查询语句 select * from Blog where title like '%string'
	 */
	@Query("select b from Blog b where b.title like ?1 or b.content like ?1")
	Page<Blog> findBySearch(String query, Pageable pageable);

	/**
	 * 数据库表中一个字段的更新Modifying，适合自定义的SQL语句 ==> 必须加入事务里面更新 Transactional
	 */
	@Transactional
	@Modifying
	@Query("update Blog b set b.views = b.views + 1 where b.id = ?1")
	int updateViews(Long id);

	/**
	 * 自定义的按照年份查询的sql语句 : 根据年份进行分组List
	 */
	@Query("select function('date_format', b.createTime, '%Y') as year from Blog b group by function('date_format', b.createTime, '%Y') order by year desc")
	List<String> findGroupByYear();

	/**
	 * 自定义根据年份来查询到相应的博客数据
	 */
	@Query("select b from Blog b where function('date_format', b.updateTime, '%Y') = ?1 ")
	List<Blog> findByYear(String year);

}
