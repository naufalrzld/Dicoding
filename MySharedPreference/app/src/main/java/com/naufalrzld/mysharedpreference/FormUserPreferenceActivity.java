package com.naufalrzld.mysharedpreference;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FormUserPreferenceActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName, edtEmail, edtPhone, edtAge;
    private RadioGroup rgLoveMu;
    private RadioButton rbYes, rbNo;
    private Button btnSave;

    public static String EXTRA_TYPE_FORM = "extra_type_form";
    public static int REQUEST_CODE = 100;

    public static int TYPE_ADD = 1;
    public static int TYPE_EDIT = 2;
    private int formType;

    private final String FIELD_REQUIRED = "Field tidak boleh kosong";
    private final String FIELD_DIGIT_ONLY = "Hanya boleh terisi numerik";
    private final String FIELD_ISNOT_VALID = "Email tidak valid";

    private UserPreference mUserPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_user_preference);

        edtName = findViewById(R.id.edt_name);
        edtAge = findViewById(R.id.edt_age);
        edtEmail = findViewById(R.id.edt_email);
        edtPhone = findViewById(R.id.edt_phone);
        rgLoveMu = findViewById(R.id.rg_love_mu);
        rbYes = findViewById(R.id.rb_yes);
        rbNo = findViewById(R.id.rb_no);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);

        formType = getIntent().getIntExtra(EXTRA_TYPE_FORM, 0);

        mUserPreference = new UserPreference(this);

        String actionBarTitle = null;
        String btnTitle = null;

        if (formType == 1) {
            actionBarTitle = "Tambah Baru";
            btnTitle = "Simpan";
        } else {
            actionBarTitle = "Ubah";
            btnTitle = "Update";
            showPreferenceInForm();
        }

        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSave.setText(btnTitle);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String age = edtAge.getText().toString().trim();
            String phoneNo = edtPhone.getText().toString().trim();
            boolean isLoveMU = rgLoveMu.getCheckedRadioButtonId() == R.id.rb_yes;
            boolean isEmpty = false;
            if (TextUtils.isEmpty(name)) {
                isEmpty = true;
                edtName.setError(FIELD_REQUIRED);
            }
            if (TextUtils.isEmpty(email)) {
                isEmpty = true;
                edtEmail.setError(FIELD_REQUIRED);
            } else if (!isValidEmail(email)) {
                isEmpty = true;
                edtEmail.setError(FIELD_ISNOT_VALID);
            }
            if (TextUtils.isEmpty(age)) {
                isEmpty = true;
                edtAge.setError(FIELD_REQUIRED);
            }
            if (TextUtils.isEmpty(phoneNo)) {
                isEmpty = true;
                edtPhone.setError(FIELD_REQUIRED);
            } else if (!TextUtils.isDigitsOnly(phoneNo)) {
                isEmpty = true;
                edtPhone.setError(FIELD_DIGIT_ONLY);
            }
            if (!isEmpty) {
                mUserPreference.setName(name);
                mUserPreference.setAge(Integer.parseInt(age));
                mUserPreference.setEmail(email);
                mUserPreference.setPhoneNumber(phoneNo);
                mUserPreference.setLoveMU(isLoveMU);
                Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPreferenceInForm() {
        edtName.setText(mUserPreference.getName());
        edtEmail.setText(mUserPreference.getEmail());
        edtAge.setText(String.valueOf(mUserPreference.getAge()));
        edtPhone.setText(mUserPreference.getPhoneNumber());
        if (mUserPreference.isLoveMU()) {
            rbYes.setChecked(true);
        } else {
            rbNo.setChecked(false);
        }
    }
}
