package service;

import android.beotron.tieuhoan.kara_2.R;
import android.graphics.Color;

import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;

/**
 * Created by TieuHoan on 02/03/2017.
 */

public class BuilderManager {

    /**
     * Created by TieuHoan on 02/03/2017.
     */


        private static int[] imageResources = new int[]{
                R.mipmap.heart,
                R.mipmap.heart,
                R.mipmap.heart,
                R.mipmap.heart,
                R.mipmap.heart,
                R.mipmap.heart,
                R.mipmap.heart,
                R.mipmap.heart,
                R.mipmap.heart,
                R.mipmap.heart,
                R.mipmap.heart,
                R.mipmap.heart,
                R.mipmap.heart,
                R.mipmap.heart,
                R.mipmap.heart,
                R.mipmap.heart,
                R.mipmap.heart
        };

        private static int imageResourceIndex = 0;

        static int getImageResource() {
            if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
            return imageResources[imageResourceIndex++];
        }

        public static SimpleCircleButton.Builder getSimpleCircleButtonBuilder() {
            return new SimpleCircleButton.Builder()
                    .normalImageRes(getImageResource());
        }

        static TextInsideCircleButton.Builder getTextInsideCircleButtonBuilder() {
            return new TextInsideCircleButton.Builder()
                    .normalImageRes(getImageResource());
        }

        static TextInsideCircleButton.Builder getTextInsideCircleButtonBuilderWithDifferentPieceColor() {
            return new TextInsideCircleButton.Builder()
                    .normalImageRes(getImageResource())
                    .pieceColor(Color.WHITE);
        }

        static TextOutsideCircleButton.Builder getTextOutsideCircleButtonBuilder() {
            return new TextOutsideCircleButton.Builder()
                    .normalImageRes(getImageResource());
        }

        static TextOutsideCircleButton.Builder getTextOutsideCircleButtonBuilderWithDifferentPieceColor() {
            return new TextOutsideCircleButton.Builder()
                    .normalImageRes(getImageResource())
                    .pieceColor(Color.WHITE);
        }

        static HamButton.Builder getHamButtonBuilder() {
            return new HamButton.Builder()
                    .normalImageRes(getImageResource());
        }

        static HamButton.Builder getHamButtonBuilderWithDifferentPieceColor() {
            return new HamButton.Builder()
                    .normalImageRes(getImageResource())
                    .pieceColor(Color.WHITE);
        }

        private static service.BuilderManager ourInstance = new service.BuilderManager();

        public static service.BuilderManager getInstance() {
            return ourInstance;
        }

        private BuilderManager() {
        }


}
