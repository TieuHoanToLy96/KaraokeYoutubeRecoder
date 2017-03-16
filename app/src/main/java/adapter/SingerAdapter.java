package adapter;

import android.beotron.tieuhoan.kara_2.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import model.Singer;

/**
 * Created by TieuHoan on 16/03/2017.
 */

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.ViewHolderSinger> {

    private ArrayList<Singer> singers;
    private Context context;


    public SingerAdapter(ArrayList<Singer> singers, Context context) {
        this.singers = singers;
        this.context = context;
    }

    @Override
    public ViewHolderSinger onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_singer, null);


        return new ViewHolderSinger(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderSinger viewHolserSinger, int i) {
        Singer singer = singers.get(i);

        viewHolserSinger.nameSinger.setText(singer.getNameSinger());
        try {
            Drawable d = Drawable.createFromStream(context.getAssets().open("viet/" + singer.getImageSinger()), null);
            viewHolserSinger.imageSinger.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return singers.size();
    }

    class ViewHolderSinger extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageSinger;
        TextView nameSinger;

        public ViewHolderSinger(View itemView) {
            super(itemView);
            imageSinger = (ImageView) itemView.findViewById(R.id.idImageSinger);
            nameSinger = (TextView) itemView.findViewById(R.id.idNameSinger);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClick != null) {
                onItemClick.OnClick(v, getPosition());
            }
        }
    }

    OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void OnClick(View view, int position);
    }
}
