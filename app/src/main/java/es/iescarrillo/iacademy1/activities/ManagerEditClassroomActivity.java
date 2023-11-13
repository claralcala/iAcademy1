package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.services.ClassRoomService;

/**
 * @author clara
 * Pantalla para que el manager edite las clases de su academia
 *
 */
public class ManagerEditClassroomActivity extends AppCompatActivity {

    Button btnSave;
    EditText etName,etCapacity;
    private ClassRoomService classroomService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_edit_classroom);

        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);

        //Recuperamos el intent
        Intent intent = getIntent();

        //Inicializamos componentes
        btnSave=findViewById(R.id.btnSave);
        etName=findViewById(R.id.etClassroomName);
        etCapacity=findViewById(R.id.etClassroomCapacity);

        //Inicializamos el servicio
        classroomService = new ClassRoomService(getApplication());


        //Ponemos los datos en los campos de texto
        etName.setText(intent.getStringExtra("name"));
        etCapacity.setText(intent.getStringExtra("capacity"));

        //Acción del botón guardar
        btnSave.setOnClickListener(v -> {

            Thread thread = new Thread(()->{


                //Actualizamos la clase usando el id de la academia y de la clase extraídos del intent
                long academy_id = Long.parseLong(intent.getStringExtra("ac_id"));
                long class_id = Long.parseLong(intent.getStringExtra("id"));

                classroomService.updateClassbyId(etName.getText().toString(), Integer.parseInt(etCapacity.getText().toString()), academy_id, class_id);


            });


            thread.start();
            try{
                thread.join();
            }catch(Exception e ){
                Log.i("error", e.getMessage());
            }

            //Tras actualizar, volvemos
            Intent back = new Intent(this, ManagerViewClassroomsActivity.class);
            startActivity(back);

        });

    }
}