package lab.com.nytimesarticlesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    EditText etQuery;
    GridView gvResults;
    Button btnSearch;
    ArrayList<Article> articls;
    ArticleArrayAdapter adapter;
    Filters filters = new Filters("","newest");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Toast.makeText(this, "Result:" + filters.getQuote(), Toast.LENGTH_LONG).show();

        setupViews();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent i = new Intent(SearchActivity.this, SettingActivity.class);

            i.putExtra("filters", filters);

            startActivityForResult(i, 1);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupViews(){
        etQuery= (EditText) findViewById(R.id.etQuery);
        gvResults= (GridView) findViewById(R.id.gvResults);
        btnSearch=(Button) findViewById(R.id.btnSearch);
        articls= new ArrayList<>();
        adapter= new ArticleArrayAdapter(this, articls);
        gvResults.setAdapter(adapter);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                Article article = articls.get(position);
                i.putExtra("url", article.getWebUrl());
                startActivity(i);
            }
        });

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
    }
    public void customLoadMoreDataFromApi(int offset) {
        String query=etQuery.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();

        String url="http://api.nytimes.com/svc/search/v2/articlesearch.json";

        String sortOrder=filters.getSortOrder();
        String fq=filters.getQuote();
        Calendar calendar=filters.getBeginDate();


        RequestParams params=new RequestParams();
        params.put("api-key","44df8b918e4cde4c7dcfbd6473346627:7:74373173");
        params.put("page", offset);
        params.put("sort",sortOrder.toLowerCase());
        if(calendar!=null) {
            String calendarYear = String.valueOf(calendar.get(Calendar.YEAR));
            String calendarMonth = String.format("%02d", calendar.get(Calendar.MONTH)+1);
            String calendarDay = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
            params.put("begin_date",calendarYear+calendarMonth+calendarDay);
            // Toast.makeText(this, "After: " +calendarMonth, Toast.LENGTH_LONG).show();
        }

        if(fq.length()>0) {
            params.put("fq", "news_desk:(" + fq + ")");
        }
        params.put("q", query);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray articleJsonResults = null;
                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    //articls.clear();
                    articls.addAll(Article.fromJsonArray(articleJsonResults));
                    adapter.notifyDataSetChanged();
                    Log.d("DEBUG", articls.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void onArticleSearch(View view) {
        String query=etQuery.getText().toString();
        Toast.makeText(this, "Result:" + query, Toast.LENGTH_LONG).show();
        AsyncHttpClient client = new AsyncHttpClient();

        String url="http://api.nytimes.com/svc/search/v2/articlesearch.json";

        String sortOrder=filters.getSortOrder();
        String fq=filters.getQuote();
        Calendar calendar=filters.getBeginDate();


        RequestParams params=new RequestParams();
        params.put("api-key","44df8b918e4cde4c7dcfbd6473346627:7:74373173");
        params.put("page","0");
        params.put("sort",sortOrder.toLowerCase());
        if(calendar!=null) {
            String calendarYear = String.valueOf(calendar.get(Calendar.YEAR));
            String calendarMonth = String.format("%02d", calendar.get(Calendar.MONTH)+1);
            String calendarDay = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
            params.put("begin_date",calendarYear+calendarMonth+calendarDay);
           // Toast.makeText(this, "After: " +calendarMonth, Toast.LENGTH_LONG).show();
        }

        if(fq.length()>0) {
           params.put("fq", "news_desk:(" + fq + ")");
        }
        params.put("q", query);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray articleJsonResults = null;
                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    articls.clear();
                    articls.addAll(Article.fromJsonArray(articleJsonResults));
                    adapter.notifyDataSetChanged();
                    Log.d("DEBUG", articls.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }




    @Override
    protected void onActivityResult(int aRequestCode, int aResultCode, Intent aDataSet) {
        // REQUEST_CODE is defined above
        if (aResultCode == RESULT_OK && aRequestCode == 1) {

            filters = (Filters) aDataSet.getExtras().getSerializable("new_filters");

           // Toast.makeText(this, "After: " + filters.getSortOrder(), Toast.LENGTH_LONG).show();

        }
    }



}
