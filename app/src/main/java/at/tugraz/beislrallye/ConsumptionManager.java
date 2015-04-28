package at.tugraz.beislrallye;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Matthias on 10.04.2015.
 */
public class ConsumptionManager {
    private static ConsumptionManager ourInstance = new ConsumptionManager();

    public static ConsumptionManager getInstance() {
        return ourInstance;
    }

    private HashMap<String, ArrayList<Consumption>> consumptions;

    private ConsumptionManager() {
        consumptions = new HashMap<>();
    }

    public void addConsumption(String place, Consumption consumption) {
        if(!consumptions.containsKey(place)) {
            consumptions.put(place, new ArrayList<Consumption>());
        }
        consumptions.get(place).add(consumption);
    }

    public ArrayList<Consumption> getAllConsumptionsByPlace(String place) {
        if(consumptions.containsKey(place)) {
            return consumptions.get(place);
        }
        return new ArrayList<Consumption>();
    }
}
