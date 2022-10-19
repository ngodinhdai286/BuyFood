package hcmute.group6.buyfood.entity;

import java.io.Serializable;

public class NotificationEntity implements Serializable {
    private Integer id;
    private String email;
    private String title;
    private String content;
    private Boolean isNew;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean aNew) {
        isNew = aNew;
    }
}
