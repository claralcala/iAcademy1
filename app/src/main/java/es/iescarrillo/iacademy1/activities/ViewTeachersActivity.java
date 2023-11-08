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
import es.iescarrillo.iacademy1.adapters.PersonAdapter;
import es.iescarrillo.iacademy1.adapters.TeacherAdapter;
import es.iescarrillo.iacademy1.models.Teacher;
import es.iescarrillo.iacademy1.services.TeacherService;


public class ViewTeachersActivity extends AppCompatActivity {

    Button btnAddTeacher, btnBack;

    private TeacherService teacherService;
    private TeacherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teachers);

        btnAddTeacher=findViewById(R.id.btnAddTeacher);
        btnBack=findViewById(R.id.btnBack);

        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);

        ListView lvTeachers = findViewById(R.id.lvTeachers);


        teacherService = new TeacherService(getApplication());

        Thread thread = new Thread(()->{


            List<Teacher> teachers = teacherService.getTeachersByAcademy(id);

            adapter = new TeacherAdapter((Context)this, teachers);


            lvTeachers.setAdapter(adapter);

         lvTeachers.setOnItemClickListener((parent, view, position, id_)->{
                        Teacher t = (Teacher) parent.getItemAtPosition(position);
                        Intent intent = new Intent(this, TeacherDetailsActivity.class);

                        startActivity(intent);
                    }
            );





        });


        thread.start();
        try{
            thread.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }


        btnAddTeacher.setOnClickListener(v->{
            Intent addTeacher = new Intent(this, ManagerRegisterTeacherActivity.class);
            startActivity(addTeacher);

        });


        btnBack.setOnClickListener(v->{
            Intent back = new Intent(this, ManagerMainActivity.class);
            startActivity(back);
        });
    }
}