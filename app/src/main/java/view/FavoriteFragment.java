package view;

import android.annotation.TargetApi;
import android.beotron.tieuhoan.kara_2.R;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.HomeAdapter;
import model.Song;
import ulti.SQLiteHelper;

/**
 * Created by TieuHoan on 09/03/2017.
 */

public class FavoriteFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ArrayList<Song> songs;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQLiteHelper sqLiteHelper = new SQLiteHelper(getActivity());
        songs = sqLiteHelper.getAllSong();

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment, null);
        listView = (ListView) view.findViewById(R.id.idListViewFavorite);
        HomeAdapter homeAdapter = new HomeAdapter(getActivity(), songs);
        listView.setAdapter(homeAdapter);
        listView.setNestedScrollingEnabled(true);
        listView.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("SONG", songs.get(position));
        bundle.putSerializable("SONGS", songs);
        Intent intent = new Intent(getActivity(), VideoYouTube.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
