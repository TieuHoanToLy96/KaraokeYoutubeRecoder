package adapter;

import android.beotron.tieuhoan.kara_2.R;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
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

import java.sql.SQLInput;
import java.util.ArrayList;

import model.Song;
import ulti.HangSo;
import ulti.Recoder;
import ulti.SQLiteHelper;
import view.ViewPageFragment;

/**
 * Created by TieuHoan on 25/03/2017.
 */

public class AdapterListVideo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<Song> songs;
    public Context context;
    private int listImageBoom[];


    public AdapterListVideo(ArrayList<Song> songs, Context context) {
        this.songs = songs;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        view = layoutInflater.inflate(R.layout.item_video, viewGroup, false);
        return new ViewHolderVideo(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolderVideo viewHolderVideo = (ViewHolderVideo) viewHolder;
        bindItem(viewHolderVideo, songs.get(position));
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHolderVideo extends RecyclerView.ViewHolder implements View.OnClickListener {
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


    public void removeDataBase(SQLiteHelper sqLiteHelper, Song song) {
        for (Song song1 : sqLiteHelper.getAllSong(SQLiteHelper.TABLEFVORITE)) {
            if (song.getVideoId().equals(song1.getVideoId())) {
                if (ViewPageFragment.viewPager.getCurrentItem() == 3) {
                    songs.remove(song);
                    AdapterListVideo.this.notifyDataSetChanged();
                }
                sqLiteHelper.deleteSong(song.getVideoId());
                break;
            }
        }
    }


    public void addToDataBase(SQLiteHelper sqLiteHelper, Song song) {
        ArrayList<Song> songDataBase = sqLiteHelper.getAllSong(SQLiteHelper.TABLEFVORITE);
        if (songDataBase.size() == 0) {
            sqLiteHelper.addSong(song);
        } else {
            if (!checkExist(songDataBase, song)) {
                sqLiteHelper.addSong(song);
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

    OnItemClickRecycle onItemClickRecycle;

    public void setOnItemClickRecycle(OnItemClickRecycle onItemClickRecycle) {
        this.onItemClickRecycle = onItemClickRecycle;
    }


    public interface OnItemClickRecycle {
        void OnItemClick(View view, int position);
    }

    public void bindItem(final ViewHolderVideo viewHolderVideo, final Song song) {

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
                if (checkExist(sqLiteHelper.getAllSong(SQLiteHelper.TABLEFVORITE), song)) {
                    listImageBoom = HangSo.imageResourcesNotFavorite;
                } else {
                    listImageBoom = HangSo.imageResourcesFavorite;
                }
                viewHolderVideo.boomButtonMore.notifyAll();
            }
        });

        if (checkExist(sqLiteHelper.getAllSong(SQLiteHelper.TABLEFVORITE), song)) {
            listImageBoom = HangSo.imageResourcesNotFavorite;
        } else {
            listImageBoom = HangSo.imageResourcesFavorite;
        }

        for (int i = 0; i < viewHolderVideo.boomButtonMore.getPiecePlaceEnum().pieceNumber(); i++) {
            final SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder();
            builder.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    switch (index) {
                        case 0: {
                            if (checkExist(sqLiteHelper.getAllSong(SQLiteHelper.TABLEFVORITE), song)) {
                                removeDataBase(sqLiteHelper, song);
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                addToDataBase(sqLiteHelper, song);
                                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
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

}
