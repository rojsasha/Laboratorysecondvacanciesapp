package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
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
import com.example.rojsa.laboratorysecondvacanciesapp.model.AllDayModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {

                getData();

            } else {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(intent);
                finish();
            }
        }
    }

    private void getData() {
        Toast.makeText(getApplicationContext(),"internet good",Toast.LENGTH_SHORT).show();
        RequestInterface service = StartApplication.get(getApplicationContext()).getService();
        service.getAllVacancies("au", "get_all_vacancies", "20", "1")
                .enqueue(new Callback<List<AllDayModel>>() {
                    @Override
                    public void onResponse(Call<List<AllDayModel>> call, Response<List<AllDayModel>> response) {

                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        List<AllDayModel> list = response.body();
                        intent.putParcelableArrayListExtra("listVacancy", (ArrayList<? extends Parcelable>) list);
                        Toast.makeText(getApplicationContext(),"internet down",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<AllDayModel>> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(),"internet good" + t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
