package android.beotron.tieuhoan.kara_2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by TieuHoan on 07/02/2017.
 */

public class TabLayoutAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> fragmentTitles = new ArrayList<>();

    public TabLayoutAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragments, ArrayList<String> strings) {
        super(fragmentManager);
        this.fragments = fragments;
        this.fragmentTitles = strings;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
