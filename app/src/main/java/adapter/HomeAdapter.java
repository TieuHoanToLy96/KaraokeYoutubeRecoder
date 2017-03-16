package adapter;

import android.beotron.tieuhoan.kara_2.R;
import android.beotron.tieuhoan.kara_2.VideoYouTube;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;

import model.Song;
import ulti.HangSo;
import ulti.SQLiteHelper;
import view.ViewPageFragment;

/**
 * Created by TieuHoan on 15/03/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolderVideo> {
    private ArrayList<Song> songs;
    private Context context;
    private int listImageBoom[];
    private int position;

    public HomeAdapter(ArrayList<Song> songs, Context context) {
        this.songs = songs;
        this.context = context;
    }

    @Override
    public ViewHolderVideo onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_video, viewGroup, false);
        return new ViewHolderVideo(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolderVideo viewHolderVideo, final int position) {
        this.position = position;

        final Song song = songs.get(position);
        final SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        viewHolderVideo.tittle.setText(song.getTittle());
        viewHolderVideo.nameChannel.setText(song.getNameChannel());
        viewHolderVideo.timeUpLoad.setText(song.getTimeUpLoad());
        Glide.with(context).load(song.getUrlImageThumb()).into(viewHolderVideo.imageThumb);

        viewHolderVideo.boomButtonMore.clearBuilders();
        viewHolderVideo.boomButtonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolderVideo.boomButtonMore.boom();
                if (!checkExist(songs, song)) {
                    song.setIsFavorite("false");
                } else {
                    song.setIsFavorite("true");
                }
            }
        });

        if (checkExist(sqLiteHelper.getAllSong(), song)) {
            listImageBoom = HangSo.imageResourcesNotFavorite;
        } else {
            listImageBoom = HangSo.imageResourcesFavorite;
        }


//        viewHolderVideo.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("SONG", songs.get(position));
//                bundle.putSerializable("SONGS", songs);
//                Intent intent = new Intent(context, VideoYouTube.class);
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//            }
//        });


        for (int i = 0; i < viewHolderVideo.boomButtonMore.getPiecePlaceEnum().pieceNumber(); i++) {
            final SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder();
            builder.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    switch (index) {
                        case 0: {
                            if (song.getIsFavorite().equals("false")) {
                                addToDataBase(sqLiteHelper, song);
                                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                removeDataBase(sqLiteHelper, song);
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    }
                }
            });
            builder.normalImageRes(listImageBoom[i]);
            builder.imagePadding(new Rect(10, 10, 10, 10));
            viewHolderVideo.boomButtonMore.addBuilder(builder);
        }
    }


    @Override
    public int getItemCount() {
        return songs.size();
    }

    public void removeDataBase(SQLiteHelper sqLiteHelper, Song song) {
        for (Song song1 : sqLiteHelper.getAllSong()) {
            if (song.getVideoId().equals(song1.getVideoId())) {
                song.setIsFavorite("false");
                if (ViewPageFragment.viewPager.getCurrentItem() == 3) {
                    songs.remove(song);
                    HomeAdapter.this.notifyDataSetChanged();
                }
                sqLiteHelper.deleteSong(song.getVideoId());
                Log.e("tieuhoan", "Xóa thành công");
                break;
            }
        }
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

    class ViewHolderVideo extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageThumb;
        private TextView tittle;
        private TextView nameChannel;
        private TextView timeUpLoad;
        private BoomMenuButton boomButtonMore;

        public ViewHolderVideo(View itemView) {
            super(itemView);
            imageThumb = (ImageView) itemView.findViewById(R.id.imageThumb);
            tittle = (TextView) itemView.findViewById(R.id.tittle);
            nameChannel = (TextView) itemView.findViewById(R.id.nameChannel);
            timeUpLoad = (TextView) itemView.findViewById(R.id.timeUpLoad);
            boomButtonMore = (BoomMenuButton) itemView.findViewById(R.id.idBoomMenu);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (onItemClickRecycle != null) {
                onItemClickRecycle.OnItemClick(v, getPosition());
            }
        }
    }

    OnItemClickRecycle onItemClickRecycle;

    public void setOnItemClickRecycle(OnItemClickRecycle onItemClickRecycle) {
        this.onItemClickRecycle = onItemClickRecycle;
    }


    public interface OnItemClickRecycle {
        void OnItemClick(View view, int position);
    }
}
