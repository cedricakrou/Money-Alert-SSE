package sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.TypeDepense;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.DaoBase;
import sse.com.moneyalertsse.baseDeDonnee.MethodeBaseDeDonnee;
import sse.com.moneyalertsse.baseDeDonnee.VariableTypeDepense;

/**
 * Created by cedricakrou on 30/04/18.
 */

public class TypeDepenseDao extends DaoBase implements MethodeBaseDeDonnee, VariableTypeDepense
{
    public TypeDepenseDao(Context context) {
        super(context);
    }

    @Override
    public void insertion(Object object)
    {
        TypeDepense typeDepense = (TypeDepense) object;

        ContentValues contentValues = new ContentValues();

        contentValues.put(VariableTypeDepense.nomTypeDepense, typeDepense.getNomTypeDepense());

        sqLiteDatabase.insert(nomTableTypeDepense, null, contentValues);
    }

    @Override
    public Object afficherObject(long id)
    {
        return null;
    }

    // methode permettant d'afficher la liste des objets

    public ArrayList<TypeDepense> afficherTousLesObjets()
    {
        ArrayList<TypeDepense> listeTypeDepnse = new ArrayList<TypeDepense>();

        String requete = "SELECT * FROM " + nomTableTypeDepense;

        Cursor cursor = sqLiteDatabase.rawQuery(requete, null);

        while(cursor.moveToNext())
        {
            TypeDepense typeDepense = creationTypeDepense(cursor);

            listeTypeDepnse.add(typeDepense);

        }

        cursor.close();

        return listeTypeDepnse;
    }

    // methode servant à creer les objets de type

    private TypeDepense creationTypeDepense(Cursor cursor)
    {
        TypeDepense typeDepense = new TypeDepense();

        typeDepense.setIdTypeDepense(cursor.getLong(0));
        typeDepense.setNomTypeDepense(cursor.getString(1));

        return typeDepense;
    }
}
