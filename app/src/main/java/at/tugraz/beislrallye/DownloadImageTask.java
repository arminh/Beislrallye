package at.tugraz.beislrallye;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by armin on 27.04.15.
 */
public class DownloadImageTask extends AsyncTask<HashMap<String, String>, Void, Bitmap> {

    private static final String LOG_TAG = "DownloadImageTask";
    OnDownloadImageCompletedListener listener;
    String url;

    ImageView imageView = null;

    public DownloadImageTask(String url, OnDownloadImageCompletedListener listener) {
        this.listener = listener;
        this.url = url;
    }

    @Override
    protected Bitmap doInBackground(HashMap<String, String>... params) {

        if(url == null || url == "")
            return null;
        try {
            Bitmap result;
            InputStream is;
            try{
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

                result = BitmapFactory.decodeStream(is);
                return result;
            }catch(Exception e){
                Log.e(LOG_TAG, "Error converting result " + e.toString());
                return null;
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in async task "+e.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        listener.onTaskCompleted(result);
    }
}
