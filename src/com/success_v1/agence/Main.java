package com.success_v1.agence;

import java.util.HashMap;

import com.example.list.R;
import com.success_v1.pushnotifications.RegisterActivity;
import com.success_v1.reservation.ReservationEnCours;
import com.success_v1.reservation.ReservationTab;
import com.success_v1.reservation.ReservationValide;
import com.success_v1.user.LogPage;
import com.success_v1.user.ProfilPage;
import com.success_v1.user.SessionManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener{
	Button btnAgences;
	Button btnReservation;
	Button btnCompte;
	Button btnTestPref;
    // Session Manager Class
    SessionManager session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acceuil);
        // Session class instance
        session = new SessionManager(getApplicationContext());
		btnAgences = (Button)this.findViewById(R.id.btnAgences);
		btnReservation= (Button)this.findViewById(R.id.btnReservations);
		btnCompte = (Button)this.findViewById(R.id.btnCompte);
		btnTestPref = (Button)this.findViewById(R.id.btnTestPref);
		btnAgences.setOnClickListener(this);
		btnReservation.setOnClickListener(this);
		btnCompte.setOnClickListener(this);
		btnTestPref.setOnClickListener(this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId())
		{
		case R.id.btnAgences:
			Intent agenceActivity= new Intent(this,RegisterActivity.class);
			startActivity(agenceActivity);
			break;
		case R.id.btnTestPref:
			 // get user data from session
			Intent profilActivity= new Intent(this,ProfilPage.class);
			startActivity(profilActivity);
			break;
		case R.id.btnReservations:
			/*Intent reservationActivity= new Intent(this,ReservationEnCours.class);
			startActivity(reservationActivity);*/
			/*Intent reservationTabActivity= new Intent(this,ReservationTab.class);
			startActivity(reservationTabActivity);*/
			Log.d("clic marche", "ok");
			startActivity(new Intent(this, ReservationTab.class));
			 Log.d("Bah non", "ok");
			break;
		case R.id.btnCompte:
			Intent ReserValideActivity = new Intent(this,ProfilPage.class);
			startActivity(ReserValideActivity);
			break;
		}
		
	}
}
