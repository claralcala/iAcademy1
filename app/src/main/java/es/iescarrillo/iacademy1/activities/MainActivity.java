package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.mindrot.jbcrypt.BCrypt;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Manager;
import es.iescarrillo.iacademy1.models.Student;
import es.iescarrillo.iacademy1.models.Teacher;
import es.iescarrillo.iacademy1.services.AcademyService;
import es.iescarrillo.iacademy1.services.ClassRoomService;
import es.iescarrillo.iacademy1.services.CourseService;
import es.iescarrillo.iacademy1.services.InscriptionService;
import es.iescarrillo.iacademy1.services.LessonService;
import es.iescarrillo.iacademy1.services.ManagerService;
import es.iescarrillo.iacademy1.services.StudentService;
import es.iescarrillo.iacademy1.services.TeacherService;

public class MainActivity extends AppCompatActivity {

    private ManagerService managerService;
    private AcademyService academyService;
    private ClassRoomService classroomService;
    private CourseService courseService;
    private InscriptionService inscriptionService;

    private LessonService lessonService;
    private StudentService studentService;
    private TeacherService teacherService;

    EditText etUsername, etPassword;

    TextView tvError;

    Button btnLogin;
    Button btnRegister;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        managerService = new ManagerService(getApplication());
        academyService = new AcademyService(getApplication());
        classroomService = new ClassRoomService(getApplication());
        courseService = new CourseService(getApplication());
        inscriptionService = new InscriptionService(getApplication());
        lessonService = new LessonService(getApplication());
        studentService = new StudentService(getApplication());
        teacherService = new TeacherService(getApplication());

        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);

        tvError = findViewById(R.id.tvError);

        btnLogin = findViewById(R.id.btnLogin);

        btnRegister = findViewById(R.id.btnRegister);



        btnRegister.setOnClickListener(v -> {
                Intent intentRegister = new Intent(this, RegisterActivity.class);

                startActivity(intentRegister);

        });

       btnLogin.setOnClickListener(v ->{
            Thread thread = new Thread(() -> {


                    Manager m = managerService.getManagerByUsername(etUsername.getText().toString());


                    if (m==null) {
                        Student s = studentService.getStudentByUsername(etUsername.getText().toString());
                                if (s==null){
                                    Teacher t = teacherService.getTeacherByUsername(etUsername.getText().toString());
                                    if (t==null){
                                        tvError.setText("El usuario no es válido");
                                    }else {

                                        Boolean checkPassword = BCrypt.checkpw(etPassword.getText().toString(), t.getUser().getPassword());
                                        if (checkPassword){

                                            sharedPreferences = getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();

                                            editor.putBoolean("login", true);
                                            editor.putString("username", t.getUser().getName());
                                            editor.putLong("id", t.getId());
                                            editor.putString("role", t.getUser().getRole());
                                            editor.putString("password", t.getUser().getPassword());
                                            editor.apply();

                                            tvError.setText("Login correcto");
                                            //Falta el intent para ir a la actividad

                                        }else {
                                            tvError.setText("Contraseña no válida");
                                        }
                                    }
                                }else {

                                    Boolean checkPassword = BCrypt.checkpw(etPassword.getText().toString(), s.getUser().getPassword());
                                    if (checkPassword){

                                        sharedPreferences = getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        editor.putBoolean("login", true);
                                        editor.putString("username", s.getUser().getName());
                                        editor.putLong("id", s.getId());
                                        editor.putString("role", s.getUser().getRole());
                                        editor.putString("password", s.getUser().getPassword());
                                        editor.apply();

                                        tvError.setText("Login correcto");
                                        //Falta el intent para ir a la actividad

                                    }else {
                                        tvError.setText("Contraseña no válida");
                                    }

                                }
                    }else {
                        Boolean checkPassword = BCrypt.checkpw(etPassword.getText().toString(), m.getUser().getPassword());
                        if (checkPassword){

                            sharedPreferences = getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean("login", true);
                            editor.putString("username", m.getUser().getName());
                            editor.putLong("id", m.getId());
                            editor.putString("role", m.getUser().getRole());
                            editor.apply();

                            tvError.setText("Login correcto");
                            Intent mainManager = new Intent(this, ManagerMainActivity.class);
                            startActivity(mainManager);

                        }else {
                            tvError.setText("Contraseña no válida");
                        }


                    }








            });

            thread.start();
            try{
                thread.join();
            }catch(Exception e){
                Log.e("error", e.getMessage());
            }
        });




    }



}