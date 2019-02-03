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

package com.ssn.ssn_yrc.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mzelzoghbi.zgallery.ZGallery;
import com.mzelzoghbi.zgallery.entities.ZColor;
import com.ssn.ssn_yrc.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailsActivity extends AppCompatActivity {

    ImageView eventIv1;
    ImageView eventIv2;
    ImageView eventIv3;
    TextView eventTv;

    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);

        name = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switch (name) {
            case "Blood Camp":
                eventIv1.setImageDrawable(getResources().getDrawable(R.drawable.bc1));
                eventIv2.setImageDrawable(getResources().getDrawable(R.drawable.bc2));
                eventIv3.setImageDrawable(getResources().getDrawable(R.drawable.bc3));

                eventTv.setText(getString(R.string.blood_camp));
                break;

            case "Eye Camp":
                eventIv1.setImageDrawable(getResources().getDrawable(R.drawable.ec1));
                eventIv2.setImageDrawable(getResources().getDrawable(R.drawable.ec2));
                eventIv3.setImageDrawable(getResources().getDrawable(R.drawable.ec3));

                eventTv.setText(getString(R.string.eye_camp));
                break;

            case "Orphanage Visit":
                eventIv1.setImageDrawable(getResources().getDrawable(R.drawable.o1));
                eventIv2.setImageDrawable(getResources().getDrawable(R.drawable.o2));
                eventIv3.setImageDrawable(getResources().getDrawable(R.drawable.o3));

                eventTv.setText(getString(R.string.orphanage));
                break;

            case "Village Camp":
                eventIv1.setImageDrawable(getResources().getDrawable(R.drawable.vc1));
                eventIv2.setImageDrawable(getResources().getDrawable(R.drawable.vc2));
                eventIv3.setImageDrawable(getResources().getDrawable(R.drawable.vc3));

                eventTv.setText(getString(R.string.village_camp));
                break;

            case "Hospital Cleaning":
                eventIv1.setImageDrawable(getResources().getDrawable(R.drawable.hc1));
                eventIv2.setImageDrawable(getResources().getDrawable(R.drawable.hc2));
                eventIv3.setImageDrawable(getResources().getDrawable(R.drawable.hc3));

                eventTv.setText(getString(R.string.hospital_cleaning));
                break;

            case "Beach Cleaning":
                eventIv1.setImageDrawable(getResources().getDrawable(R.drawable.bec1));
                eventIv2.setImageDrawable(getResources().getDrawable(R.drawable.bec2));
                eventIv3.setImageDrawable(getResources().getDrawable(R.drawable.bec3));

                eventTv.setText(getString(R.string.beach_cleaning));
                break;

            case "Campus Cleaning":
                eventIv1.setImageDrawable(getResources().getDrawable(R.drawable.cc1));
                eventIv2.setImageDrawable(getResources().getDrawable(R.drawable.cc2));
                eventIv3.setImageDrawable(getResources().getDrawable(R.drawable.cc3));

                eventTv.setText(getString(R.string.campus_cleaning));
                break;

            case "Stem Cell":
                eventIv1.setImageDrawable(getResources().getDrawable(R.drawable.sc1));
                eventIv2.setImageDrawable(getResources().getDrawable(R.drawable.sc2));
                eventIv3.setImageDrawable(getResources().getDrawable(R.drawable.sc3));

                eventTv.setText(getString(R.string.stem_cell));
                break;

            case "March Past":
                eventIv1.setImageDrawable(getResources().getDrawable(R.drawable.mp1));
                eventIv2.setImageDrawable(getResources().getDrawable(R.drawable.mp2));
                eventIv3.setImageDrawable(getResources().getDrawable(R.drawable.mp3));

                eventTv.setText(getString(R.string.march_past));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void galleryOnClick(View view) {
        ArrayList<String> imagesList = new ArrayList<>();

        switch (name) {
            case "Blood Camp":
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/bc1.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/bc2.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/bc3.jpg");
                break;

            case "Eye Camp":
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/ec1.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/ec2.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/ec3.jpg");
                break;

            case "Orphanage Visit":
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/o1.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/o2.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/o3.jpg");
                break;

            case "Village Camp":
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/vc1.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/vc2.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/vc3.jpg");
                break;

            case "Hospital Cleaning":
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/hc1.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/hc2.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/hc3.jpg");
                break;

            case "Beach Cleaning":
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/bec1.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/bec2.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/bec3.jpg");
                break;

            case "Campus Cleaning":
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/cc1.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/cc2.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/cc3.jpg");
                break;

            case "Stem Cell":
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/sc1.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/sc2.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/sc3.jpg");
                break;

            case "March Past":
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/mp1.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/mp2.jpg");
                imagesList.add("https://ssnyrc-c058c.firebaseapp.com/img/drawable-nodpi/mp3.jpg");
                break;
        }

        ZGallery.with(this, imagesList)
                .setToolbarTitleColor(ZColor.WHITE)
                .setGalleryBackgroundColor(ZColor.WHITE)
                .setToolbarColorResId(R.color.colorPrimary)
                .setTitle(getString(R.string.app_name))
                .show();
    }
}
