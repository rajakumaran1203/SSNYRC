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
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssn.ssnyrc.R;
import com.ssn.ssnyrc.model.User;

import java.util.List;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.ViewHolder> {

    private List<User> donorList;
    private Context context;

    public DonorAdapter(Context context, List<User> donorList) {
        this.donorList = donorList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(context).inflate(R.layout.item_donor, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final User user = donorList.get(position);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + user.getPhone())));
            }
        });
        viewHolder.numberTV.setText(user.getPhone());
    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView numberTV;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.contact_layout);
            numberTV = (TextView) itemView.findViewById(R.id.number_tv);
        }
    }
}