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

        btnBack=findViewById(R.id.btnBack);
        btnAddClassroom=findViewById(R.id.btnAddClassroom);


        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id_ = sharedPreferences.getLong("id", 0);

        ListView lvClassrooms = findViewById(R.id.lvClassrooms);


        classroomService = new ClassRoomService(getApplication());

        academyService = new AcademyService(getApplication());

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



        lvClassrooms.setOnItemClickListener((parent, view, position, id)->{

            Classroom cl = (Classroom) parent.getItemAtPosition(position);

            Intent intent = new Intent(this, ManagerClassroomDetails.class);


            startActivity(intent);
        });

        btnAddClassroom.setOnClickListener(v->{
            Intent addClass = new Intent(this, ManagerRegisterClassroomActivity.class);
            startActivity(addClass);


        });


        btnBack.setOnClickListener(v->{
            Intent back = new Intent(this, ManagerMainActivity.class);
            startActivity(back);
        });




    }
}