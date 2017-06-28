package org.spider.mcs.main.entity;

import org.spider.util.StringUtils;

import java.util.List;

/**
 * 前端显示菜单
 *
 * Created by tianapple on 2017/6/7.
 */
public class TreeData {
    private int id;
    private String text;
    private String state = "closed";
    private TreeAttribute attributes;
    private List<TreeData> children;

    public static TreeData parse(Mcs_menu mcsMenu) {
        TreeData data = new TreeData();
        data.setId(mcsMenu.getMenuId());
        data.setText(mcsMenu.getName());
        if (!StringUtils.isNullOrEmpty(mcsMenu.getPath())) {
            TreeAttribute attribute = new TreeAttribute();
            attribute.setUrl(mcsMenu.getPath());
            data.setAttributes(attribute);
        }
        return data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public TreeAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(TreeAttribute attributes) {
        this.attributes = attributes;
    }

    public List<TreeData> getChildren() {
        return children;
    }

    public void setChildren(List<TreeData> children) {
        this.children = children;
    }
}
