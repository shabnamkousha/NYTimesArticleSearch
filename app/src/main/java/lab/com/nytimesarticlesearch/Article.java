package lab.com.nytimesarticlesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shabnam on 2/11/16.
 */
public class Article {
    String webUrl;
    String headline;

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    String thumbnail;

    public Article(JSONObject jsonObject){

        try{
            this.webUrl = jsonObject.getString("web_url");
            this.headline = jsonObject.getJSONObject("headline").getString("main");

            JSONArray multimedia=jsonObject.getJSONArray("multimedia");
            if(multimedia.length()>0){
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumbnail="http://www.nytimes.com/"+multimediaJson.getString("url");

            } else {
                this.thumbnail="";
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    public static ArrayList fromJsonArray ( JSONArray array){
        ArrayList <Article> results= new ArrayList<>();
        for(int x=0; x<array.length();x++){
            try {
                results.add(new Article(array.getJSONObject(x)));
            } catch(JSONException e){
                e.printStackTrace();
            }
        }

        return results;
    }

}
