package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.services.ClassRoomService;

public class ManagerClassroomDetails extends AppCompatActivity {

    Button btnBack, btnDelete, btnEdit;

    TextView tvName, tvCapacity;

    private ClassRoomService classroomService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_classroom_details);

        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);

        btnBack=findViewById(R.id.btnBack);
        btnDelete=findViewById(R.id.btnDelete);
        btnEdit=findViewById(R.id.btnEdit);
        tvName=findViewById(R.id.tvClassroomName);
        tvCapacity=findViewById(R.id.tvClassroomCapacity);

        Intent intent = getIntent();

        tvName.setText(intent.getStringExtra("name"));
        tvCapacity.setText(intent.getStringExtra("capacity"));

        classroomService = new ClassRoomService(getApplication());

        btnBack.setOnClickListener(v -> {
            Intent back = new Intent (this, ManagerViewClassroomsActivity.class);
            startActivity(back);
        });


        btnEdit.setOnClickListener(v -> {
            Intent edit = new Intent(this, ManagerEditClassroomActivity.class);

            edit.putExtra("name", tvName.getText().toString());
            edit.putExtra("capacity", tvCapacity.getText().toString());
            edit.putExtra("ac_id", intent.getStringExtra("ac_id"));
            edit.putExtra("id", intent.getStringExtra("id"));

            startActivity(edit);
        });


        btnDelete.setOnClickListener(v ->{
            Thread thread = new Thread(()->{


                classroomService.deleteClassById(Long.parseLong(intent.getStringExtra("id")), Long.parseLong(intent.getStringExtra("ac_id")));



            });


            thread.start();
            try{
                thread.join();
            }catch(Exception e ){
                Log.i("error", e.getMessage());
            }


            Intent b = new Intent (this, ManagerViewClassroomsActivity.class);
            startActivity(b);
        });

    }
}