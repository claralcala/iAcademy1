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

public class MnagerEditAcademyActivity extends AppCompatActivity {

    EditText etName, etDescription, etWeb, etPhone, etMail, etCountry, etState, etCity, etAddress;
    Button btnSave;
    private AcademyService academyService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnager_edit_academy);


        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);

        Intent edit = getIntent();
        etName=findViewById(R.id.etAcademyName);
        etDescription=findViewById(R.id.etAcademyDescription);
        etCountry=findViewById(R.id.etAcademyCountry);
        etState= findViewById(R.id.etAcademyState);
        etCity=findViewById(R.id.etAcademyCity);
        etAddress=findViewById(R.id.etAcademyAddress);
        etMail=findViewById(R.id.etAcademyMail);
        etPhone=findViewById(R.id.etAcademyPhone);
        etWeb=findViewById(R.id.etAcademyWeb);

        etName.setText(edit.getStringExtra("name"));
        etDescription.setText(edit.getStringExtra("description"));
        etCountry.setText(edit.getStringExtra("country"));
        etState.setText(edit.getStringExtra("state"));
        etAddress.setText(edit.getStringExtra("address"));
        etCity.setText(edit.getStringExtra("city"));
        etWeb.setText(edit.getStringExtra("web"));
        etPhone.setText(edit.getStringExtra("phone"));
        etMail.setText(edit.getStringExtra("email"));

        Academy aca = new Academy();
        aca.setName(edit.getStringExtra("name"));
        aca.setManager_id(id);
        aca.setDescription(edit.getStringExtra("description"));
        aca.setCountry(edit.getStringExtra("country"));
        aca.setState(edit.getStringExtra("state"));
        aca.setCity(edit.getStringExtra("city"));
        aca.setAddress(edit.getStringExtra("address"));
        aca.setEmail(edit.getStringExtra("email"));
        aca.setPhone(edit.getStringExtra("phone"));
        aca.setWeb(edit.getStringExtra("web"));

        Academy a = new Academy();
        a.setName(etName.getText().toString());
        a.setDescription(etDescription.getText().toString());
        a.setCountry(etCountry.getText().toString());
        a.setState(etState.getText().toString());
        a.setCity(etCity.getText().toString());
        a.setAddress(etAddress.getText().toString());
        a.setPhone(etPhone.getText().toString());
        a.setWeb(etWeb.getText().toString());
        a.setEmail(etMail.getText().toString());
        a.setManager_id(id);

        btnSave=findViewById(R.id.btnSave);

        academyService = new AcademyService(getApplication());
        btnSave.setOnClickListener(v -> {
            Thread thread = new Thread(()->{


                academyService.updatebyId(etName.getText().toString(), etDescription.getText().toString(), etCountry.getText().toString(), etState.getText().toString(), etAddress.getText().toString(), etCity.getText().toString(), etWeb.getText().toString(), etPhone.getText().toString(), etMail.getText().toString(), id);




            });


            thread.start();
            try{
                thread.join();
            }catch(Exception e ){
                Log.i("error", e.getMessage());
            }

            Intent back = new Intent(this, AcademyDetailsActivity.class);
            startActivity(back);
        });

    }

}