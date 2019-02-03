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
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ssn.ssn_yrc.R;
import com.ssn.ssn_yrc.adapter.PostAdapter;
import com.ssn.ssn_yrc.model.Node;
import com.ssn.ssn_yrc.model.Post;
import com.ssn.ssn_yrc.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView postsRecyclerView;
    ProgressBar progressBar;
    CoordinatorLayout coordinatorLayout;

    private LinearLayoutManager linearLayoutManager;
    private List<Post> postList;
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(this, postList);
        postsRecyclerView.setLayoutManager(linearLayoutManager);
        postsRecyclerView.setAdapter(postAdapter);

        checkConnectionStatus();
    }

    private void checkConnectionStatus() {
        if (NetworkUtils.isConnectedToInternet(this)) {
            progressBar.setVisibility(View.VISIBLE);

            getPosts();
        } else {
            progressBar.setVisibility(View.GONE);

            getPosts();

            Snackbar.make(coordinatorLayout, R.string.connection_failed, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.alerts_retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkConnectionStatus();
                        }
                    })
                    .show();
        }
    }

    private void getPosts() {
        postList.clear();
        postAdapter.notifyDataSetChanged();
        final ArrayList<Node> nodesList = new ArrayList<>();
        final HashMap<String, Post> postHashMap = new HashMap<>();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference nodesRef = database.getReference("categorywise_posts/admin");

        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                if (post == null) {
                    postList.remove(postHashMap.get(dataSnapshot.getKey()));
                    postHashMap.remove(dataSnapshot.getKey());
                    postAdapter.notifyDataSetChanged();
                    return;
                }
                if (postHashMap.containsKey(dataSnapshot.getKey())) {
                    if (postList.contains(postHashMap.get(dataSnapshot.getKey()))) {
                        postList.remove(postHashMap.get(dataSnapshot.getKey()));
                        postAdapter.notifyDataSetChanged();
                    }
                }
                postHashMap.put(dataSnapshot.getKey(), post);
                postList.add(post);
                postAdapter.notifyDataSetChanged();
                linearLayoutManager.scrollToPositionWithOffset(postList.size() - 1, 0);
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Node node = dataSnapshot.getValue(Node.class);
                nodesList.add(node);
                FirebaseDatabase.getInstance().getReference("posts/" + node.getPid())
                        .addValueEventListener(valueEventListener);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (nodesList.contains(dataSnapshot.getValue(Node.class))) {
                    nodesList.remove(dataSnapshot.getValue(Node.class));
                    FirebaseDatabase.getInstance().getReference("posts/" +
                            dataSnapshot.getValue(Node.class).getPid())
                            .removeEventListener(valueEventListener);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        nodesRef.addChildEventListener(childEventListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
