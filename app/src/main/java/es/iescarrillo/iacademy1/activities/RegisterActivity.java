package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import es.iescarrillo.iacademy1.R;

/**
 * @author clara
 *
 * Pantalla para seleccionar el registro (gerente o estudiante)
 */
public class RegisterActivity extends AppCompatActivity {

    Button btnRegisterStudent;
    Button btnRegisterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Botón para registrar estudiante que lleva a su activity
        btnRegisterStudent = findViewById(R.id.btnRegisterStudent);
        btnRegisterStudent.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterStudentActivity.class);
            startActivity(intent);

        });

        //Botón para registrar manager que lleva a su activity
        btnRegisterManager=findViewById(R.id.btnRegisterManager);
        btnRegisterManager.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterManagerActivity.class);
            startActivity(intent);
        });

    }
}