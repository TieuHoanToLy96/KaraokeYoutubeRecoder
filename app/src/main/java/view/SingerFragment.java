package view;

import android.beotron.tieuhoan.kara_2.MainApp;
import android.beotron.tieuhoan.kara_2.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import adapter.SingerAdapter;
import model.Singer;
import model.Song;
import ulti.FragmentControl;
import ulti.HangSo;

/**
 * Created by TieuHoan on 24/02/2017.
 */

public class SingerFragment extends Fragment {

    public ArrayList<Singer> singers;
    public Context context;


    public void setUpRecycleView(View view, final ArrayList<Singer> singers) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        SingerAdapter singerAdapter = new SingerAdapter(singers, context);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.idListViewSinger);
        recyclerView.setAdapter(singerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        singerAdapter.setOnItemClick(new SingerAdapter.OnItemClick() {
            @Override
            public void OnClick(View view, int position) {
                String pathSearch;
                try {
                    pathSearch = HangSo.API_URI2 + "&maxResults=30" + "&q=" + URLEncoder.encode("Karaoke" + singers.get(position).getNameSinger(), "UTF-8") + "&type=video" + "&key=" + HangSo.KEY_BROWSE;
                    Bundle bundle = new Bundle();
                    bundle.putString("PATH_SONGS_OF_SINGER", pathSearch);
                    MainApp.searchFragment.setArguments(bundle);
                    FragmentControl.goToFragmentAddBackStack(R.id.idFrameFather, MainApp.searchFragment, context, getClass().getName());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });


    }


}
