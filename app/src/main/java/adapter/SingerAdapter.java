package adapter;

import android.beotron.tieuhoan.kara_2.R;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import model.Singer;

/**
 * Created by TieuHoan on 25/02/2017.
 */

public class SingerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Singer> singers;
    private AssetManager assetManager;
    private Animation animation ;
    private int lastPosition;

    public SingerAdapter(Context context, ArrayList<Singer> singers) {
        this.context = context;
        this.singers = singers;
    }

    @Override
    public int getCount() {
        return singers.size();
    }

    @Override
    public Object getItem(int i) {
        return singers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_singer, null);
            viewHolder.imageSinger = (ImageView) view.findViewById(R.id.idImageSinger);
            viewHolder.nameSinger = (TextView) view.findViewById(R.id.idNameSinger);
            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) view.getTag();
        }

        Singer singer = singers.get(position);
        viewHolder.nameSinger.setText(singer.getNameSinger());
//        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/VNI-Machina.ttf");
//        viewHolder.nameSinger.setTypeface(type);

        try {
            Drawable d = Drawable.createFromStream(context.getAssets().open("viet/"+singer.getImageSinger()), null);
            viewHolder.imageSinger.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    public class ViewHolder {
        ImageView imageSinger;
        TextView nameSinger;

    }
}
