package sse.com.moneyalertsse.menu.depense;

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
public class DepenseFragment extends Fragment
{

    public DepenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_depense, container, false);

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.idAppBarDepenseFragment);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.idTabLayoutDepenseFragment);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.idViewPaperDepense);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getChildFragmentManager());

        viewPageAdapter.ajouterFragment(new EnregistrementDepense(), getResources().getString(R.string.texteEnregsitrementDepense) );
        viewPageAdapter.ajouterFragment(new AffichageDepense(), getResources().getString(R.string.texteAffichageDepense));

        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager, false);

    }
}
