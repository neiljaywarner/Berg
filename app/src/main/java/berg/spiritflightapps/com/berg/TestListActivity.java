package berg.spiritflightapps.com.berg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import berg.spiritflightapps.com.berg.util.BergDateUtility;


public class TestListActivity extends AppCompatActivity {

    ListView list;
    TestListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        EditText testNameField = (EditText) findViewById(R.id.testName);
        EditText patientNameField = (EditText) findViewById(R.id.patientName);
        patientNameField.setKeyListener(null); // make patient name non-editable

        Intent intent = getIntent();
        String patientId = intent.getStringExtra("patientId");

        adapter = new TestListAdapter(this, testNameField, Long.parseLong(patientId));
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        patientNameField.setText(adapter.getPatientName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_new:
                adapter.addTest(new BergDateUtility().getNow());
                return true;

            case R.id.action_next:
                adapter.goToNextTest();
                return true;

            case R.id.action_previous:
                adapter.goToPreviousTest();
                return true;

            case R.id.action_remove:
                adapter.deleteTest();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    protected void onPause() {
        super.onPause();
        adapter.saveTests();
    }



}