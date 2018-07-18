package com.newabel.plugin_uselib;

import com.newabel.plugin_library.IBean;

/**
 * Date: 2018/7/18 19:44
 * Description:
 */

public class Bean implements IBean {

    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
