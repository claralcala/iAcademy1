package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Teacher;
import es.iescarrillo.iacademy1.services.TeacherService;

public class TeacherDetailsActivity extends AppCompatActivity {

    Button btnBack, btnDelete;

    TextView tvName, tvSurname, tvPhone, tvEmail, tvDNI, tvAddress, tvUsername;

    Teacher t;

    List<Teacher> teachers;

    private TeacherService teacherService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);


        btnDelete=findViewById(R.id.btnDelete);
        btnBack=findViewById(R.id.btnBack);
        tvName=findViewById(R.id.tvTeacherName);
        tvSurname= findViewById(R.id.tvTeacherSurname);
        tvPhone= findViewById(R.id.tvTeacherPhone);
        tvEmail=findViewById(R.id.tvTeacherEmail);
        tvDNI=findViewById(R.id.tvTeacherDNI);
        tvAddress=findViewById(R.id.tvTeacherAddress);
        tvUsername = findViewById(R.id.tvTeacherUsername);

        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);

        Intent intent = getIntent();

        tvName.setText(intent.getStringExtra("name"));
        tvSurname.setText(intent.getStringExtra("surname"));
        tvPhone.setText(intent.getStringExtra("phone"));
        tvDNI.setText(intent.getStringExtra("dni"));
        tvAddress.setText(intent.getStringExtra("address"));
        tvEmail.setText(intent.getStringExtra("email"));
        tvUsername.setText(intent.getStringExtra("username"));



        teacherService = new TeacherService(getApplication());





        btnBack.setOnClickListener(v -> {
            Intent back = new Intent (this, ViewTeachersActivity.class);
            startActivity(back);
        });

        btnDelete.setOnClickListener(v -> {


            Thread thread = new Thread(()->{


                teacherService.deleteTeacherById(Long.parseLong(intent.getStringExtra("id")));


            });


            thread.start();
            try{
                thread.join();
            }catch(Exception e ){
                Log.i("error", e.getMessage());
            }

            Intent back = new Intent (this, ViewTeachersActivity.class);
            startActivity(back);

        });
    }
}