package com.chentong.myblog.model;

import java.util.ArrayList;
import java.util.List;

public class About {

    private List<String> tags = new ArrayList<>();
    private List<String> codes = new ArrayList<>();

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    @Override
    public String toString() {
        return "About{" +
                "tags=" + tags +
                ", codes=" + codes +
                '}';
    }
}
