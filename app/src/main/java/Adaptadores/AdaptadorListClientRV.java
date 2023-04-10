package Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Entidad.Client;
import ec.gescom.gescom.DetailsActivityClient;
import ec.gescom.gescom.R;


public class AdaptadorListClientRV extends RecyclerView.Adapter<AdaptadorListClientRV.ClienteViewHolder>  {


    List<Client> listaClient;

    public AdaptadorListClientRV(List<Client> listaClient) {
        this.listaClient = listaClient;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_list_client, viewGroup,false);
        ClienteViewHolder clientViewHolder = new ClienteViewHolder(v);
        return clientViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder clienteViewHolder, int i) {
        clienteViewHolder.imagenCli.setImageResource( listaClient.get(i).getImagenClient());
        clienteViewHolder.cCli.setText(listaClient.get(i).getCedulaCliente());
        clienteViewHolder.nCli.setText(listaClient.get(i).getNombreCliente());
        clienteViewHolder.vCli.setText(listaClient.get(i).getValorCliente());
        clienteViewHolder.sCli.setText(listaClient.get(i).getObligacionCliente());
        //clienteViewHolder.aCli.setText(listaClient.get(i).getAsignacion());


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
        TextView cCli, nCli, vCli, sCli, aCli;
        ImageButton btnFlecha;
        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            imagenCli = (ImageView)itemView.findViewById(R.id.imagen);
            cCli   = (TextView)itemView.findViewById(R.id.txtcedula);
            nCli = (TextView)itemView.findViewById(R.id.txtnombres);
            vCli = (TextView)itemView.findViewById(R.id.txtdeuda);
            sCli = (TextView)itemView.findViewById(R.id.txtobigaciones);
            ///aCli = (TextView)itemView.findViewById(R.id.txtasignacion);
            btnFlecha = (ImageButton) itemView.findViewById(R.id.imageFlecha);
        }

        void setOnClickListener()
        {
            btnFlecha.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.imageFlecha:
                    Intent intent =new Intent(context, DetailsActivityClient.class);
                    intent.putExtra("cedulaCliente", cCli.getText());
                     context.startActivity(intent);
                    break;
                ////se puede añadir mas case
            }
        }
    }
}
