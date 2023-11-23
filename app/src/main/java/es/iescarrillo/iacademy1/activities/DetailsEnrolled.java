package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.adapters.LessonAdapter;
import es.iescarrillo.iacademy1.models.Lesson;
import es.iescarrillo.iacademy1.services.LessonService;

public class DetailsEnrolled extends AppCompatActivity {

    private LessonService lessonService;
    private List<Lesson> lesson;

    private LessonAdapter lessonAdapter;

    ListView lvlessonEnrroled;


    Button btnBackDetailsLesson;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_enrolled);

        SharedPreferences sharedPreferences = getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id_= sharedPreferences.getLong("id", 0);

        Intent  intentID= getIntent();
        long courseID = intentID.getLongExtra("id",0);


        lvlessonEnrroled=findViewById(R.id.lvlessonEnrroled);

        lessonService = new LessonService(getApplication());

        Thread thread = new Thread(()->{

        lesson = lessonService.getLessonWithCouseById(courseID);
        });

        thread.start();
        try{
            thread.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }


        lessonAdapter = new LessonAdapter((Context)this, lesson);

        lvlessonEnrroled.setAdapter(lessonAdapter);

        lvlessonEnrroled.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentLesson = new Intent(DetailsEnrolled.this, StudentLessonDetails.class);
                startActivity(intentLesson);
            }
        });

        btnBackDetailsLesson =  findViewById(R.id.btnBackDetailsLesson);

        btnBackDetailsLesson.setOnClickListener( v -> {

            Intent intent = new Intent(this, CoursesRegistration.class);

            startActivity(intent);
        });


    }
}
