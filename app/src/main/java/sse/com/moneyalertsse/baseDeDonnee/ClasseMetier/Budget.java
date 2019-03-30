package sse.com.moneyalertsse.baseDeDonnee.ClasseMetier;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cedricakrou on 19/04/18.
 */

public class Budget implements Parcelable
{
    private long idBudget;
    private String libelleBudget;
    private double montantBudget;
    private String typeBudget;
    private String descriptionBudget;
    private String dateBudget;
    private String heureBudget;


    public Budget() {
    }

    public Budget(String libelleBudget) {
        this.libelleBudget = libelleBudget;
    }

    protected Budget(Parcel in) {
        idBudget = in.readLong();
        libelleBudget = in.readString();
        montantBudget = in.readDouble();
        typeBudget = in.readString();
        descriptionBudget = in.readString();
        dateBudget = in.readString();
        heureBudget = in.readString();
    }

    public static final Creator<Budget> CREATOR = new Creator<Budget>() {
        @Override
        public Budget createFromParcel(Parcel in) {
            return new Budget(in);
        }

        @Override
        public Budget[] newArray(int size) {
            return new Budget[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idBudget);
        dest.writeString(libelleBudget);
        dest.writeDouble(montantBudget);
        dest.writeString(typeBudget);
        dest.writeString(descriptionBudget);
        dest.writeString(dateBudget);
        dest.writeString(heureBudget);
    }

    public long getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(long idBudget) {
        this.idBudget = idBudget;
    }

    public String getLibelleBudget() {
        return libelleBudget;
    }

    public void setLibelleBudget(String libelleBudget) {
        this.libelleBudget = libelleBudget;
    }

    public double getMontantBudget() {
        return montantBudget;
    }

    public void setMontantBudget(double montantBudget) {
        this.montantBudget = montantBudget;
    }

    public String getTypeBudget() {
        return typeBudget;
    }

    public void setTypeBudget(String typeBudget) {
        this.typeBudget = typeBudget;
    }

    public String getDescriptionBudget() {
        return descriptionBudget;
    }

    public void setDescriptionBudget(String descriptionBudget)
    {
        this.descriptionBudget = descriptionBudget;
    }

    public String getDateBudget() {
        return dateBudget;
    }

    public void setDateBudget(String dateBudget) {
        this.dateBudget = dateBudget;
    }

    public String getHeureBudget() {
        return heureBudget;
    }

    public void setHeureBudget(String heureBudget) {
        this.heureBudget = heureBudget;
    }
}
