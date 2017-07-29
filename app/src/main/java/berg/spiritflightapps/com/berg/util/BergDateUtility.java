package berg.spiritflightapps.com.berg.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BergDateUtility {

    final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";

    public String getNow() {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return sdf.format(new Date());
    }
}
