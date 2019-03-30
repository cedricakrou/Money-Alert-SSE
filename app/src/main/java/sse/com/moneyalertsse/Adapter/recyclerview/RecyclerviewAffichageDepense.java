package sse.com.moneyalertsse.Adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sse.com.moneyalertsse.Adapter.myViewHolder.MyViewHolderDepense;
import sse.com.moneyalertsse.R;
import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.Depense;
import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.Depense;

/**
 * Created by cedric akrou on 09/04/2018.
 */

public class RecyclerviewAffichageDepense extends RecyclerView.Adapter<MyViewHolderDepense>
{
    private List<Depense> listeDepense;
    private Context context;

    public RecyclerviewAffichageDepense(Context context, List <Depense> listeDepense)
    {
        this.listeDepense = listeDepense;
        this.context = context;
    }

    @Override
    public MyViewHolderDepense onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.affichage_depense_bd, parent, false);

        return new MyViewHolderDepense(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolderDepense holder, int position)
    {

           holder.getLibelleDepense().setText(listeDepense.get(position).getLibelleDepense());
           holder.getMontantDepense().setText("" + listeDepense.get(position).getMontantDepense());
           holder.getDescriptionDepense().setText(listeDepense.get(position).getDescriptionDepense());
           holder.getUtiliteDepense().setText(listeDepense.get(position).getUtiliteDepense());
           holder.getDateDepense().setText(listeDepense.get(position).getDateDepense());
           holder.getHeureDepense().setText(listeDepense.get(position).getHeureDepense());
    }

    @Override
    public int getItemCount()
    {
        return listeDepense.size();
    }

    public Depense getDepense(int position)
    {
        return this.listeDepense.get(position);
    }


    public void supprimerDonnee(int position)
    {
        listeDepense.remove(position);
        notifyItemRemoved(position);
    }

}
