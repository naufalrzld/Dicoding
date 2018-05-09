package com.naufalrzld.kamus;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.naufalrzld.kamus.db.KamusHelper;
import com.naufalrzld.kamus.model.KamusModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashcreenActivity extends AppCompatActivity {
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashcreen);
        ButterKnife.bind(this);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        KamusHelper kamusHelper;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(SplashcreenActivity.this);
            appPreference = new AppPreference(SplashcreenActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = appPreference.getFirstRun();
            int i = 0;

            if (firstRun) {
                ArrayList<KamusModel> kamusModelsENID = preLoadRawENID();
                ArrayList<KamusModel> kamusModelsIDEN = preLoadRawIDEN();

                kamusHelper.open();

                progress = 0;
                publishProgress((int) progress);
                Double progressMaxInsert = 100.0;
                Double progressDiff = (progressMaxInsert - progress) / (kamusModelsENID.size() + kamusModelsIDEN.size());

                kamusHelper.beginTransaction();

                try {
                    /*while (i < kamusModelsENID.size() || i < kamusModelsIDEN.size()) {
                        KamusModel model = kamusModelsENID.get(i);

                        while (i < kamusModelsENID.size()) {
                            kamusHelper.insertTransactionENID(model);
                            progress += progressDiff;
                            publishProgress((int) progress);
                        }
                    }*/
                    for (KamusModel model : kamusModelsENID) {
                        kamusHelper.insertTransactionENID(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }

                    for (KamusModel model : kamusModelsIDEN) {
                        kamusHelper.insertTransactionIDEN(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }

                    kamusHelper.setTransactionSuccess();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }

                kamusHelper.endTransaction();

                kamusHelper.close();

                appPreference.setFirstRun(false);

                publishProgress((int) maxprogress);
            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(SplashcreenActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<KamusModel> preLoadRawENID() {
        ArrayList<KamusModel> kamusModels = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.english_indonesia);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                KamusModel kamusModel;

                kamusModel = new KamusModel(splitstr[0], splitstr[1]);
                kamusModels.add(kamusModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kamusModels;
    }

    public ArrayList<KamusModel> preLoadRawIDEN() {
        ArrayList<KamusModel> kamusModels = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.indonesia_english);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                KamusModel kamusModel;

                kamusModel = new KamusModel(splitstr[0], splitstr[1]);
                kamusModels.add(kamusModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kamusModels;
    }
}
