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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Adaptadores.AdaptadorListClientRV;
import Clases.AyudaBD;
import Entidad.Client;
import ec.gescom.gescom.interfaces.IComunicaFragments;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link list_client.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link list_client#newInstance} factory method to
 * create an instance of this fragment.
 */
public class list_client extends Fragment {

    private Button btnExportar;
    private TextView lblcartera;
    FloatingActionButton btnInicio;
    ////private EditText txtUsuario;
    ////private Context thisContext=this;
    List<Client> listaClientes;
    RecyclerView rv;
    Tdomiciliario conn;
    IComunicaFragments interfaceComunicaFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.list_client, container, false);
        rv = (RecyclerView)v.findViewById(R.id.rv);
        final AyudaBD ayudabd = new AyudaBD(getContext());
        conn=new Tdomiciliario(getContext(),AyudaBD.DATABASE_NAME,null,1);
        btnInicio= v.findViewById(R.id.btnHome);
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.Home();

            }
        });
        listaClientes=new ArrayList<>();

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        consultarListaPersonas();

        AdaptadorListClientRV adapter=new AdaptadorListClientRV(listaClientes);
        rv.setAdapter(adapter);

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
    private void consultarListaPersonas() {
        final AyudaBD ayudabd = new AyudaBD(getContext());
        ////SQLiteDatabase db=conn.getReadableDatabase();
        SQLiteDatabase db = ayudabd.getReadableDatabase();
        Client usuario=null;
        // listaUsuarios=new ArrayList<Usuario>();
        //select * from usuarios
        Cursor cursor=db.rawQuery("SELECT cedula_cns, nombre, sum(total_deuda), count(*) FROM "+ AyudaBD.DatosTabla.NOMBRE_TABLA +" where estado=1 group by cedula_cns, nombre",null);

        while (cursor.moveToNext()){
            usuario=new Client();
            usuario.setImagenClient(R.drawable.gescom);
            usuario.setCedulaCliente(cursor.getString(0));
            usuario.setNombreCliente(cursor.getString(1));
            usuario.setValorCliente(cursor.getString(2));
            usuario.setObligacionCliente(cursor.getString(3));
            ///usuario.setAsignacion(cursor.getString(4));

            listaClientes.add(usuario);
        }
    }
}
