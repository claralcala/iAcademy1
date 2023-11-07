package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Manager;
import es.iescarrillo.iacademy1.models.Student;
import es.iescarrillo.iacademy1.models.User;
import es.iescarrillo.iacademy1.services.ManagerService;
import es.iescarrillo.iacademy1.services.StudentService;

public class RegisterManagerActivity extends AppCompatActivity {

    Button btnSave;

    SharedPreferences sharedPreferences;

    EditText etName, etSurname, etUsername, etPasswordRegister, etMail, etDNI, etPhone;

    private ManagerService managerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_manager);


        etName = findViewById(R.id.etManagerName);
        etSurname = findViewById(R.id.etManagerSurname);
        etMail = findViewById(R.id.etManagerMail);
        etDNI = findViewById(R.id.etManagerDNI);
        etPhone = findViewById(R.id.etManagerPhone);
        etUsername = findViewById(R.id.etManagerUserName);
        etPasswordRegister = findViewById(R.id.etManagerPassword);

        btnSave = findViewById(R.id.btnSaveManager);



        managerService = new ManagerService(getApplication());

        btnSave.setOnClickListener(v -> {



            Manager m = new Manager();
            m.setName(etName.getText().toString());
            m.setSurname(etSurname.getText().toString());
            m.setEmail(etMail.getText().toString());
            m.setDni(etDNI.getText().toString());
            m.setPhone(etPhone.getText().toString());

            User u = new User();

            u.setName(etUsername.getText().toString());
            u.setPassword(etPasswordRegister.getText().toString());
            u.setRole("MANAGER");

            String encryptPassword= BCrypt.hashpw(etPasswordRegister.getText().toString(), BCrypt.gensalt(5));

            u.setPassword(encryptPassword);

            m.setUser(u);

            Thread thread = new Thread(()->{

               /* int numUsers= managerService.getUsernameUnique(etUsername.getText().toString())
                if (numUsers==0) {
                    // El nombre de usuario es único, puedes continuar con el registro
                    // Crea el nuevo usuario y guárdalo en la base de datos
                } else {
                    // Muestra un mensaje de error informando que el nombre de usuario ya está en uso
                    Toast.makeText(this, "El nombre de usuario ya está en uso", Toast.LENGTH_SHORT).show();
                }*/

                managerService.insertManager(m);

            });

            thread.start();
            try{
                thread.join();
            }catch(Exception e ){
                Log.i("error", e.getMessage());
            }

            Intent back = new Intent(this, MainActivity.class);

            startActivity(back);

        });

    }
}