package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Academy;

public class DetailsAcademy extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_academy);

        SharedPreferences sharedPreferences = getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);

        //¿Comprobar si el rol no es el de student y echarlo?
        if(!role.equals("STUDENT")){


            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);

        }

        TextView tvName = findViewById(R.id.tvName);
        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvCountry = findViewById(R.id.tvCountry);
        TextView tvState = findViewById(R.id.tvState);
        TextView tvCity = findViewById(R.id.tvCity);
        TextView tvAddress = findViewById(R.id.tvAddress);
        TextView tvWeb = findViewById(R.id.tvWeb);
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvPhone = findViewById(R.id.tvPhone);


        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String country = intent.getStringExtra("country");
        String state = intent.getStringExtra("state");
        String city = intent.getStringExtra("city");
        String address = intent.getStringExtra("address");
        String web = intent.getStringExtra("web");
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");
        Long idCourseRegistration = intent.getLongExtra("id", 0);

        tvName.setText(name);
        tvDescription.setText(description);
        tvCountry.setText(country);
        tvState.setText(state);
        tvCity.setText(city);
        tvAddress.setText(address);
        tvWeb.setText(web);
        tvEmail.setText(email);
        tvPhone.setText(phone);

        Button btnBackInfoAcademy = findViewById(R.id.btnBackInfoAcademy);

        btnBackInfoAcademy.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, ViewAcademy.class);
            startActivity(intent2);
        });

        Button btnViewcoruses =  findViewById(R.id.btnViewcoruses);

        btnViewcoruses.setOnClickListener(v -> {
            Intent intentCourses = new Intent(this, StudentAcademyCourses.class);
            intentCourses.putExtra("id", idCourseRegistration);
            startActivity(intentCourses);

        });

    }
}
