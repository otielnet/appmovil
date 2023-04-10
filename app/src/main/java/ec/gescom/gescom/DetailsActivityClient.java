package ec.gescom.gescom;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adaptadores.AdaptadorDetailsClientRV;
import Clases.AyudaBD;
import Entidad.detailsClient;



public class DetailsActivityClient extends AppCompatActivity {

    List<detailsClient> listaClientes;
    RecyclerView rvd;
    Tdomiciliario conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_client);

        String CedulaCliente="";
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
             CedulaCliente = extras.getString("cedulaCliente");

        }
        /*TextView cedulaCliente = (TextView) findViewById(R.id.cedulaCliente);
        cedulaCliente.setText(CedulaCliente);*/



        final AyudaBD ayudabd = new AyudaBD(this);
        conn=new Tdomiciliario(this,AyudaBD.DATABASE_NAME,null,1);

        listaClientes=new ArrayList<>();

        rvd= (RecyclerView) findViewById(R.id.rvd);
        rvd.setLayoutManager(new LinearLayoutManager(this));

        consultarListaCliente(CedulaCliente);

        AdaptadorDetailsClientRV adapter=new AdaptadorDetailsClientRV(listaClientes);
        rvd.setAdapter(adapter);



    }

    private void consultarListaCliente(String cedula) {
        SQLiteDatabase db=conn.getReadableDatabase();
        final AyudaBD ayudabd = new AyudaBD(this);
        detailsClient usuario=null;
        // listaUsuarios=new ArrayList<Usuario>();
        //select * from usuarios  "SELECT * FROM "+ AyudaBD.DatosTabla.NOMBRE_TABLA
        Cursor cursor=db.rawQuery("select * from "+ AyudaBD.DatosTabla.NOMBRE_TABLA +" where cedula_cns='"+cedula+"'",null);

        while (cursor.moveToNext()){
            usuario=new detailsClient();
            usuario.setImagenClient(R.drawable.gescom);
            usuario.setCedulaCliente(cursor.getString(3));
            usuario.setNombreCliente(cursor.getString(4));
            usuario.setValorCliente(cursor.getString(14));
            usuario.setCartera(cursor.getString(1));
            usuario.setAsignacion(cursor.getString(15));
            usuario.setDireccion1(cursor.getString(8));
            usuario.setDireccion2(cursor.getString(9));
            usuario.setCalificacionRiesgo(cursor.getString(2));
            usuario.setPrimerPedido(cursor.getString(23));
            usuario.setId(cursor.getString(0));
            usuario.setIdasig(cursor.getString(25));
            usuario.setDomRef(cursor.getString(17));
            usuario.setMora(cursor.getString(28));
            usuario.setAbonos(cursor.getString(29));

            ///usuario.setAsignacion(cursor.getString(4));

            listaClientes.add(usuario);
        }
    }
}
