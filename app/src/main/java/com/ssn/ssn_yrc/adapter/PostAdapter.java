/*
 *     Copyright (C) 2017  Adithya J
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.ssn.ssn_yrc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ssn.ssnyrc.R;
import com.ssn.ssnyrc.activities.NotificationDetailActivity;
import com.ssn.ssnyrc.model.Post;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> postList;
    private Context context;

    public PostAdapter(Context context, List<Post> postList) {
        this.postList = postList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_post, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final Post post = postList.get(position);
        if (post == null) return;
        Date date = new Date(post.getDate());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aaa 'on' EEE, dd MMM");
        viewHolder.dateTV.setText(simpleDateFormat.format(date));
        viewHolder.titleTV.setText(post.getTitle());
        viewHolder.descriptionTV.setText(post.getDescription());
        viewHolder.postLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NotificationDetailActivity.class);
                intent.putExtra("post", new Gson().toJson(post));
                context.startActivity(intent);
            }
        });

        if (post.getFileURL().contains("jpg") || post.getFileURL().contains("jpeg") || post.getFileURL().contains("png")) {
            Glide.with(context)
                    .load(post.getFileURL())
                    .into(viewHolder.imageView);
            viewHolder.imageView.setVisibility(View.VISIBLE);
        } else {
            Glide.clear(viewHolder.imageView);
            viewHolder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout postLL;
        private TextView titleTV;
        private TextView dateTV;
        private TextView descriptionTV;
        private ImageView imageView;

        public ViewHolder(final View itemView) {
            super(itemView);

            postLL = (LinearLayout) itemView.findViewById(R.id.post_ll);
            titleTV = (TextView) itemView.findViewById(R.id.postTitle);
            dateTV = (TextView) itemView.findViewById(R.id.postDate);
            descriptionTV = (TextView) itemView.findViewById(R.id.postDescription);
            imageView = (ImageView) itemView.findViewById(R.id.post_imageView);
        }
    }
}