package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Manager;
import es.iescarrillo.iacademy1.services.ManagerService;

public class AcademyDetailsActivity extends AppCompatActivity {

    TextView tvName, tvDescription, tvCountry, tvState, tvCity, tvAddress, tvWeb, tvEmail, tvPhone;
    Button btnBack, btnEditAcademy;
    private ManagerService managerService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academy_details);

        btnBack = findViewById(R.id.btnBack);

        btnEditAcademy=findViewById(R.id.btnEditAcademy);
        tvName = findViewById(R.id.tvAcademyName);
        tvDescription= findViewById(R.id.tvAcademyDescription);
        tvCountry=findViewById(R.id.tvAcademyCountry);
        tvState=findViewById(R.id.tvAcademyState);
        tvCity=findViewById(R.id.tvAcademyCity);
        tvAddress=findViewById(R.id.tvAcademyAddress);
        tvWeb=findViewById(R.id.tvAcademyWeb);
        tvEmail=findViewById(R.id.tvAcademyEmail);
        tvPhone=findViewById(R.id.tvAcademyPhone);

        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);


        managerService = new ManagerService(getApplication());
        Thread thread = new Thread(()->{



            Map<Manager, Academy> map =managerService.getManagerWithAcademyMap();

            Manager manager = managerService.getManagerWithAcademyMap().keySet().iterator().next();
            if (manager==null){
                tvName.setText("sin datos");
                tvDescription.setText("sin datos");
                tvCountry.setText("sin datos");
                tvState.setText("sin datos");
                tvCity.setText("sin datos");
                tvEmail.setText("sin datos");
                tvWeb.setText("sin datos");
                tvPhone.setText("sin datos");
                tvAddress.setText("sin datos");
            }else {
                Academy a = new Academy();
                a=map.get(manager);

                tvName.setText(a.getName());
                tvDescription.setText(a.getDescription());
                tvCountry.setText(a.getCountry());
                tvState.setText(a.getState());
                tvCity.setText(a.getCity());
                tvEmail.setText(a.getEmail());
                tvWeb.setText(a.getWeb());
                tvPhone.setText(a.getPhone());
                tvAddress.setText(a.getAddress());

            }







        });


        thread.start();
        try{
            thread.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }

        btnBack.setOnClickListener(v -> {
            Intent back = new Intent(this, ManagerMainActivity.class);
            startActivity(back);
        });



        btnEditAcademy.setOnClickListener(v -> {
            Intent edit = new Intent(this, MnagerEditAcademyActivity.class);
            startActivity(edit);

        });
    }
}