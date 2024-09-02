package com.villege.entity.tree;

import lombok.Data;

/**
 * @author Lewis 31343080@qq.com
 */
@Data
public class Meta {
    private String title;
    private String icon;

    public Meta(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public Meta(String title) {
        this.title = title;
    }
}
