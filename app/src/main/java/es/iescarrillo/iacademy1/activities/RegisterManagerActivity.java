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
import es.iescarrillo.iacademy1.services.TeacherService;

/**
 * @author clara
 * Pantalla para que se registre el manager
 *
 *
 */
public class RegisterManagerActivity extends AppCompatActivity {

    Button btnSave;

    SharedPreferences sharedPreferences;

    EditText etName, etSurname, etUsername, etPasswordRegister, etMail, etDNI, etPhone;

    private ManagerService managerService;
    private StudentService studentService;
    private TeacherService teacherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_manager);



        //Inicializamos componentes
        etName = findViewById(R.id.etManagerName);
        etSurname = findViewById(R.id.etManagerSurname);
        etMail = findViewById(R.id.etManagerMail);
        etDNI = findViewById(R.id.etManagerDNI);
        etPhone = findViewById(R.id.etManagerPhone);
        etUsername = findViewById(R.id.etManagerUserName);
        etPasswordRegister = findViewById(R.id.etManagerPassword);

        btnSave = findViewById(R.id.btnSaveManager);


        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);

        //Inicializamos los servicios
        managerService = new ManagerService(getApplication());
        teacherService = new TeacherService(getApplication());
        studentService = new StudentService(getApplication());

        //Acción del botón guardar
        btnSave.setOnClickListener(v -> {



            //Creamos una instancia de la clase Manager
            Manager m = new Manager();
            m.setName(etName.getText().toString());
            m.setSurname(etSurname.getText().toString());
            m.setEmail(etMail.getText().toString());
            m.setDni(etDNI.getText().toString());
            m.setPhone(etPhone.getText().toString());

            //Le asignamos el usuario
            User u = new User();

            u.setName(etUsername.getText().toString());
            u.setPassword(etPasswordRegister.getText().toString());
            u.setRole("MANAGER");

            String encryptPassword= BCrypt.hashpw(etPasswordRegister.getText().toString(), BCrypt.gensalt(5));

            u.setPassword(encryptPassword);

            m.setUser(u);

            Thread thread = new Thread(()->{

                //Comprobamos que el nombre de usuario sea único
                String userName = etUsername.getText().toString();

                if (managerService.getManagerByUsername(userName)!=null || studentService.getStudentByUsername(userName)!=null || teacherService.getTeacherByUsername(userName)!=null) {
                   runOnUiThread(()->{

                       Toast.makeText(this, "El nombre de usuario ya está en uso", Toast.LENGTH_SHORT).show();
                    });

                } else {



                    managerService.insertManager(m);

                    Intent back = new Intent(this, MainActivity.class);

                    startActivity(back);
                }



            });

            thread.start();
            try{
                thread.join();
            }catch(Exception e ){
                Log.i("error", e.getMessage());
            }


        });

    }
}