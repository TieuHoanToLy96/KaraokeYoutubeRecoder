package view;

import android.beotron.tieuhoan.kara_2.R;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import model.Singer;
import model.Song;
import ulti.HangSo;
import ulti.SQLiteAsset;
import ulti.SQLiteHelper;

/**
 * Created by TieuHoan on 12/03/2017.
 */

public class SingerVietNam extends SingerFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.singer_fragment, null);
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);

//        for (int i = 0; i < HangSo.listSingerVietNam.length; i++) {
//            Singer singer = new Singer();
//            singer.setImageSinger(HangSo.listSingerVietNam[i][0]);
//            singer.setNameSinger(HangSo.listSingerVietNam[i][1]);
//            Log.e("tieuhoan ", singer.getNameSinger());
//            sqLiteHelper.addSinger(singer, SQLiteHelper.TABLESINGERVIETNAM);
//        }

        singers = sqLiteHelper.getAllSinger(SQLiteHelper.TABLESINGERVIETNAM);
//        for (Singer singer : singers) {
//            Log.e("tieuhoan ", singer.getNameSinger());
//        }
        setUpRecycleView(view);

        sqLiteHelper.close();

        return view;

    }
}
