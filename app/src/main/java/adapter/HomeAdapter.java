package adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.beotron.tieuhoan.kara_2.R;
import android.beotron.tieuhoan.kara_2.VideoYouTube;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;

import model.Song;
import ulti.HangSo;
import ulti.Recoder;
import ulti.SQLiteHelper;
import view.ViewPageFragment;

/**
 * Created by TieuHoan on 15/03/2017.
 */

public class HomeAdapter extends AdapterListVideo {

    private static final int TYPEHEAD = 0;
    private static int TYPE_ITEM = 1;


    public HomeAdapter(ArrayList<Song> songs, Context context) {
        super(songs, context);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        if (viewType == TYPEHEAD) {
            view = layoutInflater.inflate(R.layout.header, viewGroup, false);
            Log.e("create", "header");
            return new ViewHolderVideoHeader(view);
        } else if (viewType == TYPE_ITEM) {
            view = layoutInflater.inflate(R.layout.item_video, viewGroup, false);
            Log.e("create", "video");
            return new AdapterListVideo.ViewHolderVideo(view);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPEHEAD;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof ViewHolderVideoHeader) {
            ViewHolderVideoHeader viewHolderVideoHeader = (ViewHolderVideoHeader) viewHolder;
//            Glide.with(context).load(songs.get(position).getUrlImageThumb()).into(viewHolderVideoHeader.imageThumb);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate_image);
            viewHolderVideoHeader.imageThumb.startAnimation(animation);

        } else if (viewHolder instanceof ViewHolderVideo) {
            Song song = songs.get(position - 1);
            ViewHolderVideo viewHolderVideo = (ViewHolderVideo) viewHolder;
            bindItem(viewHolderVideo, song);
        }
    }


    @Override
    public int getItemCount() {
        return songs.size() + 1;
    }

    OnClickButton onClickButton;

    public interface OnClickButton {
        public void OnClickBtnMic(Button btnMic);

        public void OnClickBtnRecoder(Button btnRecoder);

        public void OnClickBtnEqualizer(Button btnEqualizer);
    }

    public void setClickButton(OnClickButton onClickButton) {
        this.onClickButton = onClickButton;
    }


    class ViewHolderVideoHeader extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageThumb;
        private Button btnRecoder, btnMic, btnEqualizer;

        public ViewHolderVideoHeader(View itemView) {
            super(itemView);
            btnRecoder = (Button) itemView.findViewById(R.id.btnRecoder);
            btnEqualizer = (Button) itemView.findViewById(R.id.btnEqualizer);
            btnMic = (Button) itemView.findViewById(R.id.btnMic);
            imageThumb = (ImageView) itemView.findViewById(R.id.idImageAnimation);

            btnRecoder.setOnClickListener(this);
            btnEqualizer.setOnClickListener(this);
            btnMic.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnMic: {
                    onClickButton.OnClickBtnMic(btnMic);
                    break;
                }
                case R.id.btnEqualizer: {
                    onClickButton.OnClickBtnEqualizer(btnEqualizer);
                    break;
                }
                case R.id.btnRecoder: {
                    onClickButton.OnClickBtnRecoder(btnRecoder);
                    break;
                }
            }
        }


    }
}
