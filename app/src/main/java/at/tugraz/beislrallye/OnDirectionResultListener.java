package at.tugraz.beislrallye;

import org.w3c.dom.Document;

import java.io.InputStream;

/**
 * Created by matthias on 06.01.15.
 */
public interface OnDirectionResultListener {
    public <T> void onTaskCompleted(Document result);
}
