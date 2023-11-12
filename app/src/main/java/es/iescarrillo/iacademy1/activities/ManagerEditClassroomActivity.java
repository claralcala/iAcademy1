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

public class ManagerEditClassroomActivity extends AppCompatActivity {

    Button btnSave;
    EditText etName,etCapacity;
    private ClassRoomService classroomService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_edit_classroom);

        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);

        Intent intent = getIntent();

        btnSave=findViewById(R.id.btnSave);
        etName=findViewById(R.id.etClassroomName);
        etCapacity=findViewById(R.id.etClassroomCapacity);

        classroomService = new ClassRoomService(getApplication());


        etName.setText(intent.getStringExtra("name"));
        etCapacity.setText(intent.getStringExtra("capacity"));

        btnSave.setOnClickListener(v -> {

            Thread thread = new Thread(()->{


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

            Intent back = new Intent(this, ManagerViewClassroomsActivity.class);
            startActivity(back);

        });

    }
}