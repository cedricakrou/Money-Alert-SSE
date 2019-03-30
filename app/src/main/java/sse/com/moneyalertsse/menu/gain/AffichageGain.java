package sse.com.moneyalertsse.menu.gain;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import sse.com.moneyalertsse.Adapter.recyclerview.ItemClickSupport;
import sse.com.moneyalertsse.Adapter.recyclerview.RecyclerviewAffichageGain;
import sse.com.moneyalertsse.R;
import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.Gain;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.GainDao;
import sse.com.moneyalertsse.baseDeDonnee.VariableGain;
import sse.com.moneyalertsse.menu.statistique.StatistiqueGain;

/**
 * cette classe sert à l'affichage des données
 */

public class AffichageGain extends Fragment implements VariableGain
{
    private Logger logger = Logger.getLogger(AffichageGain.class.getName());

    private RecyclerView recyclerViewGain;

    public static List<Gain> listeGain = null;
    public static RecyclerviewAffichageGain recyclerviewAffichageGain = null;

    public int idGainSelectionne = -1;

    public Gain gainSelectionne = null;

    private String texteLibelleGain = "";


    // nom de l'intent

    public static String intentModificationDonnee = "modificationDonnee";


    // MenuItem

    private MenuItem deselectionner;
    private MenuItem supprimer;
    private MenuItem modifier;
    private MenuItem parametre;

    public AffichageGain()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        listeGain = new ArrayList <Gain>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // recuperation des donnees de la base de donnée pour l'affichage

        GainDao gainDao = new GainDao(getContext());

        gainDao.ouvrirBaseDonnee();

        listeGain.clear();

        listeGain.addAll(gainDao.afficherTousLesObjets());

        gainDao.fermerBaseDeDonnee();

        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_affichage_gain, container, false);
    }

    @Override
    public synchronized void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // recuperation des widgets

        recyclerViewGain = (RecyclerView) view.findViewById(R.id.idAffichageGain);
        recyclerviewAffichageGain = new RecyclerviewAffichageGain(getContext(), listeGain);

        recyclerViewGain.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewGain.setAdapter(recyclerviewAffichageGain);

        // recuperation des interactions avec le recyclerview ( le toucher des données )

        interactionRecyclerview();

    }

    // recuperation des infos du menu

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_modif_suppression, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu)
    {
        deselectionner = menu.findItem(R.id.deselectionnerDonnee);
        supprimer = menu.findItem(R.id.suppressionDonnee);
        modifier = menu.findItem(R.id.modifierDonnee);
        parametre = menu.findItem(R.id.action_settings);

        deselectionner.setVisible(false);
        supprimer.setVisible(false);
        modifier.setVisible(false);
        parametre.setVisible(false);

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.suppressionDonnee:

                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {

                        String message ="";

                        if(idGainSelectionne >= 0)
                        {
                            GainDao gainDao = new GainDao(getContext());

                            gainDao.ouvrirBaseDonnee();

                            gainDao.supprimerGain(gainSelectionne.getIdGain());

                            gainDao.fermerBaseDeDonnee();


                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run()
                                {
                                    recyclerviewAffichageGain.supprimerDonnee(idGainSelectionne);
                                    idGainSelectionne = -1;
                                }
                            });

                            message = "Suppression";

                        }
                        else
                        {
                            message = "Selectionner une depense s'il vous plait";
                        }

                        final String finalMessage = message;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), finalMessage, Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }).start();

                return true;


            case R.id.deselectionnerDonnee:

                if(idGainSelectionne >= 0 )
                {
                    deselectionner.setVisible(false);
                    modifier.setVisible(false);
                    supprimer.setVisible(false);
                    parametre.setVisible(true);

                    recyclerViewGain.getChildAt(idGainSelectionne).findViewById(R.id.idEtuiGain).setBackgroundColor( getResources().getColor(R.color.couleurBlanche) );

                    idGainSelectionne = -1;

                }

                return true;

            case R.id.modifierDonnee:

                String message = "";

                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        if(idGainSelectionne >=0 )
                        {
                            final AlertDialog.Builder boiteDeDialogue = new AlertDialog.Builder(getContext());
                            final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_enregistrement_gain, null);
                            final AlertDialog[] dialog = {null};

                            final String[] texteDate = {""};
                            final String[] texteHeure = {""};

                            List<String> listeTypeGainBudget = new ArrayList<String>();
                            listeTypeGainBudget.add(StatistiqueGain.salaire);
                            listeTypeGainBudget.add(StatistiqueGain.pret);
                            listeTypeGainBudget.add(StatistiqueGain.epargne);

                            // acces à la bd pour obtenir les types gain

                            Spinner libelleGain = (Spinner) view.findViewById(R.id.idLibelleGain);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listeTypeGainBudget);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            libelleGain.setAdapter(adapter);

                            texteLibelleGain = gainSelectionne.getLibelleGain();

                            libelleGain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                                {
                                    texteLibelleGain = parent.getItemAtPosition(position).toString();

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                            final EditText montantGain = (EditText) view.findViewById(R.id.idMontantGain);
                            montantGain.setText("" +gainSelectionne.getMontantGain());

                            final EditText descriptionGain = (EditText) view.findViewById(R.id.idDescriptionDuGain);
                            descriptionGain.setText(gainSelectionne.getDescriptionGain());


                            // methode permettant gerer l'affichage et le changement de date
                            final DatePicker calendrierGain = (DatePicker) view.findViewById(R.id.idDateDuGain);
                            texteDate[0] = gainSelectionne.getDateGain();


                            calendrierGain.init(calendrierGain.getYear(), calendrierGain.getMonth(), calendrierGain.getDayOfMonth(),  new DatePicker.OnDateChangedListener()
                            {
                                @Override
                                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                                {
                                    texteDate[0] = dayOfMonth + "/" + monthOfYear + "/" + year;
                                    Toast.makeText(getContext(), texteDate[0], Toast.LENGTH_SHORT).show();
                                }

                            });


                            // methode permettant l'affichage et changer d'heure
                            final TimePicker timePickerGain = (TimePicker) view.findViewById(R.id.idTempsDuGain);
                            timePickerGain.setIs24HourView(true);
                            texteHeure[0] = gainSelectionne.getHeureGain();


                            timePickerGain.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                                @Override
                                public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
                                {
                                    texteHeure[0] = hourOfDay + ":" + minute;
                                    Toast.makeText(getContext(), texteHeure[0], Toast.LENGTH_SHORT).show();
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


                            Button boutonEnvoyer = (Button) view.findViewById(R.id.idBoutonSubmitGain);

                            boutonEnvoyer.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v)
                                {

                                    new Thread(new Runnable()
                                    {
                                        String message = "";

                                        @Override
                                        public void run() {
                                            try
                                            {
                                                Gain gain = new Gain();

                                                gain.setIdGain(gainSelectionne.getIdGain());
                                                gain.setLibelleGain(texteLibelleGain);
                                                gain.setMontantGain(Double.parseDouble(montantGain.getText().toString()));
                                                gain.setDescriptionGain(descriptionGain.getText().toString());
                                                gain.setDateGain(texteDate[0]);
                                                gain.setHeureGain(texteHeure[0]);

                                                GainDao gainDao = new GainDao(getContext());

                                                gainDao.ouvrirBaseDonnee();

                                                gainDao.miseAjour(gain);

                                                listeGain.clear();

                                                listeGain.addAll(gainDao.afficherTousLesObjets());

                                                gainDao.fermerBaseDeDonnee();

                                                message = getActivity().getApplicationContext().getResources().getString(R.string.messageReusiste);

                                            }
                                            catch (Exception e) {
                                                message = getActivity().getApplicationContext().getResources().getString(R.string.messageEchec);
                                            }
                                            finally
                                            {
                                                final String finalMessage = message;

                                                getActivity().runOnUiThread(new Runnable()
                                                {
                                                    @Override
                                                    public void run() {

                                                        Toast.makeText(getActivity(), finalMessage, Toast.LENGTH_SHORT).show();

                                                        if ( message.equals( getActivity().getApplicationContext().getResources().getString(R.string.messageReusiste) ) )
                                                        {
                                                            recyclerviewAffichageGain.notifyDataSetChanged();
                                                            dialog[0].cancel();
                                                        }

                                                    }
                                                });

                                            }


                                        }
                                    }).start();


                                }
                            });

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run()
                                {
                                    boiteDeDialogue.setView(view);

                                    dialog[0] = boiteDeDialogue.create();

                                    dialog[0].show();


                                }
                            });



//                            startActivity(intent);
                        }

                    }
                }).start();

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // la fonction pour detecter les touches et recuperation du gain selectionné

    private void interactionRecyclerview()
    {

        ItemClickSupport.addTo(recyclerViewGain, R.layout.fragment_affichage_gain)
                .setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v)
                    {

                        if(idGainSelectionne >= 0)
                        {
                            recyclerView.getChildAt(idGainSelectionne).findViewById(R.id.idEtuiGain).setBackgroundColor( getResources().getColor(R.color.couleurBlanche) );
                        }

                        idGainSelectionne = position;

                        gainSelectionne = recyclerviewAffichageGain.getGain(position);

                        recyclerView.getChildAt(position).findViewById(R.id.idEtuiGain).setBackgroundColor( getResources().getColor(R.color.colorAccent) );

                        deselectionner.setVisible(true);
                        modifier.setVisible(true);
                        supprimer.setVisible(true);
                        parametre.setVisible(false);


                        return false;
                    }
                });
    }

}
