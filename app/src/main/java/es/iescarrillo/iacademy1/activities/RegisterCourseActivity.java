package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Teacher;
import es.iescarrillo.iacademy1.services.AcademyService;
import es.iescarrillo.iacademy1.services.CourseService;
import es.iescarrillo.iacademy1.services.TeacherService;

/**
 * @author clara
 *
 * Pantalla para que el manager registre un curso en su academia
 *
 */
public class RegisterCourseActivity extends AppCompatActivity {

    Button btnSave;
    EditText etTeacher, etTitle, etDescription, etLevel, etCapacity, etStartDate, etEndDate, etActivated;


    Spinner spinnerT;
    private CourseService courseService;
    private TeacherService teacherService;
    private AcademyService academyService;

    long idAcademy;
    long teacherId;

    Academy a;

    List<Teacher> teachers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_course);

        //Inicializamos componentes
        etTitle=findViewById(R.id.etCourseTitle);
        etDescription=findViewById(R.id.etCourseDescription);
        etLevel=findViewById(R.id.etCourseLevel);
        etCapacity=findViewById(R.id.etCourseCapacity);
        etEndDate=findViewById(R.id.etCourseEnd);
        etStartDate=findViewById((R.id.etCourseStart));
        etActivated=findViewById(R.id.etCourseActivated);

        spinnerT = findViewById(R.id.spinnerTeachers);


        btnSave =findViewById(R.id.btnSaveCourse);

        //Inicializamos servicios
        courseService=new CourseService(getApplication());
        teacherService= new TeacherService(getApplication());
        academyService=new AcademyService(getApplication());

        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id = sharedPreferences.getLong("id", 0);

        //Buscamos la academia por el id del manager
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


        //Buscamos los profesores y nos traemos una lista. Esto lo necesitaremos para el spinner
        Thread thread2 = new Thread(()->{

            teachers =teacherService.getTeachersByAcademy(idAcademy);





        });

        thread2.start();
        try{
            thread2.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }


        //Creamos otro arraylist para meter los nombres de usuario de los profesores
        ArrayList<String>teacherName= new ArrayList<String>();
        for (Teacher te : teachers){
            String tName=te.getUser().getName();
            teacherName.add(tName);
        }

        //Adaptador para el spinner
        ArrayAdapter tAdapter = new ArrayAdapter(RegisterCourseActivity.this, android.R.layout.simple_spinner_dropdown_item, teacherName);
        spinnerT.setAdapter(tAdapter);

        //Cuando seleccionamos un item
        spinnerT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Metemos el id del profesor en la variable que hemos creado
                teacherId= teachers.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //Acción del botón guardar
        btnSave.setOnClickListener(v -> {
            Course c = new Course();
            c.setTitle(etTitle.getText().toString());
            c.setDescription(etDescription.getText().toString());
            c.setCapacity((Integer.parseInt(etCapacity.getText().toString())));
            c.setLevel(etLevel.getText().toString());
            c.setActivated(Boolean.parseBoolean(etActivated.getText().toString()));



            c.setAcademy_id(idAcademy);
            c.setTeacher_id(teacherId);


            //Comprobamos que los campos de las fechas no estén vacíos
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


            Thread thread4 = new Thread(()->{







                courseService.insertCourse(c);
                Intent back = new Intent(this, ManagerMainActivity.class);
                startActivity(back);


            });

            thread4.start();
            try{
                thread4.join();
            }catch(Exception e){
                Log.i("error", e.getMessage());
            }


        });


    }
}