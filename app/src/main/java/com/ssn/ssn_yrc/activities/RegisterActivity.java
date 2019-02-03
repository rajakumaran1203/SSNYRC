package com.ssn.ssn_yrc.activities;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ssn.ssn_yrc.R;
import com.ssn.ssn_yrc.fragment.DatePickerFragment;
import com.ssn.ssn_yrc.model.User;
import com.ssn.ssn_yrc.ui.LabelledSpinner;
import com.ssn.ssn_yrc.utils.LogHelper;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = LogHelper.makeLogTag(RegisterActivity.class);

    TextInputEditText nameEt;
    TextInputEditText registerNoEt;
    RadioButton beRb;
    RadioButton meRb;
    LabelledSpinner departmentSpinner;
    TextInputEditText departmentEt;
    TextInputLayout departmentLayout;
    LabelledSpinner yearSpinner;
    Button dobBtn;
    TextInputEditText phoneEt;
    Button lastDonatedBtn;
    TextInputEditText emailEt;
    Button submitBtn;
    LabelledSpinner bloodGroupSpinner;
    LabelledSpinner memberSpinner;
    TextInputLayout nameTil;
    TextInputLayout registerTil;
    TextInputLayout phoneTil;

    private boolean dob, lastDonated;

    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    private int currentApiVersion;

    public static Intent createIntent(Context context, IdpResponse idpResponse) {
        Intent in = IdpResponse.getIntent(idpResponse);
        in.setClass(context, RegisterActivity.class);
        return in;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentApiVersion = Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorViem.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }

        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        final String[] departments = {"BME", "Chemical", "Civil", "CSE", "ECE", "EEE", "IT", "Mech"};
        final String[] years = {"1", "2", "3", "4"};
        final String[] bloodGroups = {"A −ve", "A +ve", "AB −ve", "AB +ve", "B −ve", "B +ve", "O −ve", "O +ve"};
        final String[] isMember = {"Yes", "No"};

        departmentSpinner.setItemsArray(Arrays.asList(departments));
        yearSpinner.setItemsArray(Arrays.asList(years));
        bloodGroupSpinner.setItemsArray(Arrays.asList(bloodGroups));
        memberSpinner.setItemsArray(Arrays.asList(isMember));

        databaseReference = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        dobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dob = true;
                lastDonated = false;
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "dobDatePicker");
            }
        });

        lastDonatedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dob = false;
                lastDonated = true;
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "lastDonatedDatePicker");
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    saveToDatabase();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                }
            }
        });

        populateProfile();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (dob) dobBtn.setText(simpleDateFormat.format(calendar.getTime()));
        if (lastDonated) lastDonatedBtn.setText(simpleDateFormat.format(calendar.getTime()));
    }

    public void populateProfile() {
        nameEt.setText(currentUser.getDisplayName());
        emailEt.setText(currentUser.getEmail());

        Query userQuery = databaseReference.child("users").child(currentUser.getUid());
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    User user = dataSnapshot.getValue(User.class);
                    nameEt.setText(user.getName());
                    registerNoEt.setText(user.getRegisterNo());
                    if (user.getBeOrMe().equals("B.E.")) {
                        beRb.setChecked(true);
                        meRb.setChecked(false);
                        departmentSpinner.getSpinner().setSelection(((ArrayAdapter<String>) departmentSpinner.getSpinner()
                                .getAdapter()).getPosition(String.valueOf(user.getDepartment())));
                    } else if (user.getBeOrMe().equals("M.E.")) {
                        beRb.setChecked(false);
                        meRb.setChecked(true);
                        departmentEt.setText(user.getDepartment());
                    }
                    yearSpinner.getSpinner().setSelection(((ArrayAdapter<String>) yearSpinner.getSpinner()
                            .getAdapter()).getPosition(String.valueOf(user.getYear())));
                    dobBtn.setText(user.getDateOfBirth());
                    phoneEt.setText(user.getPhone());
                    bloodGroupSpinner.getSpinner().setSelection(((ArrayAdapter<String>) bloodGroupSpinner.getSpinner()
                            .getAdapter()).getPosition(String.valueOf(user.getBloodGroup())));
                    lastDonatedBtn.setText(user.getLastDonatedDate());
                    memberSpinner.getSpinner().setSelection(((ArrayAdapter<String>) memberSpinner.getSpinner()
                            .getAdapter()).getPosition(String.valueOf(user.getMember())));
                    emailEt.setText(user.getEmail());
                } catch (Exception e) {
                    Log.e("populateProfile", e.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void saveToDatabase() {
        String department = "";
        String beOrMe = "";
        if (departmentSpinner.getVisibility() == View.VISIBLE) {
            department = departmentSpinner.getSpinner().getSelectedItem().toString();
            beOrMe = "B.E.";
        } else if (departmentLayout.getVisibility() == View.VISIBLE) {
            department = departmentEt.getText().toString();
            beOrMe = "M.E.";
        }

        User user = new User(currentUser.getUid(),
                nameEt.getText().toString(), registerNoEt.getText().toString(),
                beOrMe, department,
                Integer.parseInt(yearSpinner.getSpinner().getSelectedItem().toString()),
                dobBtn.getText().toString(), phoneEt.getText().toString(),
                bloodGroupSpinner.getSpinner().getSelectedItem().toString(), lastDonatedBtn.getText().toString(),
                memberSpinner.getSpinner().getSelectedItem().toString(), emailEt.getText().toString());

        databaseReference.child("users").child(currentUser.getUid()).setValue(user);
    }

    public void beOnClick(View view) {
        departmentSpinner.setVisibility(View.VISIBLE);
    }

    public void meOnClick(View view) {
        departmentLayout.setVisibility(View.VISIBLE);
    }

    private boolean validate() {
        boolean flag = true;
        if (nameEt.getText().toString().trim().equals("")) {
            nameTil.setError("Enter a name");
            Toast.makeText(this, "Enter a name", Toast.LENGTH_SHORT).show();
            flag = false;
        } else nameTil.setError(null);

        /*if (registerNoEt.getText().toString().trim().length() != 12) {
            registerTil.setError("Enter a valid SSN register number");
            Toast.makeText(this, "Enter a valid SSN register number", Toast.LENGTH_SHORT).show();
            flag = false;
        } else registerTil.setError(null);*/

        if (phoneEt.getText().toString().trim().length() < 10) {
            phoneTil.setError("Enter a valid phone number");
            flag = false;
        } else phoneTil.setError(null);

        return flag;
    }
}
