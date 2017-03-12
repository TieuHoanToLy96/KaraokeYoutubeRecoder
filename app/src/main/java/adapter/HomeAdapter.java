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

import com.bumptech.glide.Glide;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;

import model.Song;
import ulti.HangSo;
import ulti.SQLiteHelper;


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
    public View getView(final int position, final View convertView, ViewGroup parent) {
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

        viewHolder.boomButtonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.boomButtonMore.boom();
            }
        });

        final SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        for (int i = 0; i < viewHolder.boomButtonMore.getPiecePlaceEnum().pieceNumber(); i++) {
            final SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            switch (index) {
                                case 0: {
                                    if (song.getIsFavorite().equals("false")) {
                                        addToDataBase(sqLiteHelper, song);
                                    } else {
                                        for (int j = 0; j < sqLiteHelper.getAllSong().size(); j++) {
                                            Log.e("tieuhoan", String.valueOf(sqLiteHelper.getAllSong().get(j).getVideoId()));
                                        }
                                        Log.e("song", song.getVideoId());

                                        for (Song song : sqLiteHelper.getAllSong()) {
                                            if (song.getVideoId().equalsIgnoreCase(song.getVideoId())) {
                                                sqLiteHelper.deleteSong(song.getVideoId());
                                                HomeAdapter.this.notifyDataSetChanged();
                                                Log.e("tieuhoan", "Xóa thành công");
                                                break;
                                            }
                                        }
                                    }
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

    public void removeDataBase() {


    }


    public void addToDataBase(SQLiteHelper sqLiteHelper, Song song) {

        final ArrayList<Song> songDatabase = sqLiteHelper.getAllSong();
        if (songDatabase.size() == 0) {
            song.setIsFavorite("true");
            sqLiteHelper.addSong(song);
            Log.e("tieuhoan", "Thêm thành công");
        } else {
            if (checkExist(songDatabase, song) == false) {
                song.setIsFavorite("true");
                sqLiteHelper.addSong(song);
                Log.e("tieuhoan", "Thêm thành công");
            }
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
