package com.reddyz.newz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by reddy on 20-Feb-17.
 */

public class NewsListAdapter extends ArrayAdapter<NewsData> {

    public NewsListAdapter(Context context, int resource, List<NewsData> newsDataList) {
        super(context, resource, newsDataList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }

        NewsData currentItem = getItem(position);

        TextView titleText = (TextView) listItemView.findViewById(R.id.list_item_title);
        titleText.setText(currentItem.getTitle());

        TextView authorText = (TextView) listItemView.findViewById(R.id.list_item_author);
        authorText.setText(currentItem.getAuthor());

        String dateAndTimeString = currentItem.getDateTime(); // Format : "2017-02-19T14:07:56Z"
        String dateTime [] = dateAndTimeString.split("T");
        TextView dateText = (TextView) listItemView.findViewById(R.id.list_item_date);
        dateText.setText(dateTime[0]);

        if(dateTime.length > 1) {
            TextView timeText = (TextView) listItemView.findViewById(R.id.list_item_time);
            timeText.setText(dateTime[1]);
        }

        return listItemView;
    }
}
