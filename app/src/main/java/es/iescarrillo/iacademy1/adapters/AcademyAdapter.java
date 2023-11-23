package es.iescarrillo.iacademy1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Classroom;

public class AcademyAdapter extends ArrayAdapter<Academy>{

        public AcademyAdapter (Context context, List<Academy> academiesList){
            //Como nosotros usamos SortedSet, casteamos para no tener problemas
            super(context, 0, academiesList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Academy academies = getItem(position);

            if(convertView==null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_person, parent, false);

            }

            TextView tvNameL = convertView.findViewById(R.id.tvNameList);

            //En nuestro caso en la pantalla de inicio mostraremos el nombre y el apellido
            tvNameL.setText(academies.getName());

            return convertView;

        }
    }

