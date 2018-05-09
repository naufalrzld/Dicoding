package com.naufalrzld.kamus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.naufalrzld.kamus.model.KamusModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailKamusActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_meaning)
    TextView tvMeaning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kamus);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent dataIntent = getIntent();

        KamusModel kamusModel = dataIntent.getParcelableExtra("data");

        String kata = kamusModel.getKata();
        String arti = kamusModel.getArti();

        getSupportActionBar().setTitle(kata);

        tvMeaning.setText(arti);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
