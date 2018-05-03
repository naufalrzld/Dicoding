package com.naufalrzld.learnespresso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    public static String EXTRA_INPUT = "extra_input";
    TextView tvResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getSupportActionBar().setTitle("Activity Second");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvResultView = (TextView)findViewById(R.id.tv_result_view);
        String input = getIntent().getStringExtra(EXTRA_INPUT);
        tvResultView.setText(input);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
