package com.chentong.myblog.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity // 与数据库 对应生成的能力
@Table(name ="t_blog") // 表名称
public class Blog {

    @Id
    @GeneratedValue //选择自动生成
    private Long id; // long 类型不行。因为它是值类型。它的原型可以。因为它是对象类型。Long l = null;
    private String title;
    private String description; // 副标题的具体描述内容

    @Basic(fetch = FetchType.LAZY) //LongText大字段的类型
    @Lob
    private String content; // 保存博客的整篇的内容的长度一定要够 varchar(255)
    private String firstpicture;
    private String flag;
    private Integer views; // 浏览的次数
    private Integer good; // 点赞的次数

    // 数据库的读写规则，对于布尔类型的变量，要做前端的判断，则必须使用小写的变量符号 !!!!!!
    private boolean appreciation; // 是否允许赞赏功能
    private boolean shared; // 转载声明的一个区域
    private boolean comment; // 是否允许留言
    private boolean published; // 判断是草稿还是已经发布了 1 = true
    private boolean recommend; // 是否推荐文章

    // 表示处理时间数据
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    // TODO. 标记表之间的关联和外键的一些信息 !!
    @ManyToOne
    private User user;

    // TODO. 设置外键的(列)的名称，同时设定ForeignKey的特定值 !!
    @ManyToOne
    @JoinColumn(name = "type_id", foreignKey = @ForeignKey(name = "blog_type_pk"))
    private Type type; // 只有唯一的值

    // 级联新增：新增Blog是如果有新增加的Tag也会加到Tag的表中
    // CascadeType.PERSIST 级联的类型，表示在持久化数据时使用级联 !!
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> listTags = new ArrayList<>();

    // 不会进入数据库的属性值，只是存在于类中本身，ORM框架将会忽略该属性
    @Transient
    private String tagIds;  // 必须于前端的name的值一致 name="tagIds" (1,2,3,4)

    @OneToMany(mappedBy = "blog") // 始终在OneToMany的一段建立主动维护的关系
    private List<Comment> listComments = new ArrayList<>();

    // 类的构造方法; 如果不自定义含参的构造方法
    // 编译器会自动创建一个不带参数的构造方法。反之则没有该方法的出现
    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstpicture() {
        return firstpicture;
    }

    public void setFirstpicture(String firstpicture) {
        this.firstpicture = firstpicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getGood() { return good; }

    public void setGood(Integer good) { this.good = good; }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getListTags() {
        return listTags;
    }

    public void setListTags(List<Tag> listtags) {
        this.listTags = listtags;
    }

    public String getTagIds() {
        return tagIds;
    }

    // 从数据库中取的单独的id之后生成对应的 字符串, 赋值到对象的属性中
    public void init(){
        this.tagIds = tagsToIds(this.getListTags());
    }

    // 设置生成 1, 2, 3
    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public List<Comment> getListComments() {
        return listComments;
    }

    public void setListComments(List<Comment> listcomments) {
        this.listComments = listcomments;
    }

    @Override
    public String toString() {
        return "Blog{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", content='" + content + '\'' +
            ", firstpicture='" + firstpicture + '\'' +
            ", flag='" + flag + '\'' +
            ", views=" + views +
            ", appreciation=" + appreciation +
            ", shared=" + shared +
            ", comment=" + comment +
            ", published=" + published +
            ", recommend=" + recommend +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", user=" + user +
            ", type=" + type +
            ", listTags=" + listTags +
            ", tagIds='" + tagIds + '\'' +
            ", listComments=" + listComments +
            '}';
    }
}
