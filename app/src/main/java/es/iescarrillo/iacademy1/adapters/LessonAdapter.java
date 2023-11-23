package es.iescarrillo.iacademy1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Lesson;
import es.iescarrillo.iacademy1.models.Student;

public class LessonAdapter extends ArrayAdapter<Lesson> {

    public LessonAdapter (Context context, List<Lesson> lessonList){

        super(context, 0, lessonList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Lesson lesson = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_person, parent, false);

        }

        TextView tvNameL = convertView.findViewById(R.id.tvNameList);

        //En nuestro caso en la pantalla de inicio mostraremos el nombre y el apellido
        tvNameL.setText(lesson.getLessonDate() +" " + lesson.getLessonHour());

        return convertView;

    }
}
