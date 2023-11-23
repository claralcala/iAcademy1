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

/**
 * @author clara
 * Pantalla para que el manager edite la academia
 */
public class MnagerEditAcademyActivity extends AppCompatActivity {

    EditText etName, etDescription, etWeb, etPhone, etMail, etCountry, etState, etCity, etAddress;
    Button btnSave;
    private AcademyService academyService;
    Academy a;

    long idAcademy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnager_edit_academy);


        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);

        //¿Comprobar si el rol no es el de manager y echarlo?
        if(!role.equals("MANAGER")){


            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);

        }

        //Recuperamos el intent
        Intent edit = getIntent();

        //Inicializamos componentes
        etName=findViewById(R.id.etAcademyName);
        etDescription=findViewById(R.id.etAcademyDescription);
        etCountry=findViewById(R.id.etAcademyCountry);
        etState= findViewById(R.id.etAcademyState);
        etCity=findViewById(R.id.etAcademyCity);
        etAddress=findViewById(R.id.etAcademyAddress);
        etMail=findViewById(R.id.etAcademyMail);
        etPhone=findViewById(R.id.etAcademyPhone);
        etWeb=findViewById(R.id.etAcademyWeb);

        //Nos traemos los datos del intent y los ponemos en los campos de texto
        etName.setText(edit.getStringExtra("name"));
        etDescription.setText(edit.getStringExtra("description"));
        etCountry.setText(edit.getStringExtra("country"));
        etState.setText(edit.getStringExtra("state"));
        etAddress.setText(edit.getStringExtra("address"));
        etCity.setText(edit.getStringExtra("city"));
        etWeb.setText(edit.getStringExtra("web"));
        etPhone.setText(edit.getStringExtra("phone"));
        etMail.setText(edit.getStringExtra("email"));


        btnSave=findViewById(R.id.btnSave);


        academyService = new AcademyService(getApplication());


        Thread thread = new Thread(()->{

            //Buscamos la academia por el id del manager
            a= academyService.getAcademyByManagerid(id);

            idAcademy=a.getId();

        });


        thread.start();
        try{
            thread.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }

       //Acción del botón guardar
        btnSave.setOnClickListener(v -> {
            Thread thread2 = new Thread(()->{


                //Llamamos a nuestro método personalizado
                academyService.updatebyId(etName.getText().toString(), etDescription.getText().toString(), etCountry.getText().toString(), etState.getText().toString(), etAddress.getText().toString(), etCity.getText().toString(), etWeb.getText().toString(), etPhone.getText().toString(), etMail.getText().toString(), idAcademy);




            });


            thread2.start();
            try{
                thread2.join();
            }catch(Exception e ){
                Log.i("error", e.getMessage());
            }

            Intent back = new Intent(this, AcademyDetailsActivity.class);
            startActivity(back);
        });

    }
}