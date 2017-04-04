package adapter;

import android.beotron.tieuhoan.kara_2.R;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import model.RecoderFile;

/**
 * Created by TieuHoan on 02/04/2017.
 */

public class ListRecoderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<RecoderFile> recoderFiles;
    private Context context;

    public ListRecoderAdapter(Context context, ArrayList<RecoderFile> recoderFiles) {
        this.context = context;
        this.recoderFiles = recoderFiles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recoder_file, parent, false);
        return new RecoderHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecoderFile recoderFile = recoderFiles.get(position);
        RecoderHolder recoderHolder = (RecoderHolder) holder;
        recoderHolder.nameRecoderFile.setText(recoderFile.getNameRecoderFile());
        recoderHolder.dateRecoderFile.setText(recoderFile.getDateRecoderFile());
    }


    public interface OnClickItemRecycleView {
        void OnClickItem(View view, int position);
    }

    OnClickItemRecycleView onClickItemRecycleView;

    public void setOnClickItemREcycleView(OnClickItemRecycleView onClickItemREcycleView) {
        this.onClickItemRecycleView = onClickItemREcycleView;
    }


    public interface OnLongItemClickRecycleView {
        void OnLongItemClick(View view, int position);
    }

    OnLongItemClickRecycleView onLongItemClickRecycleView;

    public void setOnLongClickItemRecycleView(OnLongItemClickRecycleView onLongClickItemRecycleView) {
        this.onLongItemClickRecycleView = onLongClickItemRecycleView;
    }

    @Override
    public int getItemCount() {
        return recoderFiles.size();
    }


    class RecoderHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView nameRecoderFile;
        private TextView dateRecoderFile;

        public RecoderHolder(View itemView) {
            super(itemView);
            nameRecoderFile = (TextView) itemView.findViewById(R.id.idTextViewNameRecoder);
            dateRecoderFile = (TextView) itemView.findViewById(R.id.idTextViewDateRecoder);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickItemRecycleView.OnClickItem(v, getPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onLongItemClickRecycleView.OnLongItemClick(v, getPosition());
            return true;
        }
    }


    public void swap(ArrayList<RecoderFile> recoderFiles) {
        this.recoderFiles.clear();
        this.recoderFiles.addAll(recoderFiles);
        notifyDataSetChanged();
    }
}
