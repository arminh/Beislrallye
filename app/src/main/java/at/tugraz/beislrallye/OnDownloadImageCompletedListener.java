package at.tugraz.beislrallye;

import android.graphics.Bitmap;

/**
 * Created by matthias on 06.01.15.
 */
public interface OnDownloadImageCompletedListener {
    public <T> void onTaskCompleted(Bitmap result);
}