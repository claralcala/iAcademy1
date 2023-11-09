package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import es.iescarrillo.iacademy1.R;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Button btnVisualizarAcademias = findViewById(R.id.btnVisualizarAcademias);

        btnVisualizarAcademias.setOnClickListener(v -> {

            Intent intent = new Intent(this, ViewAcademy.class);

            startActivity(intent);
        });

    }
}