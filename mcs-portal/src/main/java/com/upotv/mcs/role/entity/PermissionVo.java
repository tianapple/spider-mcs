package com.upotv.mcs.role.entity;

/**
 * Created by wangyunpeng on 2017/7/25.
 */
public class PermissionVo {
    private int menu_id;
    private String menu_ids;
    private int role_id;
    private String priv;
    private int is_half;

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_ids() {
        return menu_ids;
    }

    public void setMenu_ids(String menu_ids) {
        this.menu_ids = menu_ids;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getPriv() {
        return priv;
    }

    public void setPriv(String priv) {
        this.priv = priv;
    }

    public int getIs_half() {
        return is_half;
    }

    public void setIs_half(int is_half) {
        this.is_half = is_half;
    }
}
