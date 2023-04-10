package ec.gescom.gescom;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Adaptadores.AdaptadorListClientRV;
import Clases.AyudaBD;
import Entidad.Client;
import ec.gescom.gescom.interfaces.IComunicaFragments;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link list_compromisos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link list_compromisos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class list_compromisos extends Fragment implements View.OnClickListener {

    private Button btnExportar;
    private TextView lblcartera;
    ////private EditText txtUsuario;
    ////private Context thisContext=this;
    FloatingActionButton btnInicio;
    IComunicaFragments interfaceComunicaFragments;
    List<Client> listaClientes;
    RecyclerView rv;
    Tdomiciliario conn;
    EditText documento;
    Button busqueda;
    TextView mensaje;
    private  int dia,mes,ano,hora2,minutos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.list_compromisos, container, false);
        /////para el home
        btnInicio= v.findViewById(R.id.btnHome);
        documento = (EditText)v.findViewById(R.id.editTextFechaCompromisohoy);
        busqueda = (Button)v.findViewById(R.id.btnConsultarCompromiso);
        mensaje = (TextView)v.findViewById(R.id.mensajeNotificacion);
        documento.setOnClickListener(this);
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.Home();

            }
        });
        rv = (RecyclerView)v.findViewById(R.id.rv);
        final AyudaBD ayudabd = new AyudaBD(getContext());
        conn=new Tdomiciliario(getContext(),AyudaBD.DATABASE_NAME,null,1);

        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaClientes=new ArrayList<>();
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                consultarListaPersonas(documento.getText().toString());
                AdaptadorListClientRV adapter=new AdaptadorListClientRV(listaClientes);
                rv.setAdapter(adapter);
            }
        });

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

    private void consultarListaPersonas(String documento) {
        final AyudaBD ayudabd = new AyudaBD(getContext());
        ////SQLiteDatabase db=conn.getReadableDatabase();
        SQLiteDatabase db = ayudabd.getReadableDatabase();
        Client usuario=null;
        // listaUsuarios=new ArrayList<Usuario>();
        //select * from usuarios
        int contador=0;
        if(documento.isEmpty())
        {
            mensaje.setText("Ingresa la Fecha Compromiso");
        }
        else {
            /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
            Date date = new Date();
            String fecha = dateFormat.format(date);*/
            Cursor cursor = db.rawQuery("SELECT d.cedula_cns, d.nombre, sum(d.total_deuda), count(*) FROM Directorio as d, gestiones as g where g.gestion_id=d.gestion_id and g.respuesta in ('COMPROMISO DE PAGO','COMPROMISO DE PAGO INDIRECTO') and SUBSTR(g.fecha_promesa,1,10)='" + documento + "' group by d.cedula_cns, d.nombre", null);

            while (cursor.moveToNext()) {
                contador=1;
                usuario = new Client();
                usuario.setImagenClient(R.drawable.gescom);
                usuario.setCedulaCliente(cursor.getString(0));
                usuario.setNombreCliente(cursor.getString(1));
                usuario.setValorCliente(cursor.getString(2));
                usuario.setObligacionCliente(cursor.getString(3));
                ///usuario.setAsignacion(cursor.getString(4));
                mensaje.setText("Compromisos encontrados");

                listaClientes.add(usuario);
            }

            if(contador==0)
            {
                mensaje.setText("No tienes compromisos para esta fecha");
            }
            else
            {

            }
        }

    }

    //////para diseño de las fecha y horas fechaCompromiso
    public void onClick(View v) {
        if(v==documento){
            final Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    documento.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                }
            }
                    ,ano,mes,dia);
            datePickerDialog.show();
        }



    }

}
