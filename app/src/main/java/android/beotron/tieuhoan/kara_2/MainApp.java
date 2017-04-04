package android.beotron.tieuhoan.kara_2;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import model.Song;
import ulti.FragmentControl;
import ulti.HangSo;
import ulti.Json;
import view.FavoriteFragment;
import view.HomeFragment;
import view.ListRecoderFragment;
import view.SearchFragment;
import view.SingerFragment;
import view.SingerGlobal;
import view.SingerVietNam;
import view.ViewPageFragment;


public class MainApp extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {

    private Toolbar toolbar;
    private MenuItem searchItem, menuItem;
    public static SearchView searchView;
    private String pathJson;
    Json.Load load;
    ArrayList<Song> songs;
    public static HomeFragment homeFragment;
    public static SingerFragment singersVietNam;
    public static SingerFragment singersGlobal;
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
        singersVietNam = new SingerVietNam();
        singersGlobal = new SingerGlobal();
        searchFragment = new SearchFragment();
        favoriteFragment = new FavoriteFragment();

        toolbar = (Toolbar) findViewById(R.id.idToolBar);
        toolbar.setTitle("Béo Tròn");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().getDisplayOptions();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tool_bar, menu);

        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        // find by id
        searchItem = menu.findItem(R.id.searchView);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setBackgroundResource(R.drawable.btnmic_not_active);
        searchView.setQueryHint("Search");
        menuItem = menu.findItem(R.id.menu);
        menuItem.setOnMenuItemClickListener(this);


        //set up item

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {

            if (!doublePressBack) {
                ViewPageFragment.viewPager.setCurrentItem(0);
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


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu: {
                final Dialog dialog = new Dialog(this);
                dialog.setTitle("Tùy chọn");
                ArrayList<String> test = new ArrayList<>();
                test.add("File ghi âm");
                test.add("Cài đặt");
                test.add("Thông tin");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, test);
                ListView listView = new ListView(this);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        switch (position) {
                            case 0: {
                                FragmentControl.goToFragmentAddBackStack(R.id.idFrameLayout, new ListRecoderFragment(), MainApp.this, getClass().getName());
                                break;
                            }
                            case 1: {

                                break;
                            }
                            case 2: {

                                break;
                            }
                        }
                        dialog.dismiss();
                    }
                });

                dialog.setContentView(listView);
                dialog.show();

                break;
            }
        }
        return true;
    }
}
