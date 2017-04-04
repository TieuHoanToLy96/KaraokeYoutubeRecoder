package view;

import android.beotron.tieuhoan.kara_2.R;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import model.Singer;
import ulti.HangSo;
import ulti.SQLiteHelper;

/**
 * Created by TieuHoan on 12/03/2017.
 */

public class SingerGlobal extends SingerFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        Log.e("tieuhoan", "onCreate singerglobal");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.singer_fragment, null);
        Log.e("tieuhoan", "onCreateView singerglobal");
        if (singers == null) {
            singers = new ArrayList<>();
            for (int i = 0; i < HangSo.listSingerGlobal.length; i++) {
                Singer singer = new Singer();
                singer.setImageSinger(HangSo.listSingerGlobal[i][0]);
                singer.setNameSinger(HangSo.listSingerGlobal[i][1]);
                singers.add(singer);
            }
        }
        setUpRecycleView(view, singers);

//        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
//
//        if(sqLiteHelper.getAllSinger(SQLiteHelper.TABLESINGERGLOBAL) == null){
//            for (int i = 0; i < HangSo.listSingerGlobal.length; i++) {
//                Singer singer = new Singer();
//                singer.setImageSinger(HangSo.listSingerGlobal[i][0]);
//                singer.setNameSinger(HangSo.listSingerGlobal[i][1]);
//                Log.e("tieuhoan ", singer.getNameSinger());
//                sqLiteHelper.addSinger(singer, SQLiteHelper.TABLESINGERGLOBAL);
//            }
//        }

//        singers = sqLiteHelper.getAllSinger(SQLiteHelper.TABLESINGERGLOBAL);
//            sqLiteHelper.close();

        return view;


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e("tieuhoan", "setUserVisibleHint singerglobal");
        }
    }
}

