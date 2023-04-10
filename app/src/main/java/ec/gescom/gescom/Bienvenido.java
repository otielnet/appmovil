package ec.gescom.gescom;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import Clases.AyudaBD;
import Clases.Conexion;
import ec.gescom.gescom.interfaces.IComunicaFragments;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Bienvenido.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Bienvenido#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bienvenido extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        vista=  inflater.inflate(R.layout.form_bienvenido, container, false);
        CardCompromiso= vista.findViewById(R.id.CardCompromiso);
        CardListaCliente= vista.findViewById(R.id.CardListaCliente);
        CardSeguimiento= vista.findViewById(R.id.CardSeguimiento);
        CardBusquedaCedula= vista.findViewById(R.id.CardBusquedaCedula);
        CardImportar= vista.findViewById(R.id.CardImportar);
        CardExportar= vista.findViewById(R.id.CardExportar);


        
        eventosMenu();
        
        return vista;
    }

    private void eventosMenu() {

        CardCompromiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Toast.makeText(getContext(),"Iniciar Juego Activity",Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.iniciarCompromiso();
            }
        });

        CardListaCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Toast.makeText(getContext(),"Iniciar Juego Activity",Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.iniciarListaCliente();
            }
        });

        CardSeguimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Toast.makeText(getContext(),"Iniciar Juego Activity",Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.iniciarSeguimiento();
            }
        });

        CardBusquedaCedula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Toast.makeText(getContext(),"Iniciar Juego Activity",Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.iniciarBusquedaCedula();
            }
        });

        CardImportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Toast.makeText(getContext(),"Iniciar Juego Activity",Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.iniciarImportar();
            }
        });

        CardExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Toast.makeText(getContext(),"Iniciar Juego Activity",Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.iniciarExportar();
            }
        });
    }


    View vista;
    Activity actividad;
    CardView CardCompromiso, CardListaCliente, CardSeguimiento, CardBusquedaCedula, CardImportar, CardExportar;
    IComunicaFragments interfaceComunicaFragments;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.actividad=(Activity) context;
            interfaceComunicaFragments= (IComunicaFragments) this.actividad;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }



}
