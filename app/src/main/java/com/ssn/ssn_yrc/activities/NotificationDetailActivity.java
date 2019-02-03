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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ssn.ssn_yrc.R;
import com.ssn.ssn_yrc.model.Post;
import com.ssn.ssn_yrc.utils.FileDownloader;
import com.ssn.ssn_yrc.utils.LogHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationDetailActivity extends AppCompatActivity {
    private static final String TAG = LogHelper.makeLogTag(NotificationDetailActivity.class);
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;

    TextView contentEmail1;
    ImageView postImageView;

    private String title, content, fileName, date;
    private TextView dispContent, dispNo1, dispUrl1, dispUrl2, dispTitle, dispPdf, dispDate;
    private Post post;

    private ArrayList<String> links;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dispTitle = (TextView) findViewById(R.id.alert_title);
        dispContent = (TextView) findViewById(R.id.content_activity);
        dispNo1 = (TextView) findViewById(R.id.content_contact1);
        dispUrl1 = (TextView) findViewById(R.id.content_url1);
        dispUrl2 = (TextView) findViewById(R.id.content_url2);
        dispPdf = (TextView) findViewById(R.id.content_pdf);
        dispDate = (TextView) findViewById(R.id.postdate);

        post = new Gson().fromJson(getIntent().getStringExtra("post"), Post.class);
        title = post.getTitle();
        content = post.getDescription();

        dispTitle.setText(title);

        Date date = new Date(post.getDate());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aaa 'on' EEE, dd MMM");
        dispDate.setText(simpleDateFormat.format(date));

        links = extractUrls();

        dispUrls();
        dispNos();

        dispContent.setText(content);

        if (!post.getFileURL().equals("")) {

            fileName = post.getFileName();

            if (fileName.contains(".pdf")) {
                dispPdf.setText(fileName);
                dispPdf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(NotificationDetailActivity.this, "Downloading..", Toast.LENGTH_SHORT).show();
                        if (ContextCompat.checkSelfPermission(NotificationDetailActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(NotificationDetailActivity.this,
                                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        } else {
                            new DownloadFile().execute(post.getFileURL(), post.getFileName());
                        }
                    }
                });
                postImageView.setVisibility(View.GONE);
            } else if (fileName.contains(".jpg") || fileName.contains(".jpeg") || fileName.contains(".png")) {
                Glide.with(this).load(post.getFileURL()).into(postImageView);
                dispPdf.setVisibility(View.GONE);
            }

        } else {
            dispPdf.setVisibility(View.GONE);
            postImageView.setVisibility(View.GONE);
        }
        if (!post.getEmail().equals("")) {
            contentEmail1.setText(post.getEmail());
            contentEmail1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                            Uri.parse("mailto:" + post.getEmail()));
                    startActivity(Intent.createChooser(emailIntent, "Send Email"));
                }
            });
        } else
            contentEmail1.setVisibility(View.GONE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new DownloadFile().execute(post.getFileURL(), post.getFileName());
            }
        } else {
            Toast.makeText(this, "Permission to open file denied!", Toast.LENGTH_LONG).show();
        }
    }

    private void dispUrls() {
        if (links.size() == 2) {
            dispUrl1.setText(links.get(0));
            dispUrl2.setText(links.get(1));

            dispUrl1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startUrlIntent(dispUrl1.getText().toString());
                }
            });

            dispUrl2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startUrlIntent(dispUrl2.getText().toString());
                }
            });
        } else if (links.size() == 1) {
            dispUrl1.setText(links.get(0));
            dispUrl2.setVisibility(View.GONE);

            dispUrl1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startUrlIntent(dispUrl1.getText().toString());
                }
            });

        } else {
            dispUrl1.setVisibility(View.GONE);
            dispUrl2.setVisibility(View.GONE);
        }
    }

    private void dispNos() {
        if (!post.getContactno().equals("")) {
            dispNo1.setText(post.getContactno());

            dispNo1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dispNo1.getText().toString().trim())));
                }
            });
        } else
            dispNo1.setVisibility(View.GONE);
    }

    private ArrayList<String> extractUrls() {
        ArrayList<String> result = new ArrayList<String>();

        Pattern pattern = Pattern.compile(
                "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.|coe1.|coe2.)" +
                        "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov|co|in" +
                        "|mil|biz|info|mobi|name|aero|jobs|edu|museum" +
                        "|travel|[a-z]{2}))(:[\\d]{1,5})?" +
                        "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" +
                        "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                        "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" +
                        "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                        "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" +
                        "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");

        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            result.add(matcher.group());
            content = content.replace(matcher.group(), "");
        }
        return result;
    }


    private void startUrlIntent(String textViewString) {
        if (!(textViewString.contains("http://") || textViewString.contains("https://"))) {
            textViewString = "http://" + textViewString;
        }
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        builder.setShowTitle(true);

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(textViewString));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DownloadFile extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String fileUrl = strings[0];
            String fileName = strings[1];

            String status;

            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "TheSSNApp");

            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            status = FileDownloader.downloadFile(fileUrl, pdfFile);
            if (status.equals("Success"))
                return fileName;

            else
                return "Failure";
        }

        @Override
        protected void onPostExecute(String fileName) {
            if (fileName.equals("Failure"))
                Toast.makeText(NotificationDetailActivity.this, "I/O Error!", Toast.LENGTH_LONG).show();
            else {
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/TheSSNApp/" + fileName);
                Uri path = FileProvider.getUriForFile(NotificationDetailActivity.this,
                        getApplicationContext().getPackageName() + ".provider", pdfFile);

                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                pdfIntent.setDataAndType(path, "application/pdf");
                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                try {
                    startActivity(pdfIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(NotificationDetailActivity.this, "Cannot find application to open PDF!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }
    }
}

