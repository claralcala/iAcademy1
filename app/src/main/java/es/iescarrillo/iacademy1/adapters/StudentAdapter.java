package es.iescarrillo.iacademy1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Student;
import es.iescarrillo.iacademy1.models.Teacher;

public class StudentAdapter extends ArrayAdapter<Student> {

    public StudentAdapter (Context context, List<Student> studentList){

        super(context, 0, studentList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Student student = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_person, parent, false);

        }

        TextView tvNameL = convertView.findViewById(R.id.tvNameList);

        //En nuestro caso en la pantalla de inicio mostraremos el nombre y el apellido
        tvNameL.setText(student.getName() +" " + student.getSurname());

        return convertView;

    }
}
