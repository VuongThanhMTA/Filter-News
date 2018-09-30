package com.vuongthanh.t3h.newsnews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vuongthanh.t3h.newsnews.databinding.UiNewsBinding;
import com.vuongthanh.t3h.newsnews.databinding.UiSavedBinding;

import java.util.ArrayList;

public class FragmentSaved extends Fragment implements NewsAdapter.ItemViewActionCallBack {

    private ArrayList<News> arrNews;
    private NewsAdapter adapter;
    private static FragmentSaved instance;
    private UiSavedBinding binding;
    private int currentSelected = -1;
    private String KEY_LINK = "Link";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = UiSavedBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    public static FragmentSaved getInstance() {
        if (instance == null) {
            instance = new FragmentSaved();
        }
        return instance;
    }

    private void initView() {
        NewsDAO newsDAO = new NewsDAO(getActivity());
        arrNews = newsDAO.getData(NewsField._SAVE_TYPE);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerSaved.setLayoutManager(manager);
        adapter = new NewsAdapter(arrNews, getActivity(), NewsField._TABSAVED);
        adapter.setCallBack(this);
        binding.recyclerSaved.setAdapter(adapter);
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

//        Intent intent = new Intent(getActivity(), WebViewActivity.class);
//        intent.putExtra(KEY_LINK, arrNews.get(position).getLink());
//        startActivity(intent);
        String link = arrNews.get(position).getLink();
        String pathFile = "file://" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/App News Download/" + link.substring(link.lastIndexOf('/') + 1, link.length());
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("Link", pathFile);
        startActivity(intent);
    }

    @Override
    public void onLongClick(int positon) {

    }

    @Override
    public void onClickPopupSave(int position) {

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

    public void addNewsSave(News news){
        arrNews.add(news);
        adapter.notifyDataSetChanged();
    }
}
