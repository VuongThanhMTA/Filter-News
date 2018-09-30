package com.vuongthanh.t3h.newsnews;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class XMLParserNews extends DefaultHandler {
    private static final String TAG = "XMLParser";
    private ArrayList<News> lsNews = new ArrayList<>();// mảng chúa tin ta par về
    private News news;
    private String value;

    //tất cả các tiens trình thực hiện lâu phải làm trên thread
    // liên quan internet phải dùng thread

    //qName = tag thẻ đang đọc
    // đọc thẻ mở
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals(XMLTag.ITEM)) {// đọc vào thẻ mở item
            news = new News();
        }
        value = "";
    }


    //đọc nội dung
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        // nội dung thẻ đọc đến
        value += new String(ch, start, length);// nối nội dung hiện tại với noi dung đọc đc
    }

    // đọc thẻ đóng
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        // kiểm tra xem pars chưa, chưa vào thẻ mở
        if (news == null) return;
        switch (qName) {
            case XMLTag.TITLE:
                news.setTitle(value);
                Log.e(TAG, "Title: " + value);
                // String nameWeb = value.substring(value.lastIndexOf('-'), value.length());
                //  news.setNameWeb(nameWeb);
                break;

            case XMLTag.LINK:
                if (news.getTitle().equals("This RSS feed URL is deprecated")) {
                    //  return;
                } else {
                    String url = "url=";
                    int iUrl = value.indexOf(url) + url.length();
                    String link = value.substring(iUrl);
                    news.setLink(link);
                }
                break;

            case XMLTag.DESCRIPTION:
                if (news.getTitle().equals("This RSS feed URL is deprecated") || value.equals("Google Tin tức")) {
                    // return;
                } else {
                    String src = "src=";
                    int iSrc = value.indexOf(src) + src.length() + 1;
                    value = value.substring(iSrc);
                    String img = value.substring(0, value.indexOf("alt=") - 2);
                    String imgLink = "https:" + img;
                    news.setImage(imgLink);
                    Log.d(TAG, "endElement: image link: " + imgLink);
                    // String des = value.substring(value.indexOf("</a><br>"));
                    news.setDescription(news.getTitle());

                }
                break;

            case XMLTag.PUB_DATE:
                news.setPubDate(value);
                break;

            case XMLTag.ITEM:
                if (news.getTitle().equals("This RSS feed URL is deprecated")) {
                    //   return;
                } else {
                    String nameWeb = news.getTitle().substring(news.getTitle().lastIndexOf('-')+2, news.getTitle().length());
                    news.setNameWeb(nameWeb);
                    lsNews.add(news);
                }
                break;
        }
    }

    public ArrayList<News> getLsNews() {
        return lsNews;
    }
}

