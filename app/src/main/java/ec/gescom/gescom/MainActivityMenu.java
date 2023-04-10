package ec.gescom.gescom;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ec.gescom.gescom.interfaces.IComunicaFragments;

public class MainActivityMenu extends AppCompatActivity
        implements IComunicaFragments, NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new Bienvenido()).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    /*se controla la pulsacion del boton atras*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de Sistema Domiciliario Gescom?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.nav_sincronizarDatos) {
            // Handle the camera action
            fragmentManager.beginTransaction().replace(R.id.contenedor, new form_sincronizar()).commit();
        } else if (id == R.id.nav_importar) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new form_export()).commit();
        } /*else if (id == R.id.nav_slideshow) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new form_client()).commit();
        }*/ else if (id == R.id.nav_listClient) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new list_client()).commit();
        } else if (id == R.id.nav_busqueda) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new form_client()).commit();
        } else if (id == R.id.nav_inicio) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new Bienvenido()).commit();
        } else if (id == R.id.nav_listClientRe) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new list_follow()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    public void iniciarCompromiso() {
        Toast.makeText(getApplicationContext(),"Busqueda de Compromisos",Toast.LENGTH_SHORT).show();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new list_compromisos()).commit();
    }

    @Override
    public void iniciarListaCliente() {
        Toast.makeText(getApplicationContext(),"Listado clientes",Toast.LENGTH_SHORT).show();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new list_client()).commit();
    }

    @Override
    public void iniciarSeguimiento() {
        Toast.makeText(getApplicationContext(),"Listado de Seguimientos",Toast.LENGTH_SHORT).show();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new list_follow()).commit();
    }

    @Override
    public void iniciarBusquedaCedula() {
        Toast.makeText(getApplicationContext(),"Iniciar busqueda con la cedula",Toast.LENGTH_SHORT).show();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new form_client()).commit();
    }

    @Override
    public void iniciarImportar() {
        Toast.makeText(getApplicationContext(),"Iniciar Importar de asignación y actualización de carteras",Toast.LENGTH_SHORT).show();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new form_export()).commit();
    }

    @Override
    public void iniciarExportar() {
        Toast.makeText(getApplicationContext(),"Iniciar Exportación de sus gestiones",Toast.LENGTH_SHORT).show();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new form_sincronizar()).commit();

        /*Intent intent=new Intent(this, form_export.class);
        startActivity(intent);*/
    }

    @Override
    public void Home() {
        Toast.makeText(getApplicationContext(),"Bienvenido al Menú principal",Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new Bienvenido()).commit();

    }
}
