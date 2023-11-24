package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.services.CourseService;

/**
 * @author jesus
 *
 * Pantalla en la que el profesor puede editar los detalles del curso seleccionado
 *
 */
public class Teacher_Edit_Course extends AppCompatActivity {

    private CourseService courseService;
    EditText etTittle, etDescription, etLevel, etCapacity, etStartDate, etEndDate;
    CheckBox cbActivated;
    Button btnAdd, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_edit_course);

        Intent intent = getIntent();

        //Incializamos componentes
        etTittle=findViewById(R.id.etTittle);
        etDescription=findViewById(R.id.etDescription);
        etLevel=findViewById(R.id.etLevel);
        etCapacity=findViewById(R.id.etCapacity);
        etStartDate=findViewById(R.id.etStartDate);
        etEndDate=findViewById(R.id.etEndDate);

        cbActivated=findViewById(R.id.cbActivated);

        btnAdd=findViewById(R.id.btnAdd);
        btnCancel=findViewById(R.id.btnCancel);

        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id = sharedPreferences.getLong("id", 0);
        Long academyID = sharedPreferences.getLong("academyID",0);

        //¿Comprobar si el rol no es el de manager y echarlo?
        if(!role.equals("TEACHER")){


            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);

        }

        //Les ponemos el texto que nos traemos a través del intent en los edit text
        etTittle.setText(intent.getStringExtra("title"));
        etDescription.setText(intent.getStringExtra("description"));
        etCapacity.setText(intent.getStringExtra("capacity"));
        etLevel.setText(intent.getStringExtra("level"));
        etStartDate.setText(intent.getStringExtra("start"));
        etEndDate.setText(intent.getStringExtra("end"));

        //Aqui nos traemos el valor del checkbox, en caso de que sea true lo dejamos activado, en caso de que sea false dejamos el checkbox vacio
        String activated = intent.getStringExtra("activated");

        if(activated.equals("true")) {
            cbActivated.setChecked(true);
        }else{
            cbActivated.setChecked(false);
        }

        //Inicializamos los servicios
        courseService = new CourseService(getApplication());

        btnAdd.setOnClickListener(v -> {
            Thread thread2 = new Thread(()->{

                //Acción del botón guardar
                //Guardamos las fechas en dos variables y nos aseguramos de que tengan el formato
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate startDate= LocalDate.parse(etStartDate.getText().toString(), formatter);
                LocalDate endDate = LocalDate.parse(etEndDate.getText().toString(), formatter);

                Boolean active;
                if(cbActivated.isChecked()){
                    active=true;
                }else{
                    active=false;
                }

                //Llamamos a la query de actualizar
                //Es una query personalizada que hemos hecho, actualizando por id y el id de la academia, porque la por defecto de Room no funcionaba
                courseService.updateCoursebyId(etTittle.getText().toString(), etDescription.getText().toString(), etLevel.getText().toString(), Integer.parseInt(etCapacity.getText().toString()), startDate, endDate, active, academyID, Long.parseLong(intent.getStringExtra("id")));


            });


            thread2.start();
            try{
                thread2.join();
            }catch(Exception e ){
                Log.i("error", e.getMessage());
            }

            //Tras acrualizar, volvemos a la vista de todos los cursos
            Intent back = new Intent(this, Teacher_View_Courses.class);

            startActivity(back);
        });

        btnCancel.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}