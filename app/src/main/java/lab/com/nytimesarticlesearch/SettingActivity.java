package lab.com.nytimesarticlesearch;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Spinner sortOrder;
    Filters filters;
    ArrayAdapter<CharSequence> aSortAdapter;
    CheckBox cbArts;
    CheckBox cbFashin;
    CheckBox cbSports;
    TextView tvDate;

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
        tvDate=  (TextView) findViewById(R.id.tvDate);

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
        Calendar calendar=filters.getBeginDate();
        if(calendar!=null) {
            String calendarYear =String.valueOf(calendar.get(Calendar.YEAR));
            String calendarMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            String calendarDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            tvDate.setText(calendarMonth+"/"+ calendarDay + "/"+calendarYear) ;
            // Toast.makeText(this, "After: " +calendarMonth, Toast.LENGTH_LONG).show();
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

    // attach to an onclick handler to show the date picker
    public void showTimePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String chosenDate=String.valueOf(monthOfYear+1)+"/"+String.valueOf(dayOfMonth)+"/"+String.valueOf(year);
        tvDate.setText(chosenDate);
        filters.setBeginDate(c);
    }

}
