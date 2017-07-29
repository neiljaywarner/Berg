package berg.spiritflightapps.com.berg.data;


public class PatientRow {

    private long _id;
    private String _name;
    private String _testdata;
    private long _timestamp;

    public PatientRow() {

    }

    public PatientRow(String name, String testdata){
        this._name = name;
        this._testdata = testdata;
        this._timestamp = System.currentTimeMillis();
    }

    public long getID(){
        return this._id;
    }


    public void setID(long id){
        this._id = id;
    }


    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getTestData(){
        return this._testdata;
    }

    public void setTestData(String testData){
        this._testdata = testData;
    }


    public long getTimestamp() {
        return _timestamp;
    }

    public void setTimestamp(long timestamp) {
        this._timestamp = timestamp;
    }


}
