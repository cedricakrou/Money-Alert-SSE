package sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.TypeGain;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.DaoBase;
import sse.com.moneyalertsse.baseDeDonnee.MethodeBaseDeDonnee;
import sse.com.moneyalertsse.baseDeDonnee.VariableTypeGain;

/**
 * Created by cedricakrou on 30/04/18.
 */

public class TypeGainDao extends DaoBase implements MethodeBaseDeDonnee, VariableTypeGain
{
    public TypeGainDao(Context context) {
        super(context);
    }

    @Override
    public void insertion(Object object)
    {

        TypeGain typeGain = (TypeGain) object;

        ContentValues contentValues = new ContentValues();

        contentValues.put(VariableTypeGain.nomTypeGain, typeGain.getNomTypeGain());

        sqLiteDatabase.insert(VariableTypeGain.nomTableTypeGain, null, contentValues);

    }

    @Override
    public Object afficherObject(long id)
    {
        return null;
    }

    // methode permettant d'afficher la liste des objets type gain

    public ArrayList<TypeGain> afficherTousLesObjets()
    {
        ArrayList<TypeGain> listeTypeGain = new ArrayList<TypeGain>();

        String requete = "SELECT * FROM " + VariableTypeGain.nomTableTypeGain;

        Cursor cursor = sqLiteDatabase.rawQuery(requete, null);

        while(cursor.moveToNext())
        {
            TypeGain typeGain = creationTypeGain(cursor);

            listeTypeGain.add(typeGain);

        }

        cursor.close();

        return listeTypeGain;
    }

    // methode servant à creer les objets de type gain

    private TypeGain creationTypeGain(Cursor cursor)
    {
        TypeGain typeGain = new TypeGain();

        typeGain.setIdTypeGain(cursor.getLong(0));
        typeGain.setNomTypeGain(cursor.getString(1));

        return typeGain;
    }
}
