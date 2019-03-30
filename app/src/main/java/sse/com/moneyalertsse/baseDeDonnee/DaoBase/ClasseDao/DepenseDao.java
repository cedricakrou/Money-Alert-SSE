package sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.Depense;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.DaoBase;
import sse.com.moneyalertsse.baseDeDonnee.MethodeBaseDeDonnee;
import sse.com.moneyalertsse.baseDeDonnee.VariableDepense;

/**
 * Created by cedric akrou on 21/03/2018.
 * classe servant de creer les methodes pour inserer , afficher et faire les mises à jour des depenses
 */

public class DepenseDao extends DaoBase implements VariableDepense, MethodeBaseDeDonnee
{
    public DepenseDao(Context context)
    {
        super(context);
    }

    @Override
    public void insertion(Object object)
    {
        Depense depense = (Depense) object;

        ContentValues contentValues = new ContentValues();

        contentValues.put(libelleDepense, depense.getLibelleDepense());
        contentValues.put(montantDepense, depense.getMontantDepense());
        contentValues.put(utiliteDepense, depense.getUtiliteDepense());
        contentValues.put(descriptionDepense, depense.getDescriptionDepense());
        contentValues.put(dateDepense, depense.getDateDepense());
        contentValues.put(heureDepense, depense.getHeureDepense());

        sqLiteDatabase.insert(nomTableDepense, null, contentValues);

    }

    public void miseAjour(Object object)
    {
        Depense Depense = (Depense) object;

        ContentValues contentValues = new ContentValues();

        contentValues.put(libelleDepense, Depense.getLibelleDepense());
        contentValues.put(montantDepense, Depense.getMontantDepense());
        contentValues.put(descriptionDepense, Depense.getDescriptionDepense());
        contentValues.put(dateDepense, Depense.getDateDepense());
        contentValues.put(heureDepense, Depense.getHeureDepense());

        sqLiteDatabase.update(VariableDepense.nomTableDepense, contentValues, VariableDepense.idDepense + "=?", new String[]{String.valueOf(Depense.getIdDepense())});
    }


    public int supprimerDepense(long id)
    {
        return sqLiteDatabase.delete(VariableDepense.nomTableDepense, VariableDepense.idDepense + "=?", new String []{String.valueOf(id)});
    }


    @Override
    public Depense afficherObject(long id)
    {
        String requete = "SELECT * FROM " + nomTableDepense + " WHERE " + idDepense + " =?";

        Cursor cursor = sqLiteDatabase.rawQuery(requete, new String[]{String.valueOf(id)});

        cursor.moveToFirst();

        return creationDepense(cursor);
    }

    /**
     * methode permettant d'afficher toutes les depenses de la base de donnée
o     * @return . il retourne la liste des depenses
     */

    public ArrayList<Depense> afficherTousLesObjets()
    {
        ArrayList<Depense> listeDepense = new ArrayList <Depense>();

        String requete = "SELECT * FROM " + nomTableDepense;

        Cursor cursor = sqLiteDatabase.rawQuery(requete, null);

        while (cursor.moveToNext())
        {

            Depense depense = creationDepense(cursor);

            listeDepense.add(depense);

        }

        cursor.close();

        return listeDepense;
    }

    /**
     * methode permettant de faire la somme des montants des données qui ont le meme intitulé
     * @param champ
     * @return
     */

    public double SommeChamp(String champ)
    {

        String scriptBd = "SELECT SUM(" + montantDepense + ") FROM " + nomTableDepense + " WHERE " + utiliteDepense + "= ? ";

        Cursor cursor = sqLiteDatabase.rawQuery(scriptBd, new String[]{champ});

        cursor.moveToFirst();

        double somme = 0;

        if(cursor != null && cursor.getCount() > 0 )
        {
            somme = cursor.getDouble(0);
        }

        return somme;
    }

    public double SommeChampByType(String champ)
    {

        String scriptBd = "SELECT SUM(" + montantDepense + ") FROM " + nomTableDepense + " WHERE " + VariableDepense.libelleDepense + "= ? ";

        Cursor cursor = sqLiteDatabase.rawQuery(scriptBd, new String[]{champ});

        cursor.moveToFirst();

        double somme = 0;

        if(cursor != null && cursor.getCount() > 0 )
        {
            somme = cursor.getDouble(0);
        }

        return somme;
    }


    /**
     * methode servant à obtenir le montant total de depenses effectués
     * @return
     */


    public double montantTotal()
    {
        String scriptBd = "SELECT SUM(" + VariableDepense.montantDepense + ") FROM " + VariableDepense.nomTableDepense;

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

    private Depense creationDepense(Cursor cursor)
    {
        Depense depense = new Depense();

        depense.setIdDepense(cursor.getLong(0));
        depense.setLibelleDepense(cursor.getString(1));
        depense.setMontantDepense(cursor.getDouble(2));
        depense.setUtiliteDepense(cursor.getString(3));
        depense.setDescriptionDepense(cursor.getString(4));
        depense.setDateDepense(cursor.getString(5));
        depense.setHeureDepense(cursor.getString(6));

        return depense;
    }

    public void misAjourUtilite()
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(utiliteDepense, "inutile");

        sqLiteDatabase.update(VariableDepense.nomTableDepense, contentValues, VariableDepense.utiliteDepense + "=?", new String[]{String.valueOf("unutile")});

    }

}
