package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Teacher;
import es.iescarrillo.iacademy1.services.ManagerService;
import es.iescarrillo.iacademy1.services.TeacherService;

public class TeacherDetailsActivity extends AppCompatActivity {

    Button btnBack, btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);


        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);


        SharedPreferences sharedPreferences = getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);


        TextView tvTeacherName = findViewById(R.id.tvTeacherName);
        TextView tvTeacherSurname = findViewById(R.id.tvTeacherSurname);
        TextView tvTeacherEmail = findViewById(R.id.tvTeacherEmail);
        TextView tvTeacherDNI = findViewById(R.id.tvTeacherDNI);
        TextView tvTeacherPhone = findViewById(R.id.tvTeacherPhone);
        TextView tvTeacherAddress = findViewById(R.id.tvTeacherAddress);

// Dentro del método onCreate() o donde sea apropiado en TeacherDetailsActivity
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String surname = intent.getStringExtra("surname");
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");
        String dni = intent.getStringExtra("dni");
        String address = intent.getStringExtra("address");

// Ahora puedes usar estos datos como desees en tu actividad

        tvTeacherName.setText(name);
        tvTeacherSurname.setText(surname);
        tvTeacherEmail.setText(email);
        tvTeacherPhone.setText(phone);
        tvTeacherAddress.setText(address);
        tvTeacherDNI.setText(dni);


        btnBack.setOnClickListener(v->{
            Intent back = new Intent(this, ManagerMainActivity.class);
            startActivity(back);
        });
        

    }
}