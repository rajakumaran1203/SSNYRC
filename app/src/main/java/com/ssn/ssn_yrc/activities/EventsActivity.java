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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.ssn.ssn_yrc.R;
import com.ssn.ssn_yrc.adapter.EventAdapter;
import com.ssn.ssn_yrc.model.Event;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsActivity extends AppCompatActivity {

    RecyclerView eventsRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_events);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String[] events = {"Blood Camp", "Eye Camp", "Orphanage Visit", "Village Camp",
                "Stem Cell", "Hospital Cleaning", "Beach Cleaning", "Campus Cleaning",
                "March Past"};
        List<Event> eventList = new ArrayList<>();
        eventList.add(new Event(events[0], R.drawable.blood_donors));
        eventList.add(new Event(events[1], R.drawable.eye_camp));
        eventList.add(new Event(events[2], R.drawable.orphanage));
        eventList.add(new Event(events[3], R.drawable.village));
        eventList.add(new Event(events[4], R.drawable.stem_cell));
        eventList.add(new Event(events[5], R.drawable.hospital_cleaning));
        eventList.add(new Event(events[6], R.drawable.beach_cleaning));
        eventList.add(new Event(events[7], R.drawable.campus_cleaning));
        eventList.add(new Event(events[8], R.drawable.march_past));

        final EventAdapter eventAdapter = new EventAdapter(this, eventList);
        eventsRv.setAdapter(eventAdapter);
        eventsRv.setLayoutManager(new GridLayoutManager(this, 3));
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
