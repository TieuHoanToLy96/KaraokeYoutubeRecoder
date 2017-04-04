package view;

import android.beotron.tieuhoan.kara_2.R;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import model.Singer;
import ulti.HangSo;

/**
 * Created by TieuHoan on 12/03/2017.
 */

public class SingerVietNam extends SingerFragment {
    private boolean isViewShown = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        Log.e("tieuhoan", "onCreate singervietnam");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.singer_fragment, null);
        Log.e("tieuhoan", "onCreateView singervietnam");
        if (!isViewShown) {
            loadSingerVietNam();
            setUpRecycleView(view, singers);
        }


//        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);

//        if(sqLiteHelper.getAllSinger(SQLiteHelper.TABLESINGERVIETNAM)==null){
//            for (int i = 0; i < HangSo.listSingerVietNam.length; i++) {
//                Singer singer = new Singer();
//                singer.setImageSinger(HangSo.listSingerVietNam[i][0]);
//                singer.setNameSinger(HangSo.listSingerVietNam[i][1]);
//                Log.e("tieuhoan ", singer.getNameSinger());
//                sqLiteHelper.addSinger(singer, SQLiteHelper.TABLESINGERVIETNAM);
//            }
//        }
//        singers = sqLiteHelper.getAllSinger(SQLiteHelper.TABLESINGERVIETNAM);
//        for (Singer singer : singers) {
//            Log.e("tieuhoan ", singer.getNameSinger());
//        }


//        sqLiteHelper.close();

        return view;

    }

    private void loadSingerVietNam() {
        singers = new ArrayList<>();
        for (int i = 0; i < HangSo.listSingerVietNam.length; i++) {
            Singer singer = new Singer();
            singer.setImageSinger(HangSo.listSingerVietNam[i][0]);
            singer.setNameSinger(HangSo.listSingerVietNam[i][1]);
            singers.add(singer);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getView() != null) {
            isViewShown = true;
            loadSingerVietNam();
        } else {
            isViewShown = false;
        }


    }
}
