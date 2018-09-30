package com.vuongthanh.t3h.newsnews;

public interface NewsField {
    String DB_NAME = "news manager";
    int DB_VERSION = 1;
    String TB_NAME = "news";
    String _ID = "id";
    String _TITLE = "title";
    String _LINK = "link";
    String _DESCRIPTION = "description";
    String _IMAGE = "image";
    String _DATE = "date";
    String _TYPE = "type";

    String _TABSAVED = "tab saved";
    String _TABNEWS = "tab news";
    String _TABFAVORITE = "tab favorite";
    String _SAVE_TYPE = "saved";
    String _FAVORITE_TYPE = "favorite";
}
