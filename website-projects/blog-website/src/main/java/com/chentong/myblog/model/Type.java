package com.chentong.myblog.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity // 与数据库 对应生成的能力
@Table(name ="t_type") // 表名称
public class Type {

    @Id
    @GeneratedValue //选择自动生成主键
    private long id;

    //后端对存入的数据进行验证; 在前端types-edit使用该错误的信息
    @NotBlank(message = "存入的类型名称不能为空")
    private String name;

    // 使用Blog中type属性, 建立类联系和维护关系 ==> 被维护端
    @OneToMany(mappedBy = "type")
    private List<Blog> listBlogs = new ArrayList<>();

    public Type() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getListBlogs() {
        return listBlogs;
    }

    public void setListBlogs(List<Blog> listBlogs) {
        this.listBlogs = listBlogs;
    }

    @Override
    public String toString() {
        return "Type{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
