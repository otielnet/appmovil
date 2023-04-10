package Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Entidad.detailsClient;
import ec.gescom.gescom.GestionCliente;
import ec.gescom.gescom.R;


public class AdaptadorDetailsClientRV extends RecyclerView.Adapter<AdaptadorDetailsClientRV.ClienteViewHolder>  {


    List<detailsClient> listaClient;

    public AdaptadorDetailsClientRV(List<detailsClient> listaClient) {
        this.listaClient = listaClient;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_detalis_client, viewGroup,false);
        ClienteViewHolder clientViewHolder = new ClienteViewHolder(v);
        return clientViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder clienteViewHolder, int i) {
        clienteViewHolder.imagenCli.setImageResource( listaClient.get(i).getImagenClient());
        clienteViewHolder.cCli.setText(listaClient.get(i).getCedulaCliente());
        clienteViewHolder.nCli.setText(listaClient.get(i).getNombreCliente());
        clienteViewHolder.vCli.setText(listaClient.get(i).getValorCliente());
        /////clienteViewHolder.sCli.setText(listaClient.get(i).getSector());
        clienteViewHolder.aCli.setText(listaClient.get(i).getAsignacion());
        clienteViewHolder.carCli.setText(listaClient.get(i).getCartera());
        clienteViewHolder.d1Cli.setText(listaClient.get(i).getDireccion1());
        clienteViewHolder.d2Cli.setText(listaClient.get(i).getDireccion2());
        clienteViewHolder.crClir.setText(listaClient.get(i).getCalificacionRiesgo());
        clienteViewHolder.ppCli.setText(listaClient.get(i).getPrimerPedido());
        clienteViewHolder.idCli.setText(listaClient.get(i).getId());
        clienteViewHolder.idCliAsig.setText(listaClient.get(i).getIdasig());
        clienteViewHolder.refDom.setText(listaClient.get(i).getDomRef());
        clienteViewHolder.mora.setText(listaClient.get(i).getMora());
        clienteViewHolder.abonos.setText(listaClient.get(i).getAbonos());
        ///clienteViewHolder.refDom);

        //set events
        clienteViewHolder.setOnClickListener();

    }



    @Override
    public int getItemCount() {
        return listaClient.size();
    }


    public static class ClienteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //context
        Context context;
        ///text
        ImageView imagenCli;
        TextView cCli, nCli, vCli, sCli, aCli, carCli, d1Cli, d2Cli, crClir, ppCli, idCli, idCliAsig, refDom, mora, abonos;
        Button btnFlecha;
        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            imagenCli = (ImageView)itemView.findViewById(R.id.imagenGescom);
            cCli   = (TextView)itemView.findViewById(R.id.txtcedulaDetails);
            nCli = (TextView)itemView.findViewById(R.id.txtnombresDetails);
            vCli = (TextView)itemView.findViewById(R.id.txtdeudaDetails);
            //////sCli = (TextView)itemView.findViewById(R.id.txtas);
            aCli = (TextView)itemView.findViewById(R.id.txtasignacion);
            carCli = (TextView)itemView.findViewById(R.id.txtcartera);
            btnFlecha = (Button) itemView.findViewById(R.id.imageFlechaDetails);
            d1Cli = (TextView)itemView.findViewById(R.id.txtdireccion1);
            d2Cli = (TextView)itemView.findViewById(R.id.txtdireccion2);
            crClir = (TextView)itemView.findViewById(R.id.txtcalificacionRiesgo);
            ppCli = (TextView)itemView.findViewById(R.id.txtprimerpedido);
            idCli = (TextView)itemView.findViewById(R.id.idObligacionCliente);
            idCliAsig = (TextView)itemView.findViewById(R.id.idObligacionClienteAsig);
            refDom = (TextView)itemView.findViewById(R.id.txtreferencia);
            mora = (TextView)itemView.findViewById(R.id.txtedadmora);
            abonos = (TextView)itemView.findViewById(R.id.txtabonosrealizados);
        }

        void setOnClickListener()
        {
            btnFlecha.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.imageFlechaDetails:
                    Intent intent =new Intent(context, GestionCliente.class);
                    intent.putExtra("cedulaCliente", cCli.getText());
                    intent.putExtra("nombresCliente", nCli.getText());
                    intent.putExtra("valorCliente", vCli.getText());
                    intent.putExtra("asignacionCliente", aCli.getText());
                    intent.putExtra("carteraCliente", carCli.getText());
                    intent.putExtra("calificacioonRiesgoCliente", crClir.getText());
                    intent.putExtra("direccion1Cliente", d1Cli.getText());
                    intent.putExtra("direccion2Cliente", d2Cli.getText());
                    intent.putExtra("primerPedidoCliente", ppCli.getText());
                    intent.putExtra("idCliente", idCli.getText());
                    intent.putExtra("idClienteAsig", idCliAsig.getText());
                    intent.putExtra("direccionReferencia", refDom.getText());
                    intent.putExtra("mora", mora.getText());
                    intent.putExtra("abono", abonos.getText());
                     context.startActivity(intent);
                    break;
                ////se puede añadir mas case
            }
        }
    }
}
