package at.tugraz.beislrallye;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;
import android.widget.TextView;

/**
 * Created by Matthias on 07.05.2015.
 */
    public class PlacesPreviewActivityTest extends ActivityInstrumentationTestCase2<PlacesPreviewActivity> {
    private Activity activity;
    private Context context;
    public PlacesPreviewActivityTest(Class<PlacesPreviewActivity> activityClass) {
        super(activityClass);
    }

    public PlacesPreviewActivityTest() {
        super(PlacesPreviewActivity.class);
    }

    @Override
    public void setUp() {
        Intent intent = new Intent();
        Place place = new Place("123", 47.055, 42.1212, "TestName", "TestAddress", "456");
        intent.putExtra("place", place);
        setActivityIntent(intent);
        activity = getActivity();
        //context = new RenamingDelegatingContext(getActivity(), "test_");
    }

    public void testLayoutIsSet() {
        TextView placeName = (TextView) ((PlacesPreviewActivity)activity).findViewById(R.id.placeName);
        assertNotNull(placeName);
        assertEquals("TestName", "" + placeName.getText());

        TextView addressName = (TextView) ((PlacesPreviewActivity)activity).findViewById(R.id.placeAddress);
        assertEquals("TestAddress", "" + addressName.getText());
    }
}
