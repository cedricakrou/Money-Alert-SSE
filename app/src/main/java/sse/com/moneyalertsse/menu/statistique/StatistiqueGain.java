package sse.com.moneyalertsse.menu.statistique;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.List;

import sse.com.moneyalertsse.R;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.DepenseDao;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.GainDao;
import sse.com.moneyalertsse.menu.statistique.diagramme.DiagrammeCirculaire;
import sse.com.moneyalertsse.menu.statistique.diagramme.DiagrammeEnBande;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatistiqueGain extends Fragment {

    // declaration des differents diagrammes

    private PieChart pieChart = null;
    private BarChart barchart = null;

    // declaration des spinners permettant de selection le type de diagramme
    private Spinner spinnerSelectionnerTypeDiagramme = null;

    // les liste permmettant de stocker les types de diagrammes
    List<String> listeTypeDiagramme = null;


    // les types de Gain

    public static String salaire = "salaire";
    public static String pret = "pret";
    public static String epargne = "epargne";


    // variable contenant le type d'affichage de diagramme choisi

    private String typeAffichageDiagramme = "";




    GainDao gainDao = new GainDao(getActivity());


    public StatistiqueGain() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // liste des types de diagrammes

        listeTypeDiagramme = new ArrayList<String>();
        listeTypeDiagramme.add("Choisir un type de diagramme");
        listeTypeDiagramme.add("Diagramme circulaire");
        listeTypeDiagramme.add("Diagramme en bande verticale");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_statistique_gain, container, false);

        pieChart = (PieChart) view.findViewById(R.id.pieChartGain);
        barchart = (BarChart) view.findViewById(R.id.idBarChartGain);

        spinnerSelectionnerTypeDiagramme = (Spinner) view.findViewById(R.id.idSpinnerChoisirDiagrammeStatGain);


        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // deserialisation des elements graphiques

        // gestion du spinner "spinnerSelectionnerTypeDiagramme" pour determiner le type de diagramme

        ArrayAdapter<String> adapterTypeDiagramme = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listeTypeDiagramme);
        adapterTypeDiagramme.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectionnerTypeDiagramme.setAdapter(adapterTypeDiagramme);
        spinnerSelectionnerTypeDiagramme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // recuperation du type d'affichage
                typeAffichageDiagramme = parent.getItemAtPosition(position).toString();




                if(!typeAffichageDiagramme.equals("Choisir un type de diagramme"))
                {
                    // on relance la methode "onActivityCreated" pour recréer les vues
                    onActivityCreated(savedInstanceState);

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        GestionDesDiagrammes();

    }


    /**
     * Cette methode permet gerer le diagramme
     */
    private void GestionDesDiagrammes()
    {


        if (typeAffichageDiagramme.equals("Diagramme en bande verticale")) {
            // on instantie le diagramme en bande
            DiagrammeEnBande<GainDao> diagrammeEnBande = new DiagrammeEnBande<GainDao>(barchart, getActivity());

            // barchart recupere le diagramme construit et l'affiche
            barchart = diagrammeEnBande.ConstructionDiagrammeEnBande(true, gainDao, "");
            barchart.setVisibility(View.VISIBLE);
            pieChart.setVisibility(View.GONE);
        } else if(typeAffichageDiagramme.equals("Diagramme circulaire"))
        {
            // on instantie le diagramme en bande
            DiagrammeCirculaire<GainDao> diagrammeCirculaire = new DiagrammeCirculaire<GainDao>(pieChart, getActivity());

            // piechart recupere le diagramme construit et l'affiche
            pieChart = diagrammeCirculaire.ConstructionDiagrammeCirculaire(true, gainDao, "" );
            pieChart.setVisibility(View.VISIBLE);
            barchart.setVisibility(View.GONE);
        }

    }
}