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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ssn.ssn_yrc.R;
import com.ssn.ssn_yrc.adapter.DonorAdapter;
import com.ssn.ssn_yrc.model.User;
import com.ssn.ssn_yrc.ui.LabelledSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BloodDonorsActivity extends AppCompatActivity {

    RecyclerView donorsRv;
    LabelledSpinner bloodGroupSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blood_donors);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Blood Donors");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference nodesRef = database.getReference("users");
        final List<User> donorList = new ArrayList<>();
        final List<User> tempDonorList = new ArrayList<>();

        final DonorAdapter donorAdapter = new DonorAdapter(this, tempDonorList);
        donorsRv.setAdapter(donorAdapter);
        donorsRv.setLayoutManager(new LinearLayoutManager(this));

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                Log.e("user", user.toString());
                donorList.add(user);
                tempDonorList.add(user);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        nodesRef.addChildEventListener(childEventListener);

        final String[] bloodGroups = {"Select a Blood Group", "A −ve", "A +ve", "AB −ve", "AB +ve", "B −ve", "B +ve", "O −ve", "O +ve"};
        bloodGroupSpinner.setItemsArray(Arrays.asList(bloodGroups));
        bloodGroupSpinner.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
            @Override
            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
                tempDonorList.clear();
                tempDonorList.addAll(donorList);

                String selectedBloodGroup = bloodGroupSpinner.getSpinner().getSelectedItem().toString();
                if (selectedBloodGroup.equals(bloodGroups[0])) {
                    donorAdapter.notifyDataSetChanged();
                    return;
                }

                Iterator<User> it = tempDonorList.iterator();
                while (it.hasNext()) {
                    User user = it.next();
                    if (user.getBloodGroup() == null || !user.getBloodGroup().equals(selectedBloodGroup))
                        it.remove();
                }
                donorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_help:
                new AlertDialog.Builder(this)
                        .setView(getLayoutInflater().inflate(R.layout.dialog_blood_donors, null))
                        .create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void lionsOnClick(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:04428414949")));
    }

    public void jeevanOnClick(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:04428263113")));
    }

    public void rotaryOnClick(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:04422422639")));
    }

    public void venkateshwaraOnClick(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:04425960226")));
    }

    public void dhanvandriOnClick(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:04424310660")));
    }

    public void indianOnClick(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:04428554548")));
    }
}
