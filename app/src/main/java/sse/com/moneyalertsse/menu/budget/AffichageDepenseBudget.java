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
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.TypeDepenseDao;
import sse.com.moneyalertsse.baseDeDonnee.VariableBudget;

/**
 * A simple {@link Fragment} subclass.
 */
public class AffichageDepenseBudget extends Fragment
{
    private RecyclerView recyclerView = null;
    private RecyclerviewAffichageBudget recyclerviewAffichageBudget = null;
    private List<Budget> listeDepense;

    private String texteLibelleDepenseBudget = "";

    public int idDepenseBudgetSelectionne = -1;

    public Budget budgetSelectionne = null;

    // MenuItem

    private MenuItem deselectionner;
    private MenuItem supprimer;
    private MenuItem modifier;
    private MenuItem parametre;

    public AffichageDepenseBudget()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        listeDepense = new ArrayList<Budget>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        // recuperer les infos de la bd

                BudgetDao budgetDao = new BudgetDao(getContext());

                budgetDao.ouvrirBaseDonnee();

                listeDepense = budgetDao.afficherBudget(VariableBudget.libelleTypeBudgetDepense);

                budgetDao.fermerBaseDeDonnee();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_affichage_depense_budget, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.idRecyclerviewDepenseBudget);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.idFloactingDepenseBudget);
                recyclerviewAffichageBudget = new RecyclerviewAffichageBudget(listeDepense, getContext());

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(recyclerviewAffichageBudget);


                interactionRecyclerview();

                floatingActionButton.setOnClickListener(clickSurFloatAction);

    }

    // methode servant à afficher la boite de dialogue pour l'enregistrerment du budget

    private View.OnClickListener clickSurFloatAction = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            new Thread(new Runnable() {
                @Override
                public void run()
                {

                    final AlertDialog.Builder boiteDeDialogue = new AlertDialog.Builder(getContext());
                    final AlertDialog[] dialog = {null};
                    final View view = getActivity().getLayoutInflater().inflate(R.layout.boite_dialoogue_depense_budget, null);

                    final String[] texteDate = {""};
                    final String[] texteHeure = {""};

                    List<String> listeTypeDepenseBudget = new ArrayList<String>();
                    listeTypeDepenseBudget.add("transport");
                    listeTypeDepenseBudget.add("nourriture");

                    // acces à la base de donnée pour obtenir les types de depense

                    TypeDepenseDao typeDepenseDao = new TypeDepenseDao(getActivity().getApplicationContext());

                    typeDepenseDao.ouvrirBaseDonnee();

                    typeDepenseDao.afficherTousLesObjets();

                    if (typeDepenseDao.afficherTousLesObjets().size() > 0)
                    {
                        for (int i = 0; i < typeDepenseDao.afficherTousLesObjets().size(); i++)
                        {
                            listeTypeDepenseBudget.add(typeDepenseDao.afficherTousLesObjets().get(i).getNomTypeDepense());
                        }

                    }

                    typeDepenseDao.fermerBaseDeDonnee();

                    // fin de l'acces


                    final Spinner libelleDepense = (Spinner) view.findViewById(R.id.idLibelleDepenseBudget);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listeTypeDepenseBudget);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    libelleDepense.setAdapter(adapter);

                    libelleDepense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                        {
                            texteLibelleDepenseBudget = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    final EditText montantDepense = (EditText) view.findViewById(R.id.idMontantDepenseBudget);
                    final EditText descriptionDepense = (EditText) view.findViewById(R.id.idDescriptionDeDepenseBudget);

                    // methode permettant gerer l'affichage et le changement de date
                    final DatePicker calendrierGain = (DatePicker) view.findViewById(R.id.idDateDepenseBudget);
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
                    final TimePicker timePickerGain = (TimePicker) view.findViewById(R.id.idTempsDepenseBudget);
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


                    final Switch choixGHGain = (Switch) view.findViewById(R.id.idChoixDhDepenseBugdet);

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



                    Button boutonEnvoyer = (Button) view.findViewById(R.id.idBoutonSubmitDepenseBudget);

                    boutonEnvoyer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new Thread(new Runnable()
                            {
                                String message =  "";

                                @Override
                                public void run()
                                {
                                    try
                                    {

                                        Budget budget = new Budget();

                                        budget.setLibelleBudget(texteLibelleDepenseBudget);
                                        budget.setMontantBudget(Double.parseDouble(montantDepense.getText().toString()));
                                        budget.setTypeBudget(VariableBudget.libelleTypeBudgetDepense);
                                        budget.setDescriptionBudget(descriptionDepense.getText().toString());
                                        budget.setDateBudget(texteDate[0]);
                                        budget.setHeureBudget(texteHeure[0]);

                                        BudgetDao budgetDao = new BudgetDao(getContext());

                                        budgetDao.ouvrirBaseDonnee();

                                        budgetDao.insertion(budget);

                                        listeDepense.clear();

                                        listeDepense.addAll(budgetDao.afficherBudget(VariableBudget.libelleTypeBudgetDepense));

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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.suppressionDonnee:

                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {

                        String message ="";

                        if(idDepenseBudgetSelectionne >= 0)
                        {

                            BudgetDao budgetDao = new BudgetDao(getContext());

                            budgetDao.ouvrirBaseDonnee();

                            budgetDao.supprimerBudget(budgetSelectionne.getIdBudget());

                            budgetDao.fermerBaseDeDonnee();



                            getActivity().runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run() {
                                    recyclerviewAffichageBudget.supprimerDonnee(idDepenseBudgetSelectionne);
                                    idDepenseBudgetSelectionne = -1;

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

                if(idDepenseBudgetSelectionne >= 0 ) {
                    deselectionner.setVisible(false);
                    modifier.setVisible(false);
                    supprimer.setVisible(false);
                    parametre.setVisible(true);

                    recyclerView.getChildAt(idDepenseBudgetSelectionne).findViewById(R.id.idEtuiBudget).setBackgroundColor(getResources().getColor(R.color.couleurBlanche));

                    idDepenseBudgetSelectionne = -1;

                }

                    return true;

                    case R.id.modifierDonnee:

                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        if(idDepenseBudgetSelectionne >=0) {

                            final AlertDialog.Builder boiteDeDialogue = new AlertDialog.Builder(getContext());
                            final AlertDialog[] dialog = {null};
                            final View view = getActivity().getLayoutInflater().inflate(R.layout.boite_dialoogue_depense_budget, null);

                            final String[] texteDate = {""};
                            final String[] texteHeure = {""};

                            List<String> listeTypeDepenseBudget = new ArrayList<String>();
                            listeTypeDepenseBudget.add("transport");
                            listeTypeDepenseBudget.add("nourriture");
                            listeTypeDepenseBudget.add("bésoin hygienique");

                            // acces à la base de donnée pour obtenir les types de depense

                            TypeDepenseDao typeDepenseDao = new TypeDepenseDao(getActivity().getApplicationContext());

                            typeDepenseDao.ouvrirBaseDonnee();

                            typeDepenseDao.afficherTousLesObjets();

                            if (typeDepenseDao.afficherTousLesObjets().size() > 0) {
                                for (int i = 0; i < typeDepenseDao.afficherTousLesObjets().size(); i++) {
                                    listeTypeDepenseBudget.add(typeDepenseDao.afficherTousLesObjets().get(i).getNomTypeDepense());
                                }

                            }

                            typeDepenseDao.fermerBaseDeDonnee();

                            // fin de l'acces


                            final Spinner libelleDepense = (Spinner) view.findViewById(R.id.idLibelleDepenseBudget);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listeTypeDepenseBudget);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            libelleDepense.setAdapter(adapter);

                            libelleDepense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    texteLibelleDepenseBudget = parent.getItemAtPosition(position).toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            final EditText montantDepense = (EditText) view.findViewById(R.id.idMontantDepenseBudget);
                            montantDepense.setText("" + budgetSelectionne.getMontantBudget());

                            final EditText descriptionDepense = (EditText) view.findViewById(R.id.idDescriptionDeDepenseBudget);
                            descriptionDepense.setText(budgetSelectionne.getDescriptionBudget());

                            // methode permettant gerer l'affichage et le changement de date
                            final DatePicker calendrierGain = (DatePicker) view.findViewById(R.id.idDateDepenseBudget);
                            texteDate[0] = budgetSelectionne.getDateBudget();

                            calendrierGain.init(calendrierGain.getYear(), calendrierGain.getMonth(), calendrierGain.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
                                @Override
                                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    texteDate[0] = dayOfMonth + "/" + monthOfYear + "/" + year;
                                    Toast.makeText(getContext(), texteDate[0], Toast.LENGTH_SHORT).show();
                                }

                            });

                            // methode permettant l'affichage et changer d'heure
                            final TimePicker timePickerGain = (TimePicker) view.findViewById(R.id.idTempsDepenseBudget);
                            timePickerGain.setIs24HourView(true);
                            texteHeure[0] = budgetSelectionne.getHeureBudget();


                            timePickerGain.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                                @Override
                                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                                    texteHeure[0] = hourOfDay + ":" + minute;
                                    Toast.makeText(getContext(), texteHeure[0], Toast.LENGTH_SHORT).show();
                                }
                            });


                            final Switch choixGHGain = (Switch) view.findViewById(R.id.idChoixDhDepenseBugdet);

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


                            Button boutonEnvoyer = (Button) view.findViewById(R.id.idBoutonSubmitDepenseBudget);

                            boutonEnvoyer.setOnClickListener(new View.OnClickListener() {
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

                                                budget.setIdBudget(budgetSelectionne.getIdBudget());
                                                budget.setLibelleBudget(texteLibelleDepenseBudget);
                                                budget.setMontantBudget(Double.parseDouble(montantDepense.getText().toString()));
                                                budget.setTypeBudget(budgetSelectionne.getTypeBudget());
                                                budget.setDescriptionBudget(descriptionDepense.getText().toString());
                                                budget.setDateBudget(texteDate[0]);
                                                budget.setHeureBudget(texteHeure[0]);

                                                BudgetDao budgetDao = new BudgetDao(getContext());

                                                budgetDao.ouvrirBaseDonnee();

                                                budgetDao.miseAjour(budget);

                                                listeDepense.clear();

                                                listeDepense.addAll(budgetDao.afficherBudget(VariableBudget.libelleTypeBudgetDepense));

                                                budgetDao.fermerBaseDeDonnee();

                                                message = getActivity().getApplicationContext().getResources().getString(R.string.messageReusiste);

                                            } catch (Exception e)
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

        // lorsqu'on appuie longuement sur le bouton

        ItemClickSupport.addTo(recyclerView, R.layout.fragment_affichage_depense_budget)
                .setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v)
                    {
                        if(idDepenseBudgetSelectionne >= 0)
                        {
                            recyclerView.getChildAt(idDepenseBudgetSelectionne).findViewById(R.id.idEtuiBudget).setBackgroundColor( getResources().getColor(R.color.couleurBlanche) );
                        }

                        idDepenseBudgetSelectionne = position;

                        budgetSelectionne = recyclerviewAffichageBudget.getBudget(position);

                        recyclerView.getChildAt(position).findViewById(R.id.idEtuiBudget).setBackgroundColor( getResources().getColor(R.color.colorAccent) );

                        deselectionner.setVisible(true);
                        modifier.setVisible(true);
                        supprimer.setVisible(true);
                        parametre.setVisible(false);


                        return true;
                    }
                });
    }

}