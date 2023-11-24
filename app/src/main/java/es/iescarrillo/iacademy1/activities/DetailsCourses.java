package es.iescarrillo.iacademy1.activities;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Inscription;
import es.iescarrillo.iacademy1.models.Manager;
import es.iescarrillo.iacademy1.models.Student;
import es.iescarrillo.iacademy1.models.Teacher;
import es.iescarrillo.iacademy1.services.InscriptionService;

public class DetailsCourses extends AppCompatActivity {

    TextView tvTitle, tvDescription, tvLevel, tvCapacity, tvStartDate, tvEndDate, tvActivated;

    Button btnBackInfoCourses, btnRegistration;



    LocalDateTime currentDate;

    boolean alreadyEnrolled = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_courses);

        InscriptionService inscriptionService = new InscriptionService(getApplication());

        SharedPreferences sharedPreferences = getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);


        //¿Comprobar si el rol no es el de student y echarlo?
        if(!role.equals("STUDENT")){


            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);

        }


        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvLevel = findViewById(R.id.tvLevel);
        tvCapacity = findViewById(R.id.tvCapacity);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        tvActivated = findViewById(R.id.tvCourseActivated);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String level = intent.getStringExtra("level");
        int capacity = intent.getIntExtra("capacity", 0);
        LocalDate startDate = (LocalDate) intent.getSerializableExtra("start");
        LocalDate endDate = (LocalDate) intent.getSerializableExtra("end");
        boolean activated = intent.getBooleanExtra("activated", false);
        Long idCourse = intent.getLongExtra("id",0);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);

        tvTitle.setText(title);
        tvDescription.setText(description);
        tvLevel.setText(level);
        tvCapacity.setText(String.valueOf(capacity));
        tvStartDate.setText(formattedStartDate.toString());
        tvEndDate.setText(formattedEndDate.toString());

        btnBackInfoCourses = findViewById(R.id.btnBackInfoCourses);

        btnBackInfoCourses.setOnClickListener(v -> {

            Intent intentBack = new Intent(this, ViewCourses.class);

            startActivity(intentBack);

        });

        Inscription matricula = new Inscription();

        btnRegistration = findViewById(R.id.btnRegistration);

        if (activated == true) {

            btnRegistration.setClickable(true);
            btnRegistration.setBackgroundColor(getResources().getColor(R.color.verde));
            Toast.makeText(DetailsCourses.this, "Matriculación habilitada. ¡Puedes matricularte!", Toast.LENGTH_SHORT).show();
            btnRegistration.setOnClickListener(v -> {
                currentDate = LocalDateTime.now();

                matricula.setCourse_id(idCourse);
                matricula.setRegistrationTime(currentDate);
                matricula.setStudent_id(id);

                // Verificar si el estudiante ya está matriculado en el mismo curso

                try {
                    Thread thread2 = new Thread(() -> {

                        alreadyEnrolled = !inscriptionService.isStudentEnrolled(id, idCourse);
                    });

                    thread2.start();
                    try {
                        thread2.join();
                        Toast.makeText(DetailsCourses.this, "Matriculación Correcta.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("Error", "Error al realizar la matriculación", e);
                    }
                } catch (Exception e) {
                    Log.e("Error", "Error al verificar si el estudiante está matriculado", e);
                }

                if (!alreadyEnrolled) {
                    // Si ya está matriculado, mostramos mensaje
                    Toast.makeText(DetailsCourses.this, "Ya estás matriculado en este curso.", Toast.LENGTH_SHORT).show();
                } else {
                    // Si no está matriculado, proceder con la matriculación
                    Thread thread = new Thread(() -> {
                        inscriptionService.insertInscription(matricula);
                    });

                    thread.start();
                    try {
                        thread.join();
                        Toast.makeText(DetailsCourses.this, "Matriculación Correcta.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("Error", "Error al realizar la matriculación", e);
                    }
                }
            });

        } else {
            btnRegistration.setClickable(false);
            btnRegistration.setBackgroundColor(getResources().getColor(R.color.rojo));
            Toast.makeText(DetailsCourses.this, "Matriculación deshabilitada. ¡No es posible matricularte en este momento!", Toast.LENGTH_SHORT).show();
        }



    }

}

