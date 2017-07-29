package berg.spiritflightapps.com.berg;

import java.util.ArrayList;

public class Patient {

    private Long id = -1L;

    private String name;

    private ArrayList<BergTest> bergTests;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<BergTest> getBergTests() {
        return bergTests;
    }

    public void setBergTests(ArrayList<BergTest> bergTests) {
        this.bergTests = bergTests;
    }


}
