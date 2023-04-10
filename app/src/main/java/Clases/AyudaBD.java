package Clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class AyudaBD extends SQLiteOpenHelper{

    public static abstract class DatosTabla implements BaseColumns {
        public static final String NOMBRE_TABLA = "Directorio";
        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_ID_2 = "id";
        public static final String COLUMNA_CARTERA = "cartera";
        public static final String COLUMNA_CALIF_DE_RIESGO = "calif_de_riesgo";
        public static final String COLUMNA_CEDULA_CNS = "cedula_cns";
        public static final String COLUMNA_NOMBRE = "nombre";
        public static final String COLUMNA_PROVINCIA = "provincia";
        public static final String COLUMNA_CANTON = "canton";
        public static final String COLUMNA_PARROQUIA = "parroquia";
        public static final String COLUMNA_COD_DIRECCION_1 = "cod_direccion_1";
        public static final String COLUMNA_DIRECCION_1 = "direccion_1";
        public static final String COLUMNA_COD_DIRECCION_2 = "cod_direccion_2";
        public static final String COLUMNA_DIRECCION_2 = "direccion_2";
        public static final String COLUMNA_TELFCNS = "telfcns";
        public static final String COLUMNA_TELFCEL = "telfcel";
        public static final String COLUMNA_TELF_REFERENCIA = "telf_referencia";
        public static final String COLUMNA_NOMBRE_REFERENCIA = "nombre_referencia";
        public static final String COLUMNA_TOTAL_DEUDA = "total_deuda";
        public static final String COLUMNA_ASIGNACION = "asignacion";
        public static final String COLUMNA_EMAIL = "email";
        public static final String COLUMNA_REF_DOMICILIO = "ref_domicilio";
        public static final String COLUMNA_REF_DESPACHO = "ref_despacho";
        public static final String COLUMNA_TELEFONO_1 = "telefono_1";
        public static final String COLUMNA_TELEFONO_2 = "telefono_2";
        public static final String COLUMNA_ZONA = "zona";
        public static final String COLUMNA_SECTOR = "sector";
        public static final String COLUMNA_PRIMER_PEDIDO = "primero_pedido";
        public static final String COLUMNA_ESTADO = "estado";
        public static final String COLUMNA_FECHA_GESTION = "fecha_gestion";
        public static final String COLUMNA_ID_GESTION = "gestion_id";
        public static final String COLUMNA_MORA = "mora";
        public static final String COLUMNA_ABONOS = "abonos";


        public static final String NOMBRE_TABLA_2 = "gestiones";
        public static final String COLUMNA_DATA_DOMICILIARIO_ID = "data_domiciliario_id";
        public static final String COLUMNA_CEDENTE = "cedente";
        public static final String COLUMNA_ACCION = "accion";
        public static final String COLUMNA_RESPUESTA = "respuesta";
        public static final String COLUMNA_FECHA_VISITA = "fecha_visita";
        public static final String COLUMNA_FECHA_PROMESA = "fecha_promesa";
        public static final String COLUMNA_HORA = "hora";
        public static final String COLUMNA_VALOR = "valor";
        public static final String COLUMNA_OBSERVACION = "observacion";
        public static final String COLUMNA_UBICACION = "ubicacion";
        public static final String COLUMNA_CONTACTO = "contacto";
        public static final String COLUMNA_RECIBO = "numero_recibo";
        public static final String COLUMNA_IMAGEN1 = "imagen1";
        public static final String COLUMNA_URL_IMAGEN1 = "url_imagen1";
        public static final String COLUMNA_NOMBRE_IMAGEN1 = "nombreimagen1";
        public static final String COLUMNA_IMAGEN2 = "imagen2";
        public static final String COLUMNA_URL_IMAGEN2 = "url_imagen2";
        public static final String COLUMNA_IMAGEN3 = "imagen3";
        public static final String COLUMNA_URL_IMAGEN3 = "url_imagen3";
        public static final String COLUMNA_INTENSIDAD = "intensidad";
        ///PARA CONTROL DE DOMICILIO
        public static final String COLUMNA_ESTADO_DIRECCION = "estado_direccion";
        public static final String COLUMNA_DIRECCION1 = "direccion1";
        public static final String COLUMNA_DIRECCION2 = "direccion2";
        public static final String COLUMNA_REFERENCIA_DOMICILIO = "referencia_domicilio";




        private static final String TEXT_TYPE = " TEXT";
        private static final String INT_TYPE = " INTEGER";
        private static final String COMMA_SEP = ",";
        private static final String CREAR_TABLA_1 =
                "CREATE TABLE " + DatosTabla.NOMBRE_TABLA + " (" +
                        DatosTabla.COLUMNA_ID + " INTEGER PRIMARY KEY," +
                        DatosTabla.COLUMNA_CARTERA + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_CALIF_DE_RIESGO + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_CEDULA_CNS + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_NOMBRE + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_PROVINCIA + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_CANTON + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_PARROQUIA + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_DIRECCION_1 + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_DIRECCION_2 + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_TELFCNS + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_TELFCEL + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_TELF_REFERENCIA + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_NOMBRE_REFERENCIA + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_TOTAL_DEUDA + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_ASIGNACION + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_EMAIL + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_REF_DOMICILIO + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_REF_DESPACHO + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_TELEFONO_1 + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_TELEFONO_2 + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_ZONA + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_SECTOR + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_PRIMER_PEDIDO + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_ESTADO + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_ID_GESTION + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_COD_DIRECCION_1 + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_COD_DIRECCION_2 + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_MORA + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_ABONOS + TEXT_TYPE +
                        " )";

        private static final String CREAR_TABLA_2 =
                "CREATE TABLE " + DatosTabla.NOMBRE_TABLA_2 + " (" +
                        DatosTabla.COLUMNA_ID_2 + " INTEGER PRIMARY KEY autoincrement," +
                        DatosTabla.COLUMNA_DATA_DOMICILIARIO_ID + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_CEDENTE + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_CEDULA_CNS + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_ACCION + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_RESPUESTA + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_FECHA_VISITA + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_FECHA_PROMESA + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_HORA + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_VALOR + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_OBSERVACION + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_ESTADO + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_UBICACION + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_FECHA_GESTION + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_CONTACTO + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_RECIBO + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_ID_GESTION + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_IMAGEN1 + " blob" + COMMA_SEP +
                        DatosTabla.COLUMNA_URL_IMAGEN1 + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_NOMBRE_IMAGEN1 + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_INTENSIDAD + INT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_ESTADO_DIRECCION + INT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_DIRECCION1 + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_DIRECCION2 + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_REFERENCIA_DOMICILIO + TEXT_TYPE +
                        " )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + DatosTabla.NOMBRE_TABLA;

    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "gescomProduccionUio001.db";

    public AyudaBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DatosTabla.CREAR_TABLA_1);
        db.execSQL(DatosTabla.CREAR_TABLA_2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DatosTabla.SQL_DELETE_ENTRIES);
        onCreate(db);

    }
}
