package com.vuongthanh.t3h.newsnews;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadNewsAsync extends AsyncTask<String, Integer, String> {
    private static final String FOLDER_NAME = "App News DownLoad";
    private Context context;
    private String file;
    File folder;
    private String TAG = "DownloadNewsAsync dowloadFile ";

    public DownloadNewsAsync(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        folder = new File(Environment.getExternalStorageDirectory(), FOLDER_NAME);
        if (!folder.exists()) {
            folder.mkdirs();
        }

    }

    @Override
    protected String doInBackground(String... strings) {
        dowloadFile(strings[0]);

        return null;
    }

    public void dowloadFile(String link) {
        try {
            String state;
            state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
//                File root = Environment.getExternalStorageDirectory();
//                File dir = new File(root.getAbsolutePath() + "/DownloadLink");
//                if (!dir.exists()) {
//                    dir.mkdir();
//                }
                String fileName = link.substring(link.lastIndexOf('/') + 1, link.length());
                File file = new File(folder, fileName);
                //  Toast.makeText(context, "da tạo file", Toast.LENGTH_LONG).show();
                Log.i(TAG,"crate file");

                URL url = new URL(link);
                URLConnection connection = url.openConnection();
                connection.connect();
                FileOutputStream outputStream = new FileOutputStream(file);
                InputStream inputStream = connection.getInputStream();
                Log.i(TAG,"inputstream link");
                byte[] b = new byte[1024];
                int count = inputStream.read();
                Log.i(TAG,"inputstream read");
//                long dowloaded = 0;
//                long total = 0;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                    total = connection.getContentLengthLong();
//                }
                while (count != -1) {
                    outputStream.write(b, 0, count);
                    count = inputStream.read(b);
                }
                inputStream.close();
                outputStream.close();
                Log.i(TAG,"inputstream close");
              //  Toast.makeText(context, "Saved file!", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context, "Storage not found!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

