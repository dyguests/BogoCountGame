package com.fanhl.game.bogocount.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fanhl.game.bogocount.model.Card;
import com.fanhl.game.bogocount.widget.CardView;

import java.util.List;

/**
 * Created by fanhl on 15/4/7.
 */
public class BogoAdapter extends BaseAdapter {
    Context context;
    List<Card> list;

    public BogoAdapter(Context context, List<Card> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Card getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) convertView = new CardView(context);

        ((CardView) convertView).setData(list.get(position));

        return convertView;
    }

    public void setDirection(int type) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setType(type);
        }
    }
}
