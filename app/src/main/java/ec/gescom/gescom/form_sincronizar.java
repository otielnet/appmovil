package ec.gescom.gescom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Clases.AyudaBD;
import Clases.Conexion;
import Entidad.VolleySingleton;
import ec.gescom.gescom.interfaces.IComunicaFragments;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link form_sincronizar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link form_sincronizar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class form_sincronizar extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {


    FloatingActionButton btnInicio2;
    Bitmap bitmap;
    Button Sincronizar;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;
    ProgressDialog progreso;
    String CLIENTE_ID;
    String CEDENTE;
    String CEDULA;
    String ACCION;
    String RESPUESTA;
    String FECHA_VISITA;
    String FECHA_PROMESA;
    String HORA;
    String VALOR;
    String OBSERVACION;
    String UBICACION;
    String FECHA_GESTION;
    String CONTACTO;
    String RECIBO;
    String GESTION_ID;
    String GESTION_ID_ASIG;
    String URLIMAGEN1;
    String NOMBREIMAGEN1;
    String INTENSIDAD;
    String DIRECCION1;
    String DIRECCION2;
    String REFERENCIA_DOMICILIO;
    String ESTADO_DIRECCION;
    String path;
    ////ProgressDialog progreso;
    IComunicaFragments interfaceComunicaFragments;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.form_sincronizar, container, false);

        btnInicio2= v.findViewById(R.id.btnHome);
        btnInicio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.Home();
            }
        });

        Sincronizar = (Button) v.findViewById(R.id.btnSincronizar);
        Sincronizar.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {

                ////funcion para sincronizar datos de gestiones y de datos de domiciliario


                final AyudaBD ayudabd = new AyudaBD(getContext());
                SQLiteDatabase db = ayudabd.getReadableDatabase();
                Cursor cursor=db.rawQuery("SELECT * FROM "+ AyudaBD.DatosTabla.NOMBRE_TABLA_2 +" where estado=1",null);
                int contador=0;
                ContentValues valoresUpdate = new ContentValues();
                while (cursor.moveToNext()){
                    contador = contador + 1;
                    ////new form_sincronizar.CargarDatos().execute("http://186.4.174.162:8089/GescomAndroid/registro.php?CLIENTE_ID="+ cursor.getString(1)+"&CEDENTE="+ cursor.getString(2)+"&CEDULA="+ cursor.getString(3)+"&ACCION="+ cursor.getString(4)+"&RESPUESTA="+ cursor.getString(5)+"&FECHA_VISITA="+ cursor.getString(6)+"&FECHA_PROMESA="+ cursor.getString(7)+"&HORA="+ cursor.getString(8)+"&VALOR="+ cursor.getString(9)+"&OBSERVACION="+ cursor.getString(10)+"&UBICACION="+ cursor.getString(12)+"&FECHA_GESTION="+ cursor.getString(13));

                    ////String exportar = "http://186.4.174.162:8089/GescomAndroid/registroGescom0620Leo.php?CLIENTE_ID="+ cursor.getString(1)+"&CEDENTE="+ cursor.getString(2)+"&CEDULA="+ cursor.getString(3)+"&ACCION="+ cursor.getString(4)+"&RESPUESTA="+ cursor.getString(5)+"&FECHA_VISITA="+ cursor.getString(6)+"&FECHA_PROMESA="+ cursor.getString(7)+"&HORA="+ cursor.getString(8)+"&VALOR="+ cursor.getString(9)+"&OBSERVACION="+ cursor.getString(10)+"&UBICACION="+ cursor.getString(12)+"&FECHA_GESTION="+ cursor.getString(13);
                    ////EnviarRecibirDatos(exportar, cursor.getString(0), contador);
                     GESTION_ID=cursor.getString(0);
                     CLIENTE_ID=cursor.getString(1);
                     CEDENTE=cursor.getString(2);
                     CEDULA=cursor.getString(3);
                     ACCION=cursor.getString(4);
                     RESPUESTA=cursor.getString(5);
                     FECHA_VISITA=cursor.getString(6);
                     FECHA_PROMESA=cursor.getString(7);
                     HORA=cursor.getString(8);
                     VALOR=cursor.getString(9);
                     OBSERVACION=cursor.getString(10);
                     UBICACION=cursor.getString(12);
                     FECHA_GESTION=cursor.getString(13);
                    CONTACTO=cursor.getString(14);
                    RECIBO=cursor.getString(15);
                    GESTION_ID_ASIG=cursor.getString(16);
                    URLIMAGEN1=cursor.getString(18);
                    NOMBREIMAGEN1=cursor.getString(19);
                    INTENSIDAD=cursor.getString(20);
                    ESTADO_DIRECCION=cursor.getString(21);
                    DIRECCION1=cursor.getString(22);
                    DIRECCION2=cursor.getString(23);
                    REFERENCIA_DOMICILIO=cursor.getString(24);

                    cargarWebService(CLIENTE_ID, CEDENTE, CEDULA, ACCION, RESPUESTA, FECHA_VISITA, FECHA_PROMESA, HORA, VALOR, OBSERVACION, UBICACION, FECHA_GESTION, GESTION_ID, CONTACTO, RECIBO, GESTION_ID_ASIG, URLIMAGEN1, NOMBREIMAGEN1, INTENSIDAD,ESTADO_DIRECCION,DIRECCION1,DIRECCION2,REFERENCIA_DOMICILIO);
                }
                if(contador == 0)
                {
                    Toast.makeText(getContext(), "No hay gestiones nuevas por enviar...", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Tus gestiones se están enviando a nuestros servidores de Gescom S.A ...", Toast.LENGTH_LONG).show();
                }
            }
        }
        );

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

    private void cargarWebService(final String CLIENTE_ID, final String CEDENTE, final String CEDULA, final String ACCION, final String RESPUESTA, final String FECHA_VISITA, final String FECHA_PROMESA, final String HORA, final String VALOR, final String OBSERVACION, final String UBICACION, final String FECHA_GESTION, final String GESTION_ID, final String CONTACTO, final String RECIBO, final String GESTION_ID_ASIG, final String URLIMAGEN1, final String NOMBREIMAGEN1, final String INTENSIDAD, final String ESTADO_DIRECCION, final String DIRECCION1, final String DIRECCION2, final String REFERENCIA_DOMICILIO) {

        /*progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();*/

        String ip=getString(R.string.ip);

        String url=ip+"/domiciliario/storage/registroGescom0620LeonardoVAALv3.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                final AyudaBD ayudabd = new AyudaBD(getContext());
                SQLiteDatabase db = ayudabd.getWritableDatabase();
                ContentValues valoresUpdate = new ContentValues();
                if (response.trim().equalsIgnoreCase("registrado")){
                    ////PARA CONTROLAR QUE LAS GESTIONES NO SE CARGUEN MAS DE DOS VECES EN LA BASE DE DATOS DE GESCOM
                    String[] parametros={GESTION_ID};
                    valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_ESTADO, 0);
                    db.update(AyudaBD.DatosTabla.NOMBRE_TABLA_2, valoresUpdate, AyudaBD.DatosTabla.COLUMNA_ID_2+"=?", parametros);
                    Toast.makeText(getContext(),"Se ha registrado con éxito",Toast.LENGTH_SHORT).show();
                    /////progreso.hide();
                }else{
                    Toast.makeText(getContext(),"No se ha registrado "+response.toString(),Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ",""+response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String CLIENTE_ID_JL = CLIENTE_ID;
                String CEDENTE_JL = CEDENTE;
                String CEDULA_JL = CEDULA;
                String ACCION_JL = ACCION;
                String RESPUESTA_JL = RESPUESTA;
                String FECHA_VISITA_JL = FECHA_VISITA;
                String FECHA_PROMESA_JL = FECHA_PROMESA;
                String HORA_JL = HORA;
                String VALOR_JL = VALOR;
                String OBSERVACION_JL = OBSERVACION;
                String UBICACION_JL = UBICACION;
                String FECHA_GESTION_JL = FECHA_GESTION;
                String CONTACTO_JL = CONTACTO;
                String RECIBO_JL = RECIBO;
                String GESTION_ID_ASIG_JL = GESTION_ID_ASIG;
                String NOMBREIMAGEN1_JL = NOMBREIMAGEN1;
                String URLIMAGEN1_JL = URLIMAGEN1;
                String INTENSIDAD_JL = INTENSIDAD;
                String ESTADO_DIRECCION_JL = ESTADO_DIRECCION;
                String DIRECCION1_JL = DIRECCION1;
                String DIRECCION2_JL = DIRECCION2;
                String REFERENCIA_DOMICILIO_JL = REFERENCIA_DOMICILIO;


                 String IMAGEN1 = null;
                if(URLIMAGEN1_JL.equals("SIN_IMAGEN"))
                {
                    IMAGEN1="SIN_IMAGEN";
                    NOMBREIMAGEN1_JL="SIN_IMAGEN";
                }
                else
                {

                    try {
                        MediaScannerConnection.scanFile(getContext(), new String[]{URLIMAGEN1_JL}, null,
                                new MediaScannerConnection.OnScanCompletedListener() {
                                    @Override
                                    public void onScanCompleted(String URLIMAGEN1_JL, Uri uri) {
                                        Log.i("Path", "" + URLIMAGEN1_JL);
                                    }
                                });

                        bitmap = BitmapFactory.decodeFile(URLIMAGEN1_JL);
                        bitmap=redimensionarImagen(bitmap,660,480);
                        IMAGEN1 = convertirImgString(bitmap);
                        ////String IMAGEN2 = "";
                    } catch (SecurityException io) {
                    }

                }


                Map<String, String> parametros = new HashMap<>();
                parametros.put("CLIENTE_ID", CLIENTE_ID_JL);
                parametros.put("CEDENTE", CEDENTE_JL);
                parametros.put("CEDULA", CEDULA_JL);
                parametros.put("ACCION", ACCION_JL);
                parametros.put("RESPUESTA", RESPUESTA_JL);
                parametros.put("FECHA_VISITA", FECHA_VISITA_JL);
                parametros.put("FECHA_PROMESA", FECHA_PROMESA_JL);
                parametros.put("HORA", HORA_JL);
                parametros.put("VALOR", VALOR_JL);
                parametros.put("OBSERVACION", OBSERVACION_JL);
                parametros.put("UBICACION", UBICACION_JL);
                parametros.put("FECHA_GESTION", FECHA_GESTION_JL);
                parametros.put("CONTACTO", CONTACTO_JL);
                parametros.put("RECIBO", RECIBO_JL);
                parametros.put("GESTION_ID_ASIG", GESTION_ID_ASIG_JL);
                parametros.put("IMAGEN1", IMAGEN1);
                parametros.put("NOMBREIMAGEN1", NOMBREIMAGEN1_JL);
                parametros.put("INTENSIDAD", INTENSIDAD_JL);
                parametros.put("ESTADO_DIRECCION", ESTADO_DIRECCION_JL);
                parametros.put("DIRECCION1", DIRECCION1_JL);
                parametros.put("DIRECCION2", DIRECCION2_JL);
                parametros.put("REFERENCIA_DOMICILIO", REFERENCIA_DOMICILIO_JL);

                return parametros;
            }
        };
        //request.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }


    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        int ancho=bitmap.getWidth();
        int alto=bitmap.getHeight();

        if(ancho>anchoNuevo || alto>altoNuevo){
            float escalaAncho=anchoNuevo/ancho;
            float escalaAlto= altoNuevo/alto;

            Matrix matrix=new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

        }else{
            return bitmap;
        }


    }


    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }



    /*private class CargarDatos extends AsyncTask<String, Void, String> {
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

            ////Toast.makeText(getContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();
            ///Toast.makeText(getApplicationContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();

        }
    }*/
}
