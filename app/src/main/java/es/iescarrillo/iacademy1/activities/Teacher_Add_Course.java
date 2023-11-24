package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.services.CourseService;

public class Teacher_Add_Course extends AppCompatActivity {

    private CourseService courseService;
    EditText etTittle, etDescription, etLevel, etCapacity, etStartDate, etEndDate;

    CheckBox cbActivated;

    Button btnAdd, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_course);

        //Incializamos componentes
        etTittle=findViewById(R.id.etTittle);
        etDescription=findViewById(R.id.etDescription);
        etLevel=findViewById(R.id.etLevel);
        etCapacity=findViewById(R.id.etCapacity);
        etStartDate=findViewById(R.id.etStartDate);
        etEndDate=findViewById(R.id.etEndDate);

        cbActivated=findViewById(R.id.cbActivated);

        btnAdd=findViewById(R.id.btnAdd);
        btnCancel=findViewById(R.id.btnCancel);

        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id = sharedPreferences.getLong("id", 0);
        Long academyID = sharedPreferences.getLong("academyID",0);

        //¿Comprobar si el rol no es el de manager y echarlo?
        if(!role.equals("TEACHER")){


            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);

        }

        //Inicializamos servicios
        courseService=new CourseService(getApplication());

        btnAdd.setOnClickListener(v -> {
            Course c = new Course();
            c.setTitle(etTittle.getText().toString());
            c.setDescription(etDescription.getText().toString());
            c.setCapacity((Integer.parseInt(etCapacity.getText().toString())));
            c.setLevel(etLevel.getText().toString());

            if(cbActivated.isChecked()){
                c.setActivated(true);
            }else{
                c.setActivated(false);
            }



            c.setAcademy_id(academyID);
            c.setTeacher_id(id);

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
                Intent back = new Intent(this, Teacher_View_Courses.class);
                startActivity(back);


            });

            thread4.start();
            try{
                thread4.join();
            }catch(Exception e){
                Log.i("error", e.getMessage());
            }
        });

        btnCancel.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}