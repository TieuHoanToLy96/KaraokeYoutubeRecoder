package view;

import android.annotation.TargetApi;
import android.beotron.tieuhoan.kara_2.R;
import android.beotron.tieuhoan.kara_2.VideoYouTube;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import adapter.HomeAdapter;
import model.Song;
import ulti.HangSo;
import ulti.Json;


/**
 * Created by TieuHoan on 24/02/2017.
 */

public class HomeFragment extends Fragment implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener, Serializable {

    private ArrayList<Song> songs;
    private ListView listView;
    private HomeAdapter homeAdapter;
    private int lastVisibleItem;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songs = (ArrayList<Song>) getArguments().getSerializable("SONG_FROM_MAIN_TO_HOME");
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, null);

        Log.e("songs bundle ", String.valueOf(songs.size()));
        setUpListView(songs, view);

        return view;
    }


    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HangSo.KEY_HANDLER2) {
                super.handleMessage(msg);
                songs.addAll((ArrayList<Song>) msg.obj);
                homeAdapter.notifyDataSetChanged();
            }

        }
    };


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setUpListView(ArrayList<Song> songs, View view) {
        homeAdapter = new HomeAdapter(getActivity(), songs);
        listView = (ListView) view.findViewById(R.id.idListViewHome);
        listView.setAdapter(homeAdapter);
        listView.setOnItemClickListener(this);
        listView.setNestedScrollingEnabled(true);
        listView.setOnScrollListener(this);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    int i = 0;

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (lastVisibleItem < firstVisibleItem) {

            Log.i("SCROLLING DOWN", "TRUE");

            if (songs.size() >= (i + 10)) {
                new Json.LoadAdd(handler2, listView, getActivity()).execute();
                i = i + 10;
            }
            Log.e("tieuhoan", String.valueOf(totalItemCount));
        }
        if (lastVisibleItem > firstVisibleItem) {
            Log.i("SCROLLING UP", "TRUE");
        }
        lastVisibleItem = firstVisibleItem;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("SONG", songs.get(i));
        bundle.putSerializable("SONGS", songs);
        Intent intent = new Intent(getActivity(), VideoYouTube.class);
        intent.putExtras(bundle);
        startActivity(intent);


    }
}





