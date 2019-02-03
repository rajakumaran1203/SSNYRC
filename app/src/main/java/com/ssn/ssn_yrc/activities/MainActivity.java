

package com.ssn.ssn_yrc.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ssn.ssn_yrc.R;
import com.ssn.ssn_yrc.utils.LogHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    LinearLayout bloodDonorsBtn;
    LinearLayout eventsBtn;
    LinearLayout notificationsBtn;
    LinearLayout aboutYrcBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!sharedPreferences.getBoolean("hasSubscribedToNotifications", false)) {
            FirebaseMessaging.getInstance().subscribeToTopic("admin");
        }
        editor.putBoolean("hasSubscribedToNotifications", true);
        editor.apply();
    }

    @OnClick({R.id.blood_donors_btn, R.id.events_btn, R.id.notifications_btn, R.id.about_yrc_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.blood_donors_btn:
                startActivity(new Intent(this, BloodDonorsActivity.class));
                break;
            case R.id.events_btn:
                startActivity(new Intent(this, EventsActivity.class));
                break;
            case R.id.notifications_btn:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.about_yrc_btn:
                startActivity(new Intent(this, AboutYrcActivity.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                LogHelper.e("TAG", "YEAH");
                new AlertDialog.Builder(this)
                        .setView(getLayoutInflater().inflate(R.layout.dialog_main, null))
                        .create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ambulanceOnClick(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:108")));
    }

    public void policeOnClick(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:100")));
    }

    public void fireOnClick(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:101")));
    }

    public void childHelpOnClick(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1098")));
    }

    public void womenHelpOnClick(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1091")));
    }
}
