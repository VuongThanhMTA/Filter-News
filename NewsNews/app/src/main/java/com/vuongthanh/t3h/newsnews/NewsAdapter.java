package com.vuongthanh.t3h.newsnews;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.vuongthanh.t3h.newsnews.databinding.ItemNewsBinding;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> arrNews;
    private LayoutInflater inflater;
    private ItemViewActionCallBack callBack;
    private String tabView;

    public void setCallBack(ItemViewActionCallBack callBack) {
        this.callBack = callBack;
    }

    public NewsAdapter(ArrayList<News> arrNews, Context context, String tabView) {
        this.arrNews = arrNews;
        inflater = LayoutInflater.from(context);
        this.tabView = tabView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemNewsBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_news
                , viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.bindData(arrNews.get(i));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.onClick(i);
                }
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (callBack != null) {
                    callBack.onLongClick(i);
                }
                return true;
            }
        });

        viewHolder.binding.txvPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(i, viewHolder);
            }
        });
    }

    private void showPopup(final int position, ViewHolder viewHolder) {
        PopupMenu popupMenu = new PopupMenu(inflater.getContext(), viewHolder.binding.txvPopup);
        popupMenu.inflate(R.menu.menu_item);
        popupMenu.getMenu().findItem(R.id.save_menu).setVisible(false);
        popupMenu.getMenu().findItem(R.id.favorite_menu).setVisible(false);
        popupMenu.getMenu().findItem(R.id.delete_menu).setVisible(false);
        switch (tabView) {
            case NewsField._TABNEWS:
                popupMenu.getMenu().findItem(R.id.save_menu).setVisible(true);
                popupMenu.getMenu().findItem(R.id.favorite_menu).setVisible(true);
                popupMenu.getMenu().findItem(R.id.delete_menu).setVisible(true);
                break;
            case NewsField._TABSAVED:
                // popupMenu.getMenu().findItem(R.id.save_menu).setVisible(false);
                // popupMenu.getMenu().findItem(R.id.favorite_menu).setVisible(false);
                popupMenu.getMenu().findItem(R.id.delete_menu).setVisible(true);
                break;
            case NewsField._TABFAVORITE:
                popupMenu.getMenu().findItem(R.id.save_menu).setVisible(true);
                popupMenu.getMenu().findItem(R.id.favorite_menu).setVisible(false);
                popupMenu.getMenu().findItem(R.id.delete_menu).setVisible(true);
                break;
            default:
                break;
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.save_menu:
                        if (callBack != null) {
                            callBack.onClickPopupSave(position);
                        }
                        break;
                    case R.id.favorite_menu:
                        if (callBack != null) {
                            callBack.onClickPopupFavorite(position);
                        }
                        break;
                    case R.id.delete_menu:
                        if (callBack != null) {
                            callBack.onClickPopupDelete(position);
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();

    }


    @Override
    public int getItemCount() {
        return arrNews.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemNewsBinding binding;

        public ViewHolder(ItemNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindData(News news) {
            binding.tvTitle.setText(news.getTitle());
            binding.tvDescription.setText(news.getDescription());
            binding.tvPubdate.setText(news.getPubDate());
            binding.tvPage.setText(news.getNameWeb());
            Glide.with(itemView.getContext()).load(news.getImage()).into(binding.imgvNews);
            Log.d(TAG, "binddata" + news.getImage());
//            if (news.isSelected()) {
//                itemView.setBackgroundColor(Color.parseColor("#B0BEC5"));
//            } else {
//                itemView.setBackgroundColor(Color.WHITE);
//            }
        }
    }

    interface ItemViewActionCallBack {
        void onClick(int position);

        void onLongClick(int positon);

        void onClickPopupSave(int position);

        void onClickPopupFavorite(int position);

        void onClickPopupDelete(int position);
    }
}
