package view;

import android.annotation.TargetApi;
import android.beotron.tieuhoan.kara_2.R;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.HomeAdapter;
import model.Song;
import service.SQLiteAsset;

/**
 * Created by TieuHoan on 09/03/2017.
 */

public class FavoriteFragment extends Fragment {
    private ArrayList<Song> songs;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteAsset sqLiteAsset = new SQLiteAsset(getActivity());
        songs = sqLiteAsset.getAllSong();


    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment, null);
        listView = (ListView) view.findViewById(R.id.idListViewFavorite);
        HomeAdapter homeAdapter = new HomeAdapter(getActivity(), songs);
        listView.setAdapter(homeAdapter);
        listView.setNestedScrollingEnabled(true);
        return view;
    }
}
