package service;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by TieuHoan on 16/02/2017.
 */

public class FragmentControl extends Fragment {

    public static FragmentManager fragmentManager;
    public static FragmentTransaction transaction;

    public static void goToFragmentNoAddBackStack(int idFragment, Fragment fragment, Context context) {
        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit);
        transaction.replace(idFragment, fragment);
        transaction.commitAllowingStateLoss();
    }

    public static void addFragment(int idFragment, Fragment fragment, Context context) {
        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(idFragment, fragment);
        transaction.commitAllowingStateLoss();


    }


    public static void goToFragmentAddBackStack(int idFragment, Fragment fragment, Context context, String nameClass) {
        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit);
        transaction.addToBackStack(nameClass);
        transaction.replace(idFragment, fragment);
        transaction.commitAllowingStateLoss();
    }


}
