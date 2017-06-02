package org.spider.mcs.main.domain;

import org.spider.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianapple on 2017/6/1.
 */
public class UserPermission {
    private String name;
    private String path;
    private String priv_list;

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

    public String getPriv_list() {
        return priv_list;
    }

    public void setPriv_list(String priv_list) {
        this.priv_list = priv_list;
    }

    public List<String> getPermissionList() {
        List<String> permissionList = new ArrayList<>();
        if (StringUtils.isNullOrEmpty(priv_list) || StringUtils.isNullOrEmpty(path)) {
            return permissionList;
        }

        String[] strs = priv_list.split(",");
        for (String str : strs) {
            permissionList.add(path + "/" + str);
        }

        return permissionList;
    }
}
