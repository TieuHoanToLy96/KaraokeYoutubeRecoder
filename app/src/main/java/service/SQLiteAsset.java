package service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import model.Song;

/**
 * Created by TieuHoan on 04/03/2017.
 */

public class SQLiteAsset extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "favorite";
    private static final int DATABASE_VERSION = 1;

    public static final String ID = "ID";
    public static final String TITLE = "TITLE";
    public static final String NAMECHANNEL = "NAMECHANNEL";
    public static final String TIMEUPLOAD = "TIMEUPLOAD";
    public static final String URLIMAGETHUMB = "URLIMAGETHUMB";
    public static final String VIDEOID = "VIDEOID";
    public static final String TABLE = "FAVORITE";

    public SQLiteAsset(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<Song> getAllSong() {
        ArrayList<Song> songs = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String colums[] = {SQLiteAsset.ID, SQLiteAsset.TITLE, SQLiteAsset.NAMECHANNEL, SQLiteAsset.TIMEUPLOAD, SQLiteAsset.URLIMAGETHUMB, SQLiteAsset.VIDEOID};
        Cursor cursor = sqLiteDatabase.query(SQLiteAsset.TABLE, colums, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Song song = new Song();
            song.setTittle(cursor.getString(cursor.getColumnIndex(SQLiteAsset.TITLE)));
            song.setNameChannel(cursor.getString(cursor.getColumnIndex(SQLiteAsset.NAMECHANNEL)));
            song.setTimeUpLoad(cursor.getString(cursor.getColumnIndex(SQLiteAsset.TIMEUPLOAD)));
            song.setUrlImageThumb(cursor.getString(cursor.getColumnIndex(SQLiteAsset.URLIMAGETHUMB)));
            song.setVideoId(cursor.getString(cursor.getColumnIndex(SQLiteAsset.VIDEOID)));
            songs.add(song);
        }
        return songs;
    }
    public int addSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", song.getTittle());
        contentValues.put("NAMECHANNEL", song.getNameChannel());
        contentValues.put("TIMEUPLOAD", song.getTimeUpLoad());
        contentValues.put("URLIMAGETHUMB", song.getUrlImageThumb());
        contentValues.put("VIDEOID", song.getVideoId());
        int result = (int) db.insert(SQLiteAsset.TABLE, "ID", contentValues);
        return result;
    }


}
