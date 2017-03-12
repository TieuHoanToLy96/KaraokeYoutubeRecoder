package ulti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import model.Singer;
import model.Song;

/**
 * Created by TieuHoan on 12/03/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "data";
    public static final int DATABASE_VERSION = 1;

    public static final String ID = "ID";
    public static final String TITLE = "TITLE";
    public static final String NAMECHANNEL = "NAMECHANNEL";
    public static final String TIMEUPLOAD = "TIMEUPLOAD";
    public static final String URLIMAGETHUMB = "URLIMAGETHUMB";
    public static final String VIDEOID = "VIDEOID";
    public static final String FAVORITE = "FAVORITE";
    public static final String TABLEFVORITE = "FAVORITE";


    public static final String NAMESINGER = "NAMESINGER";
    public static final String IMAGESINGER = "IMAGESINGER";
    public static final String TABLESINGERVIETNAM = "SINGERVIETNAM";
    public static final String TABLESINGERGLOBAL = "SINGERGLOBAL";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FAVORITE = "CREATE TABLE `FAVORITE` (\n" +
                "\t`ID`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`TITLE`\tTEXT,\n" +
                "\t`NAMECHANNEL`\tTEXT,\n" +
                "\t`TIMEUPLOAD`\tTEXT,\n" +
                "\t`URLIMAGETHUMB`\tTEXT,\n" +
                "\t`VIDEOID`\tTEXT,\n" +
                "\t`FAVORITE`\tTEXT\n" +
                ");";

        String CREATE_SINGER_VIETNAM = "CREATE TABLE `SINGERVIETNAM` (\n" +
                "\t`NAMESINGER`\tTEXT,\n" +
                "\t`IMAGESINGER`\tTEXT\n" +
                ");";

        String CREATE_SINGER_GLOBAL = "CREATE TABLE `SINGERGLOBAL` (\n" +
                "\t`NAMESINGER`\tTEXT,\n" +
                "\t`IMAGESINGER`\tTEXT\n" +
                ");";

        db.execSQL(CREATE_FAVORITE);
        db.execSQL(CREATE_SINGER_VIETNAM);
        db.execSQL(CREATE_SINGER_GLOBAL);

    }

    public ArrayList<Song> getAllSong() {
        ArrayList<Song> songs = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String colums[] = {SQLiteHelper.ID, SQLiteHelper.TITLE, SQLiteHelper.NAMECHANNEL, SQLiteHelper.TIMEUPLOAD, SQLiteHelper.URLIMAGETHUMB, SQLiteHelper.VIDEOID, SQLiteHelper.FAVORITE};
        Cursor cursor = sqLiteDatabase.query(SQLiteHelper.TABLEFVORITE, colums, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Song song = new Song();
            song.setTittle(cursor.getString(cursor.getColumnIndex(SQLiteHelper.TITLE)));
            song.setNameChannel(cursor.getString(cursor.getColumnIndex(SQLiteHelper.NAMECHANNEL)));
            song.setTimeUpLoad(cursor.getString(cursor.getColumnIndex(SQLiteHelper.TIMEUPLOAD)));
            song.setUrlImageThumb(cursor.getString(cursor.getColumnIndex(SQLiteHelper.URLIMAGETHUMB)));
            song.setVideoId(cursor.getString(cursor.getColumnIndex(SQLiteHelper.VIDEOID)));
            song.setIsFavorite(cursor.getString(cursor.getColumnIndex(SQLiteHelper.FAVORITE)));
            songs.add(song);
        }
        return songs;
    }

    public int addSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.TITLE, song.getTittle());
        contentValues.put(SQLiteHelper.NAMECHANNEL, song.getNameChannel());
        contentValues.put(SQLiteHelper.TIMEUPLOAD, song.getTimeUpLoad());
        contentValues.put(SQLiteHelper.URLIMAGETHUMB, song.getUrlImageThumb());
        contentValues.put(SQLiteHelper.VIDEOID, song.getVideoId());
        contentValues.put(SQLiteHelper.FAVORITE, song.getIsFavorite());
        int result = (int) db.insert(SQLiteHelper.TABLEFVORITE, SQLiteHelper.ID, contentValues);
        return result;
    }


    public ArrayList<Singer> getAllSinger(String nameTable) {
        ArrayList<Singer> singers = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String colums[] = {SQLiteHelper.NAMESINGER, SQLiteHelper.IMAGESINGER};
        Cursor cursor = sqLiteDatabase.query(nameTable, colums, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Singer singer = new Singer();
            singer.setNameSinger(cursor.getString(cursor.getColumnIndex(SQLiteHelper.NAMESINGER)));
            singer.setImageSinger(cursor.getString(cursor.getColumnIndex(SQLiteHelper.IMAGESINGER)));
            singers.add(singer);
        }
        return singers;
    }

    public int addSinger(Singer singer, String nameTable) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.NAMESINGER, singer.getNameSinger());
        contentValues.put(SQLiteHelper.IMAGESINGER, singer.getImageSinger());
        int result = (int) db.insert(nameTable, null, contentValues);
        return result;
    }

    public boolean deleteSong(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SQLiteHelper.TABLEFVORITE, SQLiteHelper.VIDEOID + "=" + name, null) > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
