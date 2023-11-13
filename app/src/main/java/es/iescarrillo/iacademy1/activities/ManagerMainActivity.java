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
import es.iescarrillo.iacademy1.services.ManagerService;

/**
 * @author clara
 *
 * Pantalla principal del manager cuando inicia sesión y ve las opciones que puede hacer
 *
 */

public class ManagerMainActivity extends AppCompatActivity {
    Button btnViewTeachers, btnViewCourses, btnViewAcademy, btnViewClassrooms, btnLogout, btnAddAcademy;
    SharedPreferences sharedPreferences;
    AcademyService academyService;
    Academy a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        //Inicializamos componentes
        btnViewTeachers = findViewById(R.id.btnViewTeachers);
        btnViewAcademy = findViewById(R.id.btnViewAcademy);
        btnViewCourses=findViewById(R.id.btnViewCourses);
        btnViewClassrooms = findViewById(R.id.btnViewClassrooms);
        btnLogout = findViewById(R.id.btnLogout);
        btnAddAcademy=findViewById(R.id.btnAddAcademy);

        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id = sharedPreferences.getLong("id", 0);



        //¿Comprobar si el rol no es el de manager y echarlo?
        if(!role.equals("MANAGER")){


            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);

        }

        //Inicializamos el servicio
        academyService = new AcademyService(getApplication());




        //Obtenemos la academia
        Thread thread = new Thread(() -> {

           a= academyService.getAcademyByManagerid(id);


        });

        thread.start();
        try {
            thread.join();//Esperar a que termine el hilo
        } catch (Exception e){
            Log.e("error hilo", e.getMessage());
        }


        //Dependiendo del valor de la academia, hacemos una cosa u otra
        if (a!=null){
            //Si la academia ya existe, desactivamos el botón de agregar academia para que no se pueda pulsar
            btnAddAcademy.setEnabled(false);
        }else {
            //Si no existe una academia, quitamos el resto de botones pues da error si intentamos crear un profesor o un curso sin academia
            btnViewAcademy.setEnabled(false);
            btnViewTeachers.setEnabled(false);
            btnViewClassrooms.setEnabled(false);
            btnViewCourses.setEnabled(false);

        }


        //Acción del botón para visualizar profesores
        btnViewTeachers.setOnClickListener(v -> {
            Intent intentViewTeachers = new Intent(this, ViewTeachersActivity.class);
           startActivity(intentViewTeachers);

        });

        //Acción del botón para visualizar datos de la academia
        btnViewAcademy.setOnClickListener(v -> {
            Intent intentViewAcademy = new Intent(this, AcademyDetailsActivity.class);
            startActivity(intentViewAcademy);
        });

        //Acción del botón para visualizar los cursos
        btnViewCourses.setOnClickListener(v -> {
            Intent intentViewCourses = new Intent(this, ManagerViewCourses.class);
            startActivity(intentViewCourses);
        });

        //Acción del botón para visualizar las aulas
        btnViewClassrooms.setOnClickListener(v -> {
            Intent intentViewClassrooms = new Intent(this, ManagerViewClassroomsActivity.class);
            startActivity(intentViewClassrooms);
        });

        //Acción del botón para agregar academia
        btnAddAcademy.setOnClickListener(v->{
            Intent add = new Intent(this, ManagerRegisterAcademyActivity.class);
            startActivity(add);
        });

        //Acción del botón logout. Borramos los datos que haya en las variables de sesión y volvemos a la pantalla de login
        btnLogout.setOnClickListener(v -> {

            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);
        });
    }
}