package es.iescarrillo.iacademy1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Person;

public class CourseAdapter extends ArrayAdapter<Course> {

    public CourseAdapter(Context context, List<Course> courseList){
        //Como nosotros usamos SortedSet, casteamos para no tener problemas
        super(context, 0, courseList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Course course = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_person, parent, false);

        }

        TextView tvNameL = convertView.findViewById(R.id.tvNameList);

        //En nuestro caso en la pantalla de inicio mostraremos el nombre y el apellido
        tvNameL.setText(course.getTitle());

        return convertView;

    }
}
