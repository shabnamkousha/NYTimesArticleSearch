package lab.com.nytimesarticlesearch;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Shabnam on 2/12/16.
 */
public class Filters implements Serializable {

    String quote;

    String sortOrder;

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getQuote() {
        return quote;
    }

    public Filters(String aQuote, String aSortOrder){

        this.setQuote(aQuote);
        this.setSortOrder(aSortOrder);

    }
}
