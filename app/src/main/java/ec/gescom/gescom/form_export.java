package ec.gescom.gescom;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import Clases.AyudaBD;
import Clases.Conexion;
import ec.gescom.gescom.interfaces.IComunicaFragments;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link form_export.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link form_export#newInstance} factory method to
 * create an instance of this fragment.
 */
public class form_export extends Fragment {

   EditText txtproceso, txtUsuario;
    FloatingActionButton btnInicio;
    private Button btnExportar;
    ////private Button btnconsultar;
    IComunicaFragments interfaceComunicaFragments;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.form_export, container, false);

        txtproceso = (EditText)v.findViewById(R.id.txtproceso);
        txtUsuario = (EditText)v.findViewById(R.id.txtUsuario);
        btnExportar=(Button) v.findViewById(R.id.btnExportar);
        ///btnconsultar=(Button) v.findViewById(R.id.btnconsultar);

       /// btnInicio=v.findViewById(R.id.btnHome);
        btnInicio= v.findViewById(R.id.btnHome);
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.Home();

            }
        });



        btnExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String fecha = "2023-03-05 V20";
                ///String exportar ="http://192.168.1.19/GescomAndroid/consulta.php?usuario="+txtUsuario.getText().toString()+"&proceso="+txtproceso.getText().toString();
                String exportar ="http://186.4.186.102:8089/domiciliario/storage/consulta.php?usuario="+txtUsuario.getText().toString()+"&proceso="+fecha;
                EnviarRecibirDatos(exportar);

            }
        });


        /*btnconsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new form_export.CargarDatos().execute("http://192.168.1.19/GescomAndroid/registro.php?CARTERA="+txtproceso.getText().toString()+"&CALIF_DE_RIESGO="+txtUsuario.getText().toString());

            }
        });*/


        return v;
    }

    Activity actividad;
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

    public void EnviarRecibirDatos(String URL){

        ////Toast.makeText(getContext(), ""+URL, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "Conectado con nuestro WebService Gescom...", Toast.LENGTH_LONG).show();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        //Toast.makeText(getContext(), "LLEGO AQUI 1" + queue, Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        ///Toast.makeText(getContext(), "LLEGO AQUI 1", Toast.LENGTH_LONG).show();
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        GuardarDatos(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }


    private class CargarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            Conexion conn = new Conexion();
            // params comes from the execute() call: params[0] is the url.
            try {
                return conn.downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();
            ///Toast.makeText(getApplicationContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();

        }
    }

    public class ExportarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            Conexion conn = new Conexion();
            // params comes from the execute() call: params[0] is the url.
            try {
                return conn.downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            JSONArray ja = null;
            result = result.replace("][",",");
            try {
                if(result.length()>0)
                {
                    ja = new JSONArray(result);
                    Log.i("sizejson",""+ja.length());
                    GuardarDatos(ja);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void GuardarDatos(JSONArray ja)
    {
        final AyudaBD ayudabd = new AyudaBD(getContext());

        ArrayList<String> lista = new ArrayList<>();
        ////PARA PONER TODOS EN ESTADO 0
        String valoresc = "1";
        String[] parametrosv={valoresc};
        SQLiteDatabase db2 = ayudabd.getWritableDatabase();
        ContentValues valoresUpdateDisable = new ContentValues();
        valoresUpdateDisable.put(AyudaBD.DatosTabla.COLUMNA_ESTADO, 0);
        db2.update(AyudaBD.DatosTabla.NOMBRE_TABLA, valoresUpdateDisable, AyudaBD.DatosTabla.COLUMNA_ESTADO+"=?", parametrosv);
        for(int i=0; i<ja.length();i+=30)
        {
            try
            {   int contador=0;
                //ja.getString(i);
                SQLiteDatabase db = ayudabd.getWritableDatabase();

                Cursor cursor=db.rawQuery("SELECT * FROM "+ AyudaBD.DatosTabla.NOMBRE_TABLA +" where id="+ja.getString(i),null);

                while (cursor.moveToNext())
                {
                    contador ++;

                }
                if(contador==1)
                {
                    ///PARA VER DATOS EXISTENTES AQUI ES UNA ACTUALIZACION A LA BASE DE DATOS DEL CELULAR
                    ////Toast.makeText(getContext(), "Datos existentes", Toast.LENGTH_LONG).show();
                    ContentValues valoresUpdate = new ContentValues();
                    String[] parametros={ja.getString(i)};
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_CARTERA,  ja.getString(i+1));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_CALIF_DE_RIESGO,  ja.getString(i+2));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_CEDULA_CNS,  ja.getString(i+3));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_NOMBRE,  ja.getString(i+4));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_PROVINCIA,  ja.getString(i+5));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_CANTON,  ja.getString(i+6));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_PARROQUIA,  ja.getString(i+7));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_DIRECCION_1,  ja.getString(i+8));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_DIRECCION_2,  ja.getString(i+9));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_TELFCNS,  ja.getString(i+10));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_TELFCEL,  ja.getString(i+11));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_TELF_REFERENCIA,  ja.getString(i+12));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_NOMBRE_REFERENCIA,  ja.getString(i+13));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_TOTAL_DEUDA,  ja.getString(i+14));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_ASIGNACION,  ja.getString(i+15));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_EMAIL,  ja.getString(i+16));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_REF_DOMICILIO,  ja.getString(i+17));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_REF_DESPACHO,  ja.getString(i+18));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_TELEFONO_1,  ja.getString(i+19));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_TELEFONO_2,  ja.getString(i+20));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_ZONA,  ja.getString(i+21));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_SECTOR,  ja.getString(i+22));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_PRIMER_PEDIDO,  ja.getString(i+23));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_ESTADO,  ja.getString(i+24));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_ID_GESTION,  ja.getString(i+25));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_COD_DIRECCION_1,  ja.getString(i+26));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_COD_DIRECCION_2,  ja.getString(i+27));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_COD_DIRECCION_2,  ja.getString(i+27));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_MORA,  ja.getString(i+28));
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_ABONOS,  ja.getString(i+29));
                    db.update(AyudaBD.DatosTabla.NOMBRE_TABLA, valoresUpdate, AyudaBD.DatosTabla.COLUMNA_ID+"=?", parametros);

                }
                else
                {
                    ContentValues valores = new ContentValues();
                    valores.put(AyudaBD.DatosTabla.COLUMNA_ID,ja.getString(i));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_CARTERA,  ja.getString(i+1));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_CALIF_DE_RIESGO,  ja.getString(i+2));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_CEDULA_CNS,  ja.getString(i+3));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_NOMBRE,  ja.getString(i+4));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_PROVINCIA,  ja.getString(i+5));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_CANTON,  ja.getString(i+6));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_PARROQUIA,  ja.getString(i+7));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_DIRECCION_1,  ja.getString(i+8));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_DIRECCION_2,  ja.getString(i+9));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_TELFCNS,  ja.getString(i+10));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_TELFCEL,  ja.getString(i+11));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_TELF_REFERENCIA,  ja.getString(i+12));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_NOMBRE_REFERENCIA,  ja.getString(i+13));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_TOTAL_DEUDA,  ja.getString(i+14));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_ASIGNACION,  ja.getString(i+15));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_EMAIL,  ja.getString(i+16));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_REF_DOMICILIO,  ja.getString(i+17));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_REF_DESPACHO,  ja.getString(i+18));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_TELEFONO_1,  ja.getString(i+19));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_TELEFONO_2,  ja.getString(i+20));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_ZONA,  ja.getString(i+21));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_SECTOR,  ja.getString(i+22));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_PRIMER_PEDIDO,  ja.getString(i+23));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_ESTADO,  ja.getString(i+24));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_ID_GESTION,  ja.getString(i+25));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_COD_DIRECCION_1,  ja.getString(i+26));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_COD_DIRECCION_2,  ja.getString(i+27));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_MORA,  ja.getString(i+28));
                    valores.put(AyudaBD.DatosTabla.COLUMNA_ABONOS,  ja.getString(i+29));
                    Long IdGuardado = db.insert(AyudaBD.DatosTabla.NOMBRE_TABLA, AyudaBD.DatosTabla.COLUMNA_ID, valores);
                }


            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
        Toast.makeText(getContext(), "La base de datos ya esta importada en su celular", Toast.LENGTH_LONG).show();
    }
}
