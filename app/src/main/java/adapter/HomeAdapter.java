package adapter;

import android.app.Activity;
import android.beotron.tieuhoan.kara_2.R;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;

import model.Song;
import service.HangSo;
import service.SQLiteAsset;


/**
 * Created by TieuHoan on 08/02/2017.
 */

public class HomeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Song> songs;
    private Animation animation;
    private int lastPosition = -1;
    private Activity activity;


    public HomeAdapter(Context context, ArrayList<Song> songs) {
        this.context = context;
        this.songs = songs;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_video, null);
            viewHolder.imageThumb = (ImageView) view.findViewById(R.id.imageThumb);
            viewHolder.tittle = (TextView) view.findViewById(R.id.tittle);
            viewHolder.nameChannel = (TextView) view.findViewById(R.id.nameChannel);
            viewHolder.timeUpLoad = (TextView) view.findViewById(R.id.timeUpLoad);
            viewHolder.boomButtonMore = (BoomMenuButton) view.findViewById(R.id.idBoomMenu);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

//        // hiệu ứng listview
//        lastPosition = position;
//        animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.scroll_listview_bottom : R.anim.scroll_listview_top);
//        view.startAnimation(animation);
//        lastPosition = position;

        // set item cho listview
        final Song song = songs.get(position);
        viewHolder.tittle.setText(song.getTittle());
        viewHolder.nameChannel.setText(song.getNameChannel());
        viewHolder.timeUpLoad.setText(song.getTimeUpLoad());
        Glide.with(context).load(song.getUrlImageThumb()).into(viewHolder.imageThumb);

        viewHolder.boomButtonMore.clearBuilders();
//        for (int i = 0; i < viewHolder.boomButtonMore.getPiecePlaceEnum().pieceNumber(); i++) {
//            viewHolder.boomButtonMore.addBuilder(BuilderManager.getSimpleCircleButtonBuilder());
//        }

        viewHolder.boomButtonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.boomButtonMore.boom();
            }
        });

        final SQLiteAsset sqLiteAsset = new SQLiteAsset(context);
        for (int i = 0; i < viewHolder.boomButtonMore.getPiecePlaceEnum().pieceNumber(); i++) {
            final SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            switch (index) {
                                case 0: {
//                                    if (!song.getFavorite()) {
                                        addToDataBase(sqLiteAsset, song);
//                                    } else {
//
//                                        Log.e("tieuhoan", "Xóa thành công");
//                                    }
                                    break;
                                }
                            }
                        }
                    });

            builder.normalImageRes(HangSo.imageResources[i]);
            builder.imagePadding(new Rect(10, 10, 10, 10));
            viewHolder.boomButtonMore.addBuilder(builder);
        }
        return view;
    }

public void  removeDataBase(){


}
    public void addToDataBase(SQLiteAsset sqLiteAsset, Song song) {

        final ArrayList<Song> songDatabase = sqLiteAsset.getAllSong();
        if (songDatabase.size() == 0) {
            sqLiteAsset.addSong(song);
        } else {
            if (checkExist(songDatabase, song) == false) {
                sqLiteAsset.addSong(song);
            }
        }

//        song.setFavorite(true);
        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT);
        for (int i = 0; i < songDatabase.size(); i++) {
            Log.e("tieuhoan", sqLiteAsset.getAllSong().get(i).getVideoId());
        }
    }


    public boolean checkExist(ArrayList<Song> songs, Song song) {
        boolean check = false;
        for (int i = 0; i < songs.size(); i++) {
            if (song.getVideoId().equals(songs.get(i).getVideoId())) {
                check = true;
                break;
            }
        }
        return check;
    }

    public class ViewHolder {
        private ImageView imageThumb;
        private TextView tittle;
        private TextView nameChannel;
        private TextView timeUpLoad;
        private BoomMenuButton boomButtonMore;
    }


}
