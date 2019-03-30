package sse.com.moneyalertsse.Adapter.myViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sse.com.moneyalertsse.R;

/**
 * Created by cedricakrou on 09/04/2018.
 */

public class MyViewHolderDepense extends RecyclerView.ViewHolder
{
    private TextView libelleDepense;
    private TextView montantDepense;
    private TextView descriptionDepense;
    private TextView utiliteDepense;
    private TextView dateDepense;
    private TextView heureDepense;


    public MyViewHolderDepense(View itemView) {
        super(itemView);

        libelleDepense =  (TextView) itemView.findViewById(R.id.libelleDepenseBd);
        montantDepense = (TextView) itemView.findViewById(R.id.montantDepenseBd);
        descriptionDepense = (TextView) itemView.findViewById(R.id.descriptionDepenseBd);
        utiliteDepense = (TextView) itemView.findViewById(R.id.utiliteDepenseBd);
        dateDepense = (TextView) itemView.findViewById(R.id.dateDepenseBd);
        heureDepense = (TextView) itemView.findViewById(R.id.heureDepenseBd);
    }

    public TextView getLibelleDepense() {
        return libelleDepense;
    }

    public TextView getMontantDepense() {
        return montantDepense;
    }

    public TextView getDescriptionDepense() {
        return descriptionDepense;
    }

    public TextView getUtiliteDepense() {
        return utiliteDepense;
    }

    public TextView getDateDepense() {
        return dateDepense;
    }

    public TextView getHeureDepense() {
        return heureDepense;
    }
}
