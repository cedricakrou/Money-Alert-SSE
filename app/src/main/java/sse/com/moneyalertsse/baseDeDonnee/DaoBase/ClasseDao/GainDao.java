package sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.Gain;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.DaoBase;
import sse.com.moneyalertsse.baseDeDonnee.MethodeBaseDeDonnee;
import sse.com.moneyalertsse.baseDeDonnee.VariableDepense;
import sse.com.moneyalertsse.baseDeDonnee.VariableGain;

/**
 * Created by cedric akrou on 20/03/2018.
 * classe servant de creer les methodes pour inserer , afficher et faire les mises à jour des gains
 */

public class GainDao extends DaoBase implements VariableGain, MethodeBaseDeDonnee
{
    public GainDao(Context context)
    {
        super(context);
    }

    @Override
    public void insertion(Object object)
    {
        Gain gain = (Gain) object;

        ContentValues contentValues = new ContentValues();

        contentValues.put(libelleGain, gain.getLibelleGain());
        contentValues.put(montantGain, gain.getMontantGain());
        contentValues.put(descriptionGain, gain.getDescriptionGain());
        contentValues.put(dateGain, gain.getDateGain());
        contentValues.put(heureGain, gain.getHeureGain());

        sqLiteDatabase.insert(nomTableGain, null, contentValues);
    }

    @Override
    public Gain afficherObject(long id)
    {
        String scriptBd = "SELECT * FROM " + nomTableGain + " WHERE " + idGain + "= ? ";

        Cursor cursor = sqLiteDatabase.rawQuery(scriptBd, new String[]{String.valueOf(id)});

        cursor.moveToFirst();

        return creationGain(cursor);
    }

    public void miseAjour(Object object)
    {
        Gain gain = (Gain) object;

        ContentValues contentValues = new ContentValues();

        contentValues.put(libelleGain, gain.getLibelleGain());
        contentValues.put(montantGain, gain.getMontantGain());
        contentValues.put(descriptionGain, gain.getDescriptionGain());
        contentValues.put(dateGain, gain.getDateGain());
        contentValues.put(heureGain, gain.getHeureGain());

        sqLiteDatabase.update(VariableGain.nomTableGain, contentValues, VariableGain.idGain + "=?", new String[]{String.valueOf(gain.getIdGain())});
    }


    public int supprimerGain(long id)
    {

     return sqLiteDatabase.delete(VariableGain.nomTableGain, VariableGain.idGain + "=?", new String []{String.valueOf(id)});
    }

    /**
     * methode permettant d'afficher toutes les gains de la base de donnée
     * @return . il retourne la liste des gains
     */

    public ArrayList<Gain> afficherTousLesObjets()
    {
        String scriptBd = "SELECT * FROM " + nomTableGain;

        Cursor cursor = sqLiteDatabase.rawQuery(scriptBd, null);
        ArrayList<Gain> listeGain = new ArrayList <Gain>();

        while(cursor.moveToNext())
        {
            Gain gain = creationGain(cursor);

            listeGain.add(gain);
        }

        cursor.close();

        return listeGain;
    }


    public double SommeChamp(String champ)
    {

        String scriptBd = "SELECT SUM(" + montantGain + ") FROM " + nomTableGain + " WHERE " + libelleGain + "= ? ";

        Cursor cursor = sqLiteDatabase.rawQuery(scriptBd, new String[]{champ});

        cursor.moveToFirst();

        double somme = 0;

        if(cursor.getCount() > 0)
        {
            somme = cursor.getDouble(0);
        }

        return somme;
    }

    /**
     * methode servant à obtenir le montant total de revenus enregistrés
     * @return
     */

    public double montantTotal()
    {
        String scriptBd = "SELECT SUM(" + VariableGain.montantGain + ") FROM " + VariableGain.nomTableGain;

        Cursor cursor = sqLiteDatabase.rawQuery(scriptBd, null);

        cursor.moveToFirst();

        double somme = 0;

        if(cursor.getCount() > 0)
        {
            somme = cursor.getDouble(0);
        }

        return somme;
    }


    // methode servant à creer une depense à partir des info de la bd et du cursor recupéré pour la recherche de donnée

    private Gain creationGain(Cursor cursor)
    {
        Gain gain = new Gain();

        gain.setIdGain(cursor.getLong(0));
        gain.setLibelleGain(cursor.getString(1));
        gain.setMontantGain(cursor.getDouble(2));
        gain.setDescriptionGain(cursor.getString(3));
        gain.setDateGain(cursor.getString(4));
        gain.setHeureGain(cursor.getString(5));

        return gain;
    }
}
