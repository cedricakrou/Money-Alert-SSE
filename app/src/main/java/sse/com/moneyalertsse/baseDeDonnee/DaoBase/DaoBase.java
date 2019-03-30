package sse.com.moneyalertsse.baseDeDonnee.DaoBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import sse.com.moneyalertsse.baseDeDonnee.BaseDeDonnee;

/**
 * Created by cedric akrou on 20/03/2018.
 */

public abstract class DaoBase extends BaseDeDonnee
{
    private static final String nomBaseDeDonnee = "MoneyAlertSSE.db";
    private static final int versionBaseDeDonnee = 1;

    protected BaseDeDonnee baseDeDonnee = null;
    protected SQLiteDatabase sqLiteDatabase = null;

    public DaoBase(Context context)
    {
        super(context, nomBaseDeDonnee, null, versionBaseDeDonnee);
        baseDeDonnee = new BaseDeDonnee(context, nomBaseDeDonnee, null, versionBaseDeDonnee);
    }

    public SQLiteDatabase ouvrirBaseDonnee()
    {
        sqLiteDatabase = baseDeDonnee.getWritableDatabase();
        return sqLiteDatabase;
    }

    public void fermerBaseDeDonnee()
    {
        sqLiteDatabase.close();
    }

}
