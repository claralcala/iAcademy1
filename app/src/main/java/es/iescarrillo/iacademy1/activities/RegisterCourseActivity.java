package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Teacher;
import es.iescarrillo.iacademy1.services.AcademyService;
import es.iescarrillo.iacademy1.services.CourseService;
import es.iescarrillo.iacademy1.services.TeacherService;

public class RegisterCourseActivity extends AppCompatActivity {

    Button btnSave;
    EditText etTeacher, etTitle, etDescription, etLevel, etCapacity, etStartDate, etEndDate, etActivated;
    private CourseService courseService;
    private TeacherService teacherService;
    private AcademyService academyService;

    long idAcademy;
    Academy a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_course);

        etTitle=findViewById(R.id.etCourseTitle);
        etDescription=findViewById(R.id.etCourseDescription);
        etLevel=findViewById(R.id.etCourseLevel);
        etCapacity=findViewById(R.id.etCourseCapacity);
        etEndDate=findViewById(R.id.etCourseEnd);
        etStartDate=findViewById((R.id.etCourseStart));
        etActivated=findViewById(R.id.etCourseActivated);

        etTeacher=findViewById(R.id.etCourseTeacher);


        btnSave =findViewById(R.id.btnSaveCourse);

        courseService=new CourseService(getApplication());
        teacherService= new TeacherService(getApplication());

        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id = sharedPreferences.getLong("id", 0);





        academyService = new AcademyService(getApplication());

        btnSave.setOnClickListener(v -> {
            Course c = new Course();
            c.setTitle(etTitle.getText().toString());
            c.setDescription(etDescription.getText().toString());
            c.setCapacity((Integer.parseInt(etCapacity.getText().toString())));
            c.setLevel(etLevel.getText().toString());
            c.setActivated(Boolean.parseBoolean(etActivated.getText().toString()));

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

            c.setAcademy_id(idAcademy);

            Thread thread2 = new Thread(()->{



                Teacher t = teacherService.getTeacherByUsername(etTeacher.getText().toString());

                c.setTeacher_id(t.getId());


            });


            thread2.start();
            try{
                thread2.join();
            }catch(Exception e ){
                Log.i("error", e.getMessage());
            }

            if (!TextUtils.isEmpty(etStartDate.getText().toString())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                c.setStartDate(LocalDate.parse(etStartDate.getText().toString(), formatter));
            } else {
                Toast.makeText(this, "La fecha no puede estar vacía", Toast.LENGTH_SHORT).show();
                return; // Detener la ejecución del método si el campo de fecha de nacimiento está vacío
            }


            if (!TextUtils.isEmpty(etEndDate.getText().toString())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                c.setEndDate(LocalDate.parse(etEndDate.getText().toString(), formatter));
            } else {
                Toast.makeText(this, "La fecha no puede estar vacía", Toast.LENGTH_SHORT).show();
                return; // Detener la ejecución del método si el campo de fecha de nacimiento está vacío
            }


            Thread thread3 = new Thread(()->{







                courseService.insertCourse(c);
                Intent back = new Intent(this, ManagerMainActivity.class);
                startActivity(back);

            });

            thread3.start();
            try{
                thread3.join();
            }catch(Exception e){
                Log.i("error", e.getMessage());
            }


        });


    }
}