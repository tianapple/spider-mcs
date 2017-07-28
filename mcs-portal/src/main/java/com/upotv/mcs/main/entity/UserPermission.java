package com.upotv.mcs.main.entity;

import org.spider.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *  用户所对应的授权信息
 *
 * Created by tianapple on 2017/6/1.
 */
public class UserPermission {
    private String name;
    private String path;
    private String priv;

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

    public String getPriv() {
        return priv;
    }

    public void setPriv(String priv) {
        this.priv = priv;
    }

    public List<String> getPermissionList() {
        List<String> permissionList = new ArrayList<>();
        if (StringUtils.isNullOrEmpty(priv) || StringUtils.isNullOrEmpty(path)) {
            return permissionList;
        }
        permissionList.add(path + "/" + priv);

        return permissionList;
    }
}
