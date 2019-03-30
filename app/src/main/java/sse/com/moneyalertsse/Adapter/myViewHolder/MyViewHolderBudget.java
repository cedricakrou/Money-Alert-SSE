package sse.com.moneyalertsse.Adapter.myViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sse.com.moneyalertsse.R;

/**
 * Created by cedricakrou on 19/04/18.
 */

public class MyViewHolderBudget extends RecyclerView.ViewHolder
{
    private TextView libelle = null;
    private TextView montant = null;
    private TextView description = null;
    private TextView date = null;
    private TextView heure = null;

    public MyViewHolderBudget(View itemView)
    {
        super(itemView);

        libelle = itemView.findViewById(R.id.libelleBudgetBd);
        montant = itemView.findViewById(R.id.montantBudgetBd);
        description = itemView.findViewById(R.id.descriptionBudgetBd);
        date = itemView.findViewById(R.id.dateBudgetBd);
        heure = itemView.findViewById(R.id.heureBudgetBd);

    }

    public TextView getLibelle() {
        return libelle;
    }

    public TextView getMontant() {
        return montant;
    }

    public TextView getDescription() {
        return description;
    }

    public TextView getDate() {
        return date;
    }

    public TextView getHeure() {
        return heure;
    }
}
