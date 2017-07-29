package berg.spiritflightapps.com.berg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import berg.spiritflightapps.com.berg.data.DatabaseHandler;
import berg.spiritflightapps.com.berg.util.BergDateUtility;

public class PatientListAdapter extends BaseAdapter {

    private Context context;
    private static LayoutInflater inflater=null;
    private ArrayList<Patient> patients = new ArrayList<Patient>();
    DatabaseHandler db;


    public PatientListAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = new DatabaseHandler(context);
        readData();
    }

    public int getCount() {
        return patients.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi;

        vi = inflater.inflate(R.layout.patient_list_row, null);

        TextView patientNameView = (TextView) vi.findViewById(R.id.testname);

        patientNameView.setText(patients.get(position).getName());

        return vi;
    }

    public void addPatient(String name) {

        Integer[] tests = new Integer[14];
        for(int i=0; i<tests.length; ++i) {
            tests[i] = 0;
        }
        Patient patient = new Patient();
        patient.setName(name);
        patient.setBergTests(new ArrayList<BergTest>());
        patient.getBergTests().add(new BergTest(new BergDateUtility().getNow(), tests));

        db.addPatient(patient);
        readData();
        notifyDataSetChanged();
    }

    public long patientAt(int position) {
        return patients.get(position).getId();
    }

    public void deletePatient(int position) {
        String name = patients.get(position).getName();
        Patient patient = patients.get(position);
        patients.remove(position);
        notifyDataSetChanged();
        db.deletePatient(patient);
        Toast.makeText(context, "Patient '" + name + "' deleted.", Toast.LENGTH_LONG).show();
    }

    public void readData() {
        patients.clear();
        patients = new ArrayList<Patient>(db.getAllPatients());
    }



}
