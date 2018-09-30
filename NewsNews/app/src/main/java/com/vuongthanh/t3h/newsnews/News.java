package com.vuongthanh.t3h.newsnews;

public class News {

    private int id;
    private String title;
    private String description;
    private String image;
    private String pubDate;
    private String link;
    private String pathSaved;
    private String nameWeb;

    public String getNameWeb() {
        return nameWeb;
    }

    public void setNameWeb(String nameWeb) {
        this.nameWeb = nameWeb;
    }

    private boolean isSelected = false;

    public News() {

    }


    public News(int id, String title, String description, String image, String pubDate, String link) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.pubDate = pubDate;
        this.link = link;

    }

    public int getId() {
        return id;
    }

    public String getPathSaved() {
        return pathSaved;
    }

    public void setPathSaved(String pathSaved) {
        this.pathSaved = pathSaved;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
