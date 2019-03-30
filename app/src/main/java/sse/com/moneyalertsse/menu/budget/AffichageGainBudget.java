package sse.com.moneyalertsse.menu.budget;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import sse.com.moneyalertsse.Adapter.recyclerview.ItemClickSupport;
import sse.com.moneyalertsse.Adapter.recyclerview.RecyclerviewAffichageBudget;
import sse.com.moneyalertsse.R;
import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.Budget;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.BudgetDao;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.TypeGainDao;
import sse.com.moneyalertsse.baseDeDonnee.VariableBudget;
import sse.com.moneyalertsse.menu.statistique.StatistiqueGain;

/**
 * A simple {@link Fragment} subclass.
 */
public class AffichageGainBudget extends Fragment
{
    private RecyclerView recyclerView = null;
    private RecyclerviewAffichageBudget recyclerviewAffichageBudget = null;
    private List<Budget> listeBudget = null;

    private String texteLibelleGainBudget = "";

    public int idGainBudgetSelectionne = -1;

    public Budget budgetSelectionne = null;


    private MenuItem deselectionner;
    private MenuItem supprimer;
    private MenuItem modifier;
    private MenuItem parametre;


    public AffichageGainBudget()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        listeBudget = new ArrayList<Budget>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        // thread pour recuperer les données de la base de donnee et pour l'aficher

                BudgetDao budgetDao = new BudgetDao(getContext());

                budgetDao.ouvrirBaseDonnee();

                listeBudget.addAll(budgetDao.afficherBudget(VariableBudget.libelleTypeBudgetGain));

                budgetDao.fermerBaseDeDonnee();

        return inflater.inflate(R.layout.fragment_affichage_gain_budget, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

                recyclerView = (RecyclerView) view.findViewById(R.id.idRecyclerviewGainBudget);
                recyclerviewAffichageBudget = new RecyclerviewAffichageBudget(listeBudget, getContext());

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(recyclerviewAffichageBudget);

                interactionRecyclerview();

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.idFloactingGainBudget);

        floatingActionButton.setOnClickListener(clickFloatBouton);

    }

    private View.OnClickListener clickFloatBouton = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {

            new Thread(new Runnable()
            {
                @Override
                public void run()
                {

                    final AlertDialog.Builder boiteDeDialogue = new AlertDialog.Builder(getContext());
                    final View view = getActivity().getLayoutInflater().inflate(R.layout.boite_dialoogue_gain_budget, null );
                    final AlertDialog[] dialog = {null};

                    final String[] texteDate = {""};
                    final String[] texteHeure = {""};

                    List<String> listeTypeGainBudget = new ArrayList<String>();
                    listeTypeGainBudget.add(StatistiqueGain.salaire);
                    listeTypeGainBudget.add(StatistiqueGain.pret);
                    listeTypeGainBudget.add(StatistiqueGain.epargne);

                    // acces à la bd pour obtenir les types gain

                    TypeGainDao typeGainDao = new TypeGainDao(getActivity().getApplicationContext());

                    typeGainDao.ouvrirBaseDonnee();

                    typeGainDao.afficherTousLesObjets();

                    if (typeGainDao.afficherTousLesObjets().size() > 0)
                    {
                        for (int i = 0; i < typeGainDao.afficherTousLesObjets().size(); i++)
                        {
                            listeTypeGainBudget.add(typeGainDao.afficherTousLesObjets().get(i).getNomTypeGain());
                        }

                    }

                    typeGainDao.fermerBaseDeDonnee();



                    final Spinner libelleGain = (Spinner) view.findViewById(R.id.idLibelleGainBudget);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listeTypeGainBudget);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    libelleGain.setAdapter(adapter);

                    libelleGain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                    {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                        {
                            texteLibelleGainBudget = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    final EditText montantGain = (EditText) view.findViewById(R.id.idMontantGainBudget);
                    final EditText descriptionGain = (EditText) view.findViewById(R.id.idDescriptionDuGainBudget);

                    // methode permettant gerer l'affichage et le changement de date
                    final DatePicker calendrierGain = (DatePicker) view.findViewById(R.id.idDateGainBudget);
                    texteDate[0] = calendrierGain.getDayOfMonth() + "/" + (calendrierGain.getMonth() + 1) + "/" + calendrierGain.getYear();

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
                    final TimePicker timePickerGain = (TimePicker) view.findViewById(R.id.idTempsGainBudget);
                    timePickerGain.setIs24HourView(true);
                    texteHeure[0] = timePickerGain.getCurrentHour() + ":" + timePickerGain.getCurrentMinute();


                    timePickerGain.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                        @Override
                        public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
                        {
                            texteHeure[0] = hourOfDay + ":" + minute;
                            Toast.makeText(getContext(), texteHeure[0], Toast.LENGTH_SHORT).show();
                        }
                    });

                    final Switch choixGHGain = (Switch) view.findViewById(R.id.idChoixDhGainBudget);

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



                    Button boutonEnvoyer = (Button) view.findViewById(R.id.idBoutonSubmitGainBudget);

                    boutonEnvoyer.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {

                            new Thread(new Runnable()
                            {
                                String message = "";

                                @Override
                                public void run()
                                {

                                    try
                                    {

                                        Budget budget = new Budget();
                                        budget.setLibelleBudget(texteLibelleGainBudget);
                                        budget.setMontantBudget(Double.parseDouble(montantGain.getText().toString()));
                                        budget.setTypeBudget(VariableBudget.libelleTypeBudgetGain);
                                        budget.setDescriptionBudget(descriptionGain.getText().toString());
                                        budget.setDateBudget(texteDate[0]);
                                        budget.setHeureBudget(texteHeure[0]);

                                        BudgetDao budgetDao = new BudgetDao(getContext());

                                        budgetDao.ouvrirBaseDonnee();

                                        budgetDao.insertion(budget);

                                        listeBudget.clear();

                                        listeBudget.addAll(budgetDao.afficherBudget(VariableBudget.libelleTypeBudgetGain));

                                        budgetDao.fermerBaseDeDonnee();

                                        message = getActivity().getApplicationContext().getResources().getString(R.string.messageReusiste);

                                    }
                                    catch (Exception e)
                                    {
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
                                                    recyclerviewAffichageBudget.notifyDataSetChanged();
                                                    dialog[0].cancel();
                                                }

                                            }
                                        });

                                    }

                                }
                            }).start();

                        }
                    });

                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            boiteDeDialogue.setView(view);

                            dialog[0] = boiteDeDialogue.create();

                            dialog[0].show();

                        }
                    });


                }
            }).start();


        }
    };

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

                        if(idGainBudgetSelectionne >= 0)
                        {
                            BudgetDao budgetDao = new BudgetDao(getContext());

                            budgetDao.ouvrirBaseDonnee();

                            budgetDao.supprimerBudget(budgetSelectionne.getIdBudget());

                            budgetDao.fermerBaseDeDonnee();


                            getActivity().runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run() {
                                    recyclerviewAffichageBudget.supprimerDonnee(idGainBudgetSelectionne);
                                    idGainBudgetSelectionne = -1;

                                }
                            });

                            message = "Suppression";
                        }
                        else
                        {
                            message = "Selectionner une Gain s'il vous plait";
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

                if(idGainBudgetSelectionne >= 0 )
                {
                    deselectionner.setVisible(false);
                    modifier.setVisible(false);
                    supprimer.setVisible(false);


                    recyclerView.getChildAt(idGainBudgetSelectionne).findViewById(R.id.idEtuiBudget).setBackgroundColor( getResources().getColor(R.color.couleurBlanche) );

                    idGainBudgetSelectionne = -1;
                }


            case R.id.modifierDonnee:

                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {

                        if(idGainBudgetSelectionne >= 0)
                        {

                            final AlertDialog.Builder boiteDeDialogue = new AlertDialog.Builder(getContext());
                            final View view = getActivity().getLayoutInflater().inflate(R.layout.boite_dialoogue_gain_budget, null );
                            final AlertDialog[] dialog = {null};


                            final String[] texteDate = {""};
                            final String[] texteHeure = {""};

                            List<String> listeTypeGainBudget = new ArrayList<String>();
                            listeTypeGainBudget.add(StatistiqueGain.salaire);
                            listeTypeGainBudget.add(StatistiqueGain.pret);
                            listeTypeGainBudget.add(StatistiqueGain.epargne);

                            // acces à la bd pour obtenir les types gain

                            TypeGainDao typeGainDao = new TypeGainDao(getActivity().getApplicationContext());

                            typeGainDao.ouvrirBaseDonnee();

                            typeGainDao.afficherTousLesObjets();

                            if (typeGainDao.afficherTousLesObjets().size() > 0)
                            {
                                for (int i = 0; i < typeGainDao.afficherTousLesObjets().size(); i++)
                                {
                                    listeTypeGainBudget.add(typeGainDao.afficherTousLesObjets().get(i).getNomTypeGain());
                                }

                            }

                            typeGainDao.fermerBaseDeDonnee();



                            final Spinner libelleGain = (Spinner) view.findViewById(R.id.idLibelleGainBudget);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listeTypeGainBudget);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            libelleGain.setAdapter(adapter);

                            libelleGain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                            {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                                {
                                    texteLibelleGainBudget = parent.getItemAtPosition(position).toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            final EditText montantGain = (EditText) view.findViewById(R.id.idMontantGainBudget);
                            montantGain.setText("" + budgetSelectionne.getMontantBudget());

                            final EditText descriptionGain = (EditText) view.findViewById(R.id.idDescriptionDuGainBudget);
                            descriptionGain.setText(budgetSelectionne.getDescriptionBudget());

                            // methode permettant gerer l'affichage et le changement de date
                            final DatePicker calendrierGain = (DatePicker) view.findViewById(R.id.idDateGainBudget);
                            texteDate[0] = budgetSelectionne.getDateBudget();

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
                            final TimePicker timePickerGain = (TimePicker) view.findViewById(R.id.idTempsGainBudget);
                            timePickerGain.setIs24HourView(true);
                            texteHeure[0] = budgetSelectionne.getHeureBudget();


                            timePickerGain.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                                @Override
                                public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
                                {
                                    texteHeure[0] = hourOfDay + ":" + minute;
                                    Toast.makeText(getContext(), texteHeure[0], Toast.LENGTH_SHORT).show();
                                }
                            });

                            final Switch choixGHGain = (Switch) view.findViewById(R.id.idChoixDhGainBudget);

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



                            Button boutonEnvoyer = (Button) view.findViewById(R.id.idBoutonSubmitGainBudget);

                            boutonEnvoyer.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    new Thread(new Runnable()
                                    {
                                        String message = "";

                                        @Override
                                        public void run()
                                        {

                                            try
                                            {

                                                Budget budget = new Budget();

                                                budget.setIdBudget(budgetSelectionne.getIdBudget());
                                                budget.setLibelleBudget(texteLibelleGainBudget);
                                                budget.setMontantBudget(Double.parseDouble(montantGain.getText().toString()));
                                                budget.setTypeBudget(budgetSelectionne.getTypeBudget());
                                                budget.setDescriptionBudget(descriptionGain.getText().toString());
                                                budget.setDateBudget(texteDate[0]);
                                                budget.setHeureBudget(texteHeure[0]);

                                                BudgetDao budgetDao = new BudgetDao(getContext());

                                                budgetDao.ouvrirBaseDonnee();

                                                budgetDao.miseAjour(budget);

                                                listeBudget.clear();

                                                listeBudget.addAll(budgetDao.afficherBudget(VariableBudget.libelleTypeBudgetGain));

                                                budgetDao.fermerBaseDeDonnee();

                                                message = getActivity().getApplicationContext().getResources().getString(R.string.messageReusiste);

                                            }
                                            catch (Exception e)
                                            {
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
                                                            recyclerviewAffichageBudget.notifyDataSetChanged();
                                                            dialog[0].cancel();
                                                        }

                                                    }
                                                });

                                            }

                                        }
                                    }).start();

                                }
                            });

                            getActivity().runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    boiteDeDialogue.setView(view);

                                    dialog[0] = boiteDeDialogue.create();

                                    dialog[0].show();

                                }
                            });


                        }

                    }
                }).start();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // methode servant à gerer l'interaction d l'utilsateur avec la liste des données

    private void interactionRecyclerview()
    {

        ItemClickSupport.addTo(recyclerView, R.layout.fragment_affichage_gain_budget)
                .setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener()
                {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v)
                    {

                        if(idGainBudgetSelectionne >= 0)
                        {
                            recyclerView.getChildAt(idGainBudgetSelectionne).findViewById(R.id.idEtuiBudget).setBackgroundColor( getResources().getColor(R.color.couleurBlanche) );
                        }

                        idGainBudgetSelectionne = position;

                        budgetSelectionne = recyclerviewAffichageBudget.getBudget(position);

                        recyclerView.getChildAt(position).findViewById(R.id.idEtuiBudget).setBackgroundColor( getResources().getColor(R.color.colorAccent) );

                        deselectionner.setVisible(true);
                        modifier.setVisible(true);
                        supprimer.setVisible(true);

                        return false;
                    }
                });
    }

}
