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

    //Declaramos botones, servicios y una academia
    Button btnStudenLogout, btnVisualizarAcademias, btnVisualizarCursos, btnVisualizarCursosMatriculados;

    AcademyService academyService;
    Academy a;

    /**
     * @author Manu Rguez
     * Pantalla de menú con todas las acciones de un student
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        //Declaramos las variables de session

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

        //A los botones creados en la parte superior le introducimos a donde se tiene que dirigir en caso de una accion

        btnVisualizarAcademias = findViewById(R.id.btnVisualizarAcademias);

        btnStudenLogout =  findViewById(R.id.btnStudenLogOut);

        btnVisualizarCursos = findViewById(R.id.btnViewAllCourses);

        btnVisualizarCursosMatriculados =  findViewById(R.id.btnViewCoursesEnrolled);



        //Declaramos el servicio y creamos un hilo
        academyService = new AcademyService(getApplication());

        Thread thread = new Thread(() -> {

            //Aqui guaramos en la academy a que hemos creado en la parte superior le asignamos la consulta
            //que se encuentra en AcademyService para obtener las academias por el id del manager
            a= academyService.getAcademyByManagerid(id);


        });

        thread.start();
        try {
            thread.join();//Esperar a que termine el hilo
        } catch (Exception e){
            Log.e("error hilo", e.getMessage());
        }



        //Aqui le damos funcion a los botones que hemos creado en la parte superior

        //Aquitenemos el boton de visualizar academias
        btnVisualizarAcademias.setOnClickListener(v -> {

            Intent intent = new Intent(this, ViewAcademy.class);

            startActivity(intent);


        });
        //Aquitenemos el boton de visualizar cursos
        btnVisualizarCursos.setOnClickListener(v ->{

            Intent intent = new Intent(this, ViewCourses.class);

            startActivity(intent);

        });

        //Aquitenemos el boton de visualizar cursos en los que el alumno se ha matriculado
        btnVisualizarCursosMatriculados.setOnClickListener( v->{
            Intent intent =new Intent(this, CoursesRegistration.class);
            startActivity(intent);

        });


        //Aquitenemos el boton de  logout que al hacer clic se eliminan el resguardo de las variables de session y vuelve al login
        btnStudenLogout .setOnClickListener( v ->{
            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);

        });

    }
}