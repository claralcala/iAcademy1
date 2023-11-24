package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.services.AcademyService;

public class StudentActivity extends AppCompatActivity {
    Button btnStudenLogout, btnVisualizarAcademias, btnVisualizarCursos, btnVisualizarCursosMatriculados;

    AcademyService academyService;
    Academy a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id = sharedPreferences.getLong("id", 0);

        //¿Comprobar si el rol no es el de student y echarlo?
        if(!role.equals("STUDENT")){


            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);

        }

        btnVisualizarAcademias = findViewById(R.id.btnVisualizarAcademias);


        btnStudenLogout =  findViewById(R.id.btnStudenLogOut);

        btnVisualizarCursos = findViewById(R.id.btnViewAllCourses);

        btnVisualizarCursosMatriculados =  findViewById(R.id.btnViewCoursesEnrolled);




        academyService = new AcademyService(getApplication());




        Thread thread = new Thread(() -> {

            a= academyService.getAcademyByManagerid(id);


        });

        thread.start();
        try {
            thread.join();//Esperar a que termine el hilo
        } catch (Exception e){
            Log.e("error hilo", e.getMessage());
        }



        btnVisualizarAcademias.setOnClickListener(v -> {

            Intent intent = new Intent(this, ViewAcademy.class);

            startActivity(intent);


        });

        btnVisualizarCursos.setOnClickListener(v ->{

            Intent intent = new Intent(this, ViewCourses.class);

            startActivity(intent);

        });
        btnVisualizarCursosMatriculados.setOnClickListener( v->{
            Intent intent =new Intent(this, CoursesRegistration.class);
            startActivity(intent);

        });



        btnStudenLogout .setOnClickListener( v ->{
            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);

        });

    }
}