package at.tugraz.beislrallye;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Matthias on 27.03.2015.
 */
public class ConsumptionManagerTest extends TestCase {
    public void testAddConsumption() {
        String place = "MyPlace";
        ArrayList<Consumption> consumptions = ConsumptionManager.getInstance().getAllConsumptionsByPlace(place);
        assertEquals(0,consumptions.size());
        Consumption consumption = new Consumption("Beer");
        ConsumptionManager.getInstance().addConsumption(place, consumption);
        consumptions = ConsumptionManager.getInstance().getAllConsumptionsByPlace(place);
        assertEquals(1, consumptions.size());
        assertEquals( "Beer", consumptions.get(0).getName());
    }

    public void testGetAllConsumption() {
        ConsumptionManager.getInstance().reset();
        String place = "MyPlace";
        String place2 = "MySecondPlace";
        Consumption consumption = new Consumption("Beer");
        ConsumptionManager.getInstance().addConsumption(place, consumption);
        ConsumptionManager.getInstance().addConsumption(place, consumption);
        ConsumptionManager.getInstance().addConsumption(place, consumption);
        ConsumptionManager.getInstance().addConsumption(place2, consumption);
        ConsumptionManager.getInstance().addConsumption(place2, consumption);
        HashMap<String, Integer> allConsumptions = ConsumptionManager.getInstance().getAllConsumptions();
        assertNotNull(allConsumptions.get("Beer"));
        assertEquals((Integer)5, allConsumptions.get("Beer"));
    }

    public void testReset() {
        ConsumptionManager.getInstance().reset();
        String place = "MyPlace";
        Consumption consumption = new Consumption("Beer");
        ConsumptionManager.getInstance().addConsumption(place, consumption);
        ConsumptionManager.getInstance().addConsumption(place, consumption);
        ArrayList<Consumption> consumptions = ConsumptionManager.getInstance().getAllConsumptionsByPlace(place);
        assertEquals(2, consumptions.size());
        ConsumptionManager.getInstance().reset();
        consumptions = ConsumptionManager.getInstance().getAllConsumptionsByPlace(place);
        assertEquals(0,consumptions.size());
    }
}
