package com.example.rojsa.laboratorysecondvacanciesapp.ui.splashscreen;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.data.RequestInterface;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {

                getData();

            } else {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                mProgressBar.setVisibility(View.INVISIBLE);
                startActivity(intent);
                finish();
            }
        }
    }

    private void getData() {
        Toast.makeText(getApplicationContext(),"internet good",Toast.LENGTH_SHORT).show();
        RequestInterface service = StartApplication.get(getApplicationContext()).getService();
        service.getAllVacancies("login", "f", "limit", "page")
                .enqueue(new Callback<List<VacanciesModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<VacanciesModel>> call, @NonNull Response<List<VacanciesModel>> response) {

                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        List<VacanciesModel> list = response.body();
                        intent.putParcelableArrayListExtra("listVacancy", (ArrayList<? extends Parcelable>) list);
                        Toast.makeText(getApplicationContext(),"internet down",Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<VacanciesModel>> call, @NonNull Throwable t) {
                    }
                });
    }

}
