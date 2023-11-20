package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.adapters.ClassroomAdapter;
import es.iescarrillo.iacademy1.adapters.CourseAdapter;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Classroom;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.services.AcademyService;
import es.iescarrillo.iacademy1.services.ClassRoomService;
import es.iescarrillo.iacademy1.services.CourseService;

/**
 * @author clara
 * Pantalla para visualizar las clases - desde el manager
 */
public class ManagerViewClassroomsActivity extends AppCompatActivity {

    ClassroomAdapter adapter;
    private ClassRoomService classroomService;
    private AcademyService academyService;

    Button btnBack, btnAddClassroom;


    List<Classroom> classrooms;

    Academy a;

    long idAcademy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_classrooms);

        //Inicializar los botones
        btnBack=findViewById(R.id.btnBack);
        btnAddClassroom=findViewById(R.id.btnAddClassroom);


        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id_ = sharedPreferences.getLong("id", 0);

        //Listview
        ListView lvClassrooms = findViewById(R.id.lvClassrooms);


        classroomService = new ClassRoomService(getApplication());

        academyService = new AcademyService(getApplication());

        //Buscamos la academia por el id del manager
        Thread thread = new Thread(()->{

            a =academyService.getAcademyByManagerid(id_);
            idAcademy=a.getId();



        });

        thread.start();
        try{
            thread.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }


        //Nos traemos la lista de clases por la academia
        Thread thread2 = new Thread(()->{

            classrooms= classroomService.getClassroomsByAcademy(idAcademy);



        });


        thread2.start();
        try{
            thread2.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }

        adapter = new ClassroomAdapter((Context)this, classrooms);



        lvClassrooms.setAdapter(adapter);



        //Al hacer click en un elemento de la listview
        lvClassrooms.setOnItemClickListener((parent, view, position, id)->{

            Classroom cl = (Classroom) parent.getItemAtPosition(position);

            Intent intent = new Intent(this, ManagerClassroomDetails.class);

            //Nos llevamos los datos de la clase en el intent
            //Incluidos el id de la academia y del manager
            intent.putExtra("name", cl.getName());
            intent.putExtra("capacity", cl.getCapacity().toString());
            intent.putExtra("id", Long.toString(cl.getId()));
            intent.putExtra("ac_id", Long.toString(cl.getAcademy_id()));


            startActivity(intent);
        });

        //Acción del botón de añadir clase
        btnAddClassroom.setOnClickListener(v->{
            Intent addClass = new Intent(this, ManagerRegisterClassroomActivity.class);
            startActivity(addClass);


        });


        //Acción del botón para volver a la pantalla principal del manager
        btnBack.setOnClickListener(v->{
            Intent back = new Intent(this, ManagerMainActivity.class);
            startActivity(back);
        });







    }
}