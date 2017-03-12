package ulti;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by TieuHoan on 03/03/2017.
 */

public class ReadFile {


    public void read(Context context, String fileName) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));
            String mLine;

            while ((mLine = br.readLine()) != null) {
                //process line
//                show(to2dim(mLine ,  "," ,""));
//                System.out.println(mLine.length());
//                mLine.split("\\]");
//                mLine.replaceAll()
//                mLine.split("\"title\":");

//                System.out.println(mLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if ((br != null)) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static String[][] to2dim(String source, String outerdelim, String innerdelim) {

        String[][] result = new String[source.replaceAll("{^" + outerdelim + "}", "").length() + 1][];
        int count = 0;
        for (String line : source.split("{" + outerdelim + "}")) {
            result[count++] = line.split(innerdelim);
        }
        return result;
    }

    public static void show(String[][] arr) {
        for (String[] ar : arr) {
            for (String a : ar)
                System.out.print(" " + a);
            System.out.println();
        }
    }

}
