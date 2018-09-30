package com.vuongthanh.t3h.newsnews;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vuongthanh.t3h.newsnews.databinding.UiNewsBinding;

import java.util.ArrayList;

public class FragmentNews extends Fragment implements XMLAsyncNews.XMLParserCallBack, NewsAdapter.ItemViewActionCallBack {
    private static FragmentNews instance;
    private UiNewsBinding binding;
    private NewsAdapter adapter;
    private ArrayList<News> arrNews = new ArrayList<>();
    private int currentSelected = -1;
    private String KEY_LINK = "Link";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = UiNewsBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();

    }

    public static FragmentNews getInstance() {
        if (instance == null) {
            instance = new FragmentNews();
        }
        return instance;
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerNews.setLayoutManager(manager);
        adapter = new NewsAdapter(arrNews, getActivity(), NewsField._TABNEWS);
        adapter.setCallBack(this);
        binding.recyclerNews.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
//        if (currentSelected != -1) {
//            arrNews.get(currentSelected).setSelected(false);
//            adapter.notifyItemChanged(currentSelected);
//        }
//
//        arrNews.get(position).setSelected(true);
//        adapter.notifyItemChanged(position);
//        currentSelected = position;

        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(KEY_LINK, arrNews.get(position).getLink());
        startActivity(intent);
    }

    @Override
    public void onLongClick(int position) {

    }

    @Override
    public void onClickPopupSave(int position) {
        ShowDialogDownload(position);
        FragmentSaved.getInstance().addNewsSave(arrNews.get(position));
    }

    @Override
    public void onClickPopupFavorite(int position) {
        NewsDAO newsDAO = new NewsDAO(getActivity());
        newsDAO.insertNews(arrNews.get(position), NewsField._FAVORITE_TYPE);
        FragmentFavorite.getInstance().addNewsFavorite(arrNews.get(position));
        Toast.makeText(getContext(), "Favorite!", Toast.LENGTH_LONG).show();;
    }

    @Override
    public void onClickPopupDelete(int position) {
        arrNews.remove(position);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onParseResult(ArrayList<News> lsNews) {
        this.arrNews.addAll(lsNews);
        adapter.notifyDataSetChanged();
    }

    public void SearchNews(String keySearch) {
        // arrNews.clear();
        XMLAsyncNews asyncNews = new XMLAsyncNews(getActivity(), this);
        String link = "https://news.google.de/news/feeds?pz=1&cf=vi_vn&ned=vi_vn&hl=vi_vn&q=" + keySearch;
        asyncNews.execute(link); // xử lý trên thread khác
    }

    private void ShowDialogDownload(final int positon) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Download file");
        builder1.setCancelable(true);

        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                NewsDAO newsDAO = new NewsDAO(getActivity());
                newsDAO.insertNews(arrNews.get(positon), NewsField._SAVE_TYPE);
                DownloadNewsAsync downloadNewsAsync = new DownloadNewsAsync(getActivity());
                downloadNewsAsync.execute(arrNews.get(positon).getLink());
                dialog.dismiss();
                Toast.makeText(getContext(), " News Saved !", Toast.LENGTH_LONG).show();
            }
        });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
