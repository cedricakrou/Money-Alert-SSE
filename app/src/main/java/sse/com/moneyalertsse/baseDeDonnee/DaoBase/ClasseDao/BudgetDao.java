package sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.Budget;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.DaoBase;
import sse.com.moneyalertsse.baseDeDonnee.MethodeBaseDeDonnee;
import sse.com.moneyalertsse.baseDeDonnee.VariableBudget;

/**
 * Created by cedricakrou on 19/04/18.
 */

public class BudgetDao extends DaoBase implements MethodeBaseDeDonnee, VariableBudget
{
    public BudgetDao(Context context) {
        super(context);
    }


    @Override
    public void insertion(Object object)
    {
        Budget budget = (Budget) object;

        ContentValues contentValues = new ContentValues();

        contentValues.put(VariableBudget.libelleBudget, budget.getLibelleBudget());
        contentValues.put(VariableBudget.montantBudget, budget.getMontantBudget());
        contentValues.put(VariableBudget.typeBudget, budget.getTypeBudget());
        contentValues.put(VariableBudget.descriptionBudget, budget.getDescriptionBudget());
        contentValues.put(VariableBudget.dateBudget, budget.getDateBudget());
        contentValues.put(VariableBudget.heureBudget, budget.getHeureBudget());

        sqLiteDatabase.insert(VariableBudget.nomTableBudget, null, contentValues);
    }

    public void miseAjour(Object object)
    {
        Budget budget = (Budget) object;

        ContentValues contentValues = new ContentValues();

        contentValues.put(VariableBudget.libelleBudget, budget.getLibelleBudget());
        contentValues.put(VariableBudget.montantBudget, budget.getMontantBudget());
        contentValues.put(VariableBudget.typeBudget, budget.getTypeBudget());
        contentValues.put(VariableBudget.descriptionBudget, budget.getDescriptionBudget());
        contentValues.put(VariableBudget.dateBudget, budget.getDateBudget());
        contentValues.put(VariableBudget.heureBudget, budget.getHeureBudget());


        sqLiteDatabase.update(VariableBudget.nomTableBudget, contentValues, VariableBudget.idBudget + "=?", new String[]{String.valueOf(budget.getIdBudget())});
    }


    public int supprimerBudget(long id)
    {

        return sqLiteDatabase.delete(VariableBudget.nomTableBudget, VariableBudget.idBudget + "=?", new String []{String.valueOf(id)});
    }



    @Override
    public Object afficherObject(long id)
    {
        return null;
    }

    public List<Budget> afficherBudget(String typeBudget)
    {
        String requete = "SELECT * FROM " + VariableBudget.nomTableBudget + " WHERE " + VariableBudget.typeBudget + " =?";

        Cursor cursor =  sqLiteDatabase.rawQuery(requete, new String[]{typeBudget});

        List<Budget> listeBudget = new ArrayList<Budget>();

        while (cursor.moveToNext())
        {
            Budget budget = creationBudget(cursor);

            listeBudget.add(budget);

        }

        cursor.close();

        return listeBudget;
    }

    /**
     * methode servant à obtenir le montant total de depenses effectués
     * @return
     */

    public double montantTotalSelonLibelle(String typeDepense)
    {
        String scriptBd = "SELECT SUM(" + VariableBudget.montantBudget + ") FROM " + VariableBudget.nomTableBudget + " WHERE " + VariableBudget.typeBudget + "=?";

        Cursor cursor = sqLiteDatabase.rawQuery(scriptBd, new String[]{typeDepense});

        cursor.moveToFirst();

        double somme = 0;

        if(cursor.getCount() > 0)
        {
            somme = cursor.getDouble(0);
        }

        return somme;
    }


    public Budget creationBudget(Cursor cursor)
    {
        Budget budget = new Budget();

        budget.setIdBudget(cursor.getLong(0));
        budget.setLibelleBudget(cursor.getString(1));
        budget.setMontantBudget(cursor.getDouble(2));
        budget.setTypeBudget(cursor.getString(3));
        budget.setDescriptionBudget(cursor.getString(4));
        budget.setDateBudget(cursor.getString(5));
        budget.setHeureBudget(cursor.getString(6));

        return budget;
    }

}
