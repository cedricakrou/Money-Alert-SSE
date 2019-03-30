package sse.com.moneyalertsse.baseDeDonnee.ClasseMetier;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cedric akrou on 20/03/2018.
 */

public class Depense implements Parcelable
{
    private long idDepense;
    private String libelleDepense;
    private double montantDepense;
    private String utiliteDepense;
    private String descriptionDepense;
    private String dateDepense;
    private String heureDepense;


    protected Depense(Parcel in) {
        idDepense = in.readLong();
        libelleDepense = in.readString();
        montantDepense = in.readDouble();
        utiliteDepense = in.readString();
        descriptionDepense = in.readString();
        dateDepense = in.readString();
        heureDepense = in.readString();
    }

    public Depense() {
    }

    public static final Creator <Depense> CREATOR = new Creator <Depense>() {
        @Override
        public Depense createFromParcel(Parcel in) {
            return new Depense(in);
        }

        @Override
        public Depense[] newArray(int size) {
            return new Depense[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(idDepense);
        parcel.writeString(libelleDepense);
        parcel.writeDouble(montantDepense);
        parcel.writeString(utiliteDepense);
        parcel.writeString(descriptionDepense);
        parcel.writeString(dateDepense);
        parcel.writeString(heureDepense);
    }

    public long getIdDepense()
    {
        return idDepense;
    }

    public void setIdDepense(long idDepense) {
        this.idDepense = idDepense;
    }

    public String getLibelleDepense() {
        return libelleDepense;
    }

    public void setLibelleDepense(String libelleDepense) {
        this.libelleDepense = libelleDepense;
    }

    public double getMontantDepense() {
        return montantDepense;
    }

    public void setMontantDepense(double montantDepense) {
        this.montantDepense = montantDepense;
    }

    public String getUtiliteDepense() {
        return utiliteDepense;
    }

    public void setUtiliteDepense(String utiliteDepense) {
        this.utiliteDepense =utiliteDepense;
    }

    public String getDescriptionDepense() {
        return descriptionDepense;
    }

    public void setDescriptionDepense(String descriptionDepense) {
        this.descriptionDepense = descriptionDepense;
    }

    public String getDateDepense() {
        return dateDepense;
    }

    public void setDateDepense(String dateDepense) {
        this.dateDepense = dateDepense;
    }

    public String getHeureDepense() {
        return heureDepense;
    }

    public void setHeureDepense(String heureDepense) {
        this.heureDepense = heureDepense;
    }
}
