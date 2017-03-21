//package ulti;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
//
//import java.util.ArrayList;
//
//import model.Singer;
//import model.Song;
//
///**
// * Created by TieuHoan on 04/03/2017.
// */
//
//public class SQLiteAsset extends SQLiteAssetHelper {
//    public static final String DATABASE_NAME = "kara.db";
//    public static final int DATABASE_VERSION = 1;
//
//    public static final String ID = "ID";
//    public static final String TITLE = "TITLE";
//    public static final String NAMECHANNEL = "NAMECHANNEL";
//    public static final String TIMEUPLOAD = "TIMEUPLOAD";
//    public static final String URLIMAGETHUMB = "URLIMAGETHUMB";
//    public static final String VIDEOID = "VIDEOID";
//    public static final String FAVORITE = "FAVORITE";
//    public static final String TABLEFVORITE = "FAVORITE";
//
//
//    public static final String NAMESINGER = "NAMESINGER";
//    public static final String IMAGESINGER = "IMAGESINGER";
//    public static final String TABLESINGERVIETNAM = "SINGERVIETNAM";
//    public static final String TABLESINGERGLOBAL = "SINGERGLOBAL";
//
//    public SQLiteAsset(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    public ArrayList<Song> getAllSong() {
//        ArrayList<Song> songs = new ArrayList<>();
//        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        String colums[] = {SQLiteAsset.ID, SQLiteAsset.TITLE, SQLiteAsset.NAMECHANNEL, SQLiteAsset.TIMEUPLOAD, SQLiteAsset.URLIMAGETHUMB, SQLiteAsset.VIDEOID, SQLiteAsset.FAVORITE};
//        Cursor cursor = sqLiteDatabase.query(SQLiteAsset.TABLEFVORITE, colums, null, null, null, null, null);
//        while (cursor.moveToNext()) {
//            Song song = new Song();
//            song.setTittle(cursor.getString(cursor.getColumnIndex(SQLiteAsset.TITLE)));
//            song.setNameChannel(cursor.getString(cursor.getColumnIndex(SQLiteAsset.NAMECHANNEL)));
//            song.setTimeUpLoad(cursor.getString(cursor.getColumnIndex(SQLiteAsset.TIMEUPLOAD)));
//            song.setUrlImageThumb(cursor.getString(cursor.getColumnIndex(SQLiteAsset.URLIMAGETHUMB)));
//            song.setVideoId(cursor.getString(cursor.getColumnIndex(SQLiteAsset.VIDEOID)));
//            song.setIsFavorite(cursor.getString(cursor.getColumnIndex(SQLiteAsset.FAVORITE)));
//            songs.add(song);
//        }
//        return songs;
//    }
//
////    public ArrayList<Singer> getAllSinger() {
////        ArrayList<Singer> singers = new ArrayList<>();
////        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
////        String colums[] = {SQLiteAsset.NAMESINGER, SQLiteAsset.IMAGESINGER};
////        Cursor cursor = sqLiteDatabase.query(SQLiteAsset.TABLESINGER, colums, null, null, null, null, null);
////        while (cursor.moveToNext()) {
////            Singer singer = new Singer();
////            singer.setNameSinger(cursor.getString(cursor.getColumnIndex(SQLiteAsset.NAMESINGER)));
////            singer.setImageSinger(cursor.getString(cursor.getColumnIndex(SQLiteAsset.IMAGESINGER)));
////            singers.add(singer);
////        }
////        return singers;
////    }
//
////    public int addSinger(Singer singer) {
////        SQLiteDatabase db = this.getWritableDatabase();
////        ContentValues contentValues = new ContentValues();
////        contentValues.put(SQLiteAsset.NAMESINGER, singer.getNameSinger());
////        contentValues.put(SQLiteAsset.IMAGESINGER, singer.getImageSinger());
////        int result = (int) db.insert(SQLiteAsset.TABLESINGER, null, contentValues);
////        return result;
////    }
//
//    public int addSong(Song song) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(SQLiteAsset.TITLE, song.getTittle());
//        contentValues.put(SQLiteAsset.NAMECHANNEL, song.getNameChannel());
//        contentValues.put(SQLiteAsset.TIMEUPLOAD, song.getTimeUpLoad());
//        contentValues.put(SQLiteAsset.URLIMAGETHUMB, song.getUrlImageThumb());
//        contentValues.put(SQLiteAsset.VIDEOID, song.getVideoId());
//        contentValues.put(SQLiteAsset.FAVORITE, song.getIsFavorite());
//        int result = (int) db.insert(SQLiteAsset.TABLEFVORITE, SQLiteAsset.ID, contentValues);
//        return result;
//    }
//
//
//    public boolean deleteSong(String name) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(SQLiteAsset.TABLEFVORITE, SQLiteAsset.VIDEOID + "=" + name, null) > 0;
//    }
//
//
//}
