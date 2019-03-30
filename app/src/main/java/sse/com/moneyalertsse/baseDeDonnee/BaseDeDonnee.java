package sse.com.moneyalertsse.baseDeDonnee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static sse.com.moneyalertsse.baseDeDonnee.VariableTypeGain.idTypeGain;
import static sse.com.moneyalertsse.baseDeDonnee.VariableTypeGain.nomTableTypeGain;
import static sse.com.moneyalertsse.baseDeDonnee.VariableTypeGain.nomTypeGain;

/**
 * Created by cedricakrou on 20/03/2018.
 * cette classe sert à
 */

public class BaseDeDonnee extends SQLiteOpenHelper implements VariableGain, VariableDepense, VariableBudget, VariableTypeDepense
{
    // script de creation de la table gain

    private String creationTableGain = "CREATE TABLE " +  nomTableGain
            + "("
            + idGain + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + libelleGain + " TEXT,"
            + montantGain + " REAL,"
            + descriptionGain + " TEXT,"
            + dateGain + " TEXT,"
            + heureGain + " TEXT"
            + ");";

    // script de creation de la table depense

    private String creationTableDepense = "CREATE TABLE " + nomTableDepense
            + "("
            + idDepense + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + libelleDepense + " TEXT,"
            + montantDepense + " REAL,"
            + utiliteDepense + " TEXT,"
            + descriptionDepense + " TEXT,"
            + dateDepense + " TEXT,"
            + heureDepense + " TEXT"
            + ");";

    // script de creation de la table budget

    private String creationTableBudget = "CREATE TABLE " +  nomTableBudget
            + "("
            + idBudget + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + libelleBudget + " TEXT,"
            + montantBudget + " REAL,"
            + typeBudget + " TEXT,"
            + descriptionBudget + " TEXT,"
            + dateBudget + " TEXT,"
            + heureBudget + " TEXT"
            + ");";

    // script de creation de la table type de depense

    private String creationTableTypeDepense = "CREATE TABLE " + nomTableTypeDepense
            + "("
            + idTypeDepense + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + nomTypeDepense + " TEXT NOT NULL"
            + ");";

    // script de creation de la table type de depense

    private String creationTableTypeGain = "CREATE TABLE " + nomTableTypeGain
            + "("
            + idTypeGain + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + nomTypeGain + " TEXT NOT NULL"
            + ");";


    private String miseAjourTableGain = "DROP TABLE IF EXISTS " + nomTableGain + ";";
    private String miseAjourTableDepense = "DROP TABLE IF EXISTS " + nomTableDepense +";";
    private String miseAjourTableBudget = "DROP TABLE IF EXISTS " + nomTableBudget +";";
    private String miseAjourTableTypeDepense = "DROP TABLE IF EXISTS " + nomTableTypeDepense +";";
    private String miseAjourTableTypeGain = "DROP TABLE IF EXISTS " + nomTableTypeGain +";";

    public BaseDeDonnee(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(creationTableGain);
        sqLiteDatabase.execSQL(creationTableDepense);
        sqLiteDatabase.execSQL(creationTableBudget);
        sqLiteDatabase.execSQL(creationTableTypeDepense);
        sqLiteDatabase.execSQL(creationTableTypeGain);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL(miseAjourTableGain);
        sqLiteDatabase.execSQL(miseAjourTableDepense);
        sqLiteDatabase.execSQL(miseAjourTableBudget);
        sqLiteDatabase.execSQL(miseAjourTableTypeDepense);
        sqLiteDatabase.execSQL(miseAjourTableTypeGain);
        onCreate(sqLiteDatabase);
    }
}
