package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.SimpleFormatter;


import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Student;
import es.iescarrillo.iacademy1.models.User;
import es.iescarrillo.iacademy1.services.ManagerService;
import es.iescarrillo.iacademy1.services.StudentService;
import es.iescarrillo.iacademy1.services.TeacherService;

public class RegisterStudentActivity extends AppCompatActivity {

    EditText etName, etSurname, etMail, etDNI, etPhone, etFamPhone, etBirthdate, etUsername, etPasswordRegister;

    Button btnSave;

    SharedPreferences sharedPreferences;

    private StudentService studentService;
    private ManagerService managerService;
    private TeacherService teacherService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        etName = findViewById(R.id.etStudentName);
        etSurname = findViewById(R.id.etStudentSurname);
        etMail = findViewById(R.id.etStudentMail);
        etDNI = findViewById(R.id.etStudentDNI);
        etPhone = findViewById(R.id.etStudentPhone);
        etFamPhone = findViewById(R.id.etFamilyPhone);
        etBirthdate = findViewById(R.id.etStudentBirthdate);
        etUsername = findViewById(R.id.etStudentUserName);
        etPasswordRegister = findViewById(R.id.etStudentPassword);

        btnSave = findViewById(R.id.btnSaveStudent);

        sharedPreferences = getSharedPreferences("AcademyPreferences", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        long id = sharedPreferences.getLong("id", 0);

        studentService = new StudentService(getApplication());
        managerService = new ManagerService(getApplication());
        teacherService = new TeacherService(getApplication());

        btnSave.setOnClickListener(v -> {

            Student s = new Student();
            s.setName(etName.getText().toString());
            s.setSurname(etSurname.getText().toString());
            s.setEmail(etMail.getText().toString());
            s.setDni(etDNI.getText().toString());
            s.setPhone(etPhone.getText().toString());
            s.setFamilyPhone(etFamPhone.getText().toString());


            if (!TextUtils.isEmpty(etBirthdate.getText().toString())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                s.setBirthdate(LocalDate.parse(etBirthdate.getText().toString(), formatter));
            } else {
                Toast.makeText(this, "La fecha no puede estar vacía", Toast.LENGTH_SHORT).show();
                return; // Detener la ejecución del método si el campo de fecha de nacimiento está vacío
            }

            User u = new User();

            u.setName(etUsername.getText().toString());
            u.setPassword(etPasswordRegister.getText().toString());
            u.setRole("STUDENT");

            String encryptPassword= BCrypt.hashpw(etPasswordRegister.getText().toString(), BCrypt.gensalt(5));

            u.setPassword(encryptPassword);

            s.setUser(u);

            Thread thread = new Thread(()->{

                String userName = etUsername.getText().toString();

                if (managerService.getManagerByUsername(userName)!=null || studentService.getStudentByUsername(userName)!=null || teacherService.getTeacherByUsername(userName)!=null) {
                    runOnUiThread(()->{

                        Toast.makeText(this, "El nombre de usuario ya está en uso", Toast.LENGTH_SHORT).show();
                    });

                } else {


                    studentService.insertStudent(s);

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