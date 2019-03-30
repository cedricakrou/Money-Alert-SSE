package sse.com.moneyalertsse.baseDeDonnee.ClasseMetier;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cedric akrou on 20/03/2018.
 */

public class Gain implements Parcelable
{
    private long idGain;
    private String libelleGain;
    private double montantGain;
    private String descriptionGain;
    private String dateGain;
    private String heureGain;


    public Gain() {
    }

    public Gain(String libelleGain) {
        this.libelleGain = libelleGain;
    }

    public long getIdGain() {
        return idGain;
    }

    public void setIdGain(long idGain) {
        this.idGain = idGain;
    }

    public String getLibelleGain() {
        return libelleGain;
    }

    public void setLibelleGain(String libelleGain) {
        this.libelleGain = libelleGain;
    }

    public double getMontantGain() {
        return montantGain;
    }

    public void setMontantGain(double montantGain) {
        this.montantGain = montantGain;
    }

    public String getDescriptionGain() {
        return descriptionGain;
    }

    public void setDescriptionGain(String descriptionGain) {
        this.descriptionGain = descriptionGain;
    }

    public String getDateGain() {
        return dateGain;
    }

    public void setDateGain(String dateGain) {
        this.dateGain = dateGain;
    }

    public String getHeureGain() {
        return heureGain;
    }

    public void setHeureGain(String heureGain) {
        this.heureGain = heureGain;
    }

    public static Creator <Gain> getCREATOR() {
        return CREATOR;
    }

    protected Gain(Parcel in)
    {
        idGain = in.readLong();
        libelleGain = in.readString();
        montantGain = in.readDouble();
        descriptionGain = in.readString();
        dateGain = in.readString();
        heureGain = in.readString();
    }

    public static final Creator <Gain> CREATOR = new Creator <Gain>() {
        @Override
        public Gain createFromParcel(Parcel in) {
            return new Gain(in);
        }

        @Override
        public Gain[] newArray(int size) {
            return new Gain[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(idGain);
        parcel.writeString(libelleGain);
        parcel.writeDouble(montantGain);
        parcel.writeString(descriptionGain);
        parcel.writeString(dateGain);
        parcel.writeString(heureGain);
    }
}
