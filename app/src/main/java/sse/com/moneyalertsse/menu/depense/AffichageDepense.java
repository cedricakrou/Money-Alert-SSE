package sse.com.moneyalertsse.menu.depense;

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

import sse.com.moneyalertsse.Adapter.recyclerview.ItemClickSupport;
import sse.com.moneyalertsse.Adapter.recyclerview.RecyclerviewAffichageDepense;
import sse.com.moneyalertsse.R;
import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.Depense;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.DepenseDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class AffichageDepense extends Fragment
{
    private RecyclerView recyclerView = null;

    public static RecyclerviewAffichageDepense recyclerviewAffichageDepense = null;
    public static List<Depense> listeDepense = null;


    public int idDepenseSelectionne = -1;

    public Depense depenseSelectionne = null;

    private String texteLibelleDepense = "";


    // MenuItem

    private MenuItem deselectionner;
    private MenuItem supprimer;
    private MenuItem modifier;
    private MenuItem parametre;


    public AffichageDepense() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        listeDepense = new ArrayList <Depense>();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // recuperation et affichage des informations de la base de donnée

        DepenseDao depenseDao = new DepenseDao(getContext());

        depenseDao.ouvrirBaseDonnee();

        listeDepense.clear();

        listeDepense.addAll(depenseDao.afficherTousLesObjets());

        depenseDao.fermerBaseDeDonnee();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_affichage_depense, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

                recyclerView = (RecyclerView) view.findViewById(R.id.idAffichageDepense);
                recyclerviewAffichageDepense = new RecyclerviewAffichageDepense(getContext(), listeDepense );

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(recyclerviewAffichageDepense);

                interactionRecyclerview();
    }

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
        switch(item.getItemId())
        {
            case R.id.suppressionDonnee:

                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {

                        String message ="";

                        if(idDepenseSelectionne >= 0)
                        {
                            DepenseDao depenseDao = new DepenseDao(getContext());

                            depenseDao.ouvrirBaseDonnee();

                            depenseDao.supprimerDepense(depenseSelectionne.getIdDepense());

                            depenseDao.fermerBaseDeDonnee();


                            getActivity().runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run() {
                                    recyclerviewAffichageDepense.supprimerDonnee(idDepenseSelectionne);
                                    idDepenseSelectionne = -1;

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

                if(idDepenseSelectionne >= 0 )
                {
                    deselectionner.setVisible(false);
                    modifier.setVisible(false);
                    supprimer.setVisible(false);
                    parametre.setVisible(true);

                    recyclerView.getChildAt(idDepenseSelectionne).findViewById(R.id.idEtuiDepense).setBackgroundColor( getResources().getColor(R.color.couleurBlanche) );

                    idDepenseSelectionne = -1;

                }

                return true;


            case R.id.modifierDonnee:

                String message = "";

                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        if(idDepenseSelectionne >=0)
                        {
                            final AlertDialog.Builder boiteDeDialogue = new AlertDialog.Builder(getContext());
                            final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_enregistrement_depense, null);
                            final AlertDialog[] dialog = {null};

                            final String[] texteDate = {""};
                            final String[] texteHeure = {""};

                            List<String> listeTypeDepense = new ArrayList<String>();
                            listeTypeDepense.add("transport");
                            listeTypeDepense.add("nourriture");
                            listeTypeDepense.add("bésoin hygienique");
                            listeTypeDepense.add("vetement");
                            listeTypeDepense.add("autre");

                            // acces à la bd pour obtenir les types Depense

                            Spinner libelleDepense = (Spinner) view.findViewById(R.id.idLibelleDepense);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listeTypeDepense);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            libelleDepense.setAdapter(adapter);

                            texteLibelleDepense = depenseSelectionne.getLibelleDepense();

                            libelleDepense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                                {
                                    texteLibelleDepense = parent.getItemAtPosition(position).toString();

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                            final EditText montantDepense = (EditText) view.findViewById(R.id.idMontantDepense);
                            montantDepense.setText("" +depenseSelectionne.getMontantDepense());

                            final EditText descriptionDepense = (EditText) view.findViewById(R.id.idDescriptionDeDepense);
                            descriptionDepense.setText(depenseSelectionne.getDescriptionDepense());


                            // methode permettant gerer l'affichage et le changement de date
                            final DatePicker calendrierDepense = (DatePicker) view.findViewById(R.id.idDateDepense);
                            texteDate[0] = depenseSelectionne.getDateDepense();


                            calendrierDepense.init(calendrierDepense.getYear(), calendrierDepense.getMonth(), calendrierDepense.getDayOfMonth(),  new DatePicker.OnDateChangedListener()
                            {
                                @Override
                                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                                {
                                    texteDate[0] = dayOfMonth + "/" + monthOfYear + "/" + year;
                                    Toast.makeText(getContext(), texteDate[0], Toast.LENGTH_SHORT).show();
                                }

                            });


                            // methode permettant l'affichage et changer d'heure
                            final TimePicker timePickerDepense = (TimePicker) view.findViewById(R.id.idTempsDepense);
                            timePickerDepense.setIs24HourView(true);
                            texteHeure[0] = depenseSelectionne.getHeureDepense();


                            timePickerDepense.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                                @Override
                                public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
                                {
                                    texteHeure[0] = hourOfDay + ":" + minute;
                                    Toast.makeText(getContext(), texteHeure[0], Toast.LENGTH_SHORT).show();
                                }
                            });

                            final Switch choixGHDepense = (Switch) view.findViewById(R.id.idChoixDhDepense);

                            choixGHDepense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        calendrierDepense.setVisibility(View.VISIBLE);
                                        timePickerDepense.setVisibility(View.VISIBLE);

                                    } else {
                                        calendrierDepense.setVisibility(View.GONE);
                                        timePickerDepense.setVisibility(View.GONE);

                                    }
                                }
                            });


                            Button boutonEnvoyer = (Button) view.findViewById(R.id.idBoutonSubmitDepense);

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
                                                Depense depense = new Depense();

                                                depense.setIdDepense(depenseSelectionne.getIdDepense());
                                                depense.setLibelleDepense(texteLibelleDepense);
                                                depense.setMontantDepense(Double.parseDouble(montantDepense.getText().toString()));
                                                depense.setDescriptionDepense(descriptionDepense.getText().toString());
                                                depense.setDateDepense(texteDate[0]);
                                                depense.setHeureDepense(texteHeure[0]);

                                                DepenseDao DepenseDao = new DepenseDao(getContext());

                                                DepenseDao.ouvrirBaseDonnee();

                                                DepenseDao.miseAjour(depense);

                                                listeDepense.clear();

                                                listeDepense.addAll(DepenseDao.afficherTousLesObjets());

                                                DepenseDao.fermerBaseDeDonnee();

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
                                                            recyclerviewAffichageDepense.notifyDataSetChanged();
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
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_affichage_depense)
                .setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v)
                    {
                        if(idDepenseSelectionne >= 0)
                        {
                            recyclerView.getChildAt(idDepenseSelectionne).findViewById(R.id.idEtuiDepense).setBackgroundColor( getResources().getColor(R.color.couleurBlanche) );
                        }

                        idDepenseSelectionne = position;

                        depenseSelectionne = recyclerviewAffichageDepense.getDepense(position);

                        recyclerView.getChildAt(position).findViewById(R.id.idEtuiDepense).setBackgroundColor( getResources().getColor(R.color.colorAccent) );

                        deselectionner.setVisible(true);
                        modifier.setVisible(true);
                        supprimer.setVisible(true);
                        parametre.setVisible(false);


                        return false;
                    }
                });
    }

}
