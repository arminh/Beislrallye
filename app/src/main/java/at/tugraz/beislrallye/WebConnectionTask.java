package at.tugraz.beislrallye;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by matthias on 08.03.15.
 */
public class WebConnectionTask extends AsyncTask<HashMap<String, String>, Void, String> {
    private static final String LOG_TAG = "WebConnectionTask";

            private OnWebConnectionTaskCompletedListener listener;
            private String url;

            public WebConnectionTask(String url, OnWebConnectionTaskCompletedListener listener){
                this.listener = listener;
                this.url = url;
            }

            @Override
            protected String doInBackground(HashMap<String, String>... params) {
                Log.e(LOG_TAG, "doInBackground");
                if(url == null || url == "")
                    return null;
                try {
                    Log.e(LOG_TAG, "url = " + url);
                    String result = "";
                    InputStream is;
                    try{
                        DefaultHttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost(url);

                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        HttpEntity httpEntity = httpResponse.getEntity();
                        is = httpEntity.getContent();

                        result = convertToString(is);
                        Log.e(LOG_TAG, "result = " + result);
                        return result;
                    }catch(Exception e){
                        Log.e(LOG_TAG, "Error converting result "+e.toString());
                        return null;
                    }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in async task "+e.toString());
            return null;
        }
    }

    private String convertToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }

        String json = sb.toString();
        is.close();
        return json;
    }

    protected void onPostExecute(String result) {
        listener.onTaskCompleted(result);
    }
}