package es.iescarrillo.iacademy1.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.adapters.AcademyAdapter;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.services.AcademyService;

public class ViewAcademy extends AppCompatActivity {

    private AcademyService academyService;
    private List<Academy> academies;

    private AcademyAdapter academyAdapter;

    ListView lvAcademy;

    private Button btnBackAcademy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_academy);

        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id_ = sharedPreferences.getLong("id", 0);

        //¿Comprobar si el rol no es el de student y echarlo?
        if(!role.equals("STUDENT")){


            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);

        }

        // Inicializa los botones
        btnBackAcademy = findViewById(R.id.btnbackAcademy);
        // Inicializa el ListView y el botón btnBackInfoAcademy
        lvAcademy = findViewById(R.id.lvListAcademy);


        academyService = new AcademyService(getApplication());
        Thread thread = new Thread(()->{

            academies = academyService.getAll();
        });

        thread.start();
        try{
            thread.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }

        academyAdapter = new AcademyAdapter((Context)this, academies);

        lvAcademy.setAdapter(academyAdapter);

        btnBackAcademy.setOnClickListener(v -> {
            Intent intent = new Intent(this, StudentActivity.class);
            startActivity(intent);
        });

        lvAcademy.setOnItemClickListener((parent, view, position, id) -> {
            Academy a = academies.get(position);
            Intent intent = new Intent(this, DetailsAcademy.class);

            intent.putExtra("name", a.getName());
            intent.putExtra("description", a.getDescription());
            intent.putExtra("country", a.getCountry());
            intent.putExtra("state", a.getState());
            intent.putExtra("city", a.getCity());
            intent.putExtra("address", a.getAddress());
            intent.putExtra("web", a.getWeb());
            intent.putExtra("email", a.getEmail());
            intent.putExtra("phone", a.getPhone());
            intent.putExtra("id", a.getId());

            startActivity(intent);
        });
    }
}
