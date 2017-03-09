package android.beotron.tieuhoan.kara_2;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import model.Song;
import service.FragmentControl;
import service.HangSo;
import service.Json;
import view.FavoriteFragment;
import view.HomeFragment;
import view.SearchFragment;
import view.SingerFragment;
import view.ViewPageFragment;


public class MainApp extends AppCompatActivity {

    private Toolbar toolbar;
    private MenuItem searchItem, menuItem;
    public static SearchView searchView;
    private String pathJson;
    Json.Load load;
    ArrayList<Song> songs;
    public static HomeFragment homeFragment;
    public static SingerFragment singerFragment;
    public static SearchFragment searchFragment;
    public static FavoriteFragment favoriteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        init();

        pathJson = HangSo.API_URI + "&maxResults=10" + "&playlistId=" + HangSo.PLAYLIST_ID + "&key=" + HangSo.KEY_BROWSE;
        Log.e("path", pathJson);
        load = new Json.Load(handler, MainApp.this);
        load.execute(pathJson);

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == HangSo.KEY_HANDLER) {
                songs = (ArrayList<Song>) ((ArrayList<Song>) msg.obj).clone();
                Bundle bundle = new Bundle();
                bundle.putSerializable("SONG_FROM_MAIN_TO_HOME", songs);
                homeFragment.setArguments(bundle);
                FragmentControl.addFragment(R.id.idFrameFather, new ViewPageFragment(), MainApp.this);
            }
        }
    };

    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == HangSo.KEY_HANDLER) {

                ArrayList<Song> songsResult;
                songsResult = (ArrayList<Song>) ((ArrayList<Song>) msg.obj).clone();
                Log.e("sonng result", String.valueOf(songsResult.size()));
                for (Song song : songsResult) {
                    Log.e("tieuhoan", song.getTittle());
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("SONG_RESULT", songsResult);
                searchFragment.setArguments(bundle);
                FragmentControl.goToFragmentAddBackStack(R.id.idFrameFather, searchFragment, MainApp.this, getClass().getName());
            }
        }
    };

    public void init() {


        homeFragment = new HomeFragment();
        singerFragment = new SingerFragment();
        searchFragment = new SearchFragment();
        favoriteFragment = new FavoriteFragment();

        toolbar = (Toolbar) findViewById(R.id.idToolBar);
        toolbar.setTitle("KaraokeTieuhoan");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tool_bar, menu);

        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        // find by id
        searchItem = menu.findItem(R.id.searchView);
        menuItem = menu.findItem(R.id.menu);

        //get item in tool bar
        searchView = (SearchView) searchItem.getActionView();


        //set up item
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(MainApp.this, "click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));

        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                FragmentControl.goToFragmentAddBackStack(R.id.idFrameFather, searchFragment, MainApp.this, getClass().getName());
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


    //exit app
    boolean doublePressBack = false;

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (!doublePressBack) {
                doublePressBack = true;
                Toast.makeText(MainApp.this, "Ấn back lần nữa để thoát", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doublePressBack = false;
                    }
                }, 2000);

            } else {
                super.onBackPressed();
            }
        }
    }
}