package sse.com.moneyalertsse.Adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sse.com.moneyalertsse.Adapter.myViewHolder.MyViewHolderBudget;
import sse.com.moneyalertsse.R;
import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.Budget;
import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.Budget;

/**
 * Created by cedricakrou on 19/04/18.
 */

public class RecyclerviewAffichageBudget extends RecyclerView.Adapter<MyViewHolderBudget>
{
    private List<Budget> listeBudget;
    private Context context;

    public RecyclerviewAffichageBudget(List<Budget> listeBudget, Context context) {
        this.listeBudget = listeBudget;
        this.context = context;
    }

    @Override
    public MyViewHolderBudget onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.affichage_budget_bd, parent, false);

        return new MyViewHolderBudget(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolderBudget holder, int position)
    {
        holder.getLibelle().setText(listeBudget.get(position).getLibelleBudget());
        holder.getMontant().setText("" + listeBudget.get(position).getMontantBudget());
        holder.getDescription().setText(listeBudget.get(position).getDescriptionBudget());
        holder.getDate().setText(listeBudget.get(position).getDateBudget());
        holder.getHeure().setText(listeBudget.get(position).getHeureBudget());
    }

    @Override
    public int getItemCount() {
        return listeBudget.size();
    }

    public Budget getBudget(int position)
    {
        return this.listeBudget.get(position);
    }

    public void supprimerDonnee(int position)
    {
        listeBudget.remove(position);
        notifyItemRemoved(position);
    }

}
