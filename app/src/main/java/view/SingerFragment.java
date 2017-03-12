package view;

import android.annotation.TargetApi;
import android.beotron.tieuhoan.kara_2.MainApp;
import android.beotron.tieuhoan.kara_2.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import ulti.SQLiteAsset;

/**
 * Created by TieuHoan on 24/02/2017.
 */

public class SingerFragment extends Fragment implements AdapterView.OnItemClickListener, Serializable {

    private ArrayList<Singer> singers;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artist_fragment, null);

        SQLiteAsset sqLiteAsset = new SQLiteAsset(context);
        singers = sqLiteAsset.getAllSinger();
//        for (int j = 0; j < HangSo.listSinger.length; j++) {
//            Singer singer = new Singer();
//            singer.setImageSinger(HangSo.listSinger[j][0]);
//            singer.setNameSinger(HangSo.listSinger[j][1]);
//            sqLiteAsset.addSinger(singer);
////            singers.add(singer);
//        }
              for (Singer singer : sqLiteAsset.getAllSinger()) {
            Log.e("tieuhoan", singer.getNameSinger());
        }

        SingerAdapter singerAdapter = new SingerAdapter(context, singers);
        ListView listView = (ListView) view.findViewById(R.id.idListViewSinger);
        listView.setNestedScrollingEnabled(true);
        listView.setAdapter(singerAdapter);
        listView.setOnItemClickListener(this);

        return view;
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
