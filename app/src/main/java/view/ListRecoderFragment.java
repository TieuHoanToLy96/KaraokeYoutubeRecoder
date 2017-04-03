package view;

import android.app.Dialog;
import android.beotron.tieuhoan.kara_2.R;
import android.beotron.tieuhoan.kara_2.VideoYouTube;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import adapter.ListRecoderAdapter;
import model.RecoderFile;
import ulti.Recoder;

/**
 * Created by TieuHoan on 02/04/2017.
 */

public class ListRecoderFragment extends Fragment {
    private RecyclerView recyclerView;
    private ListRecoderAdapter listRecoderAdapter;
    private Context context;
    private ArrayList<RecoderFile> recoderFiles;
    private Recoder recoder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        recoder = new Recoder();
        recoderFiles = recoder.getAllRecoder();
        listRecoderAdapter = new ListRecoderAdapter(context, recoderFiles);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_recoder_fragment, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.idRecycleViewRecoder);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listRecoderAdapter);
        listRecoderAdapter.setOnClickItemREcycleView(new ListRecoderAdapter.OnClickItemRecycleView() {
            @Override
            public void OnClickItem(View view, int position) {

                Log.e("tieuhoan path", recoderFiles.get(position).getPathRecoderFile());
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                File file = new File(recoderFiles.get(position).getPathRecoderFile());
                intent.setDataAndType(Uri.fromFile(file), "audio/*");
                startActivity(intent);
            }
        });
        listRecoderAdapter.setOnLongClickItemRecycleView(new ListRecoderAdapter.OnLongItemClickRecycleView() {
            @Override
            public void OnLongItemClick(View view, final int position) {

                final RecoderFile recoderFile = recoderFiles.get(position);
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_long_click);
                dialog.setTitle("Tùy chọn");


                TextView txvXoa = (TextView) dialog.findViewById(R.id.textViewDelete);
                TextView txvRename = (TextView) dialog.findViewById(R.id.textViewRename);
                txvXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        File file = new File(recoderFile.getPathRecoderFile());
                        if (file.exists()) {
                            file.delete();
                            dialog.dismiss();
                            recoderFiles.remove(position);
                            listRecoderAdapter.notifyDataSetChanged();
                        }

                    }
                });

                txvRename.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        final Dialog dialog1 = new Dialog(context);
                        dialog1.setContentView(R.layout.dialog_rename);
                        dialog1.setTitle("Đổi tên");
                        Button btnLuu = (Button) dialog1.findViewById(R.id.btnLuu);
                        Button btnExit = (Button) dialog1.findViewById(R.id.btnExit);
                        final EditText editTextRename = (EditText) dialog1.findViewById(R.id.editRename);
                        btnLuu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                File file = new File(VideoYouTube.PATH_FOLDER, recoderFile.getNameRecoderFile() + ".mp3");
                                File fileRename = new File(VideoYouTube.PATH_FOLDER, editTextRename.getText().toString() + ".mp3");
                                file.renameTo(fileRename);

//                                recoderFiles.clear();
                                recoderFiles = recoder.getAllRecoder();
                                listRecoderAdapter.notifyDataSetChanged();

                                dialog1.dismiss();
                            }
                        });

                        btnExit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                            }
                        });

                        dialog1.show();

                    }
                });
                dialog.show();
            }
        });
        return view;


    }


}
