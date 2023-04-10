package ec.gescom.gescom;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.design.widget.Snackbar;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Clases.AyudaBD;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class GestionCliente extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner opcionesAccion, opccionesRespuesta, opccionesContacto, opccionTipoGestion;
    EditText fechaVisita, hora, fechaCompromiso, observacionGestion, valorClienteCp, ubicacion, Erecibo, Direccion1, Direccion2, DirecccionReferencia;
    Button Guardar, gps, botonCargar;
    Context context=this;
    TextView VfechaVisita, vhora, vfechaCompromiso, VvalorClienteCp, Vrecibo, Vdireccion1, Vdireccion2, VdirecccionReferencia;
    String urlImagen1="SIN_IMAGEN";
    private  int dia,mes,ano,hora2,minutos;
    ImageView imagenCamara_1;
    String path;
    String nombreImagen1="";
    File fileImagen;
    Bitmap bitmap;
    /*private final String CARPETA_RAIZ="GescomImagen/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";*/

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "GescomImagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_cliente);

        imagenCamara_1= (ImageView) findViewById(R.id.imagemIdCamara1);
        botonCargar= (Button) findViewById(R.id.btnCargarImgCamara1);

        ///botonCargar.setVisibility(View.GONE);
        ///imagenCamara_1.setVisibility(View.GONE);
        if(validaPermisos()){
            botonCargar.setEnabled(true);
        }else{
            botonCargar.setEnabled(false);
        }

        opccionTipoGestion = (Spinner) findViewById(R.id.TipocomboAccion);
        opcionesAccion = (Spinner) findViewById(R.id.comboAccion);
        opccionesRespuesta = (Spinner) findViewById(R.id.comboRespuesta);
        opccionesContacto = (Spinner) findViewById(R.id.comboContacto);
        fechaVisita = (EditText) findViewById(R.id.editTextFechaVisita);
        VfechaVisita = (TextView) findViewById(R.id.textViewFechaVisita);
        hora = (EditText) findViewById(R.id.editTextHora);
        vhora = (TextView) findViewById(R.id.textViewHora);
        fechaCompromiso = (EditText) findViewById(R.id.editTextFechaCompromiso);
        vfechaCompromiso = (TextView) findViewById(R.id.textViewFechaCompromiso);
        observacionGestion = (EditText) findViewById(R.id.editTextObservacion);
        valorClienteCp = (EditText) findViewById(R.id.editTextValor);
        VvalorClienteCp = (TextView) findViewById(R.id.textViewValor);
        Erecibo = (EditText) findViewById(R.id.editTextRecibo);
        Vrecibo = (TextView) findViewById(R.id.textViewRecibo);
        Guardar = (Button) findViewById(R.id.button);
        gps = (Button) findViewById(R.id.buttonGps);
        ubicacion = (EditText) findViewById(R.id.editTextUbicacion);
        hora.setOnClickListener(this);
        fechaCompromiso.setOnClickListener(this);
        fechaVisita.setOnClickListener(this);
        Direccion1 = (EditText) findViewById(R.id.editTextDireccionP);
        Direccion2 = (EditText) findViewById(R.id.editTextDireccionS);
        DirecccionReferencia = (EditText) findViewById(R.id.editTextDReferencia);

        Vdireccion1 = (TextView) findViewById(R.id.textViewDireccion1);
        Vdireccion2 = (TextView) findViewById(R.id.textViewDireccion2);
        VdirecccionReferencia = (TextView) findViewById(R.id.textViewDReferencia);

        botonCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
            }
        });

        gps.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                LocationManager locationManager = (LocationManager) GestionCliente.this.getSystemService(context.LOCATION_SERVICE);
                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        ///makeUseOfNewLocation(location);
                        ubicacion.setText(""+location.getLatitude()+", "+location.getLongitude());
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };
                int permissionCheck = ContextCompat.checkSelfPermission(GestionCliente.this, Manifest.permission.ACCESS_FINE_LOCATION);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, locationListener);
            }

        });

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck== PackageManager.PERMISSION_DENIED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }

        }

        ///start accion
         ///ArrayAdapter<CharSequence> adpterSp = ArrayAdapter.createFromResource(this, R.array.opcionesAccion, android.R.layout.simple_spinner_item);
        //////tipo de gestiones
        ArrayAdapter<CharSequence> adpterSptip = ArrayAdapter.createFromResource(this, R.array.opcionesTipoGestion, android.R.layout.simple_spinner_item);
        //////courier ArrayAdapter<CharSequence> adpterSp = ArrayAdapter.createFromResource(this, R.array.opcionGestion, android.R.layout.simple_spinner_item);
        ///adpterSp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ///adpterSptip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        opccionTipoGestion.setAdapter(adpterSptip);
        opccionTipoGestion.setOnItemSelectedListener(this);
        //opcionesAccion.setAdapter(adpterSp);
        //opcionesAccion.setOnItemSelectedListener(this);
        ///opccionesRespuesta.setOnItemSelectedListener(this);

        ///end accion
        /*ArrayAdapter<CharSequence> adpterCon = ArrayAdapter.createFromResource(this, R.array.opcionesContacto, android.R.layout.simple_spinner_item);
        opccionesContacto.setAdapter(adpterCon);*/
        /*ArrayAdapter<CharSequence> adpterRep = ArrayAdapter.createFromResource(this, R.array.opcionesRespuesta, android.R.layout.simple_spinner_item);
        opccionesRespuesta.setAdapter(adpterRep);*/
        String CedulaCliente="";
        String NombresCliente="";
        String ValorCliente="";
        String AsignacionCliente="";
        String CarteraCliente="";
        String CalificacioonRiesgoCliente="";
        String Direccion1Cliente="";
        String Direccion2Cliente="";
        String PrimerPedidoCliente="";
        String IdCliente="";
        String IdClienteAsig="";

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            CedulaCliente = extras.getString("cedulaCliente");
            NombresCliente = extras.getString("nombresCliente");
            ValorCliente = extras.getString("valorCliente");
            AsignacionCliente = extras.getString("asignacionCliente");
            CarteraCliente = extras.getString("carteraCliente");
            CalificacioonRiesgoCliente = extras.getString("calificacioonRiesgoCliente");
            Direccion1Cliente = extras.getString("direccion1Cliente");
            Direccion2Cliente = extras.getString("direccion2Cliente");
            PrimerPedidoCliente = extras.getString("primerPedidoCliente");
            IdCliente = extras.getString("idCliente");
            IdClienteAsig = extras.getString("idClienteAsig");
        }

        TextView idCliente = (TextView) findViewById(R.id.idObligacionClienteAsig);
        idCliente.setText(IdClienteAsig);
        TextView idClienteAsig = (TextView) findViewById(R.id.idGestionCliente);
        idClienteAsig.setText(IdCliente);
        TextView cedulaCliente = (TextView) findViewById(R.id.cedulaGestionCliente);
        cedulaCliente.setText(CedulaCliente);
        TextView nombresCliente = (TextView) findViewById(R.id.nombresGestionClientes);
        nombresCliente.setText(NombresCliente);
        TextView valorCliente = (TextView) findViewById(R.id.totaldeudaGestionClientes);
        valorCliente.setText(ValorCliente);
        TextView carteraCliente = (TextView) findViewById(R.id.carteraGestionClientes);
        carteraCliente.setText(CarteraCliente);
        TextView asignacionCliente = (TextView) findViewById(R.id.asignacionGestionClientes);
        asignacionCliente.setText(AsignacionCliente);
        TextView calificacioonRiesgoCliente = (TextView) findViewById(R.id.calificacionRiesgoGestionClientes);
        calificacioonRiesgoCliente.setText(CalificacioonRiesgoCliente);
        TextView primerPedidoCliente = (TextView) findViewById(R.id.primerPedidoGestionClientes);
        primerPedidoCliente.setText(PrimerPedidoCliente);
        final TextView direccion1Cliente = (TextView) findViewById(R.id.direccion1GestionClientes);
        direccion1Cliente.setText(Direccion1Cliente);
        TextView direccion2Cliente = (TextView) findViewById(R.id.direccion2GestionClientes);
        direccion2Cliente.setText(Direccion2Cliente);

        ////funcion pra guardar y actualizar
        final String finalIdCliente = IdCliente;
        final String finalCarteraCliente = CarteraCliente;
        final String finalCedulaCliente = CedulaCliente;
        final String finalIdClienteAsig = IdClienteAsig;
        Guardar.setOnClickListener(new View.OnClickListener() {
            final AyudaBD ayudabd = new AyudaBD(context);
            @Override
            public void onClick(View v) {
                int contador=0;
                int estado_cliente=0;
                int intensidad=0;
                int inten=0;
                SQLiteDatabase db2 = ayudabd.getWritableDatabase();
                Cursor cursor=db2.rawQuery("SELECT max(intensidad) FROM "+ AyudaBD.DatosTabla.NOMBRE_TABLA_2 +" where gestion_id="+finalIdClienteAsig,null);
                /////select max(g.`id`) as a from `gestiones` g
                ////
                while (cursor.moveToNext())
                {
                    inten = cursor.getInt(0);
                    ////String cadena = String.valueOf(inten);
                    ////Toast.makeText(context, cadena, Toast.LENGTH_LONG).show();
                    contador=1;
                }
                if(contador==1)
                {
                    ///Snackbar.make(getWindow().getDecorView().getRootView(), "PROCESO_1", Snackbar.LENGTH_LONG).show();
                    ////Toast.makeText(context, cursor.getString(0), Toast.LENGTH_LONG).show();
                    ///Toast.makeText(context, "Gestión ya se encuentra Guardada...", Toast.LENGTH_LONG).show();
                    SQLiteDatabase db = ayudabd.getWritableDatabase();
                    ContentValues valores = new ContentValues();
                    ContentValues valoresUpdate = new ContentValues();
                    String[] parametros={finalIdCliente};
                    valores.put(AyudaBD.DatosTabla.COLUMNA_DATA_DOMICILIARIO_ID, finalIdCliente);
                    valores.put(AyudaBD.DatosTabla.COLUMNA_CEDENTE, finalCarteraCliente);
                    valores.put(AyudaBD.DatosTabla.COLUMNA_CEDULA_CNS, finalCedulaCliente);
                    valores.put(AyudaBD.DatosTabla.COLUMNA_ACCION,  opcionesAccion.getSelectedItem().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_RESPUESTA, opccionesRespuesta.getSelectedItem().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_FECHA_VISITA,  fechaVisita.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_FECHA_PROMESA, fechaCompromiso.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_HORA,  hora.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_VALOR, valorClienteCp.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_OBSERVACION,  observacionGestion.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_ESTADO,  1);
                    valores.put(AyudaBD.DatosTabla.COLUMNA_UBICACION,  ubicacion.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_CONTACTO,  opccionesContacto.getSelectedItem().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_RECIBO,  Erecibo.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_ID_GESTION, finalIdClienteAsig);
                    valores.put(AyudaBD.DatosTabla.COLUMNA_URL_IMAGEN1, urlImagen1);
                    valores.put(AyudaBD.DatosTabla.COLUMNA_NOMBRE_IMAGEN1, nombreImagen1);

                    valores.put(AyudaBD.DatosTabla.COLUMNA_DIRECCION1,  Direccion1.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_DIRECCION2,  Direccion2.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_REFERENCIA_DOMICILIO,  DirecccionReferencia.getText().toString());
                    /////OBTENER FECHA Y HORA REAL DEL CELULAR PARA GUARDARLAS EN LAS GESTIONES
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    Date date = new Date();
                    String fecha = dateFormat.format(date);
                    valores.put(AyudaBD.DatosTabla.COLUMNA_FECHA_GESTION,  fecha);
                    //////PROCESO DE RE-GESTIONES
                    if(opccionesRespuesta.getSelectedItem().toString().equals("COMPROMISO DE PAGO") || opccionesRespuesta.getSelectedItem().toString().equals("NEGATIVA DE PAGO") || opccionesRespuesta.getSelectedItem().toString().equals("POSTERGA PAGO") || opccionesRespuesta.getSelectedItem().toString().equals("RENUENTE") || opccionesRespuesta.getSelectedItem().toString().equals("FALTA DOCUMENTOS") || opccionesRespuesta.getSelectedItem().toString().equals("ENTREGA NOTIFICACION") || opccionesRespuesta.getSelectedItem().toString().equals("VOLVER A VISITAR"))
                    {
                        intensidad=inten+1;
                        estado_cliente=2;
                    }
                    else
                    {
                        intensidad=inten+1;
                        estado_cliente=0;
                    }
                      /////condicion para control de los compromisos de campos obligatorios
                    if(opccionesRespuesta.getSelectedItem().toString().equals("COMPROMISO DE PAGO") || opccionesRespuesta.getSelectedItem().toString().equals("COMPROMISO DE PAGO INDIRECTO"))
                    {
                        if (fechaCompromiso.getText().toString().isEmpty() || valorClienteCp.getText().toString().isEmpty())
                        {
                            Snackbar.make(getWindow().getDecorView().getRootView(), "LLENAR CAMPOS OBLIGATORIOS PARA SUS COMPROMISOS", Snackbar.LENGTH_LONG).show();


                        }
                        else
                        {
                            ////PROCESO PARA GUARDAR
                            valores.put(AyudaBD.DatosTabla.COLUMNA_INTENSIDAD,  intensidad);
                            valores.put(AyudaBD.DatosTabla.COLUMNA_ESTADO_DIRECCION,  0);
                            Long IdGuardado = db.insert(AyudaBD.DatosTabla.NOMBRE_TABLA_2, AyudaBD.DatosTabla.COLUMNA_ID_2, valores);
                            /////ACTUALIZACION DE ESTADO PARA QUE NO VUELVA APARECER EN LA LISTA PRINCIPAL DE LOS CLIENTES
                            valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_ESTADO, estado_cliente);
                            db.update(AyudaBD.DatosTabla.NOMBRE_TABLA, valoresUpdate, AyudaBD.DatosTabla.COLUMNA_ID+"=?",parametros);
                            ////MENSAJE DE CONFIRMACION DE GESTIONES ALMACENADAS EN EL CELULAR
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Gestión Guardada con éxito", Snackbar.LENGTH_LONG).show();
                            ////Toast.makeText(context, "Gestión Guardada con éxito", Toast.LENGTH_LONG).show();
                            Guardar.setEnabled(false);
                        }
                    }
                    else
                    {
                        if(opccionesRespuesta.getSelectedItem().toString().equals("NUEVA DIRECCION"))
                        {
                            ////PROCESO PARA GUARDAR
                            valores.put(AyudaBD.DatosTabla.COLUMNA_INTENSIDAD,  intensidad);
                            valores.put(AyudaBD.DatosTabla.COLUMNA_DIRECCION1,  Direccion1.getText().toString());
                            valores.put(AyudaBD.DatosTabla.COLUMNA_DIRECCION2,  Direccion2.getText().toString());
                            valores.put(AyudaBD.DatosTabla.COLUMNA_REFERENCIA_DOMICILIO,  DirecccionReferencia.getText().toString());
                            valores.put(AyudaBD.DatosTabla.COLUMNA_ESTADO_DIRECCION,  1);
                            Long IdGuardado = db.insert(AyudaBD.DatosTabla.NOMBRE_TABLA_2, AyudaBD.DatosTabla.COLUMNA_ID_2, valores);
                            /////ACTUALIZACION DE ESTADO PARA QUE NO VUELVA APARECER EN LA LISTA PRINCIPAL DE LOS CLIENTES
                            /////valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_ESTADO, estado_cliente);
                            /////db.update(AyudaBD.DatosTabla.NOMBRE_TABLA, valoresUpdate, AyudaBD.DatosTabla.COLUMNA_ID+"=?",parametros);
                            ////MENSAJE DE CONFIRMACION DE GESTIONES ALMACENADAS EN EL CELULAR
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Gestión de Dirección nueva guardada con éxito", Snackbar.LENGTH_LONG).show();
                            ////Toast.makeText(context, "Gestión Guardada con éxito", Toast.LENGTH_LONG).show();
                            Guardar.setEnabled(false);
                        }
                        else
                        {

                            ////PROCESO PARA GUARDAR
                            valores.put(AyudaBD.DatosTabla.COLUMNA_INTENSIDAD,  intensidad);
                            valores.put(AyudaBD.DatosTabla.COLUMNA_ESTADO_DIRECCION,  0);
                            Long IdGuardado = db.insert(AyudaBD.DatosTabla.NOMBRE_TABLA_2, AyudaBD.DatosTabla.COLUMNA_ID_2, valores);
                            /////ACTUALIZACION DE ESTADO PARA QUE NO VUELVA APARECER EN LA LISTA PRINCIPAL DE LOS CLIENTES
                            valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_ESTADO, estado_cliente);
                            db.update(AyudaBD.DatosTabla.NOMBRE_TABLA, valoresUpdate, AyudaBD.DatosTabla.COLUMNA_ID+"=?",parametros);
                            ////MENSAJE DE CONFIRMACION DE GESTIONES ALMACENADAS EN EL CELULAR
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Gestión Guardada con éxito", Snackbar.LENGTH_LONG).show();
                            ////Toast.makeText(context, "Gestión Guardada con éxito", Toast.LENGTH_LONG).show();
                            Guardar.setEnabled(false);

                        }
                    }


                }
                else
                {
                    SQLiteDatabase db = ayudabd.getWritableDatabase();
                    ContentValues valores = new ContentValues();
                    ContentValues valoresUpdate = new ContentValues();
                    String[] parametros={finalIdCliente};
                    valores.put(AyudaBD.DatosTabla.COLUMNA_DATA_DOMICILIARIO_ID, finalIdCliente);
                    valores.put(AyudaBD.DatosTabla.COLUMNA_CEDENTE, finalCarteraCliente);
                    valores.put(AyudaBD.DatosTabla.COLUMNA_CEDULA_CNS, finalCedulaCliente);
                    valores.put(AyudaBD.DatosTabla.COLUMNA_ACCION,  opcionesAccion.getSelectedItem().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_RESPUESTA, opccionesRespuesta.getSelectedItem().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_FECHA_VISITA,  fechaVisita.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_FECHA_PROMESA, fechaCompromiso.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_HORA,  hora.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_VALOR, valorClienteCp.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_OBSERVACION,  observacionGestion.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_ESTADO,  1);
                    valores.put(AyudaBD.DatosTabla.COLUMNA_UBICACION,  ubicacion.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_CONTACTO,  opccionesContacto.getSelectedItem().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_RECIBO,  Erecibo.getText().toString());
                    valores.put(AyudaBD.DatosTabla.COLUMNA_ID_GESTION, finalIdClienteAsig);
                    valores.put(AyudaBD.DatosTabla.COLUMNA_URL_IMAGEN1, urlImagen1);
                    valores.put(AyudaBD.DatosTabla.COLUMNA_NOMBRE_IMAGEN1, nombreImagen1);
                    /////OBTENER FECHA Y HORA REAL DEL CELULAR PARA GUARDARLAS EN LAS GESTIONES
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.getDefault());
                    Date date = new Date();
                    String fecha = dateFormat.format(date);
                    valores.put(AyudaBD.DatosTabla.COLUMNA_FECHA_GESTION,  fecha);
                    //////PROCESO DE RE-GESTIONES
                    if(opccionesRespuesta.getSelectedItem().toString().equals("PAGO REALIZADO CON DOMICILIARIO") || opccionesRespuesta.getSelectedItem().toString().equals("COMPROMISO DE PAGO") || opccionesRespuesta.getSelectedItem().toString().equals("NEGATIVA DE PAGO") || opccionesRespuesta.getSelectedItem().toString().equals("POSTERGA PAGO") || opccionesRespuesta.getSelectedItem().toString().equals("RENUENTE") || opccionesRespuesta.getSelectedItem().toString().equals("FALTA DOCUMENTOS") || opccionesRespuesta.getSelectedItem().toString().equals("ENTREGA NOTIFICACION") || opccionesRespuesta.getSelectedItem().toString().equals("VOLVER A VISITAR"))
                    {
                        intensidad=1;
                        estado_cliente=2;
                    }
                    else
                    {
                        intensidad=0;
                        estado_cliente=0;
                    }

                    /////condicion para control de los compromisos de campos obligatorios
                    if(opccionesRespuesta.getSelectedItem().toString().equals("COMPROMISO DE PAGO") || opccionesRespuesta.getSelectedItem().toString().equals("COMPROMISO DE PAGO INDIRECTO"))
                    {
                        if (fechaCompromiso.getText().toString().isEmpty() || valorClienteCp.getText().toString().isEmpty()) {
                            Snackbar.make(getWindow().getDecorView().getRootView(), "LLENAR CAMPOS OBLIGATORIOS PARA SUS COMPROMISOS", Snackbar.LENGTH_LONG).show();


                        }
                        else
                        {
                            ////PROCESO PARA GUARDAR
                            valores.put(AyudaBD.DatosTabla.COLUMNA_INTENSIDAD,  intensidad);
                            valores.put(AyudaBD.DatosTabla.COLUMNA_ESTADO_DIRECCION,  0);
                            Long IdGuardado = db.insert(AyudaBD.DatosTabla.NOMBRE_TABLA_2, AyudaBD.DatosTabla.COLUMNA_ID_2, valores);
                            /////ACTUALIZACION DE ESTADO PARA QUE NO VUELVA APARECER EN LA LISTA PRINCIPAL DE LOS CLIENTES
                            valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_ESTADO, estado_cliente);
                            db.update(AyudaBD.DatosTabla.NOMBRE_TABLA, valoresUpdate, AyudaBD.DatosTabla.COLUMNA_ID+"=?",parametros);
                            ////MENSAJE DE CONFIRMACION DE GESTIONES ALMACENADAS EN EL CELULAR
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Gestión Guardada con éxito", Snackbar.LENGTH_LONG).show();
                            ////Toast.makeText(context, "Gestión Guardada con éxito", Toast.LENGTH_LONG).show();
                            Guardar.setEnabled(false);
                        }
                    }
                    else
                    {


                            ////PROCESO PARA GUARDAR
                            valores.put(AyudaBD.DatosTabla.COLUMNA_INTENSIDAD,  intensidad);
                            valores.put(AyudaBD.DatosTabla.COLUMNA_ESTADO_DIRECCION,  0);
                            Long IdGuardado = db.insert(AyudaBD.DatosTabla.NOMBRE_TABLA_2, AyudaBD.DatosTabla.COLUMNA_ID_2, valores);
                            /////ACTUALIZACION DE ESTADO PARA QUE NO VUELVA APARECER EN LA LISTA PRINCIPAL DE LOS CLIENTES
                            valoresUpdate.put(AyudaBD.DatosTabla.COLUMNA_ESTADO, estado_cliente);
                            db.update(AyudaBD.DatosTabla.NOMBRE_TABLA, valoresUpdate, AyudaBD.DatosTabla.COLUMNA_ID+"=?",parametros);
                            ////MENSAJE DE CONFIRMACION DE GESTIONES ALMACENADAS EN EL CELULAR
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Gestión Guardada con éxito", Snackbar.LENGTH_LONG).show();
                            ////Toast.makeText(context, "Gestión Guardada con éxito", Toast.LENGTH_LONG).show();
                            Guardar.setEnabled(false);




                    }

                }/////////FIN DE GUARDAR NUEVO



            }
        });
        /////para combo de 3 niveles dinamico
        opcionesAccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(opccionTipoGestion.getSelectedItem().toString().equals("GESTIONES GESCOM"))
                {
                    int [] acciones = {R.array.opcionesRespuestaContactoDirecto, R.array.opcionesRespuestContactoIndirecto, R.array.opcionesRespuestNoLocalizados, R.array.opcionesRespuestNuevaVisita,  R.array.opcionesRespuestRezagoTerreno};
                    int [] accionesContacto = {R.array.opcionesDirecto, R.array.opcionesInDirecto, R.array.opcionesNoLocalizado, R.array.opcionesNuevaVisita, R.array.opcionesRezagoTerreno};
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            context,  acciones[position], android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    opccionesRespuesta.setAdapter(adapter);


                    ArrayAdapter<CharSequence> adapterContacto = ArrayAdapter.createFromResource(
                            context,  accionesContacto[position], android.R.layout.simple_spinner_item);
                    adapterContacto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    opccionesContacto.setAdapter(adapterContacto);
                }
                 else if (opccionTipoGestion.getSelectedItem().toString().equals("OPERATIVO YANBAL"))
                {
                    int [] acciones = {R.array.opcionesNoSeRealizaVisita, R.array.opcionesContactoDirecto, R.array.opcionesContactoIndirecto, R.array.opcionesNoContactado};
                    int [] accionesContacto = {R.array.opcionesNoConta, R.array.opcionesDirecto, R.array.opcionesInDirecto, R.array.opcionesNoConta};
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            context,  acciones[position], android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    opccionesRespuesta.setAdapter(adapter);

                    ArrayAdapter<CharSequence> adapterContacto = ArrayAdapter.createFromResource(
                            context,  accionesContacto[position], android.R.layout.simple_spinner_item);
                    adapterContacto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    opccionesContacto.setAdapter(adapterContacto);
                }
                else if (opccionTipoGestion.getSelectedItem().toString().equals("GESTIONES DINERS"))
                {
                    int [] acciones = {R.array.opcionesDiners2Refinancia, R.array.opcionesDiners2Ofrecimiento, R.array.opcionesDiners2Contactosin, R.array.opcionesDiners2MensajeTercero, R.array.opcionesDiners2ContSinArreglo, R.array.opcionesDiners2Ilocalizable, R.array.opcionesDiners2SinArregloCliente};
                    int [] accionesContacto = {R.array.opcionesDirecto,R.array.opcionesDirecto,R.array.opcionesDirecto, R.array.opcionesInDirecto, R.array.opcionesDirecto, R.array.opcionesNoConta, R.array.opcionesNoConta};
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            context,  acciones[position], android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    opccionesRespuesta.setAdapter(adapter);

                    ArrayAdapter<CharSequence> adapterContacto = ArrayAdapter.createFromResource(
                            context,  accionesContacto[position], android.R.layout.simple_spinner_item);
                    adapterContacto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    opccionesContacto.setAdapter(adapterContacto);
                }
                else if (opccionTipoGestion.getSelectedItem().toString().equals("MUTUALISTA PICHINCHA"))
                {
                    int [] acciones = {R.array.opcionesMutualistaContactoDirecto, R.array.opcionesMutualistaContactoInDirecto, R.array.opcionesMutualistaVueltaVisita, R.array.opcionesMutualistaVirtual, R.array.opcionesMutualistaRezago};
                    int [] accionesContacto = {R.array.opcionesDirecto, R.array.opcionesInDirecto, R.array.opcionesNuevaVisita, R.array.opcionesNoConta, R.array.opcionesRezagoTerreno};
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            context,  acciones[position], android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    opccionesRespuesta.setAdapter(adapter);

                    ArrayAdapter<CharSequence> adapterContacto = ArrayAdapter.createFromResource(
                            context,  accionesContacto[position], android.R.layout.simple_spinner_item);
                    adapterContacto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    opccionesContacto.setAdapter(adapterContacto);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
         /////para formularios dinamicos
        opccionesRespuesta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                observacionGestion.setText(parent.getItemAtPosition(position).toString());
               //// String valor = parent.getItemAtPosition(position).toString();
               /// fechaVisita.setVisibility(View.GONE);
                if(parent.getItemAtPosition(position).toString().equals("COMPROMISO DE PAGO"))
                {
                    ///fechaVisita.setSelectAllOnFocus(true);
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    vfechaCompromiso.setVisibility(View.VISIBLE);
                    fechaCompromiso.setVisibility(View.VISIBLE);
                    VvalorClienteCp.setVisibility(View.VISIBLE);
                    valorClienteCp.setVisibility(View.VISIBLE);


                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("NEGATIVA DE PAGO"))
                {
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("NUEVA DIRECCION"))
                {
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.VISIBLE);
                    Direccion1.setVisibility(View.VISIBLE);
                    Vdireccion2.setVisibility(View.VISIBLE);
                    Direccion2.setVisibility(View.VISIBLE);
                    VdirecccionReferencia.setVisibility(View.VISIBLE);
                    DirecccionReferencia.setVisibility(View.VISIBLE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("PAGO REALIZADO CON DOMICILIARIO"))
                {
                    Erecibo.setVisibility(View.VISIBLE);
                    Vrecibo.setVisibility(View.VISIBLE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.VISIBLE);
                    valorClienteCp.setVisibility(View.VISIBLE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("PAGO REALIZADO CENTRO AUTORIZADO"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if(parent.getItemAtPosition(position).toString().equals("POSTERGA PAGO"))
                {
                    ///fechaVisita.setSelectAllOnFocus(true);
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    vfechaCompromiso.setVisibility(View.VISIBLE);
                    fechaCompromiso.setVisibility(View.VISIBLE);
                    VvalorClienteCp.setVisibility(View.VISIBLE);
                    valorClienteCp.setVisibility(View.VISIBLE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("RENUENTE"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("ACUERDO REFINANCIAMIENTO"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("Refinancia"))
                {
                    ///fechaVisita.setSelectAllOnFocus(true);
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    vfechaCompromiso.setVisibility(View.VISIBLE);
                    fechaCompromiso.setVisibility(View.VISIBLE);
                    VvalorClienteCp.setVisibility(View.VISIBLE);
                    valorClienteCp.setVisibility(View.VISIBLE);


                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("Ofrecimiento de Pago Total"))
                {
                    ///fechaVisita.setSelectAllOnFocus(true);
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    vfechaCompromiso.setVisibility(View.VISIBLE);
                    fechaCompromiso.setVisibility(View.VISIBLE);
                    VvalorClienteCp.setVisibility(View.VISIBLE);
                    valorClienteCp.setVisibility(View.VISIBLE);


                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("Ofrecimiento de Arreglo"))
                {
                    ///fechaVisita.setSelectAllOnFocus(true);
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    vfechaCompromiso.setVisibility(View.VISIBLE);
                    fechaCompromiso.setVisibility(View.VISIBLE);
                    VvalorClienteCp.setVisibility(View.VISIBLE);
                    valorClienteCp.setVisibility(View.VISIBLE);


                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("Ofrec.Pago Parcial Dentro Cort"))
                {
                    ///fechaVisita.setSelectAllOnFocus(true);
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    vfechaCompromiso.setVisibility(View.VISIBLE);
                    fechaCompromiso.setVisibility(View.VISIBLE);
                    VvalorClienteCp.setVisibility(View.VISIBLE);
                    valorClienteCp.setVisibility(View.VISIBLE);


                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("Ofrec.Pago Despues del corte"))
                {
                    ///fechaVisita.setSelectAllOnFocus(true);
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    vfechaCompromiso.setVisibility(View.VISIBLE);
                    fechaCompromiso.setVisibility(View.VISIBLE);
                    VvalorClienteCp.setVisibility(View.VISIBLE);
                    valorClienteCp.setVisibility(View.VISIBLE);


                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("No Concreta"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("Esposa (o)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("Hijos / Padres"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("Familiares"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("Secretaria / Asistente"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("Sin Arreglo Domicilio"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("REFINANCIAMIENTO FIRMADO"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("FALTA DOCUMENTOS"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("FIRMA NOTIFICACION"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("ENTREGA NOTIFICACION"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("Cont. Sin Arreglo Definitivo"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("Sin Arreglo Cliente"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("Ilocalizable Domicilio"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("NO ENTREGA NOTIFICACION"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("VOLVER A VISITAR"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.VISIBLE);
                    VfechaVisita.setVisibility(View.VISIBLE);
                    vhora.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("NUEVA INFORMACION"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                /*else if (parent.getItemAtPosition(position).toString().equals("INVESTIGACION WEB"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);

                }*/
                else if (parent.getItemAtPosition(position).toString().equals("DIRECCION INSUFICIENTE"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("SIN COBERTURA"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("ZONA PELIGROSA"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("FALLECIDO"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("DIRECCION INCORRECTA"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("DIRECTORA NO ASISTE A GESTION"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("GESTION SUSPENDIDA POR YANBAL"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("DIRECCION ERRADA"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("NO VIVE EN DIRECCION"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("CARTA BAJO LA PUERTA"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);


                }
                else if (parent.getItemAtPosition(position).toString().equals("CARTA DEVUELTA ZONA PELIGROSA"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("NO LA CONOCEN EN EL SECTOR"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("SIN COBERTURA EN EL SECTOR"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("CNS GASTO EL DINERO RECAUDADO"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("ENTREGO DINERO A DIRECTORA"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("CARTA ENTREGADA A TITULAR"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("NO DESEA RECIBIR CARTA/ BAJO LA PUERTA"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("CLIENTE FINAL NO PAGA"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("NO REALIZO PEDIDO"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("CODIGO UTILIZADO POR TERCERO"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("RENUENTE AL PAGO"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("NO PAGA POR MOTIVOS DE FUERZA MAYOR"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if(parent.getItemAtPosition(position).toString().equals("COMPROMISO DE PAGO INDIRECTO"))
                {
                    ///fechaVisita.setSelectAllOnFocus(true);
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    vfechaCompromiso.setVisibility(View.VISIBLE);
                    fechaCompromiso.setVisibility(View.VISIBLE);
                    VvalorClienteCp.setVisibility(View.VISIBLE);
                    valorClienteCp.setVisibility(View.VISIBLE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("CARTA ENTREGADA A TERCERO"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("NO DESEA RECIBIR CARTA/ BAJO LA PUERTA"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("CARTA DEVUELTA TITULAR FALLECIDO"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
                else if (parent.getItemAtPosition(position).toString().equals("NEGATIVA AL PAGO (TERRENO)"))
                {
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("SOBREENDEUDAMIENTO SIN CAPACIDAD DE PAGO (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("DEUDOR NO RECONOCE DEUDA (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("NO LLEGA ESTADO DE CUENTA (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("YA CANCELO (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("COMPROMISO DE PAGO TOTAL (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    vfechaCompromiso.setVisibility(View.VISIBLE);
                    fechaCompromiso.setVisibility(View.VISIBLE);
                    VvalorClienteCp.setVisibility(View.VISIBLE);
                    valorClienteCp.setVisibility(View.VISIBLE);


                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("COMPROMISO DE ABONO (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    vfechaCompromiso.setVisibility(View.VISIBLE);
                    fechaCompromiso.setVisibility(View.VISIBLE);
                    VvalorClienteCp.setVisibility(View.VISIBLE);
                    valorClienteCp.setVisibility(View.VISIBLE);


                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("SOLICITA REFINANCIAMIENTO (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    vfechaCompromiso.setVisibility(View.VISIBLE);
                    fechaCompromiso.setVisibility(View.VISIBLE);
                    VvalorClienteCp.setVisibility(View.VISIBLE);
                    valorClienteCp.setVisibility(View.VISIBLE);


                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("APLICACION SEGUROS (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("POSTERGA PAGO (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    vfechaCompromiso.setVisibility(View.VISIBLE);
                    fechaCompromiso.setVisibility(View.VISIBLE);
                    VvalorClienteCp.setVisibility(View.VISIBLE);
                    valorClienteCp.setVisibility(View.VISIBLE);


                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("DEUDA ES DE TERCERO (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("NEGATIVA DE PAGO (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("CESANTE (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                //////para lo indirecto de la mutualista
                else if (parent.getItemAtPosition(position).toString().equals("MENSAJE A TERCEROS (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("SOCIO FUERA DEL PAIS (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("SOCIO FALLECIDO (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("NOTIFICACION A TERCEROS (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("NOTIFICACION BAJO PUERTA (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("NOTIFICACION EN DOMICILIO (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("NOTIFICACION EN TRABAJO (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("NO HAY NADIE (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("RESCATE DE DATOS (TERRENO)"))
                {
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.VISIBLE);
                    Direccion1.setVisibility(View.VISIBLE);
                    Vdireccion2.setVisibility(View.VISIBLE);
                    Direccion2.setVisibility(View.VISIBLE);
                    VdirecccionReferencia.setVisibility(View.VISIBLE);
                    DirecccionReferencia.setVisibility(View.VISIBLE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("NO CONOCEN A TITULAR (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("DIRECCION INCORRECTA (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("YA NO VIVE AHI (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("ZONA PELIGROSA / DIFICIL ACCESO (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("NO LO CONOCEN EN EL SECTOR (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("DIRECCION INCOMPLETA (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("DIRECCION NO EXISTE (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("NO CONOCEN A TITULAR (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else if (parent.getItemAtPosition(position).toString().equals("LUGAR DESAHABITADO (TERRENO)"))
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.GONE);
                    VfechaVisita.setVisibility(View.GONE);
                    vhora.setVisibility(View.GONE);
                    hora.setVisibility(View.GONE);
                    vfechaCompromiso.setVisibility(View.GONE);
                    fechaCompromiso.setVisibility(View.GONE);
                    VvalorClienteCp.setVisibility(View.GONE);
                    valorClienteCp.setVisibility(View.GONE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);
                }
                else
                {
                    Erecibo.setVisibility(View.GONE);
                    Vrecibo.setVisibility(View.GONE);
                    fechaVisita.setVisibility(View.VISIBLE);
                    VfechaVisita.setVisibility(View.VISIBLE);
                    vhora.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    vfechaCompromiso.setVisibility(View.VISIBLE);
                    fechaCompromiso.setVisibility(View.VISIBLE);
                    VvalorClienteCp.setVisibility(View.VISIBLE);
                    valorClienteCp.setVisibility(View.VISIBLE);
                    Vdireccion1.setVisibility(View.GONE);
                    Direccion1.setVisibility(View.GONE);
                    Vdireccion2.setVisibility(View.GONE);
                    Direccion2.setVisibility(View.GONE);
                    VdirecccionReferencia.setVisibility(View.GONE);
                    DirecccionReferencia.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private boolean validaPermisos() {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                botonCargar.setEnabled(true);
            }else{
                solicitarPermisosManual();
            }
        }
    }




    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final android.support.v7.app.AlertDialog.Builder alertOpciones=new android.support.v7.app.AlertDialog.Builder(this);//estamos en fragment
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(context,"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacion() {
        android.support.v7.app.AlertDialog.Builder dialogo=new android.support.v7.app.AlertDialog.Builder(this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }

    private void mostrarDialogOpciones() {
        final CharSequence[] opciones={"Tomar Foto","Elegir de Galeria","Cancelar"};
        final android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    abriCamara();
                }else{
                    /*if (opciones[i].equals("Elegir de Galeria")){
                        Intent intent=new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);
                    }*/
                    if (opciones[i].equals("Cancelar")){
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    private void abriCamara() {
        File miFile=new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada=miFile.exists();

        if(isCreada==false){
            isCreada=miFile.mkdirs();
        }

        if(isCreada==true){
            Long consecutivo= System.currentTimeMillis()/1000;
            String nombre=consecutivo.toString()+".jpg";
            nombreImagen1=nombre;
            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
                    +File.separator+nombre;//indicamos la ruta de almacenamiento

            fileImagen=new File(path);

            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImagen));

            ////
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            {
                String authorities=this.getPackageName()+".provider";
                Uri imageUri= FileProvider.getUriForFile(this,authorities,fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }else
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
            }
            startActivityForResult(intent,COD_FOTO);

            ////

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case COD_SELECCIONA:
                Uri miPath=data.getData();
                /////imgFoto.setImageURI(miPath);
                imagenCamara_1.setImageURI(miPath);

                try {
                    bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),miPath);
                    imagenCamara_1.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case COD_FOTO:


                bitmap= BitmapFactory.decodeFile(path);
                ////campoNombre.setText(path);
                /////imgFoto.setImageBitmap(bitmap);
                bitmap=redimensionarImagen(bitmap,300,200);
                imagenCamara_1.setImageBitmap(bitmap);
                urlImagen1=path;

                break;
        }

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    int [] accionesTipoGestiones = {R.array.opcionesAccion,R.array.opcionesGestionCampo, R.array.opcionesDiners2, R.array.opcionesGestionMutualista};
    //int [] acciones = {R.array.opcionesRespuestaContactoDirecto, R.array.opcionesRespuestContactoIndirecto, R.array.opcionesRespuestNoLocalizados, R.array.opcionesRespuestNuevaVisita,  R.array.opcionesRespuestRezagoTerreno, R.array.opcionesNoSeRealizaVisita, R.array.opcionesNoContactado};
    ///int [] accionesContacto = {R.array.opcionesDirecto, R.array.opcionesInDirecto, R.array.opcionesNoLocalizado, R.array.opcionesNuevaVisita, R.array.opcionesRezagoTerreno, R.array.opcionesNoRealizaVisitas, R.array.opcionesNoConta};

        ArrayAdapter<CharSequence> adapterTipo = ArrayAdapter.createFromResource(
                this,  accionesTipoGestiones[position], android.R.layout.simple_spinner_item);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        opcionesAccion.setAdapter(adapterTipo);


   /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
           this,  acciones[position], android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    opccionesRespuesta.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapterContacto = ArrayAdapter.createFromResource(
                this,  accionesContacto[position], android.R.layout.simple_spinner_item);
        adapterContacto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        opccionesContacto.setAdapter(adapterContacto);*/

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //////para diseño de las fecha y horas fechaCompromiso
    public void onClick(View v) {
        if(v==fechaCompromiso){
            final Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    fechaCompromiso.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);

                }
            }
                    ,ano,mes,dia);
            datePickerDialog.show();
        }
        if(v==fechaVisita){
            final Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    fechaVisita.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                }
            }
                    ,ano,mes,dia);
            datePickerDialog.show();
        }
        
        if (v==hora){
            final Calendar c= Calendar.getInstance();
            hora2=c.get(Calendar.HOUR_OF_DAY);
            minutos=c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    hora.setText(hourOfDay+":"+minute);
                }
            },hora2,minutos,false);
            timePickerDialog.show();
        }
    }


}
