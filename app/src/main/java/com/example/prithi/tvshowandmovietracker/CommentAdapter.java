package com.example.prithi.tvshowandmovietracker;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.List;

public class CommentAdapter extends ArrayAdapter {

    private Context mContext;
    private List<Comment> mComments;

    public CommentAdapter(Context context, List<Comment> objects){
        super(context, R.layout.task_row_item, objects);
        this.mContext = context;
        this.mComments = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
            convertView = mLayoutInflater.inflate(R.layout.task_row_item, null);

        }

        Comment comment = mComments.get(position);
        TextView commentView = (TextView) convertView.findViewById(R.id.comment_description);
        commentView.setText(comment.getComment() + " - " + comment.getUsername());

        String s = ParseUser.getCurrentUser().getUsername();



        return convertView;
    }
}
