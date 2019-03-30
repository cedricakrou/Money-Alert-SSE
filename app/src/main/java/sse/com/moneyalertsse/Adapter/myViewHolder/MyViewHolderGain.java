package sse.com.moneyalertsse.Adapter.myViewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sse.com.moneyalertsse.R;

/**
 * Created by cedric akrou on 09/04/2018.
 */

public class MyViewHolderGain extends RecyclerView.ViewHolder
{
    private TextView libelleGain;
    private TextView montantGain;
    private TextView descriptionGain;
    private TextView dateGain;
    private TextView heureGain;

    public MyViewHolderGain(View itemView) {
        super(itemView);

        libelleGain = (TextView) itemView.findViewById(R.id.libelleGainBd);
        montantGain = (TextView) itemView.findViewById(R.id.montantGainBd);
        descriptionGain = (TextView) itemView.findViewById(R.id.descriptionGainBd);
        dateGain = (TextView) itemView.findViewById(R.id.dateGainBd);
        heureGain = (TextView) itemView.findViewById(R.id.heureGainBd);
    }

    public TextView getLibelleGain() {
        return libelleGain;
    }

    public TextView getMontantGain() {
        return montantGain;
    }

    public TextView getDescriptionGain() {
        return descriptionGain;
    }

    public TextView getDateGain() {
        return dateGain;
    }

    public TextView getHeureGain() {
        return heureGain;
    }

}
