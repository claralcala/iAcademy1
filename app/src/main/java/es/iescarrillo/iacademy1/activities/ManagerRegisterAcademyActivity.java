package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.services.AcademyService;

public class ManagerRegisterAcademyActivity extends AppCompatActivity {

    Button btnSave;
    SharedPreferences sharedPreferences;

    EditText etName, etDescription, etCountry, etState, etCity, etAddress, etWeb, etMail, etPhone;

    private AcademyService academyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_register_academy);


        etName=findViewById(R.id.etAcademyName);
        etDescription=findViewById(R.id.etAcademyDescription);
        etCountry=findViewById(R.id.etAcademyCountry);
        etState=findViewById(R.id.etAcademyState);
        etCity=findViewById(R.id.etAcademyCity);
        etAddress=findViewById(R.id.etAcademyAddress);
        etWeb=findViewById(R.id.etAcademyWeb);
        etMail=findViewById(R.id.etAcademyMail);
        etPhone=findViewById(R.id.etAcademyPhone);

        btnSave=findViewById(R.id.btnSave);

        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id = sharedPreferences.getLong("id", 0);

        academyService = new AcademyService(getApplication());

        btnSave.setOnClickListener(v -> {
            Academy a = new Academy();
            a.setName(etName.getText().toString());
            a.setDescription(etDescription.getText().toString());
            a.setCountry(etCountry.getText().toString());
            a.setState(etState.getText().toString());
            a.setCity(etCity.getText().toString());
            a.setAddress(etAddress.getText().toString());
            a.setWeb(etWeb.getText().toString());
            a.setEmail(etMail.getText().toString());
            a.setPhone(etPhone.getText().toString());

            //Manager id?

            Thread thread = new Thread(()->{
                academyService.insertAcademy(a);
            });

            thread.start();
            try{
                thread.join();
            }catch(Exception e){
                Log.i("error", e.getMessage());
            }

            Intent back = new Intent(this, ManagerMainActivity.class);
            startActivity(back);
        });
    }
}