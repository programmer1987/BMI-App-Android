package de.rkasper.rkbmi.gui;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import de.rkasper.rkbmi.gui.listview.ListViewAdapter;
import de.rkasper.rkbmi.logic.MainActivityListener;
import de.rkasper.rkbmi.R;
import de.rkasper.rkbmi.model.Person;

/**
 * Startet als erstes.
 * Generiert alle direkt benoetigten Widgets.
 */
public class MainActivity extends AppCompatActivity {
	//region 0.Konstanten
	private static final String TAG = MainActivity.class.getSimpleName();
	//endregion
	
	//region 1. Decl. and Init Widgets
	
	/**
	 * Geschlecht maennlich
	 */
	private RadioButton rdbMale;
	
	/**
	 * Geschlecht weiblich
	 */
	private RadioButton rdbFemale;
	
	/**
	 * Geschlecht andere
	 */
	private RadioButton rdbOther;
	
	/**
	 * Nimmt den Vornamen einer Person entgegen
	 */
	private EditText txtFirstName;
	
	/**
	 * Nimmt den Nachnamen einer Person entegen
	 */
	private EditText txtLastName;
	
	/**
	 * Nimmt den Geburtstag einer Person entgegen
	 */
	private EditText txtBday;
	
	/**
	 * Nimmt die Groesse einer Person entgegen
	 */
	private EditText txtHeight;
	
	/**
	 * Nimmt das Gewicht einer Person entgegen
	 */
	private EditText txtWeight;
	
	/**
	 * Zeigt den berechnenten Bmi an
	 */
	private EditText txtBmi;
	
	/**
	 * Die ListView zeigt durch
	 * {@link de.rkasper.rkbmi.gui.listview.ListViewAdapter}
	 * generierte Eintraege an.
	 */
	private ListView lvPersons;
	/**
	 * Leitet das berechnen des Bmis ein
	 */
	private Button   btnCalcBmi;
	private Button   btnDeletePerson;
	
	/**
	 * Decl. Listener Nimmt die Klicks des Buttons entegeen
	 * und leitet das Speichern einer Person
	 * zur Laufzeit ein.
	 */
	private MainActivityListener mainActivityListener;
	
	//endregion
	
	//region 2. Lebenszyklus
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//1. Layout setzen
		this.setContentView(R.layout.main_activity_layout);
		
		//2. Widgets generieren
		this.rdbMale = this.findViewById(R.id.rdbMale);
		this.rdbFemale = this.findViewById(R.id.rdbFemale);
		this.rdbOther = this.findViewById(R.id.rdbOther);
		
		this.txtFirstName = this.findViewById(R.id.txtFirstName);
		this.txtLastName = this.findViewById(R.id.txtLastName);
		this.txtBday = this.findViewById(R.id.txtBday);
		
		this.txtHeight = this.findViewById(R.id.txtHeight);
		this.txtWeight = this.findViewById(R.id.txtWeight);
		this.txtBmi = this.findViewById(R.id.txtBmi);
		
		this.btnCalcBmi = this.findViewById(R.id.btnCalcBmi);
		this.btnDeletePerson = this.findViewById(R.id.btnDeletePerson);
		
		this.lvPersons = this.findViewById(R.id.lvPersons);
		//3. Listener generieren
		this.mainActivityListener = new MainActivityListener(this);
		
		//6. Listener zuweisen
		this.btnCalcBmi.setOnClickListener(this.mainActivityListener);
		this.btnDeletePerson.setOnClickListener(this.mainActivityListener);
		this.lvPersons.setOnItemClickListener(this.mainActivityListener);
//		this.lvPersons.setOnItemLongClickListener(this.mainActivityListener);
		
	}
	//endregion
}
