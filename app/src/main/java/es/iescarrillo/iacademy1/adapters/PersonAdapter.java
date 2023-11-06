package es.iescarrillo.iacademy1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Person;

import java.util.List;
import java.util.stream.Collectors;

public class PersonAdapter extends ArrayAdapter<Person> {

public PersonAdapter (Context context, List<Person> personList){
        //Como nosotros usamos SortedSet, casteamos para no tener problemas
        super(context, 0, personList);
        }

@Override
public View getView(int position, View convertView, ViewGroup parent){
        Person person = getItem(position);

        if(convertView==null){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_person, parent, false);

        }

        TextView tvNameL = convertView.findViewById(R.id.tvNameList);

        //En nuestro caso en la pantalla de inicio mostraremos el nombre y el apellido
        tvNameL.setText(person.getName() +" " + person.getSurname());

        return convertView;

        }
}


