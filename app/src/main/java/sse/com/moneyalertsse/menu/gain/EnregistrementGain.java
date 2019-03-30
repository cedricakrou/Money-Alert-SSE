package sse.com.moneyalertsse.menu.gain;


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
import java.util.logging.Logger;

import sse.com.moneyalertsse.R;
import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.Gain;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.GainDao;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.TypeGainDao;
import sse.com.moneyalertsse.menu.statistique.StatistiqueGain;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnregistrementGain extends Fragment implements AdapterView.OnItemSelectedListener
{
    private Logger logger = Logger.getLogger(EnregistrementGain.class.getName());

    private EditText montantGain = null;
    private EditText descriptionGain = null;

    private String texteLibelle = null;
    private String texteDate = null;
    private String texteHeure = null;

    private List<String> listeTypeGain = null;

    public EnregistrementGain()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        listeTypeGain = new ArrayList<String>();
        listeTypeGain.add(getResources().getString(R.string.texteSelectionnerUnTypeOperation));
        listeTypeGain.add(StatistiqueGain.salaire);
        listeTypeGain.add(StatistiqueGain.pret);
        listeTypeGain.add(StatistiqueGain.epargne);

        // acces à la bd pour obtenir les types gain

        TypeGainDao typeGainDao = new TypeGainDao(getActivity().getApplicationContext());

        typeGainDao.ouvrirBaseDonnee();

        typeGainDao.afficherTousLesObjets();

        if (typeGainDao.afficherTousLesObjets().size() > 0)
        {
            for (int i = 0; i < typeGainDao.afficherTousLesObjets().size(); i++)
            {
                listeTypeGain.add(typeGainDao.afficherTousLesObjets().get(i).getNomTypeGain());
            }

        }

        typeGainDao.fermerBaseDeDonnee();


        return inflater.inflate(R.layout.fragment_enregistrement_gain, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        final Spinner libelleGain = (Spinner) view.findViewById(R.id.idLibelleGain);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listeTypeGain);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        libelleGain.setAdapter(adapter);

        montantGain = (EditText) view.findViewById(R.id.idMontantGain);
        descriptionGain = (EditText) view.findViewById(R.id.idDescriptionDuGain);
        final Button boutonSubmit = (Button) view.findViewById(R.id.idBoutonSubmitGain);

        // methode permettant gerer l'affichage et le changement de date
        final DatePicker calendrierGain = (DatePicker) view.findViewById(R.id.idDateDuGain);
        texteDate = calendrierGain.getDayOfMonth() + "/" + (calendrierGain.getMonth() + 1) + "/" + calendrierGain.getYear();

        calendrierGain.init(calendrierGain.getYear(), calendrierGain.getMonth(), calendrierGain.getDayOfMonth(),  new DatePicker.OnDateChangedListener()
        {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                texteDate = dayOfMonth + "/" + monthOfYear + "/" + year;
            }

        });


        // methode permettant l'affichage et changer d'heure
        final TimePicker timePickerGain = (TimePicker) view.findViewById(R.id.idTempsDuGain);
        timePickerGain.setIs24HourView(true);
        texteHeure = timePickerGain.getCurrentHour() + ":" + timePickerGain.getCurrentMinute();


        timePickerGain.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
            {
                texteHeure = hourOfDay + ":" + minute;
            }
        });

        final Switch choixGHGain = (Switch) view.findViewById(R.id.idChoixDhGain);

        choixGHGain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    calendrierGain.setVisibility(View.VISIBLE);
                    timePickerGain.setVisibility(View.VISIBLE);

                } else {
                    calendrierGain.setVisibility(View.GONE);
                    timePickerGain.setVisibility(View.GONE);

                }
            }
        });


        boutonSubmit.setOnClickListener(clickBoutonSubmit);
        
        libelleGain.setOnItemSelectedListener(this);

    }

    private View.OnClickListener clickBoutonSubmit = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    String message = "";

                    if (texteLibelle.equals(getResources().getString(R.string.texteSelectionnerUnTypeOperation)))
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.texteSelectionnerUnTypeOperation), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {

                        try {

                            GainDao gainDao = new GainDao(getActivity());

                            gainDao.ouvrirBaseDonnee();

                            Gain gain = new Gain();

                            gain.setLibelleGain(texteLibelle);
                            gain.setMontantGain(Double.parseDouble(montantGain.getText().toString()));
                            gain.setDescriptionGain(descriptionGain.getText().toString());
                            gain.setDateGain(texteDate);
                            gain.setHeureGain(texteHeure);

                            gainDao.insertion(gain);

                            AffichageGain.listeGain.clear();
                            AffichageGain.listeGain.addAll(gainDao.afficherTousLesObjets());

                            gainDao.fermerBaseDeDonnee();

                            message = "Enregistrement effectué";

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    AffichageGain.recyclerviewAffichageGain.notifyDataSetChanged();

                                    montantGain.getText().clear();
                                    descriptionGain.getText().clear();
                                }
                            });
                        } catch (Exception e) {
                            message = "Echec de l'enregistrement";
                        } finally {
                            final String finalMessage = message;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), finalMessage, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                }
            }).start();
        }
    };

    // les methodes permettant de le spinner pour le choix du libelle de la depense

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        texteLibelle = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
