package at.tugraz.beislrallye;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SummaryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        getSupportActionBar().hide();
        LinearLayout layout = (LinearLayout) findViewById(R.id.summary_layout);
        TextView start = (TextView) findViewById(R.id.start_tv);
        TextView end = (TextView) findViewById(R.id.end_tv);
        TextView duration = (TextView) findViewById(R.id.duration_tv);

        start.setText(getResources().getString(R.string.start) + " " + new SimpleDateFormat("HH:mm dd:MM:yyyy").format(RalleyStatisticsManager.getInstance().getStartTime()));
        end.setText(getResources().getString(R.string.end) + " " + new SimpleDateFormat("HH:mm dd:MM:yyyy").format(RalleyStatisticsManager.getInstance().getEndTime()));
        long diff = RalleyStatisticsManager.getInstance().getEndTime().getTime() - RalleyStatisticsManager.getInstance().getStartTime().getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        String secStr = diffSeconds < 10 ? "0" + diffSeconds : "" + diffSeconds;
        String minStr = diffMinutes < 10 ? "0" + diffMinutes : "" + diffMinutes;
        String hrStr = diffHours < 10 ? "0" + diffHours : "" + diffHours;

        duration.setText(getResources().getString(R.string.duration) + " " + hrStr + ":" + minStr + ":" + secStr);

        HashMap<String, Integer> consumptions = ConsumptionManager.getInstance().getAllConsumptions();
        Iterator it = consumptions.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            TextView text = new TextView(this);
            text.setTextAppearance(this, android.R.style.TextAppearance_Medium);
            text.setTextColor(getResources().getColor(android.R.color.secondary_text_light));
            text.setText(pair.getKey() + ": " + pair.getValue());
            layout.addView(text);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summary, menu);
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
