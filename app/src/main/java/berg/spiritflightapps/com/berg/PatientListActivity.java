package berg.spiritflightapps.com.berg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class PatientListActivity extends AppCompatActivity {

    EditText addNewPatient;
    ListView list;
    PatientListAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        list = (ListView)findViewById(R.id.list);

        adapter = new PatientListAdapter(this);
        adapter.readData();

        list.setAdapter(adapter);

        final Intent myIntent=new Intent(this, TestListActivity.class);

        // Click event for single gridView row
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                myIntent.putExtra("patientId", String.valueOf(adapter.patientAt(position)));
                startActivity(myIntent);
            }
        });

        list.requestFocus();

        addNewPatient = (EditText) findViewById(R.id.patientName);
        addNewPatient.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    EditText textField = (EditText) findViewById(R.id.patientName);
                    adapter.addPatient(textField.getText().toString());
                    textField.setText("");
                    handled = true;
                }
                return handled;
            }
        });

        registerForContextMenu(list);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete_item:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int position = (int) info.id;
                adapter.deletePatient(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

}