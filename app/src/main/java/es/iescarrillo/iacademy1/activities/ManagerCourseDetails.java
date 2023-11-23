package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.services.AcademyService;
import es.iescarrillo.iacademy1.services.CourseService;

/**
 * @author clara
 * Pantalla para ver los detalles de cada curso
 *
 */
public class ManagerCourseDetails extends AppCompatActivity {

    Button btnBack, btnDelete, btnEdit;
    TextView tvTitle, tvDescription, tvLevel, tvCapacity, tvstart, tvend, tvActivated;

    private AcademyService academyService;

    long idAcademy;

    Academy a;


    private CourseService courseService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_course_details);

        //Recuperamos el intent
        Intent intent = getIntent();

        //Inicializamos componentes
        btnBack=findViewById(R.id.btnBack);
        btnEdit=findViewById(R.id.btnEdit);
        btnDelete=findViewById(R.id.btnDelete);
        tvTitle=findViewById(R.id.tvCourseTitle);
        tvDescription=findViewById(R.id.tvCourseDescription);
        tvCapacity= findViewById(R.id.tvCourseCapacity);
        tvstart=findViewById(R.id.tvCourseStart);
        tvend=findViewById(R.id.tvCourseEnd);
        tvLevel=findViewById(R.id.tvCourseLevel);
        tvActivated=findViewById(R.id.tvCourseActivated);

        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);


        //Inicializamos los servicios

        courseService = new CourseService(getApplication());

        academyService = new AcademyService(getApplication());

        //Ponemos los datos que traemos del intent en los campos de texto
        tvTitle.setText(intent.getStringExtra("title"));
        tvDescription.setText(intent.getStringExtra("description"));
        tvLevel.setText(intent.getStringExtra("level"));
        tvCapacity.setText(intent.getStringExtra("capacity"));
        tvstart.setText(intent.getStringExtra("startDate"));
        tvend.setText(intent.getStringExtra("endDate"));
        tvActivated.setText(intent.getStringExtra("activated"));





    //Acción del botón volver
        btnBack.setOnClickListener(v -> {
            Intent back = new Intent(this, ManagerViewCourses.class);
            startActivity(back);
        });

        //Acción del botón editar
        btnEdit.setOnClickListener(v -> {
            Intent edit = new Intent (this, EditCourseActivity.class);

            edit.putExtra("title", tvTitle.getText().toString());
            edit.putExtra("description", tvDescription.getText().toString());
            edit.putExtra("level", tvLevel.getText().toString());
            edit.putExtra("capacity", tvCapacity.getText().toString());
            edit.putExtra("start", tvstart.getText().toString());
            edit.putExtra("end", tvend.getText().toString());
            edit.putExtra("activated", tvActivated.getText().toString());
            edit.putExtra("id", intent.getStringExtra("id"));


            startActivity(edit);
        });

        //Obtenemos el id de la academia

        Thread thread = new Thread(()->{


            a = academyService.getAcademyByManagerid(id);

            idAcademy = a.getId();


        });


        thread.start();
        try{
            thread.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }


        //Botón borrar con nuestra query personalizada. Nos traemos el id del curso por el intent.
        //El id de la academia se puede extraer del intent o bien hacer una consulta como hemos hecho nosotros en este caso
        btnDelete.setOnClickListener(v -> {


                Thread thread2 = new Thread(()->{


                   courseService.deleteCourseById(Long.parseLong(intent.getStringExtra("id")), idAcademy);


                });


                thread2.start();
                try{
                    thread2.join();
                }catch(Exception e ){
                    Log.i("error", e.getMessage());
                }

                Intent back = new Intent (this, ManagerViewCourses.class);
                startActivity(back);

        });



    }
}