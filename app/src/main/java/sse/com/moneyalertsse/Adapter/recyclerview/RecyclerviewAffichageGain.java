package sse.com.moneyalertsse.Adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import sse.com.moneyalertsse.Adapter.myViewHolder.MyViewHolderGain;
import sse.com.moneyalertsse.R;
import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.Gain;

/**
 * Created by cedric akrou on 09/04/2018.
 */

public class RecyclerviewAffichageGain extends RecyclerView.Adapter<MyViewHolderGain>
{
    private Context context;
    private List<Gain> listeGain;

    public RecyclerviewAffichageGain(Context context, List <Gain> listeGain) {
        this.context = context;
        this.listeGain = listeGain;
    }

    @Override
    public MyViewHolderGain onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.affichage_gain_bd, parent, false);

        MyViewHolderGain myViewHolderGain = new MyViewHolderGain(view);

        return myViewHolderGain;
    }

    @Override
    public void onBindViewHolder(MyViewHolderGain holder, int position)
    {
        holder.getLibelleGain().setText(listeGain.get(position).getLibelleGain());
        holder.getMontantGain().setText( "" + listeGain.get(position).getMontantGain());
        holder.getDescriptionGain().setText(listeGain.get(position).getDescriptionGain());
        holder.getDateGain().setText(listeGain.get(position).getDateGain());
        holder.getHeureGain().setText(listeGain.get(position).getHeureGain());


    }

    @Override
    public int getItemCount() {
        return listeGain.size();
    }

    public Gain getGain(int position)
    {
        return this.listeGain.get(position);
    }

    public void supprimerDonnee(int position)
    {
        listeGain.remove(position);
        notifyItemRemoved(position);
    }


}
