package com.gjf.lovezzu.entity.school;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/9.
 */

public class TopNewsResult implements Serializable{
    private String imageUrl;
    private String newsUrl;
    private String  moduleIdentifier;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getModuleIdentifier() {
        return moduleIdentifier;
    }

    public void setModuleIdentifier(String moduleIdentifier) {
        this.moduleIdentifier = moduleIdentifier;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
