package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.services.CourseService;


public class ManagerCourseDetails extends AppCompatActivity {

    Button btnBack, btnDelete, btnEdit;
    TextView tvTitle, tvDescription, tvLevel, tvCapacity, tvstart, tvend, tvActivated;

    private CourseService courseService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_course_details);

        Intent intent = getIntent();

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



        courseService = new CourseService(getApplication());

        tvTitle.setText(intent.getStringExtra("title"));
        tvDescription.setText(intent.getStringExtra("description"));
        tvLevel.setText(intent.getStringExtra("level"));
        tvCapacity.setText(intent.getStringExtra("capacity"));
        tvstart.setText(intent.getStringExtra("startDate"));
        tvend.setText(intent.getStringExtra("endDate"));
        tvActivated.setText(intent.getStringExtra("activated"));



        btnBack.setOnClickListener(v -> {
            Intent back = new Intent(this, ManagerViewCourses.class);
            startActivity(back);
        });



        btnDelete.setOnClickListener(v -> {


                Thread thread = new Thread(()->{


                   courseService.deleteCourseById(Long.parseLong(intent.getStringExtra("id")));


                });


                thread.start();
                try{
                    thread.join();
                }catch(Exception e ){
                    Log.i("error", e.getMessage());
                }

                Intent back = new Intent (this, ManagerViewCourses.class);
                startActivity(back);

        });



    }
}