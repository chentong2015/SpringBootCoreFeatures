package com.chentong.myblog.dao;

import com.chentong.myblog.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {

    // 定义的新的非默认的方法函数
    Type findByName(String name);

    // 根据Type下的博客的数量来排序选择 ==> 自定义的数据库的查询的方式
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);

}
