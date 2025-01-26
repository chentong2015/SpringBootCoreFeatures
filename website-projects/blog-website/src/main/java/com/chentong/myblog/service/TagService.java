package com.chentong.myblog.service;

import com.chentong.myblog.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag(String ids);

    List<Tag> listTag();

    List<Tag> listTagTop(Integer size);

    //使用Type类型进行更新的操作
    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);
}
