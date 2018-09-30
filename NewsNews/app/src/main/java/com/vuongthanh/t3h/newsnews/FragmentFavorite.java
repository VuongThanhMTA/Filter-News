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

import com.vuongthanh.t3h.newsnews.databinding.UiFavoriteBinding;
import com.vuongthanh.t3h.newsnews.databinding.UiNewsBinding;
import com.vuongthanh.t3h.newsnews.databinding.UiSavedBinding;

import java.util.ArrayList;

public class FragmentFavorite extends Fragment implements NewsAdapter.ItemViewActionCallBack {

    private ArrayList<News> arrNews;
    private NewsAdapter adapter;
    private int currentSelected = -1;
    private String KEY_LINK = "Link";

    private static FragmentFavorite instance;
    private UiFavoriteBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = UiFavoriteBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    public static FragmentFavorite getInstance() {
        if (instance == null) {
            instance = new FragmentFavorite();
        }
        return instance;
    }

    private void initView() {
        NewsDAO newsDAO = new NewsDAO(getActivity());
        arrNews = newsDAO.getData(NewsField._FAVORITE_TYPE);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerFavorite.setLayoutManager(manager);
        adapter = new NewsAdapter(arrNews, getActivity(), NewsField._TABSAVED);
        adapter.setCallBack(this);
        binding.recyclerFavorite.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(KEY_LINK, arrNews.get(position).getLink());
        startActivity(intent);
    }

    @Override
    public void onLongClick(int positon) {

    }

    @Override
    public void onClickPopupSave(int position) {
        ShowDialogDownload(position);
        FragmentSaved.getInstance().addNewsSave(arrNews.get(position));

    }

    @Override
    public void onClickPopupFavorite(int position) {

    }

    @Override
    public void onClickPopupDelete(int position) {
        NewsDAO newsDAO = new NewsDAO(getActivity());
        newsDAO.deleteNews(arrNews.get(position));
        arrNews.remove(position);
        adapter.notifyItemChanged(position);
        Toast.makeText(getContext(), "Delete!", Toast.LENGTH_LONG).show();
    }

    public void addNewsFavorite(News news) {
        arrNews.add(news);
        adapter.notifyDataSetChanged();
    }

    private void ShowDialogDownload(final int positon) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Download File");
        builder1.setCancelable(true);

        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                NewsDAO newsDAO = new NewsDAO(getActivity());
                newsDAO.insertNews(arrNews.get(positon), NewsField._SAVE_TYPE);
                DownloadNewsAsync downloadNewsAsync = new DownloadNewsAsync(getActivity());
                downloadNewsAsync.execute(arrNews.get(positon).getLink());
                dialog.dismiss();
                Toast.makeText(getContext(), "Saved Favorite!", Toast.LENGTH_LONG).show();
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
