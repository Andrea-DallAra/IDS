package com.ids.progettoids.utils;

import com.ids.progettoids.models.Content;

public class ContentWithId {
    private Integer id;
    private Content content;

    public ContentWithId(Integer id, Content content) {
        this.id = id;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public Content getContent() {
        return content;
    }
}
