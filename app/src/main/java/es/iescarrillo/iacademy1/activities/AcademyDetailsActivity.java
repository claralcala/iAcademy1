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
import es.iescarrillo.iacademy1.services.AcademyService;
import es.iescarrillo.iacademy1.services.ManagerService;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Manager;
import es.iescarrillo.iacademy1.services.ManagerService;

/**
 * @author clara
 * Activity para ver los detalles de la academia
 */
public class AcademyDetailsActivity extends AppCompatActivity {

    TextView tvName, tvDescription, tvCountry, tvState, tvCity, tvAddress, tvWeb, tvEmail, tvPhone;
    Button btnBack, btnEditAcademy;

    private AcademyService academyService;
    private ManagerService managerService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academy_details);

        //Inicializamos todos los componentes
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

        //Ponemos las sharedpreferences (por si acaso, estarán en todas las pantallas del manager
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);


        //Iniciamos el servicio


        academyService = new AcademyService(getApplication());

        Thread thread = new Thread(()->{
            //Buscamos la academia por el id del manager
            Academy a = academyService.getAcademyByManagerid(id);

            //Ponemos en los campos de texto los datos extraídos de la bd
            tvName.setText(a.getName());
            tvDescription.setText(a.getDescription());
            tvCountry.setText(a.getCountry());
            tvState.setText(a.getState());
            tvCity.setText(a.getCity());
            tvAddress.setText(a.getAddress());
            tvPhone.setText(a.getPhone());
            tvEmail.setText(a.getEmail());
            tvWeb.setText(a.getWeb());


        });


        thread.start();
        try{
            thread.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }

        //Botón volver
        btnBack.setOnClickListener(v -> {
            Intent back = new Intent(this, ManagerMainActivity.class);
            startActivity(back);
        });



        //Botón para editar. Nos llevamos los datos a través del intent
        btnEditAcademy.setOnClickListener(v -> {

            Intent edit = new Intent(this, MnagerEditAcademyActivity.class);

            edit.putExtra("name", tvName.getText().toString());
            edit.putExtra("description", tvDescription.getText().toString());
            edit.putExtra("country", tvCountry.getText().toString());
            edit.putExtra("state", tvState.getText().toString());
            edit.putExtra("city", tvCity.getText().toString());
            edit.putExtra("address", tvAddress.getText().toString());
            edit.putExtra("email", tvEmail.getText().toString());
            edit.putExtra("phone", tvPhone.getText().toString());
            edit.putExtra("web", tvWeb.getText().toString());
            edit.putExtra("id", id);
            startActivity(edit);

        });

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



        managerService = new ManagerService(getApplication());
        Thread thread2 = new Thread(() -> {
            Map<Manager, Academy> map = managerService.getManagerWithAcademyMap();


                if (map != null && !map.isEmpty()) {
                    Map.Entry<Manager, Academy> entry = map.entrySet().iterator().next();
                    Manager manager = entry.getKey();
                    Academy a = entry.getValue();

                    // Actualizar los TextViews con los datos
                    tvName.setText(a.getName());
                    tvDescription.setText(a.getDescription());
                    tvCountry.setText(a.getCountry());
                    tvState.setText(a.getState());
                    tvCity.setText(a.getCity());
                    tvEmail.setText(a.getEmail());
                    tvWeb.setText(a.getWeb());
                    tvPhone.setText(a.getPhone());
                    tvAddress.setText(a.getAddress());
                } else {
                    // Si no hay datos en el mapa, mostrar los mensajes de "sin datos"
                    tvName.setText("sin datos");
                    tvDescription.setText("sin datos");
                    tvCountry.setText("sin datos");
                    tvState.setText("sin datos");
                    tvCity.setText("sin datos");
                    tvEmail.setText("sin datos");
                    tvWeb.setText("sin datos");
                    tvPhone.setText("sin datos");
                    tvAddress.setText("sin datos");
                }
        });

        thread2.start();
        try {
            thread2.join();
        } catch (Exception e) {
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