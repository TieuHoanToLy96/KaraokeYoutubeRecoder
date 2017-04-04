package view;

import android.beotron.tieuhoan.kara_2.R;
import android.beotron.tieuhoan.kara_2.VideoYouTube;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.AdapterListVideo;
import adapter.HomeAdapter;
import model.Song;
import ulti.SQLiteHelper;

import static android.beotron.tieuhoan.kara_2.R.id.listView;

/**
 * Created by TieuHoan on 09/03/2017.
 */

public class FavoriteFragment extends Fragment implements HomeAdapter.OnItemClickRecycle {
    private ArrayList<Song> songs;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQLiteHelper sqLiteHelper = new SQLiteHelper(getActivity());
        songs = sqLiteHelper.getAllSong(SQLiteHelper.TABLEFVORITE);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment, null);

        AdapterListVideo adapterListVideo = new AdapterListVideo(songs, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.idListViewFavorite);
        recyclerView.setAdapter(adapterListVideo);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterListVideo.setOnItemClickRecycle(this);

        return view;
    }


    @Override
    public void OnItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("SONG", songs.get(position));
        bundle.putSerializable("SONGS", songs);
        Intent intent = new Intent(getActivity(), VideoYouTube.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }
}
