package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Classroom;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Teacher;
import es.iescarrillo.iacademy1.services.AcademyService;
import es.iescarrillo.iacademy1.services.ClassRoomService;
import es.iescarrillo.iacademy1.services.CourseService;
import es.iescarrillo.iacademy1.services.TeacherService;

/**
 * @author clara
 * Página para que el manager registre una clase asociada a su academia
 */
public class ManagerRegisterClassroomActivity extends AppCompatActivity {

    Button btnSave;
    EditText etName, etCapacity;

    private ClassRoomService classroomService;

    private AcademyService academyService;

    long idAcademy;
    Academy a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_register_classroom);


        //Inicializamos componentes
        btnSave=findViewById(R.id.btnSave);
        etName=findViewById(R.id.etClassroomName);
        etCapacity=findViewById(R.id.etClassroomCapacity);



        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id = sharedPreferences.getLong("id", 0);



        classroomService = new ClassRoomService(getApplication());

        academyService = new AcademyService(getApplication());

        //Acción del botón guardar
        btnSave.setOnClickListener(v -> {

            Classroom cl = new Classroom();
            cl.setName(etName.getText().toString());
            cl.setCapacity(Integer.parseInt(etCapacity.getText().toString()));

            Thread thread = new Thread(()->{
                //Buscamos la academia por el id del manager y obtenemos su id
                a =academyService.getAcademyByManagerid(id);
                idAcademy=a.getId();



            });

            thread.start();
            try{
                thread.join();
            }catch(Exception e ){
                Log.i("error", e.getMessage());
            }

            //Le ponemos el id a la clase
            cl.setAcademy_id(idAcademy);



            //Insertamos la clase

            Thread thread2 = new Thread(()->{
                classroomService.insertClassroom(cl);

            });

            thread2.start();
            try{
                thread2.join();
            }catch(Exception e){
                Log.i("error", e.getMessage());
            }

            Intent back = new Intent(this, ManagerMainActivity.class);
            startActivity(back);
        });

    }
}