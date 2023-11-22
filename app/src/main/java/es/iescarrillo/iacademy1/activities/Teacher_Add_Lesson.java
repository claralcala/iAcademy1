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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Classroom;
import es.iescarrillo.iacademy1.models.Lesson;
import es.iescarrillo.iacademy1.models.Teacher;
import es.iescarrillo.iacademy1.services.ClassRoomService;
import es.iescarrillo.iacademy1.services.LessonService;

public class Teacher_Add_Lesson extends AppCompatActivity {

    Button btnAdd, btnCancel;

    EditText etLessonHour, etLessonDate;
    Spinner spinner;
    List<Classroom> classroomList;
    private LessonService lessonService;
    private ClassRoomService classRoomService;
    Long classroomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_lesson);

        //Recuperamos el intent
        Intent intent = getIntent();

        //Declaramos los botones
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        //Declaramos los editText
        etLessonHour=findViewById(R.id.etLessonHour);
        etLessonDate=findViewById(R.id.etLessonDate);

        spinner =findViewById(R.id.spinner);

        lessonService = new LessonService(getApplication());
        classRoomService = new ClassRoomService(getApplication());

        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id = sharedPreferences.getLong("id", 0);
        Long academyID = sharedPreferences.getLong("academyID",0);

        String courseID = intent.getStringExtra("courseID");


        Thread thread2 = new Thread(()->{

            classroomList =classRoomService.getClassroomsByAcademy(academyID);





        });

        thread2.start();
        try{
            thread2.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }


        //Creamos otro arraylist para meter los nombres de usuario de los profesores
        ArrayList<String> className= new ArrayList<String>();
        for (Classroom c : classroomList){
            String cName=c.getName();
            className.add(cName);
        }

        //Adaptador para el spinner
        ArrayAdapter cAdapter = new ArrayAdapter(Teacher_Add_Lesson.this, android.R.layout.simple_spinner_dropdown_item, className);
        spinner.setAdapter(cAdapter);

        //Cuando seleccionamos un item
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Metemos el id del profesor en la variable que hemos creado
                classroomID= classroomList.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Boton para
        btnAdd.setOnClickListener(v -> {
        Lesson l = new Lesson();
            l.setClassroom_id(classroomID);
            l.setCourse_id(Long.parseLong(courseID));
            //Comprobamos que los campos de las fechas no estén vacíos
            if (!TextUtils.isEmpty(etLessonDate.getText().toString())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                l.setLessonDate(LocalDate.parse(etLessonDate.getText().toString(), formatter));
            } else {
                Toast.makeText(this, "La fecha no puede estar vacía", Toast.LENGTH_SHORT).show();
                return; // Detener la ejecución del método si el campo de fecha de nacimiento está vacío
            }

            //Comprobamos que los campos de las fechas no estén vacíos
            if (!TextUtils.isEmpty(etLessonHour.getText().toString())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                l.setLessonHour(LocalTime.parse(etLessonHour.getText().toString(), formatter));
            } else {
                Toast.makeText(this, "La hora no puede estar vacía", Toast.LENGTH_SHORT).show();
                return; // Detener la ejecución del método si el campo de fecha de nacimiento está vacío
            }

            Thread thread4 = new Thread(()->{

                lessonService.insertLesson(l);



            });

            thread4.start();
            try{
                thread4.join();
            }catch(Exception e){
                Log.i("error", e.getMessage());
            }
            onBackPressed();

        });

    }
}