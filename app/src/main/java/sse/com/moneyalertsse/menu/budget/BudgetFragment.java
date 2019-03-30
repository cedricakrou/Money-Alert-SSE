package sse.com.moneyalertsse.menu.budget;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sse.com.moneyalertsse.Adapter.viewPageAdapter.ViewPageAdapter;
import sse.com.moneyalertsse.R;

/**
 * la classe de budget fragment.
 */
public class BudgetFragment extends Fragment {


    public BudgetFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_budget, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.idAppBarBudgetFragment);
                final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.idTabLayoutBudgetFragment);
                final ViewPager viewPager = (ViewPager) view.findViewById(R.id.idViewPaperBudget);

                final ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getChildFragmentManager());
                viewPageAdapter.ajouterFragment(new AffichageGainBudget(), getResources().getString(R.string.texteAffichageGain) );
                viewPageAdapter.ajouterFragment(new AffichageDepenseBudget(), getResources().getString(R.string.texteAffichageDepense) );

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        viewPager.setAdapter(viewPageAdapter);

                        tabLayout.setupWithViewPager(viewPager);
                    }
                });


            }
        }).start();


    }
}
