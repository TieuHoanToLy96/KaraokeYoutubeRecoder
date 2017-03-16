package view;

import android.beotron.tieuhoan.kara_2.R;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import model.Singer;
import ulti.HangSo;
import ulti.SQLiteAsset;
import ulti.SQLiteHelper;

/**
 * Created by TieuHoan on 12/03/2017.
 */

public class SingerGlobal extends SingerFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

//    static boolean check = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.singer_fragment, null);
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);

//        for (int i = 0; i < HangSo.listSingerGlobal.length; i++) {
//            Singer singer = new Singer();
//            singer.setImageSinger(HangSo.listSingerGlobal[i][0]);
//            singer.setNameSinger(HangSo.listSingerGlobal[i][1]);
//            Log.e("tieuhoan ", singer.getNameSinger());
//            sqLiteHelper.addSinger(singer, SQLiteHelper.TABLESINGERGLOBAL);
//        }

        singers = sqLiteHelper.getAllSinger(SQLiteHelper.TABLESINGERGLOBAL);
//        for (Singer singer : singers) {
//            Log.e("tieuhoan ", singer.getNameSinger());
//        }

        setUpRecycleView(view);
        sqLiteHelper.close();

        return view;

    }
}
