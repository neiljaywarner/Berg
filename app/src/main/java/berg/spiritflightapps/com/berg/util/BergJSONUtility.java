package berg.spiritflightapps.com.berg.util;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import berg.spiritflightapps.com.berg.BergTest;


public class BergJSONUtility {


    public String toJSON(BergTest[] bergTestArray) {
        String json = null;

        try {
            JSONObject bergTests = new JSONObject();

            JSONArray testArray = new JSONArray();

            for (BergTest bergTest : bergTestArray) {
                JSONObject testObject = new JSONObject();
                testObject.put("name", bergTest.getName());
                JSONArray scoreArray = new JSONArray();
                for (Integer score : bergTest.getScores()) {
                    scoreArray.put(score);
                }
                testObject.put("scores", scoreArray);
                testArray.put(testObject);
            }

            bergTests.put("bergTests", testArray);

            json = bergTests.toString();

        } catch (JSONException je) {
            je.printStackTrace();
        }
        System.out.println("To JSON: " + json);
        return json;
    }


    public BergTest[] fromJSON(String json) {
        ArrayList<BergTest> bergTests = new ArrayList();
        System.out.println("From JSON: " + json);
        try {
            JSONObject obj = (JSONObject) new JSONTokener(json).nextValue();
            JSONArray testArray = (JSONArray) obj.get("bergTests");

            for (int i = 0; i<testArray.length(); ++i) {

                JSONObject testObject = testArray.getJSONObject(i);

                String testName = (String) testObject.get("name");
                ArrayList<Integer> scores = new ArrayList();

                JSONArray scoresArray = (JSONArray) testObject.get("scores");

                for(int j = 0; j<scoresArray.length(); ++j) {
                    Integer score = scoresArray.getInt(j);
                    scores.add(score);
                }

                BergTest test = new BergTest(testName, scores.toArray(new Integer[]{}));
                bergTests.add(test);
            }

            return bergTests.toArray(new BergTest[]{});

        } catch (JSONException je) {
            je.printStackTrace();
        }

        return null;
    }

}

