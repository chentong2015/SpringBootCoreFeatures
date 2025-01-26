package com.chentong.myblog.service;

import com.chentong.myblog.dao.TypeRepository;
import com.chentong.myblog.handler.NotFoundException;
import com.chentong.myblog.model.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 使用Repository中自带的函数执行数据的增删改查功能
 */
@Service
public class TypeServiceImpl implements TypeService {

    /**
     * 使用typeRepository类的对象来实现TypeService接口中的方法
     * TypeRepository 中含有默认的数据表的调用和处理的方法 save & get & getAll
     */
    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) { return typeRepository.findByName(name); }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "listBlogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return typeRepository.findTop(pageable);
    }

    @Transactional // 自定义的函数
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.getOne(id); //返回保存的Type对象
        if(t == null) {
            throw new NotFoundException("Not exist ID of this Type");
        }
        // 对象之间的复制函数 !!!
        BeanUtils.copyProperties(type, t);
        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) { typeRepository.deleteById(id); }
}
