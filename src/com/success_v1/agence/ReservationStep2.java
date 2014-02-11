package com.success_v1.agence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.list.R;
import com.success_v1.res.JSONParser;
import com.success_v1.vehicule.AdapterVehicule;
import com.success_v1.vehicule.Vehicule;

public class ReservationStep2 extends Activity {

	private ProgressDialog pDialog;

	JSONParser jParser = new JSONParser();	
	JSONParser jsonParser = new JSONParser();
	JSONObject vehicule_tab = new JSONObject();
	ArrayList<Vehicule> vehiculelist;
	JSONArray jsonTab = null;
	String idAgence;
	String typeVehicule;
	String dateDepart;
	String dateRetour;
	String dateSqlDepart=null;
	String dateSqlRetour=null;
	private ListView lv;

	private static String url_all = "http://10.0.3.2/Success2i_V1/get_vehicule_detail.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TAB = "tab_vehicule";
	private static final String TAG_ID = "id";
	private static final String TAG_MARQUE = "marque";
	private static final String TAG_MODELE = "modele";
	private static final String TAG_MOTORISATION = "motorisation";
	private static final String TAG_TARIF = "tarifJour";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicule_list);

		Intent result = getIntent();
		typeVehicule = result.getStringExtra("typeVehicule");
		idAgence = result.getStringExtra("idAgence");
		dateDepart = result.getStringExtra("dateDepart");
		dateRetour=result.getStringExtra("dateRetour");

		
		Log.i("Date depart", dateDepart);
		Log.i("Date retoure", dateRetour);

		vehiculelist = new ArrayList<Vehicule>();	
		new LoadAll().execute();

		lv = (ListView)findViewById(R.id.listVehicule);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				/*Vehicule idtest = new Vehicule();
				idtest = (Vehicule) lv.getAdapter().getItem(arg2);

				String id = idtest.id.ToString();

				Intent intent = new Intent(getApplicationContext(), Detail.class);
				intent.putExtra("id_get", id);
				startActivityForResult(intent,10);*/
			}
		}
				);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 10) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(this, "Votre resérvation s'est déroulée avec success! " + data.getStringExtra("re_id"), Toast.LENGTH_SHORT).show();
			}
		}
	}

	class LoadAll extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ReservationStep2.this);
			pDialog.setMessage("Loading .Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			int success;
			try {

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id_agence", idAgence));
				params.add(new BasicNameValuePair("type_vehicule", typeVehicule));
				params.add(new BasicNameValuePair("dateDebLoc_reservation", dateDepart));
				params.add(new BasicNameValuePair("dateFinLoc_reservation", dateRetour));
				JSONObject json = jsonParser.makeHttpRequest(url_all, "GET", params);

				Log.d("Vehicules", json.toString() + idAgence);

				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					jsonTab = json.getJSONArray(TAG_TAB);
					for (int i = 0; i < jsonTab.length(); i++) {
						JSONObject c = jsonTab.getJSONObject(i);

						String id = c.getString(TAG_ID);
						String mark = c.getString(TAG_MARQUE);
						String model = c.getString(TAG_MODELE);
						String motor = c.getString(TAG_MOTORISATION);
						String tarif = c.getString(TAG_TARIF);

						Vehicule vehicule = new Vehicule(id,mark,model,motor,tarif);
						//Log.i("mark",vehicule.);

						vehiculelist.add(vehicule);
					}
				}else{
					// Resultat de requete vide
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			runOnUiThread(new Runnable() {                 
				public void run() {                 	
					AdapterVehicule ad = new AdapterVehicule(ReservationStep2.this, vehiculelist);
					lv.setAdapter(ad);               	
					//Log.i("Thread","Hello thread");
				}
			});
		}

	}

}
