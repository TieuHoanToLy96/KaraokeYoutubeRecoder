package ulti;

import android.beotron.tieuhoan.kara_2.R;
import android.media.AudioFormat;

/**
 * Created by TieuHoan on 23/02/2017.
 */

public class HangSo {
    public static String API_URI = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet";
    public static String API_URI2 = " https://www.googleapis.com/youtube/v3/search?part=snippet";
    public static String PLAYLIST_ID = "PL75PeaMqYS56hHy9Obnj2JlQmYy-vgxox";
    public static String KEY_BROWSE = "AIzaSyBT_FE1lBGjxNlbVNfL1y1l660jQEFI0y0";
    public static int KEY_HANDLER = 1, KEY_HANDLER2 = 1;
    public static String APP_KEY = "AIzaSyB_iZfaci4AdsEDaY_N-hlsw1h6w_Xrz_o";
    public static final int SAMPLERATE = 44100;
    public static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    public static String listSingerVietNam[][] = {{"sontungm-tp.jpg", "Sơn Tùng M-TP"}, {"damvinhhung.jpg", "Đàm Vĩnh Hưng"}, {"quangle.jpg", "Quang Lê"}, {"lequyen.jpg", "Lệ Quyên"}, {"dantruong.jpg", "Đan Trường"}, {"khacviet.jpg", "Khắc Việt"}, {"phamtruong.jpg", "Phạm Trưởng"}, {"luongbichhuu.jpg", "Lương Bích Hữu"}, {"noophuocthinh.jpg", "Noo Phước Thịnh"}, {"hoquanghieu.jpg", "Hồ Quang Hiếu"}, {"hoviettrung.jpg", "Hồ Việt Trung"}, {"khanhphuong.jpg", "Khánh Phương"}, {"caothaison.jpg", "Cao Thái Sơn"}, {"dongnhi.jpg", "Đông Nhi"}, {"hongocha.jpg", "Hồ Ngọc Hà"}, {"tuanhung.jpg", "Tuấn Hưng"}, {"baothy.jpg", "Bảo Thy"}, {"thuminh.jpg", "Thu Minh"}, {"minhvuongm4u.jpg", "Minh Vương M4U"}, {"mytam.jpg", "Mỹ Tâm"}, {"themen.jpg", "The Men"}, {"thuytien.jpg", "Thủy Tiên"}, {"hkt.jpg", "HKT"}, {"hoquynhhuong.jpg", "Hồ Quỳnh Hương"}, {"khongtuquynh.jpg", "Khổng Tú Quỳnh"}, {"unghoangphuc.jpg", "Ưng Hoàng Phúc"}, {"vyoanh.jpg", "Vy Oanh"}, {"miule.jpg", "Miu Lê"}, {"duongngocthai.jpg", "Dương Ngọc Thái"}, {"camly.jpg", "Cẩm Ly"}, {"vanmaihuong.jpg", "Văn Mai Hương"}, {"v.music.jpg", "V.Music"}, {"bangkieu.jpg", "Bằng Kiều"}, {"ngokienhuy.jpg", "Ngô Kiến Huy"}, {"minhhang.jpg", "Minh Hằng"}, {"khoimy.jpg", "Khởi My"}, {"duongtrieuvu.jpg", "Dương Triệu Vũ"}, {"quangdung.jpg", "Quang Dũng"}, {"karik.jpg", "Karik"}, {"lamtruong.jpg", "Lam Trường"}, {"thanhthao.jpg", "Thanh Thảo"}, {"bangcuong.jpg", "Bằng Cường"}, {"lyhai.jpg", "Lý Hải"}, {"quangha.jpg", "Quang Hà"}, {"mylinh.jpg", "Mỹ Linh"}, {"yanbi.jpg", "Yanbi"}, {"nhuquynh.jpg", "Như Quỳnh"}, {"akiraphan.jpg", "Akira Phan"}, {"mrt.jpg", "Mr T"}, {"thanhlam.jpg", "Thanh Lam"}, {"maithienvan.jpg", "Mai Thiên Vân"}, {"xuanmai.jpg", "Xuân Mai"}, {"khanhly.jpg", "Khánh Ly"}, {"ylan.jpg", "Ý Lan"}, {"tuanngoc.jpg", "Tuấn Ngọc"}, {"phandinhtung.jpg", "Phan Đinh Tùng"}, {"hoangrapper.jpg", "Hoàng Rapper"}, {"phinhung.jpg", "Phi Nhung"}, {"lehieu.jpg", "Lê Hiếu"}, {"hongnhung.jpg", "Hồng Nhung"}, {"minhtuyet.jpg", "Minh Tuyết"}, {"thuhien.jpg", "Thu Hiền"}, {"trongtan.jpg", "Trọng Tấn"}, {"nsndthanhhoa.jpg", "NSND Thanh Hoa"}, {"ngocson.jpg", "Ngọc Sơn"}, {"suboi.jpg", "Suboi"}, {"luongminhtrang.jpg", "Lương Minh Trang"}, {"knight.jpg", "Lil Knight"}, {"emily.jpg", "Emily"}, {"vinhthuyenkim.jpg", "Vĩnh Thuyên Kim"}, {"tranmanhtuan.jpg", "Trần Mạnh Tuấn"}, {"duyquang.jpg", "Duy Quang"}, {"thanhlongbass.jpg", "Thanh Long Bass"}, {"tongson.jpg", "Tòng Sơn"}, {"ongcaothang.jpg", "Ông Cao Thắng"}, {"siublack.jpg", "Siu Black"}, {"moz.jpg", "Moz"}, {"quynhdung.jpg", "Quỳnh Dung"}, {"trieukietluan.jpg", "Triệu Kiệt Luân"}, {"bueno.jpg", "Bueno"}, {"lamanh.jpg", "Lam  Anh"}, {"tmt.jpg", "TMT"}, {"nguyenhongthuan.jpg", "Nguyễn Hồng Thuận"}, {"anhtho.jpg", "Anh Thơ"}, {"bachtung.jpg", "Bách Tùng"}, {"khanhtrung.jpg", "Khánh Trung"}, {"kennguyen.jpg", "Ken Nguyễn"}, {"buctuong.jpg", "Bức Tường"}, {"huyenthoai.jpg", "Huyền thoại"}, {"trieuhai.jpg", "Triều Hải"}, {"lee7.jpg", "Lee7"}, {"nguyenduccuong.jpg", "Nguyễn Đức Cường"}};
    public static String listSingerGlobal[][] = {{"rihanna.jpg", "Rihanna"}, {"maroon5.jpg", "Maroon 5"}, {"madonna.jpg", "Madonna"}, {"beyonce.jpg", "Beyoncé"}, {"linkinpark.jpg", "Linkin Park"}, {"mariahcarey.jpg", "Mariah Carey"}, {"ladygaga.jpg", "Lady Gaga"}, {"celinedion.jpg", "Celine Dion"}, {"michaeljackson.jpg", "Michael Jackson"}, {"avrillavigne.jpg", "Avril Lavigne"}, {"eminem.jpg", "Eminem"}, {"theblackeyedpeas.jpg", "The Black Eyed Peas"}, {"usher.jpg", "Usher"}, {"backstreetboys.jpg", "Backstreet Boys"}, {"aliciakeys.jpg", "Alicia Keys"}, {"christinaaguilera.jpg", "Christina Aguilera"}, {"davidguetta.jpg", "David Guetta"}, {"adele.jpg", "Adele"}, {"britneyspears.jpg", "Britney Spears"}, {"whitneyhouston.jpg", "Whitney Houston"}, {"chrisbrown.jpg", "Chris Brown"}, {"justintimberlake.jpg", "Justin Timberlake"}, {"katyperry.jpg", "Katy Perry"}, {"richardclayderman.jpg", "Richard Clayderman"}, {"kylieminogue.jpg", "Kylie Minogue"}, {"nsync.jpg", "N Sync"}, {"m2m.jpg", "M2M"}, {"wesmontgomery.jpg", "Wes Montgomery"}, {"sclub7.jpg", "S Club 7"}, {"dianaross.jpg", "Diana Ross"}, {"ildivo.jpg", "Il Divo"}, {"elvispresley.jpg", "Elvis Presley"}, {"taylorswift.jpg", "Taylor Swift"}, {"abba.jpg", "ABBA"}, {"shakira.jpg", "Shakira"}, {"justinbieber.jpg", "Justin Bieber"}, {"spicegirls.jpg", "Spice Girls"}, {"janetjackson.jpg", "Janet Jackson"}, {"nickiminaj.jpg", "Nicki Minaj"}, {"blue.jpg", "Blue"}, {"jimihendrix.jpg", "Jimi Hendrix"}, {"michaellearnstorock.jpg", "Michael Learns To Rock"}, {"ericclapton.jpg", "Eric Clapton"}, {"troublemaker.jpg", "Trouble Maker"}, {"carlyraejepsen.jpg", "Carly Rae Jepsen"}, {"jimmypage.jpg", "Jimmy Page"}, {"adamlambert.jpg", "Adam Lambert"}, {"keithrichards.jpg", "Keith Richards"}, {"jeffbeck.jpg", "Jeff Beck"}, {"carlossantana.jpg", "Carlos Santana"}, {"b.b.king.jpg", "B.B. King"}, {"jenniferlopez.jpg", "Jennifer Lopez"}, {"chuckberry.jpg", "Chuck Berry"}, {"a1.jpg", "A1"}, {"eddievanhalen.jpg", "Eddie Van Halen"}, {"coldplay.jpg", "Coldplay"}, {"brunomars.jpg", "Bruno Mars"}, {"oasis.jpg", "Oasis"}, {"queen.jpg", "Queen"}, {"onedirection.jpg", "One Direction"}, {"rubenderonde.jpg", "Ruben De Ronde"}, {"hoangwar.jpg", "Hoangwar"}};

    public static String cutDate(String date) {
        return date.substring(0, 9);
    }

    public static int[] imageResources = new int[]{
            R.mipmap.heart,
            R.mipmap.home,
            R.mipmap.share
    };

}
