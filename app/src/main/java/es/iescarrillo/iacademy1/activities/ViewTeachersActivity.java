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
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Teacher;
import es.iescarrillo.iacademy1.services.AcademyService;
import es.iescarrillo.iacademy1.services.TeacherService;

/**
 * @author clara
 * Pantalla para visualizar los profesores de la academia
 */

public class ViewTeachersActivity extends AppCompatActivity {

    Button btnAddTeacher, btnBack;

    private TeacherService teacherService;
    private TeacherAdapter adapter;
    private AcademyService academyService;

    Academy a;
    List<Teacher> teachers;
    long idAcademy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teachers);

        btnAddTeacher=findViewById(R.id.btnAddTeacher);
        btnBack=findViewById(R.id.btnBack);

        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id_ = sharedPreferences.getLong("id", 0);

        //¿Comprobar si el rol no es el de manager y echarlo?
        if(!role.equals("MANAGER")){


            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);

        }

        ListView lvTeachers = findViewById(R.id.lvTeachers);


        teacherService = new TeacherService(getApplication());

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

            teachers = teacherService.getTeachersByAcademy(idAcademy);



        });


        thread2.start();
        try{
            thread2.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }


        adapter = new TeacherAdapter((Context)this, teachers);



        lvTeachers.setAdapter(adapter);



        //Al hacer clic en un item
        lvTeachers.setOnItemClickListener((parent, view, position, id)->{

                    Teacher t = (Teacher) parent.getItemAtPosition(position);

                    Intent intent = new Intent(this, TeacherDetailsActivity.class);
                    intent.putExtra("name", t.getName().toString());
                    intent.putExtra("surname", t.getSurname().toString());
                    intent.putExtra("email", t.getEmail().toString());
                    intent.putExtra("phone", t.getPhone().toString());
                    intent.putExtra("dni", t.getDni().toString());
                    intent.putExtra("address", t.getAddress().toString());
                    intent.putExtra("username", t.getUser().getName().toString());
                    intent.putExtra("id", Long.toString(t.getId()));
                    intent.putExtra("ac_id", Long.toString(t.getAcademy_id()));


                    startActivity(intent);
        });

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