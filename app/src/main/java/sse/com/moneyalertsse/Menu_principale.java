package sse.com.moneyalertsse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.BudgetDao;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.DepenseDao;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.GainDao;
import sse.com.moneyalertsse.baseDeDonnee.VariableBudget;
import sse.com.moneyalertsse.menu.aide.AideFragment;
import sse.com.moneyalertsse.menu.apropos.AproposFragment;
import sse.com.moneyalertsse.menu.budget.BudgetFragment;
import sse.com.moneyalertsse.menu.depense.DepenseFragment;
import sse.com.moneyalertsse.menu.equipe.EquipeFragment;
import sse.com.moneyalertsse.menu.gain.GainFragment;
import sse.com.moneyalertsse.menu.parametre.ParametreFragment;
import sse.com.moneyalertsse.menu.statistique.Statistique;


/**
 * classe principale servant à faire le recapitulatif de des depenses et permet aussi de gerer le menu
 */

public class Menu_principale extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principale);



        PieChart statOpCompte = (PieChart) findViewById(R.id.statOperationTousLesComptes);
        PieChart statBudgetCompte = (PieChart) findViewById(R.id.statBudgetMenu);
        TextView montantSolde =  (TextView) findViewById(R.id.montantSoldeBancaire);
        TextView montantRevenuOp = (TextView) findViewById(R.id.montantMesRevenus);
        TextView montantDepenseOp = (TextView) findViewById(R.id.montantMesDepenses);
        TextView montantDepenseBudget = (TextView) findViewById(R.id.montantDepenseBudget);
        TextView montantRevenuBudget = (TextView) findViewById(R.id.montantRevenuBudget);

        // Variable pour faire les statistiques du depense et de gain

        statOpCompte.setUsePercentValues(true);
        statOpCompte.getDescription().setEnabled(false);
        statOpCompte.setExtraOffsets(5, 10, 5, 5);
        statOpCompte.animateY(1000, Easing.EasingOption.EaseInBack);
        statOpCompte.setDragDecelerationFrictionCoef(0.95f);
        statOpCompte.setDrawHoleEnabled(true);
        statOpCompte.setHoleColor(Color.WHITE);
        statOpCompte.setTransparentCircleRadius(61f);

        // Variable pour faire les statistiques du budget gain et depense

        statBudgetCompte.setUsePercentValues(true);
        statBudgetCompte.getDescription().setEnabled(false);
        statBudgetCompte.setExtraOffsets(5, 10, 5, 5);
        statBudgetCompte.animateY(1000, Easing.EasingOption.EaseInBack);
        statBudgetCompte.setDragDecelerationFrictionCoef(0.95f);
        statBudgetCompte.setDrawHoleEnabled(true);
        statBudgetCompte.setHoleColor(Color.WHITE);
        statBudgetCompte.setTransparentCircleRadius(61f);


        // recuperation des valeurs dans la base de donnée

        DepenseDao depenseDao = new DepenseDao(getApplicationContext());
        depenseDao.ouvrirBaseDonnee();
        double depenseTotalOp = depenseDao.montantTotal();
        depenseDao.fermerBaseDeDonnee();

        GainDao gainDao = new GainDao(getApplicationContext());
        gainDao.ouvrirBaseDonnee();

        double gainTotalOp = gainDao.montantTotal();

        depenseDao.fermerBaseDeDonnee();


        BudgetDao budgetDao =new BudgetDao(getApplicationContext());
        budgetDao.ouvrirBaseDonnee();

        double depenseBudget = budgetDao.montantTotalSelonLibelle(VariableBudget.libelleTypeBudgetDepense);
        double gainBudget = budgetDao.montantTotalSelonLibelle(VariableBudget.libelleTypeBudgetGain);

        budgetDao.fermerBaseDeDonnee();


        // les statistiques pour les depenses et revenus courants

        List<PieEntry> listeDonneeOp = new ArrayList<PieEntry>();

        listeDonneeOp.add(new PieEntry((float) depenseTotalOp, getResources().getString(R.string.texteDepense)));
        listeDonneeOp.add(new PieEntry((float) gainTotalOp, getResources().getString(R.string.texteGain)));

        PieDataSet dataSet = new PieDataSet(listeDonneeOp, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        statOpCompte.setHoleColor(getResources().getColor(R.color.colorPrimaryDark));
        statOpCompte.setData(data);

        // les statistiques pour les depenses et revenus previsionnels

        List<PieEntry> listeBuget = new ArrayList<PieEntry>();

        listeBuget.add(new PieEntry((float) depenseBudget, getResources().getString(R.string.texteDepense)));
        listeBuget.add(new PieEntry((float) gainBudget, getResources().getString(R.string.texteGain)));

        PieDataSet dataSetBudget = new PieDataSet(listeBuget, "");
        dataSetBudget.setSliceSpace(3f);
        dataSetBudget.setSelectionShift(5f);
        dataSetBudget.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData dataBudget = new PieData((dataSetBudget));
        dataBudget.setValueTextSize(10f);
        dataBudget.setValueTextColor(Color.YELLOW);

        statBudgetCompte.setHoleColor(getResources().getColor(R.color.colorPrimaryDark));
        statBudgetCompte.setData(dataBudget);



        // affectation des valeurs aux Textview

        double solde = gainTotalOp - depenseTotalOp;


        if(solde >= 0)
        {
            montantSolde.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        else
        {
            montantSolde.setTextColor(getResources().getColor(R.color.colorAccent));
        }

        // les depenses et revenus effectués

        montantSolde.setText("" + solde);
        montantDepenseOp.setText("" + depenseTotalOp);
        montantRevenuOp.setText("" + gainTotalOp);
        montantRevenuBudget.setText("" + gainBudget);
        montantDepenseBudget.setText("" + depenseBudget);


        // fin des reports des operations


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principale, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Intent intent = new Intent(getApplicationContext(), ParametreFragment.class);

            startActivity(intent);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.

        Fragment fragmentChoix = null;

        int id = item.getItemId();

        if (id == R.id.nav_accueil)
        {
            Intent intent = new Intent(getApplicationContext(), Menu_principale.class);

            startActivity(intent);

            finish();
        }
        else if (id == R.id.nav_gain)
        {
            fragmentChoix = new GainFragment();

        }
        else if (id == R.id.nav_depense)
        {
            fragmentChoix = new DepenseFragment();

        } else if (id == R.id.nav_statistique)
        {

            fragmentChoix = new Statistique();

        } else if (id == R.id.nav_budget)
        {
            fragmentChoix = new BudgetFragment();

        } else if (id == R.id.nav_equipeSSE)
        {
            fragmentChoix = new EquipeFragment();

        } else if (id == R.id.nav_apropos)
        {
            fragmentChoix = new AproposFragment();

        } else if (id == R.id.nav_aide)
        {
            fragmentChoix = new AideFragment();

        } else if (id == R.id.nav_parametreSSE)
        {
            Intent intent = new Intent(getApplicationContext(), ParametreFragment.class);

            startActivity(intent);

            finish();
        }


        if(fragmentChoix != null)
        {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragmentChoix , "");
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            AlertDialog.Builder boiteDeDialogue = new AlertDialog.Builder(Menu_principale.this);

            boiteDeDialogue.setMessage(getResources().getString(R.string.texteSortirDeLapp));
            boiteDeDialogue.setPositiveButton(getResources().getString(R.string.texteSortirOui), clickOui);
            boiteDeDialogue.setNegativeButton(getResources().getString(R.string.texteSortirNon), clicNon);

            AlertDialog alertDialog = boiteDeDialogue.create();

            alertDialog.show();
        }

        return super.onKeyDown(keyCode, event);
    }

    // methode servant à determiner l'action si on clique sur oui de la boite de dialogue

    DialogInterface.OnClickListener clickOui = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            finish();
        }
    };

    // methode servant à determiner l'action si on clique sur non de la boite de dialogue

    DialogInterface.OnClickListener clicNon = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.cancel();
        }
    };

}
