package com.chentong.myblog.dao;

import com.chentong.myblog.model.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository  extends JpaRepository<Tag, Long> {

    // 定义的新的非默认的方法函数
    Tag findByName(String name);

    // 根据Tag下的博客的数量来排序选择
    // 自定义的数据库的查询的方式 !!!!
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);

}
