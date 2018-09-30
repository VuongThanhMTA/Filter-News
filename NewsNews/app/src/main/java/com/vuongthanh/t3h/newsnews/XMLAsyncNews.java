package com.vuongthanh.t3h.newsnews;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLAsyncNews extends AsyncTask<String,Void,ArrayList<News>> {
    // k cần update nên k gọi onProgress
    private XMLParserCallBack callBack;
    private Context mContext;
    private ProgressDialog progressDialog;

    public XMLAsyncNews(Context context, XMLParserCallBack callBack) {
        this.callBack = callBack;
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Searching");
        progressDialog.setMessage("loading");
        progressDialog.show();
    }

    @Override
    protected ArrayList<News> doInBackground(String... strings) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLParserNews xmlParser = new XMLParserNews();
            String link = strings[0];// url trỏ đến xml trên internet
            parser.parse(link,xmlParser);// parser dữ liệu trên link chuyển thành object theo XMLparser mk đã tạo
            return xmlParser.getLsNews();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        // cho nay no bat cai exption o tren nen return null.
        // chung to link chua chinh xac roi
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<News> news) {
        super.onPostExecute(news);
        callBack.onParseResult(news);
        progressDialog.dismiss();
    }


    public interface XMLParserCallBack{
        void onParseResult(ArrayList<News> lsNews);

    }
}
