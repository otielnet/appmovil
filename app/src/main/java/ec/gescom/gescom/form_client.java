package ec.gescom.gescom;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adaptadores.AdaptadorListClientRV;
import Clases.AyudaBD;
import Entidad.Client;
import ec.gescom.gescom.interfaces.IComunicaFragments;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link form_client.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link form_client#newInstance} factory method to
 * create an instance of this fragment.
 */
public class form_client extends Fragment {


    List<Client> listaClientes;
    RecyclerView rv;
    Tdomiciliario conn;
    EditText documento;
    Button busqueda;
    TextView mensaje;
    FloatingActionButton btnInicio;
    IComunicaFragments interfaceComunicaFragments;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.form_client, container, false);
        //// para el home
        btnInicio= v.findViewById(R.id.btnHome);
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.Home();

            }
        });

        rv = (RecyclerView)v.findViewById(R.id.rv_seach);
        documento = (EditText)v.findViewById(R.id.campoDocumento);
        busqueda = (Button)v.findViewById(R.id.btnConsultarUsuario);
        mensaje = (TextView)v.findViewById(R.id.mensajeNotificacion);
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
            mensaje.setText("Documento vacio");
        }
        else
        {
            /////Toast.makeText(getContext(), documento, Toast.LENGTH_LONG).show();
            Cursor cursor=db.rawQuery("SELECT cedula_cns, nombre, sum(total_deuda), count(*) FROM "+ AyudaBD.DatosTabla.NOMBRE_TABLA +" where estado in (1,2) and cedula_cns='"+documento+"' group by cedula_cns, nombre",null);

            while (cursor.moveToNext()){
                contador=1;
                usuario=new Client();
                usuario.setImagenClient(R.drawable.gescom);
                usuario.setCedulaCliente(cursor.getString(0));
                usuario.setNombreCliente(cursor.getString(1));
                usuario.setValorCliente(cursor.getString(2));
                usuario.setObligacionCliente(cursor.getString(3));
                ///usuario.setAsignacion(cursor.getString(4));
                mensaje.setText("El cliente encontrado");

                listaClientes.add(usuario);
            }

            if(contador==0)
            {
                mensaje.setText("El cliente no se encuentra");
            }
            else
            {

            }

        }

    }


}
