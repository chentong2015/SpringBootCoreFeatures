package com.chentong.myblog.query;

/**
 * 用于查询Blog的Query，所需要的3个查询的数据
 */
public class BlogQuery {

    private String title;
    private Long typeId;
    private Long tagId;
    private boolean isRecommend;
    private boolean isPublished;

    public String getTitle() { return title; }

    public Long getTagId() { return tagId; }

    public void setTagId(Long tagId) { this.tagId = tagId; }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return isRecommend;
    }

    public void setRecommend(boolean recommend) {
        this.isRecommend = recommend;
    }

    public boolean isPublished() { return isPublished; }

    public void setPublished(boolean published) { isPublished = published; }

    @Override
    public String toString() {
        return "BlogQuery{" +
                "title='" + title + '\'' +
                ", typeId=" + typeId +
                ", isRecommend=" + isRecommend +
                ", isPublished=" + isPublished +
                '}';
    }

}
