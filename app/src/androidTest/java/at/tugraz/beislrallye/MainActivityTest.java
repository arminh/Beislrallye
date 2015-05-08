package at.tugraz.beislrallye;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Matthias on 07.05.2015.
 */
    public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Activity activity;
    private ListView listView;
    private EditText editText;
    private Button startActivityButton;

    public MainActivityTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        activity = getActivity();
        startActivityButton = (Button) activity.findViewById(R.id.compute_ralley_button);
        editText = (EditText)activity.findViewById(R.id.location_count);
        listView = ((MainActivity)activity).getListView();
    }

    @Override
    public void tearDown() {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                editText.setText("");
                listView.setItemChecked(0,false);
            }
        });
        activity.finish();
    }

    public void testStartActivityNotChecked() {
        Instrumentation.ActivityMonitor monitor =
                getInstrumentation().
                        addMonitor(MapsActivity.class.getName(), null, false);

        activity.runOnUiThread(new Runnable() {

            public void run() {
                editText.setText("5");
            }
        });

        TouchUtils.clickView(this, startActivityButton);

        MapsActivity startedActivity = (MapsActivity) monitor.waitForActivityWithTimeout(2000);
        assertNull(startedActivity);
        getInstrumentation().removeMonitor(monitor);
    }

    public void testStartActivityNoNumber() {
        Instrumentation.ActivityMonitor monitor =
                getInstrumentation().
                        addMonitor(MapsActivity.class.getName(), null, false);

        activity.runOnUiThread(new Runnable() {

            public void run() {
                editText.setText("");
                listView.setItemChecked(0,true);
            }
        });

        TouchUtils.clickView(this, startActivityButton);

        MapsActivity startedActivity = (MapsActivity) monitor.waitForActivityWithTimeout(2000);
        assertNull(startedActivity);
        getInstrumentation().removeMonitor(monitor);
    }

    public void testStartActivity() {
        Instrumentation.ActivityMonitor monitor =
                getInstrumentation().
                        addMonitor(MapsActivity.class.getName(), null, false);

        activity.runOnUiThread(new Runnable() {

            public void run() {
                editText.setText("5");
                listView.setItemChecked(0,true);
                //listView.setItemChecked(1, true);
            }
        });

        TouchUtils.clickView(this, startActivityButton);

        MapsActivity startedActivity = (MapsActivity) monitor.waitForActivityWithTimeout(2000);
        assertNotNull(startedActivity);
        startedActivity.finish();
        getInstrumentation().removeMonitor(monitor);
    }

}
