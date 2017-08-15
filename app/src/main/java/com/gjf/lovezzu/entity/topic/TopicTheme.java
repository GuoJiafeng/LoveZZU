package com.gjf.lovezzu.entity.topic;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/12.
 */

public class TopicTheme implements Serializable {
    private Integer themeId;
    private String themeImg;
    private String themeTitle;
    private Integer topicCount;

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public String getThemeImg() {
        return themeImg;
    }

    public void setThemeImg(String themeImg) {
        this.themeImg = themeImg;
    }

    public String getThemeTitle() {
        return themeTitle;
    }

    public void setThemeTitle(String themeTitle) {
        this.themeTitle = themeTitle;
    }

    public Integer getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }
}
