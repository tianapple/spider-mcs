package com.upotv.mcs.menu.entity;

import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;

/**
 * Created by wow on 2017/6/20.
 */
public class MenuVo {
    private int menuid;
    private int parentid;
    @NotEmpty(message="菜单名称不能为空")
    private String name;
    @NotEmpty(message="菜单地址不能为空")
    private String path;
    private String remark;
    @NotEmpty(message="菜单排序不能为空")
    private String priority;
    private int isEnable;
    private int isAdmin;
    private String updatetime;
    private String createtime;

    public int getMenuid() {
        return menuid;
    }

    public void setMenuid(int menuid) {
        this.menuid = menuid;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
