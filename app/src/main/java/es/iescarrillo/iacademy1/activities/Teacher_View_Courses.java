package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import es.iescarrillo.iacademy1.R;

public class Teacher_View_Courses extends AppCompatActivity {

    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_courses);

        //Declaramos el boton para añadir
        btnAdd=findViewById(R.id.btnAdd);

        //Boton para ir a la pagina de añadir curso
        btnAdd.setOnClickListener(v -> {
            Intent add = new Intent(this, Teacher_Add_Course.class);
            startActivity(add);
        });
    }
}