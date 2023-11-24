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
import es.iescarrillo.iacademy1.services.ClassRoomService;
import es.iescarrillo.iacademy1.services.LessonService;
/**
 * @author jesus
 *
 * Pantalla en la que el profesor puede editar los detalles de la leccion seleccionada
 *
 */
public class Teacher_Edit_Lesson extends AppCompatActivity {

    EditText etDate,etHour;
    Spinner spinner2;
    Button btnAdd,btnCancel;
    private LessonService lessonService;
    List<Classroom> classroomList;
    private ClassRoomService classRoomService;
    Long classroomID;
    Lesson l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_edit_lesson);

        //Inicializamos los servicios
        lessonService = new LessonService(getApplication());
        classRoomService = new ClassRoomService(getApplication());
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

        //Inicializamos los componentes
        etDate = findViewById(R.id.etDate);
        etHour = findViewById(R.id.etHour);
        spinner2 = findViewById(R.id.spinner2);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        etDate.setText(getIntent().getStringExtra("date"));
        etHour.setText(getIntent().getStringExtra("time"));

        //Hacemos un hilo para realizar las consultas necesarias
        Thread thread2 = new Thread(()->{
            l = lessonService.getLessonByID(getIntent().getLongExtra("id",0));
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
        ArrayAdapter cAdapter = new ArrayAdapter(Teacher_Edit_Lesson.this, android.R.layout.simple_spinner_dropdown_item, className);
        spinner2.setAdapter(cAdapter);

        //Cuando seleccionamos un item
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Metemos el id del profesor en la variable que hemos creado
                classroomID= classroomList.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Boton para aceptar la edicion de la leccion
        btnAdd.setOnClickListener(v -> {

            DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm");
           l.setLessonDate(LocalDate.parse(etDate.getText().toString(), fecha));
           l.setLessonHour(LocalTime.parse(etHour.getText().toString(), hora));
           l.setClassroom_id(classroomID);

            Thread thread4 = new Thread(()->{


                lessonService.updateLesson(l);

            });

            thread4.start();
            try{
                thread4.join();
            }catch(Exception e){
                Log.i("error", e.getMessage());
            }

            Intent add = new Intent(this, Teacher_View_Courses.class);
            startActivity(add);
            finish();

        });

    }
}