package sse.com.moneyalertsse.menu.statistique;


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
 * A simple {@link Fragment} subclass.
 */
public class Statistique extends Fragment
{
    private AppBarLayout appBarLayout = null;
    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;


    public Statistique() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistique, container, false);

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                appBarLayout = (AppBarLayout) view.findViewById(R.id.idAppBarStatistiqueFragment);
                tabLayout = (TabLayout) view.findViewById(R.id.idTabLayoutStatistiqueFragment);
                viewPager = (ViewPager) view.findViewById(R.id.idViewPaperStatistique);

                final ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getChildFragmentManager());
                viewPageAdapter.ajouterFragment(new StatistiqueGain(), getResources().getString(R.string.texteStatistiqueGain) );
                viewPageAdapter.ajouterFragment(new StatistiqueDepense(), getResources().getString(R.string.texteStatistiqueDepense) );

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        viewPager.setAdapter(viewPageAdapter);
                        tabLayout.setupWithViewPager(viewPager);
                    }
                });
            }
        }).start();

    }
}
