package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import es.iescarrillo.iacademy1.R;

public class TeacherDetailsActivity extends AppCompatActivity {

    Button btnBack, btnDelete;

    TextView tvName, tvSurname, tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);


        btnDelete=findViewById(R.id.btnDelete);
        btnBack=findViewById(R.id.btnBack);
        tvName=findViewById(R.id.tvTeacherName);
        tvSurname= findViewById(R.id.tvTeacherSurname);

        Intent intent = getIntent();

        tvName.setText(intent.getStringExtra("name"));
        tvSurname.setText(intent.getStringExtra("surname"));


    }
}