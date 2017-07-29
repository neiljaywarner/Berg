package berg.spiritflightapps.com.berg.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import berg.spiritflightapps.com.berg.BergTest;
import berg.spiritflightapps.com.berg.Patient;
import berg.spiritflightapps.com.berg.util.BergJSONUtility;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    private static final String DATABASE_NAME = "patientsManager";

    private static final String TABLE_PATIENTS = "patients";

    private static final String KEY_ID = "id";
    private static final String KEY_PATIENT_NAME = "name";
    private static final String KEY_TEST_DATA = "companyname";
    private static final String KEY_TIMESTAMP = "timestamp";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STOCKS_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_PATIENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_PATIENT_NAME + " TEXT,"
                + KEY_TEST_DATA + " TEXT,"
                + KEY_TIMESTAMP + " INTEGER " + ")";
        db.execSQL(CREATE_STOCKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.err.println("Updating Database to version: " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
        onCreate(db);
    }

    public void addPatient(Patient patient) {
        PatientRow patientRow = toPatientRow(patient);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PATIENT_NAME, patientRow.getName());
        values.put(KEY_TEST_DATA, patientRow.getTestData());
        values.put(KEY_TIMESTAMP, patientRow.getTimestamp());

        long rowId = db.insert(TABLE_PATIENTS, null, values);
        patientRow.setID(rowId);
        patient.setId(rowId);
        db.close(); // Closing database connection
    }

    public void savePatient(Patient patient) {
        PatientRow patientRow = toPatientRow(patient);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PATIENT_NAME, patientRow.getName());
        values.put(KEY_TEST_DATA, patientRow.getTestData());
        values.put(KEY_TIMESTAMP, patientRow.getTimestamp());
        db.update(TABLE_PATIENTS, values, "id = " + patientRow.getID(), null );

        db.close(); // Closing database connection
    }

    public List<Patient> getAllPatients() {
        List<Patient> patientList = new ArrayList<Patient>();

        String selectQuery = "SELECT "
                + KEY_ID + ","
                + KEY_PATIENT_NAME + ","
                + KEY_TEST_DATA + ","
                + KEY_TIMESTAMP
                + " FROM " + TABLE_PATIENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PatientRow patientRow = new PatientRow();
                patientRow.setID(Long.parseLong(cursor.getString(0)));
                patientRow.setName(cursor.getString(1));
                patientRow.setTestData(cursor.getString(2));
                patientRow.setTimestamp(Long.parseLong(cursor.getString(3)));

                patientList.add(toPatient(patientRow));
            } while (cursor.moveToNext());
        }

        return patientList;
    }

    public Patient getPatient(long id) {
        for (Patient patient : getAllPatients()) {
            if(patient.getId() == id) {
                return patient;
            }
        }
        return null;
    }

    public void deletePatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PATIENTS, "id = " + patient.getId(), null);
        db.close();
    }

    public void deleteAllRows() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PATIENTS, null, null);
        db.close();
    }


    private PatientRow toPatientRow (Patient patient) {
        PatientRow patientRow = new PatientRow();
        patientRow.setID(patient.getId());
        patientRow.setName(patient.getName());
        patientRow.setTestData(new BergJSONUtility().toJSON(patient.getBergTests().toArray(new BergTest[]{})));
        return patientRow;
    }

    private Patient toPatient(PatientRow patientRow) {
        Patient patient = new Patient();
        patient.setId(patientRow.getID());
        patient.setName(patientRow.getName());
        patient.setBergTests(new ArrayList<BergTest>(Arrays.asList(new BergJSONUtility().fromJSON(patientRow.getTestData()))));

        return patient;
    }

}