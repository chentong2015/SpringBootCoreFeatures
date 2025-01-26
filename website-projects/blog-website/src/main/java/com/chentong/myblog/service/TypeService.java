package com.chentong.myblog.service;

import com.chentong.myblog.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 服务层：定义接口的方法，用于操作数据库中链接的Type表格
 */
public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    // 获取前top的类型
    List<Type> listTypeTop(Integer size);

    //使用Type类型进行更新的操作
    Type updateType(Long id, Type type);

    void deleteType(Long id);

}
