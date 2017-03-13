package view;

import android.beotron.tieuhoan.kara_2.MainApp;
import android.beotron.tieuhoan.kara_2.R;
import android.beotron.tieuhoan.kara_2.TabLayoutAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

import model.Singer;
import ulti.SQLiteHelper;

import static view.ReaderViewPagerTransformer.*;

/**
 * Created by TieuHoan on 25/02/2017.
 */

public class ViewPageFragment extends Fragment implements ViewPager.OnPageChangeListener {


    private ArrayList<Fragment> fragments;
    private ArrayList<String> fragmentTitles;
    private ViewPager viewPager;
    private SmartTabLayout tabLayout;
    private Toolbar toolbar;
    private ArrayList<Singer> singers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singers = new ArrayList<>();
        toolbar = (Toolbar) getActivity().findViewById(R.id.idToolBar);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpage_fragment, null);
        setUpViewPAger(view);
        return view;
    }


    public void setUpViewPAger(View view) {

        fragments = new ArrayList<>();
        fragmentTitles = new ArrayList<>();

        fragments.add(MainApp.homeFragment);
        fragmentTitles.add("Home");

        fragments.add(MainApp.singersVietNam);
        fragmentTitles.add("Việt Nam");
        fragments.add(MainApp.singersGlobal);
        fragmentTitles.add("Âu Mỹ");

        fragments.add(MainApp.favoriteFragment);
        fragmentTitles.add("Yêu thích");


        viewPager = (ViewPager) view.findViewById(R.id.idViewPager);
        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(getChildFragmentManager(), fragments, fragmentTitles);
        viewPager.setAdapter(tabLayoutAdapter);
        viewPager.setPageTransformer(true, new ReaderViewPagerTransformer(TransformType.ZOOM));
        tabLayout = (SmartTabLayout) view.findViewById(R.id.idTabLayout);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(this);
    }


    //view pager
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0: {
                toolbar.setTitle(fragmentTitles.get(0));


                break;
            }
            case 1: {
                toolbar.setTitle(fragmentTitles.get(1));
//                Bundle bundle = new Bundle();
//                SQLiteHelper sqLiteHelper = new SQLiteHelper(getActivity());
//                singers = sqLiteHelper.getAllSinger(SQLiteHelper.TABLESINGERVIETNAM);
//                for (Singer singer : sqLiteHelper.getAllSinger(SQLiteHelper.TABLESINGERVIETNAM)) {
//                    Log.e("tieuhoan ", singer.getNameSinger());
//                }
//                bundle.putSerializable("SINGER_VIETNAM", singers);
//                MainApp.singersVietNam.setArguments(bundle);
                break;
            }
            case 2: {
                toolbar.setTitle(fragmentTitles.get(2));
                break;
            }

            case 3: {
                toolbar.setTitle(fragmentTitles.get(3));
                break;
            }

        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {


        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}

