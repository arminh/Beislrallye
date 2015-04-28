package at.tugraz.beislrallye;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by matthias on 06.01.15.
 */
public interface OnWebConnectionTaskCompletedListener {
    public <T> void onTaskCompleted(String result);
}

