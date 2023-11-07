package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.services.TeacherService;


public class ViewTeachersActivity extends AppCompatActivity {

    Button btnAddTeacher, btnBack;

    private TeacherService teacherService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teachers);

        btnAddTeacher=findViewById(R.id.btnAddTeacher);
        btnBack=findViewById(R.id.btnBack);

        btnAddTeacher.setOnClickListener(v->{
            Intent addTeacher = new Intent(this, ManagerRegisterTeacherActivity.class);
            startActivity(addTeacher);

        });


        btnBack.setOnClickListener(v->{
            Intent back = new Intent(this, ManagerMainActivity.class);
            startActivity(back);
        });
    }
}