package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.mindrot.jbcrypt.BCrypt;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Teacher;
import es.iescarrillo.iacademy1.models.User;
import es.iescarrillo.iacademy1.services.AcademyService;
import es.iescarrillo.iacademy1.services.TeacherService;

public class ManagerRegisterTeacherActivity extends AppCompatActivity {

    Button btnSave;
    SharedPreferences sharedPreferences;

    EditText etName, etSurname, etMail, etDNI, etPhone, etAddress, etUsername, etPassword;

    private TeacherService teacherService;
    Academy a;
    long idAcademy;
    private AcademyService academyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_register_teacher);



        etName=findViewById(R.id.etTeacherName);
        etSurname=findViewById(R.id.etTeacherSurname);
        etMail=findViewById(R.id.etTeacherMail);
        etDNI=findViewById(R.id.etTeacherDNI);
        etPhone=findViewById(R.id.etTeacherPhone);
        etAddress=findViewById(R.id.etTeacherAddress);
        etUsername=findViewById(R.id.etTeacherUserName);
        etPassword=findViewById(R.id.etTeacherPassword);


        btnSave=findViewById(R.id.btnSave);

        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id = sharedPreferences.getLong("id", 0);

        teacherService= new TeacherService(getApplication());
        academyService = new AcademyService(getApplication());

        btnSave.setOnClickListener(v -> {
           Teacher t = new Teacher();
            t.setName(etName.getText().toString());
            t.setSurname(etSurname.getText().toString());
            t.setAddress(etAddress.getText().toString());
            t.setEmail(etMail.getText().toString());
            t.setPhone(etPhone.getText().toString());

            Thread thread = new Thread(()->{

                a =academyService.getAcademyByManagerid(id);
                idAcademy=a.getId();



            });


            thread.start();
            try{
                thread.join();
            }catch(Exception e ){
                Log.i("error", e.getMessage());
            }

            t.setAcademy_id(idAcademy);

            User u = new User();

            u.setName(etUsername.getText().toString());
            u.setPassword(etPassword.getText().toString());
            u.setRole("TEACHER");

            String encryptPassword= BCrypt.hashpw(etPassword.getText().toString(), BCrypt.gensalt(5));

            u.setPassword(encryptPassword);

            t.setUser(u);


            Thread thread2 = new Thread(()->{
                teacherService.insertTeacher(t);

            });

            thread2.start();
            try{
                thread2.join();
            }catch(Exception e){
                Log.i("error", e.getMessage());
            }

            Intent back = new Intent(this, ManagerMainActivity.class);
            startActivity(back);
        });
    }
}