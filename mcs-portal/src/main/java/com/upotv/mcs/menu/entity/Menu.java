package com.upotv.mcs.menu.entity;

import java.sql.Timestamp;

/**
 * Created by wow on 2017/6/20.
 */
public class Menu {
    private int menuid;
    private int parentid;
    private String name;
    private String path;
    private String remark;
    private String priority;
    private int isEnable;
    private String isEnableName;
    private int isAdmin;
    private String updatetime;
    private String createtime;
    private String priv_id;
    private String priv_name;

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

    public String getIsEnableName() {
        return isEnableName;
    }

    public void setIsEnableName(String isEnableName) {
        this.isEnableName = isEnableName;
    }

    public String getPriv_id() {
        return priv_id;
    }

    public void setPriv_id(String priv_id) {
        this.priv_id = priv_id;
    }

    public String getPriv_name() {
        return priv_name;
    }

    public void setPriv_name(String priv_name) {
        this.priv_name = priv_name;
    }
}
