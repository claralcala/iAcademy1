package es.iescarrillo.iacademy1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Person;
import es.iescarrillo.iacademy1.models.Teacher;

import java.util.List;
import java.util.stream.Collectors;

public class TeacherAdapter extends ArrayAdapter<Teacher> {

    public TeacherAdapter (Context context, List<Teacher> teacherList){

        super(context, 0, teacherList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Teacher teacher = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_person, parent, false);

        }

        TextView tvNameL = convertView.findViewById(R.id.tvNameList);

        //En nuestro caso en la pantalla de inicio mostraremos el nombre y el apellido
        tvNameL.setText(teacher.getName() +" " + teacher.getSurname());

        return convertView;

    }
}