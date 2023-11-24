package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.adapters.CourseAdapter;
import es.iescarrillo.iacademy1.adapters.LessonAdapter;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Lesson;
import es.iescarrillo.iacademy1.services.CourseService;
import es.iescarrillo.iacademy1.services.LessonService;

public class Teacher_View_Lesson extends AppCompatActivity {

    Button btnAdd;
    List<Lesson> Lessons;
    ListView lvLesson;
    private LessonService lessonService;
    private LessonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_lesson);

        //Recuperamos el intent
        Intent intent = getIntent();

        //Declaramos el boton para añadir
        btnAdd=findViewById(R.id.btnAdd);
        lvLesson=findViewById(R.id.lvLesson);

        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id = sharedPreferences.getLong("id", 0);

        String courseID = getIntent().getStringExtra("courseID");
        Long courseID2 = Long.parseLong(courseID);


        //¿Comprobar si el rol no es el de manager y echarlo?
        if(!role.equals("TEACHER")){


            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);

        }

        lessonService = new LessonService(getApplication());

        Thread thread = new Thread(()->{



            Lessons = lessonService.getLessonByCourseID(courseID2);



        });

        thread.start();
        try{
            thread.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }

        adapter = new LessonAdapter((Context)this, Lessons);

        lvLesson.setAdapter(adapter);

        lvLesson.setOnItemClickListener((parent, view, position, id1) -> {
            Lesson l = (Lesson) parent.getItemAtPosition(position);

            Intent intent1 = new Intent(this, Teacher_Details_Lesson.class);

            String date = format(l.getLessonDate());
            String time = formatHour(l.getLessonHour());
            intent1.putExtra("date", date);
            intent1.putExtra("time", time);
            intent1.putExtra("id", l.getId());
            intent1.putExtra("classroomID", l.getClassroom_id());
            intent1.putExtra("courseID", l.getCourse_id());

            startActivity(intent1);

        });

        //Boton para ir a la pagina de añadir curso
        btnAdd.setOnClickListener(v -> {
            Intent add = new Intent(this, Teacher_Add_Lesson.class);
            add.putExtra("courseID", courseID);
            startActivity(add);
        });
    }
    private String format(LocalDate localdate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return formatter.format(localdate);
    }

    private String formatHour(LocalTime localTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return formatter.format(localTime);
    }
}
