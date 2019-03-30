package sse.com.moneyalertsse.baseDeDonnee.ClasseMetier;

/**
 * Created by cedricakrou on 30/04/18.
 */

public class TypeDepense
{
    private long idTypeDepense;
    private String nomTypeDepense;

    public long getIdTypeDepense() {
        return idTypeDepense;
    }

    public void setIdTypeDepense(long idTypeDepense) {
        this.idTypeDepense = idTypeDepense;
    }

    public String getNomTypeDepense() {
        return nomTypeDepense;
    }

    public void setNomTypeDepense(String nomDepense) {
        this.nomTypeDepense = nomDepense;
    }
}
