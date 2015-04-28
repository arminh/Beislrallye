package at.tugraz.beislrallye;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Matthias on 10.04.2015.
 */
public class RalleyStatisticsManager {
    private static RalleyStatisticsManager ourInstance = new RalleyStatisticsManager();

    public static RalleyStatisticsManager getInstance() {
        return ourInstance;
    }

    private HashMap<String, String> checkouts;
    private Date startTime;
    private Date endTime;

    private RalleyStatisticsManager() {
        checkouts = new HashMap<>();
    }

    public void addCheckout(String place, String time) {
        checkouts.put(place, time);
    }

    public HashMap<String, String> getAllCheckouts() {
        return checkouts;
    }

    public String getCheckoutByPlace(String place) {
        return checkouts.get(place);
    }

    public void setStartTime(Date start) {
        startTime = start;
    }

    public void setEndTime(Date end) {
        endTime = end;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
