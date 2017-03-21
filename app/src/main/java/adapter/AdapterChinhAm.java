package adapter;

import android.beotron.tieuhoan.kara_2.R;
import android.content.Context;
import android.media.audiofx.Equalizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import model.ChinhAm;

/**
 * Created by TieuHoan on 17/03/2017.
 */

public class AdapterChinhAm extends BaseAdapter {

    private Context context;
    private ArrayList<ChinhAm> arrayChinhAm;
    private ChinhAm chinhAm;
    private Equalizer equalizer;


    public AdapterChinhAm(Context context, ArrayList<ChinhAm> arrayChinhAm, Equalizer equalizer) {
        this.context = context;
        this.arrayChinhAm = arrayChinhAm;
        this.equalizer = equalizer;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_chinh_am, null);
            viewHolder.textViewLower = (TextView) convertView.findViewById(R.id.textViewLower);
            viewHolder.seekBarFre = (SeekBar) convertView.findViewById(R.id.seekBarFre);
            viewHolder.textViewUpper = (TextView) convertView.findViewById(R.id.textViewUpper);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        //set text thôi , k có gì cả
        chinhAm = arrayChinhAm.get(position);
        viewHolder.textViewLower.setText(String.valueOf(chinhAm.getLower() / 1000) + "dB");
        viewHolder.textViewUpper.setText(equalizer.getCenterFreq((short) position) / 1000 + "kHz");

        //set max và process  cho seekbar
        viewHolder.seekBarFre.setMax(chinhAm.getMax());
        viewHolder.seekBarFre.setProgress(chinhAm.getProcess());

        //event kéo seekbar
        viewHolder.seekBarFre.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                viewHolder.textViewLower.setText(String.valueOf((progress + chinhAm.getLower())) + "dB");

                //set cường độ của dải tần số
                //param 1 là vị trí của dải tần số
                //param 2 là khoảng tăng thêm hoặc giảm đi của cường dộ tần số
                equalizer.setBandLevel((short) (position), (short) (progress + chinhAm.getLower()));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        return convertView;
    }

    @Override
    public int getCount() {
        return arrayChinhAm.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        private TextView textViewLower;
        private TextView textViewUpper;
        private SeekBar seekBarFre;
    }
}