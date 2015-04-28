package at.tugraz.beislrallye;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;


public class ConsumptionActivity extends ActionBarActivity {
    private String place = null;
    private ListView consumptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);
        Intent i = getIntent();
        place = i.getExtras().getString("PLACE_NAME");
        consumptionList = (ListView) findViewById(R.id.consumption_lv);
        updateListView();
    }

    private void updateListView() {
        ArrayList<Consumption> placeConsumptions = ConsumptionManager.getInstance().getAllConsumptionsByPlace(place);
        ArrayList<String> consumptionsAsString = new ArrayList<>();
        for(Consumption consumption : placeConsumptions) {
            consumptionsAsString.add(consumption.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, consumptionsAsString);
        consumptionList.setAdapter(adapter);
    }

    public void fabClicked(View v){
        if(v.getClass().equals(FloatingActionButton.class)) {
            FloatingActionButton button = (FloatingActionButton) v;
            ((FloatingActionsMenu) findViewById(R.id.floatingActionMenu)).collapse();
            ConsumptionManager.getInstance().addConsumption(place, new Consumption(button.getTitle()));
            updateListView();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_consumption, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
