package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.adapters.LessonAdapter;
import es.iescarrillo.iacademy1.models.Lesson;
import es.iescarrillo.iacademy1.services.LessonService;

public class DetailsEnrolled extends AppCompatActivity {

    //Declaramos service, list adaptadores, listView y bototnes
    private LessonService lessonService;
    private List<Lesson> lesson;

    private LessonAdapter lessonAdapter;

    ListView lvlessonEnrroled;


    Button btnBackDetailsLesson;


    /**
     * @author Manu Rguez
     * Pantalla para visualizar una lista de todas las lecciones
     *
     */

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_enrolled);

        //Declaramos las variables de session

        SharedPreferences sharedPreferences = getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id_ = sharedPreferences.getLong("id", 0);


        //Aqui guardamos en una variable el intent de CourseRegistration
        long courseID = getIntent().getLongExtra("id", 0);

        //Le asignamos la lvlessonEnrrolled al la listview que tenemos en el xml
        lvlessonEnrroled = findViewById(R.id.lvlessonEnrroled);

        //Declaramos el servicio y creamos un hilo
        lessonService = new LessonService(getApplication());
        Thread thread = new Thread(() -> {

            //Guardamos en lesson para obtener la leccion por la id del course y le pasamos por parametro la id del course
            lesson = lessonService.getLessonWithCouseById(courseID);


        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            Log.i("error", e.getMessage());
        }

        //Introducimos el adaptador y a la listview le guardamos el adaptador que es donde esta toda la informacion
        lessonAdapter = new LessonAdapter((Context) this, lesson);
        lvlessonEnrroled.setAdapter(lessonAdapter);


        //Le damos funcion a la lvlessonEnronlled que al hacer clic en un item de la lista te lleve a los detalles de las lecciones
        // y paramos los datos a traves de los input para regogerlos en la calse destion
        lvlessonEnrroled.setOnItemClickListener((parent, view, position, id1) -> {
            Lesson l = (Lesson) parent.getItemAtPosition(position);

            Intent intent1 = new Intent(this, StudentLessonDetails.class);

            String date = format(l.getLessonDate());
            String time = formatHour(l.getLessonHour());
            intent1.putExtra("date", date);
            intent1.putExtra("hour", time);
            intent1.putExtra("classroomID", l.getClassroom_id());
            startActivity(intent1);
        });


        //Le damos funcion al boton de volver
        btnBackDetailsLesson = findViewById(R.id.btnBackDetailsLesson);

        btnBackDetailsLesson.setOnClickListener(v -> {

            Intent intent = new Intent(this, CoursesRegistration.class);

            startActivity(intent);
        });


    }

    //Estos son metodos que hemos tenido que impletar al  darle formato a la fecha y hora antes de pasarlas a trave de los intent ya que si no
    //no me recogia los datos y nos lo devolvia nulos
    private String format(LocalDate localdate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return formatter.format(localdate);
    }

    private String formatHour(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return formatter.format(localTime);
    }
}