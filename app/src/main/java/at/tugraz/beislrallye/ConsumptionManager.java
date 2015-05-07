package at.tugraz.beislrallye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    public HashMap<String, Integer> getAllConsumptions() {
        HashMap<String, Integer> allConsumptions = new HashMap<>();
        Iterator it = consumptions.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            ArrayList<Consumption> list = (ArrayList<Consumption>) pair.getValue();
            for(Consumption consumption : list) {
                if(!allConsumptions.containsKey(consumption.getName())) {
                    allConsumptions.put(consumption.getName(), new Integer(0));
                }
                allConsumptions.put(consumption.getName(), allConsumptions.get(consumption.getName()) + 1);
            }
        }
        return allConsumptions;
    }

    public void reset() {
        consumptions = new HashMap<>();
    }
}
