package com.sodatracker.apptastic.sodatracker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sodatracker.apptastic.sodatracker.R;
import com.sodatracker.apptastic.sodatracker.objects.Soda;

public class AddSodaActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String CLASS_NAME = AddSodaActivity.class.getName();
    public static final boolean DEBUG_CLASS = false;
    public static final boolean DEBUG_METHOD_CALLS = false;

    private int mNumTwelvePacks = 0;
    private int mNumCans = 0;

    private EditText etBrandName;
    private TextView tvDisplayTP;
    private TextView tvDisplayCans;
    private Button btnSubtractTP;
    private Button btnAddTP;
    private Button btnSubtractCan;
    private Button btnAddCan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_soda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_soda_activity_toolbar);
        toolbar.setTitle(getString(R.string.activity_add_soda));
        setSupportActionBar(toolbar);
        etBrandName = (EditText) findViewById(R.id.add_soda_et_brand_name);
        tvDisplayTP = (TextView) findViewById(R.id.add_soda_tv_tp_display);
        tvDisplayCans = (TextView) findViewById(R.id.add_soda_tv_cans_display);
        btnSubtractTP = (Button) findViewById(R.id.add_soda_btn_tp_subtract);
        btnAddTP = (Button) findViewById(R.id.add_soda_btn_tp_add);
        btnSubtractCan = (Button) findViewById(R.id.add_soda_btn_cans_subtract);
        btnAddCan = (Button) findViewById(R.id.add_soda_btn_cans_add);

        initUI();
    }

    private void initUI() {
        tvDisplayTP.setText("0");
        tvDisplayCans.setText("0");
        btnSubtractTP.setOnClickListener(this);
        btnAddTP.setOnClickListener(this);
        btnSubtractCan.setOnClickListener(this);
        btnAddCan.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_soda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                String mBrandName = etBrandName.getText().toString().trim();
                if (!TextUtils.isEmpty(mBrandName)) {
                    Soda soda = new Soda(mBrandName, mNumTwelvePacks, mNumCans);
                    Intent sodaIntent = new Intent();
                    sodaIntent.putExtra(MainActivity.EXTRA_SODA, soda);
                    setResult(RESULT_OK, sodaIntent);
                    finish();
                } else {
                    Toast.makeText(this, "You need to enter a name for the soda!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_soda_btn_tp_subtract:
                if (mNumTwelvePacks > 0) {
                    mNumTwelvePacks--;
                }
                break;
            case R.id.add_soda_btn_tp_add:
                mNumTwelvePacks++;
                break;
            case R.id.add_soda_btn_cans_subtract:
                if (mNumCans > 0) {
                    mNumCans--;
                } else if (mNumTwelvePacks > 0) {
                    mNumTwelvePacks--;
                    mNumCans = 11;
                }
                break;
            case R.id.add_soda_btn_cans_add:
                if (mNumCans < 11) {
                    mNumCans++;
                } else {
                    mNumTwelvePacks++;
                    mNumCans = 0;
                }
                break;
        }
        updateCounts();
    }

    private void updateCounts() {
        tvDisplayTP.setText(String.valueOf(mNumTwelvePacks));
        tvDisplayCans.setText(String.valueOf(mNumCans));
    }
}
