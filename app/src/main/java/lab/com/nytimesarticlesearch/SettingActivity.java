package lab.com.nytimesarticlesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    Spinner sortOrder;
    Filters filters;
    ArrayAdapter<CharSequence> aSortAdapter;
    CheckBox cbArts;
    CheckBox cbFashin;
    CheckBox cbSports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String fq;

        sortOrder = (Spinner) findViewById(R.id.spSort);
        cbArts=  (CheckBox) findViewById(R.id.cbArts);
        cbFashin=  (CheckBox) findViewById(R.id.cbFashin);
        cbSports=  (CheckBox) findViewById(R.id.cbSports);

        aSortAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_array, android.R.layout.simple_spinner_item);
        aSortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortOrder.setAdapter(aSortAdapter);

        //Toast.makeText(this, "Result:" + query, Toast.LENGTH_LONG).show();
        filters = (Filters) getIntent().getSerializableExtra("filters");
        //Toast.makeText(this, "in activity: " + filters.getQuote(), Toast.LENGTH_LONG).show();


        int spinnerPosition = aSortAdapter.getPosition(filters.getSortOrder());

        //set the default according to value
        sortOrder.setSelection(spinnerPosition);

        fq=filters.getQuote();
        if(fq.contains("Art")){
            cbArts.setChecked(true);
        }
        if(fq.contains("Sport")){
            cbSports.setChecked(true);
        }
        if(fq.contains("Fashion")){
            cbFashin.setChecked(true);
        }



    }
    public void onFilterSubmit(View view) {
        String fq="";

        if(cbArts.isChecked()) {
            fq="\"Arts\"";
        }

        if(cbFashin.isChecked()) {
            fq=fq.length()>0?fq+" \"Fashion & Style\"":"\"Fashion & Style\"";
        }

        if(cbSports.isChecked()) {
            fq=fq.length()>0?fq+" \"Sports\"":"\"Sports\"";
        }

        filters.setQuote(fq);
        String chosenSortOrder= sortOrder.getSelectedItem().toString();

        filters.setSortOrder(chosenSortOrder);
        Intent data = new Intent();

        data.putExtra("new_filters", filters);

        setResult(RESULT_OK, data);
        finish();
    }

}
