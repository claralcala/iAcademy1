package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import es.iescarrillo.iacademy1.R;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegisterStudent;
    Button btnRegisterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegisterStudent = findViewById(R.id.btnRegisterStudent);
        btnRegisterStudent.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterStudentActivity.class);
            startActivity(intent);

        });

        btnRegisterManager=findViewById(R.id.btnRegisterManager);
        btnRegisterManager.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterManagerActivity.class);
            startActivity(intent);
        });

    }
}