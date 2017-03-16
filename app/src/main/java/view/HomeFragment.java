package view;

import android.beotron.tieuhoan.kara_2.R;
import android.beotron.tieuhoan.kara_2.VideoYouTube;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import adapter.HomeAdapter;
import model.Song;
import ulti.HangSo;
import ulti.Json;


/**
 * Created by TieuHoan on 24/02/2017.
 */

public class HomeFragment extends Fragment {

    private ArrayList<Song> songs;
    private HomeAdapter homeAdapter;
    private int lastVisibleItem;
    RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songs = (ArrayList<Song>) getArguments().getSerializable("SONG_FROM_MAIN_TO_HOME");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, null);

        Log.e("songs bundle ", String.valueOf(songs.size()));
//        setUpListView(songs, view);
        setUpRecycle(songs, view);

        return view;
    }


    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HangSo.KEY_HANDLER2) {
                super.handleMessage(msg);
                songs.addAll((ArrayList<Song>) msg.obj);
                homeAdapter.notifyDataSetChanged();
                Log.e("songs", String.valueOf(songs.size()));
            }

        }
    };


    private static int firstVisibleInListview;

    public void setUpRecycle(final ArrayList<Song> songs, View view) {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        homeAdapter = new HomeAdapter(songs, getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.idListViewHome);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);


        firstVisibleInListview = linearLayoutManager.findFirstVisibleItemPosition();
        homeAdapter.setOnItemClickRecycle(new HomeAdapter.OnItemClickRecycle() {
            @Override
            public void OnItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("SONG", songs.get(position));
                bundle.putSerializable("SONGS", songs);
                Intent intent = new Intent(getActivity(), VideoYouTube.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }


            int i = 0;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int currentFirstVisible = linearLayoutManager.findFirstVisibleItemPosition();

                if (currentFirstVisible > firstVisibleInListview)
                    Log.e("RecyclerView scrolled: ", "scroll up!");
                else {
                    Log.e("RecyclerView scrolled: ", "scroll down!");
                    if (songs.size() >= (i + 10)) {
                        new Json.LoadAdd(handler2, recyclerView, getActivity()).execute();
                        i = i + 10;
                    }
                }
                firstVisibleInListview = currentFirstVisible;
            }


        });
    }

}





