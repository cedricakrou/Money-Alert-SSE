package sse.com.moneyalertsse.Adapter.viewPageAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cedric akrou on 19/03/2018.
 */

public class ViewPageAdapter extends FragmentPagerAdapter
{
    private final List<Fragment> listeFragments = new ArrayList <Fragment>();
    private final List<String> listeNomsFragment = new ArrayList <String>();

    public ViewPageAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listeFragments.get(position);
    }

    @Override
    public int getCount() {
        return listeNomsFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listeNomsFragment.get(position);
    }

    public void ajouterFragment(Fragment fragment, String titre)
    {
        listeFragments.add(fragment);
        listeNomsFragment.add(titre);
    }


}
