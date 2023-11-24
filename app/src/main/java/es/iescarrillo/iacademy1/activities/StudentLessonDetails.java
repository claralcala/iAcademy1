package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;



import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Classroom;
import es.iescarrillo.iacademy1.services.ClassRoomService;

public class StudentLessonDetails extends AppCompatActivity {

    //Declaramos los servicios, un aula ,botones y TextView que veremos su uso proximamente
TextView tvDateLesson, tvHourLesson, tvclassroomLesson;

ClassRoomService classRoomService;

Button btnBackDetailsLesson;

Classroom c;

    /**
     * @author Manu Rguez
     * Pantalla para visualizar todos los detalles de las lecciones
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_lesson_details);

        //Declaramos las variables de session

        SharedPreferences sharedPreferences = getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);

        //Declaramos  el boton de volver y le asignamos el boton que se encuentra en el xml

        btnBackDetailsLesson = findViewById(R.id.btnBackDetailsLesson);

        //Declaramos el servicio y le asignamos funcion
        classRoomService = new ClassRoomService(getApplication());


        //Aqui introducimos en una variable el intent que recuperamos de la clase DetailsEnrrolled para encontrar el nombre del aula
        long classroomID = getIntent().getLongExtra("classroomID", 0);

        //Declaramos el hilo
        Thread thread = new Thread(()->{

            //Aqui guardamos en el aula c que hemos creado en en la parte superior y llamamos a la consulta que se encuentra en
            //ClassroomService para buscar el aula por el id del curso
            c = classRoomService.getClassroomByCourseID(classroomID);
        });
        thread.start();
        try {
            thread.join();
        }catch (Exception e ){
            Log.i("error", e.getMessage());
        }

        //Aqui nos traemos los datos de fecha y hora de detailsEnrolled para mostrarlos en los texview correspondientes
        Intent intent = getIntent();

        String fecha = intent.getStringExtra("date");
        String hora = intent.getStringExtra("hour");

        tvDateLesson = findViewById(R.id.tvDateLesson);
        tvHourLesson = findViewById(R.id.tvHourLesson);
        tvclassroomLesson = findViewById(R.id.tvclassroomLesson);


        //Le introducimos a los texview los valores que hemos recogido en el intent
        tvDateLesson.setText(fecha);
        tvHourLesson.setText(hora);
        tvclassroomLesson.setText(c.getName());

        //Le damos funcion al boton de volver con el onBackPressed para que al volver vuelvan a salir la lista de las lecciones

        btnBackDetailsLesson.setOnClickListener(v -> onBackPressed());

    }
}