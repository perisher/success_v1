package com.success_v1.agence;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import com.example.list.R;
import com.success_v1.res.JSONParser;

public class ReservationStep1 extends Activity{
	int year;
	int month;
	int day;
	String pid;
	String dateDepart;
	String dateRetour;
	Button btnAgenceDepart;
	String nomAgence;
	/***********************************dsdsdsssssssssssssssssssssssssss**********************/
	String date;
	RadioGroup radioGroup;
	private Button btnDateDepart;
	private Button btnDateRetour;
	private Button btnSearchCar;
	private int yearDepart;
	private int monthDepart;
	private int dayRetour;
	private int yearRetour;
	private int monthRetour;
	private int dayDepart;
	private int nbBtn;
	static final int DATE_DIALOG_DEPART = 999;
	static final int DATE_DIALOG_RETOUR = 899;
	 // Progress Dialog
    private ProgressDialog pDialog;
 
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    JSONParser jParser = new JSONParser();
 
    // JSON Node names
    private static String url_detail = "http://10.0.3.2/Success2i_V1/get_agence_detail.php";
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_TAB = "agence_tab";
    private static final String TAG_ID = "id";
    private static final String TAG_NOM = "nom";
    private static final String TAG_ADRESSE ="adresse";
    private static final String TAG_TELEPHONE = "telephone";
    private static final String TAG_FAX = "fax";
    private static final String TAG_MAIL = "mail";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_LONGITUDE = "longitude";
       
    JSONObject detail_tab = new JSONObject();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reservation_step1);
		
        Intent result = getIntent();
		pid = result.getStringExtra("re_id");
		
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

		
		btnDateDepart = (Button) findViewById(R.id.btnDateDeb);
		btnDateRetour = (Button) findViewById(R.id.btnDateRetour);
		btnSearchCar = (Button) findViewById(R.id.btnSearchCar);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		
		date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		btnDateDepart.setText(date);
		btnDateRetour.setText(date);
		btnDateDepart.setOnClickListener(new View.OnClickListener(){
	   	      @Override
	   	      public void onClick(View v) {
	   	    	nbBtn=1;
	   	    	showDialog(DATE_DIALOG_DEPART);
	   	      }
	   	    });
		btnDateRetour.setOnClickListener(new View.OnClickListener(){
	   	      @Override
	   	      public void onClick(View v) {
	   	    	nbBtn =2;
	   	    	showDialog(DATE_DIALOG_RETOUR);
	   	      }
	   	    });
		 btnSearchCar.setOnClickListener(new View.OnClickListener(){
   	      @Override
   	      public void onClick(View v) {
   	    	  Intent listCarActivity = new Intent(getBaseContext(), ReservationStep2.class);     
   	    	  listCarActivity.putExtra("idAgence", pid);
   	    	  
   			// Parse the input date
   			SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
   			Date inputDate = null;
   			try {
   				inputDate = fmt.parse(btnDateDepart.getText().toString());
   			} catch (ParseException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
   			// Create the MySQL datetime string
   			fmt = new SimpleDateFormat("yyyy-MM-dd");
   			dateDepart = fmt.format(inputDate);
   			
   			/*************/
   			// Parse the input date
   			SimpleDateFormat fmtt = new SimpleDateFormat("dd-MM-yyyy");
   			Date inputDateRet = null;
   			try {
   				inputDateRet = fmtt.parse(btnDateRetour.getText().toString());
   			} catch (ParseException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
   			// Create the MySQL datetime string
   			fmtt = new SimpleDateFormat("yyyy-MM-dd");
   			dateRetour= fmtt.format(inputDateRet);
   	    	  
   	    	listCarActivity.putExtra("dateDepart", dateDepart);
   	    	listCarActivity.putExtra("dateRetour", dateRetour);
   	    	int id = radioGroup.getCheckedRadioButtonId();
   	    	    if (id == R.id.rdioTourisme){
   	    	    	listCarActivity.putExtra("typeVehicule", "Tourisme");
   	    	    }
   	    	    else if (id==R.id.rdioUtil)
   	    	    {
   	    	    	listCarActivity.putExtra("typeVehicule", "Utilitaire");
   	    	    }
   	  	      startActivity(listCarActivity);
   	      }
   	    });
		nomAgence = result.getStringExtra("nomAgence");
		//btnAgenceDepart = (Button)findViewById(R.id.btnAgenceDepart);
		btnAgenceDepart.setText(nomAgence);
		btnAgenceDepart.setOnClickListener(new View.OnClickListener(){
   	      @Override
   	      public void onClick(View v) {
   	    	  Intent listAgenceActivity = new Intent(getBaseContext(), ListAgences.class);     
   	  	      startActivity(listAgenceActivity);
   	      }
   	    });
		
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_DEPART:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, year, month,day);
		case DATE_DIALOG_RETOUR:
			   // set date picker as current date
			   return new DatePickerDialog(this, datePickerListener, year, month,day);
		}
		
		return null;
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			switch (nbBtn)
			{
			case 1:
				yearDepart = selectedYear;
				monthDepart = selectedMonth;
				dayDepart = selectedDay;
				// set selected date into textview
							btnDateDepart.setText(new StringBuilder().append(dayDepart).append("-").append(monthDepart + 1).append("-").append(yearDepart).append(" "));
				break;
			case 2:
				yearRetour = selectedYear;
				monthRetour = selectedMonth;
				dayRetour = selectedDay;
				// set selected date into textview
							btnDateRetour.setText(new StringBuilder().append(dayRetour).append("-").append(monthRetour + 1).append("-").append(yearRetour).append(" "));
				break;
			}
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
