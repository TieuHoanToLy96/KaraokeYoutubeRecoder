package view;

import android.annotation.TargetApi;
import android.beotron.tieuhoan.kara_2.MainApp;
import android.beotron.tieuhoan.kara_2.R;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import adapter.SingerAdapter;
import model.Singer;
import ulti.FragmentControl;
import ulti.HangSo;
import ulti.SQLiteHelper;

/**
 * Created by TieuHoan on 24/02/2017.
 */

public class SingerFragment extends Fragment implements AdapterView.OnItemClickListener, Serializable {

    public ArrayList<Singer> singers;
    public Context context;


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.singer_fragment, null);


//
//        Bundle bundle = getArguments();
//        if (bundle != null){
//            singers = (ArrayList<Singer>) bundle.getSerializable("SINGER");
//        }
//        for (int i = 0; i < HangSo.listSinger.length; i++) {
//            Singer singer = new Singer();
//            singer.setImageSinger(HangSo.listSinger[i][0]);
//            singer.setNameSinger(HangSo.listSinger[i][1]);
//            Log.e("tieuhoan ", singer.getNameSinger());
//            sqLiteHelper.addSinger(singer);
//
//        }
//        for (Singer singer : sqLiteHelper.getAllSinger()) {
//            Log.e("tieuhoan ", singer.getNameSinger());
//        }


//        return view;
//    }

    public void setUpListView(View view) {
        SingerAdapter singerAdapter = new SingerAdapter(context, singers);
        ListView listView = (ListView) view.findViewById(R.id.idListViewSinger);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }
        listView.setAdapter(singerAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String pathSearch;
        try {
            pathSearch = HangSo.API_URI2 + "&maxResults=30" + "&q=" + URLEncoder.encode("Karaoke" + singers.get(i).getNameSinger(), "UTF-8") + "&type=video" + "&key=" + HangSo.KEY_BROWSE;
            Bundle bundle = new Bundle();
            bundle.putString("PATH_SONGS_OF_SINGER", pathSearch);
            MainApp.searchFragment.setArguments(bundle);
            FragmentControl.goToFragmentAddBackStack(R.id.idFrameFather, MainApp.searchFragment, context, getClass().getName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }


}
