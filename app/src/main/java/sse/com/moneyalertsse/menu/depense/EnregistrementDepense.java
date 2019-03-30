package sse.com.moneyalertsse.menu.depense;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sse.com.moneyalertsse.R;
import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.Depense;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.DepenseDao;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.TypeDepenseDao;
import sse.com.moneyalertsse.menu.statistique.StatistiqueDepense;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnregistrementDepense extends Fragment implements AdapterView.OnItemSelectedListener
{

    private EditText montantDepense = null;
    private Switch utiliteDepense = null;
    private EditText descriptionDepense = null;

    private String texteLibelleDepense = "";

    private String texteDate = "";
    private String texteHeure = "";

    private List<String> listeTypeDepense = null;

    public EnregistrementDepense()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        listeTypeDepense = new ArrayList<String>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        listeTypeDepense.add(getResources().getString(R.string.texteSelectionnerUnTypeOperation));
        listeTypeDepense.add("transport");
        listeTypeDepense.add("nourriture");
        listeTypeDepense.add("besoin hygiénique");

        // acces à la base de donnée pour obtenir les types de depense

        TypeDepenseDao typeDepenseDao = new TypeDepenseDao(getActivity().getApplicationContext());

        typeDepenseDao.ouvrirBaseDonnee();

        typeDepenseDao.afficherTousLesObjets();

        if (typeDepenseDao.afficherTousLesObjets().size() > 0)
        {
            for (int i = 0; i < typeDepenseDao.afficherTousLesObjets().size(); i++)
            {
                listeTypeDepense.add(typeDepenseDao.afficherTousLesObjets().get(i).getNomTypeDepense());
            }

        }

        typeDepenseDao.fermerBaseDeDonnee();

        // fin de l'acces


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enregistrement_depense, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // association des données au spinner et gestion de la selaection d'element
        Spinner libelleDepense = (Spinner) view.findViewById(R.id.idLibelleDepense);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listeTypeDepense);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        libelleDepense.setAdapter(arrayAdapter);
        libelleDepense.setOnItemSelectedListener(this);


        montantDepense = (EditText) view.findViewById(R.id.idMontantDepense);
        descriptionDepense = (EditText) view.findViewById(R.id.idDescriptionDeDepense);
        utiliteDepense = (Switch) view.findViewById(R.id.idUtiliteDepense);
        Button boutonDepense = (Button) view.findViewById(R.id.idBoutonSubmitDepense);

        // methode permettant gerer l'affichage et le changement de date
        final DatePicker calendrierDepense = (DatePicker) view.findViewById(R.id.idDateDepense);
        texteDate = calendrierDepense.getDayOfMonth() + "/" + (calendrierDepense.getMonth() + 1) + "/" + calendrierDepense.getYear();

        calendrierDepense.init(calendrierDepense.getYear(), calendrierDepense.getMonth(), calendrierDepense.getDayOfMonth(),  new DatePicker.OnDateChangedListener()
        {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                texteDate = dayOfMonth + "/" + monthOfYear + "/" + year;
            }

        });


        // methode permettant l'affichage et changer d'heure
        final TimePicker timePickerDepense = (TimePicker) view.findViewById(R.id.idTempsDepense);
        timePickerDepense.setIs24HourView(true);
        texteHeure = timePickerDepense.getCurrentHour() + ":" + timePickerDepense.getCurrentMinute();


        timePickerDepense.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
            {
                texteHeure = hourOfDay + ":" + minute;
            }
        });


        final Switch choixGHDepense = (Switch) view.findViewById(R.id.idChoixDhDepense);

        choixGHDepense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    calendrierDepense.setVisibility(View.VISIBLE);
                    timePickerDepense.setVisibility(View.VISIBLE);

                }
                else
                {
                    calendrierDepense.setVisibility(View.GONE);
                    timePickerDepense.setVisibility(View.GONE);

                }

            }
        });
        // gestion du clic sur le bouton depense

        boutonDepense.setOnClickListener(clickBoutonDepense);

    }

    // gestion de l'enregistrement des données dans la base de donnée

    private View.OnClickListener clickBoutonDepense = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            new Thread(new Runnable() {
                @Override
                public void run()
                {

                    if (texteLibelleDepense.equals(getResources().getString(R.string.texteSelectionnerUnTypeOperation)))
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.texteSelectionnerUnTypeOperation), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                    {

                        try
                        {
                            // ouverture et insertion des données dans la base de donnée

                            DepenseDao depenseDao = new DepenseDao(getContext());

                            depenseDao.ouvrirBaseDonnee();

                            Depense depense = new Depense();

                            // recuperation des données saisies

                            String texteUtiliteDepense = "";

                            if(utiliteDepense.isChecked())
                            {
                                texteUtiliteDepense = StatistiqueDepense.depenseUtile;
                            }
                            else
                            {
                                texteUtiliteDepense = StatistiqueDepense.depenseInutile;
                            }

                            depense.setLibelleDepense(texteLibelleDepense);
                            depense.setMontantDepense(Double.parseDouble(montantDepense.getText().toString()));
                            depense.setUtiliteDepense(texteUtiliteDepense);
                            depense.setDescriptionDepense(descriptionDepense.getText().toString());
                            depense.setDateDepense(texteDate);
                            depense.setHeureDepense(texteHeure);

                            depenseDao.insertion(depense);

                            // on vide la listeDepense

                            AffichageDepense.listeDepense.clear();

                            // et on recupere les données de la base pour l'affichage dans Affichage Depense

                            AffichageDepense.listeDepense.addAll(depenseDao.afficherTousLesObjets());

                            depenseDao.fermerBaseDeDonnee();

                            // remise à zero des champs de saisie



                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(getActivity(), "Depense enregistré", Toast.LENGTH_SHORT).show();

                                    montantDepense.getText().clear();
                                    descriptionDepense.getText().clear();

                                    AffichageDepense.recyclerviewAffichageDepense.notifyDataSetChanged();

                                }
                            });

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(getActivity(), "Echec, veuillez reesayer", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                }
            }).start();


        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        texteLibelleDepense = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
