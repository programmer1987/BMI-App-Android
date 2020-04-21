package de.rkasper.rkbmi.logic;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.rkasper.rkbmi.gui.listview.ListViewAdapter;
import de.rkasper.rkbmi.helper.DateHelper;
import de.rkasper.rkbmi.R;
import de.rkasper.rkbmi.gui.MainActivity;
import de.rkasper.rkbmi.model.Person;

/**
 * Nimmt alle Klicks der {@link MainActivity}
 * entgegen und leitet das Speichern/Aenderun/Loeschen einer Person
 * ein. Diese Operation bezeichnet man als
 * <b>C</b>reate <b>R</b>ead <b>U</b>pdate <b>D</b>elete Operationen
 */
public class MainActivityListener implements View.OnClickListener,
                                             AdapterView.OnItemClickListener,
		/*AdapterView.OnItemLongClickListener,*/
		                                     DialogInterface.OnClickListener {
	
	//region 0 Konstanten
	
	/**
	 * Loggingkonstante
	 */
	private static final String TAG = MainActivityListener.class.getSimpleName();
	
	/**
	 * Ausschalten des Editierungsmodus.
	 * Wen das Attribut {@link MainActivityListener#editDeleteIndex}
	 * diesen Wert hat, gibt es keine Person die bearbeitet oder geloescht
	 * werden soll. Heisst im Umkehrschluss es kann eine neue Person hinzugefuegt werden.
	 */
	private static final int EDIT_DELETE_MODE_OFF = -1;
	//endregion
	
	//region 1. Decl. and Init Attribute Widgets
	/**
	 * Arbeitsreferenz auf die {@link MainActivity}
	 */
	private MainActivity mainActivity;
	
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
	 * ListView welche die
	 * {@link Person}en anzeigt
	 */
	private ListView lvPersons;
	
	/**
	 * Deklarieren Speichert alle Personen zur Laufzeit
	 */
	private List<Person> personList;
	
	/**
	 * Delc. FileHandler
	 * Speichert und liest Personen
	 * aus lokalen Datei aus/rein.
	 */
	private FileHandler fileHandler;
	
	/**
	 * Index zum Loeschen,
	 * ist der Bearbeitungs oder Loeschmodus
	 * aus so wird zum setzen die eigenen
	 * Konstante {@link #EDIT_DELETE_MODE_OFF} genutzt
	 */
	private int editDeleteIndex;
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Nimmt die Referenz auf die {@link MainActivity}
	 * entegen und speichert diese in einem lokalen Attribut.
	 *
	 * @param mainActivity : {@link MainActivity} : Arbeitsreferenz
	 */
	public MainActivityListener(MainActivity mainActivity) {
		
		//1. MainActivity Referenz merken
		this.mainActivity = mainActivity;
		
		//2.  FileHandler generieren
		this.fileHandler = new FileHandler(this.mainActivity);
		
		//3. Personenliste mit fileHandler auselesen
		this.personList = fileHandler.readPersonsFromCsvFile();
		
		//4. Referenzen auf Widgets in der MainActivity geneireren
		generateWidgetReferences();
		
		//5. Personen aus Datei in ListView anzeigen
		this.updateListView();
		
		//6. Editierungsmodus auf AUS setzen
		this.editDeleteIndex = EDIT_DELETE_MODE_OFF;
		
		
	}
	//endregion
	
	//region 3. Klickhandling Buttons
	
	/**
	 * Springt an wenn der Button btnCalcBmi geklickt wird
	 *
	 * @param v : {@link View} : btnCalcBmi oder btnDeletePerson
	 */
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
			case R.id.btnCalcBmi:
				saveOrEdit();
				break;
			case R.id.btnDeletePerson:
				
				if (this.editDeleteIndex > EDIT_DELETE_MODE_OFF) {
					deletePerson();
				} else {
					Toast.makeText(mainActivity, R.string.strUserMsgNoPersonSelected, Toast.LENGTH_SHORT)
					     .show();
				}
				break;
		}
		
		
	}
	
	private void saveOrEdit() {
		//Daten aus der Gui auslesen lassen
		Person personToSaveOrEdit = getPersonDataFromGui();
		
		if (personToSaveOrEdit != null) {
			
			//Checken ob die Person bearbeitet oder neu hinzugefuegt werden soll
			if (this.editDeleteIndex > EDIT_DELETE_MODE_OFF) {
				//Alte Personendaten durch die neuen ersetzen
				this.personList.set(this.editDeleteIndex, personToSaveOrEdit);
				
				//Editierungsmodus
				this.editDeleteIndex = EDIT_DELETE_MODE_OFF;
				
				//ListView wieder freigeben
				this.lvPersons.setEnabled(true);
				
			} else {
				//Neue Person in Personenliste speichern
				this.personList.add(personToSaveOrEdit);
			}
			
			//PersonListe mit fileHandler speichern
			this.fileHandler.savePersonsToCsvFile(this.personList);
			
			//ListView updaten neue Person anzeigen
			this.updateListView();
			
			//Eingabefelder leeren
			resetInput();
			
			//Erfolgsmeldungen bauen und anzeigen
			Toast.makeText(this.mainActivity,
					R.string.strUserMsgBmiCalcSuccess, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this.mainActivity,
					R.string.strUserMsgWrongInput, Toast.LENGTH_LONG).show();
		}
	}
	
	private void deletePerson() {
		//1. Index merken
//		this.editDeleteIndex;
		
		//2. DialogBuilder generieren
		AlertDialog.Builder builder = new AlertDialog.Builder(this.mainActivity);
		
		//3. Titel des Dialogs festelegen (String in strings.xml definieren)
		builder.setTitle(R.string.strDeleteTitle);
		
		//4. Usernachricht setzen (String in strings.xml definieren
		builder.setMessage(R.string.strUserMsgDeletePerson);
		
		//5. Bestaetigungs Button mit Text aus der Androideigenen R Klasse und dem Listener setzen
		builder.setPositiveButton(android.R.string.yes, this);
		
		//6. Abbrechene Button mit Text aus der Androideigenen R Klasse und dem Listener setzen
		builder.setNegativeButton(android.R.string.no, this);
		
		//7. Dialog anzeigen
		builder.show();
	}
	
	
	//endregion
	
	//region 4. Klickhandling ListView
	
	/**
	 * Springt jedes mal an wenn ein ListViewItem innerhalb der {@link android.widget.ListView} geklickt wird.
	 *
	 * @param parentListView      : {@link AdapterView} : ListView aus der {@link MainActivity}
	 * @param clickedListViewItem : {@link View} : Geklickte im
	 *                            {@link de.rkasper.rkbmi.gui.listview.ListViewAdapter} generierte ListViewItem
	 * @param index               : int : index des Datenelementes aus der uebergebenen Liste an den {@link de.rkasper.rkbmi.gui.listview.ListViewAdapter#personList}
	 * @param id                  : id : eines Datenelementes aus der uebergebenene Liste an den {@link de.rkasper.rkbmi.gui.listview.ListViewAdapter#personList}
	 */
	@Override
	public void onItemClick(AdapterView<?> parentListView, View clickedListViewItem, int index, long id) {
		
		
		//Person auslesen aus der Liste
		Person personToEdit = this.personList.get(index);
		
		if (personToEdit != null) {
			//Personen daten auf der GUI anzeigen
			displayPersonDataOnGui(personToEdit);
			
			//Index der zu editierenden Person merken
			this.editDeleteIndex = index;
			
			//ListViewDeaktivieren
			this.lvPersons.setEnabled(false);
			
		}
		
	}


//	/**
//	 * Springt jedes mal an wenn ein ListViewItem innerhalb der {@link android.widget.ListView} lang geklickt wird.
//	 *
//	 * @param parentListView      : {@link AdapterView} : ListView aus der {@link MainActivity}
//	 * @param clickedListViewItem : {@link View} : Geklickte im
//	 *                            {@link de.rkasper.rkbmi.gui.listview.ListViewAdapter} generierte ListViewItem
//	 * @param index:              int : index des Datenelementes aus der uebergebenen Liste an den {@link de.rkasper.rkbmi.gui.listview.ListViewAdapter#personList}
//	 * @param id                  : id : eines Datenelementes aus der uebergebenene Liste an den {@link de.rkasper.rkbmi.gui.listview.ListViewAdapter#personList}
//	 */
//	@Override
//	public boolean onItemLongClick(AdapterView<?> parentListView, View clickedListViewItem, int index, long id) {
//
//
//		return true;
//	}
	
	//endregion
	
	//region 5. Klickhandling Dialog aus onItemLongClick
	
	/**
	 * Springt an wenn ein Button des Dialogs geklickt
	 * wird. Bei druecken des Postivien Buttons wird
	 * das Loeschen einer Person vorgenommen. Beim druecken
	 * des Negative Buttons geschieht nichts.
	 *
	 * @param dialog              : {@link DialogInterface} : Dialog der angezeigt wurde
	 * @param clickedDialogButton : int : Geklickte Button im Dialog
	 */
	@Override
	public void onClick(DialogInterface dialog, int clickedDialogButton) {
		switch (clickedDialogButton) {
			case DialogInterface.BUTTON_POSITIVE:
				
				
				//1. Person aus der Laufzeitliste loeschen
				this.personList.remove(this.editDeleteIndex);
				
				//2. Liste ohne geloeschte Person nutzen um die CSV-Datei zu aktualisieren
				this.fileHandler.savePersonsToCsvFile(this.personList);
				
				this.lvPersons.setEnabled(true);
				
				//3. Aktualisierte Liste in ListView anzeigen
				this.updateListView();
				
				//4. Editierungs/Loeschmodus abschalten
				this.editDeleteIndex = EDIT_DELETE_MODE_OFF;
				
				this.resetInput();
				
				
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				Log.d(TAG, "Hier geschieht nichts");
				break;
//			case DialogInterface.BUTTON_NEUTRAL:
//				Log.d(TAG,"Ich bin die Schweiz");
//				break;
		}
	}
	
	//endregion
	
	//region Hilfsmethoden
	
	/**
	 * Updated die ListView jedes mal
	 * wenn neue Daten angezeigt werden sollen.
	 * Wenn eine neue Person hinzugefuegt wird.
	 * Wenn eine bestehende Person geaendert wird.
	 * Wenn eine Person geloescht wird.
	 */
	public void updateListView() {
		ListViewAdapter lvAdapter = new ListViewAdapter(this.mainActivity, this.personList);
		this.lvPersons.setAdapter(lvAdapter);
	}
	
	/**
	 * Leert die Textfelder
	 * und setzt die Standardauswahl beim
	 * Geschlecht.
	 */
	private void resetInput() {
		this.rdbMale.setChecked(true);
		
		this.txtFirstName.setText("");
		this.txtLastName.setText("");
		this.txtBday.setText("");
		this.txtHeight.setText("");
		this.txtWeight.setText("");
		this.txtBmi.setText("");
		
		//Platzieren des Eingabecursors
		this.txtFirstName.requestFocus();
	}
	
	
	/**
	 * Liest alle Eingaben aus der GUI
	 * aus und gibt eine fertig befuellte
	 * {@link Person} zurueck, sollten
	 * die Eingaben richtig sein. Sind
	 * die Eingaben flasch so wird null
	 * zurueck geliefert.
	 *
	 * @return personDataFromGui : {@link Person} : Person oder null
	 */
	@Nullable
	private Person getPersonDataFromGui() {
		//Decl and Init
		Person personDataFromGui = null;
		
		//1.Usereingabe auslesen
		String strSex = getSelectedSex();
		String strFirstName = txtFirstName.getText().toString();
		String strLastName = txtLastName.getText().toString();
		String strBday = txtBday.getText().toString();
		String strHeight = txtHeight.getText().toString();
		String strWeight = txtWeight.getText().toString();
		
		//2. Alle User eingaben in eine dynamische List packen
		List<String> strUserInputValues = new ArrayList<>();
		strUserInputValues.add(strFirstName);
		strUserInputValues.add(strLastName);
		strUserInputValues.add(strBday);
		strUserInputValues.add(strHeight);
		strUserInputValues.add(strWeight);
		
		boolean userInputValuesAreValid = true;
		
		//3. Alle Eingabewerte ueberpruefen ob sie auch nicht leer sind
		for (String strValue : strUserInputValues) {
			if (strValue.isEmpty()) {
				userInputValuesAreValid = false;
			}
		}
		
		//4. Checken ob die Eingabewerte als richtig befunden worden sind
		if (userInputValuesAreValid) {
			
			//5. BMI Daten EXPLIZIT CASTEN !!!!
			Double dblHeight = Double.valueOf(strHeight);
			Double dblWeight = Double.valueOf(strWeight);
			
			//6. Wenn die Bmi Daten auch groesser 0 sind
			if ((dblHeight >= Person.DEFAULT_VALUE_DBL) && (dblWeight >= Person.DEFAULT_VALUE_DBL)) {
				
				//7. Neue Person geneireren
				personDataFromGui = new Person();
				
				//8. Personen Attribute setzen
				personDataFromGui.setSex(strSex);
				personDataFromGui.setFirstName(strFirstName);
				personDataFromGui.setLastName(strLastName);
				personDataFromGui.setBday(strBday);
				
				personDataFromGui.setEditDate(DateHelper.getCurrentTimeStamp(this.mainActivity));
				
				personDataFromGui.setHeight(dblHeight);
				personDataFromGui.setWeight(dblWeight);
				
			}
		}
		
		return personDataFromGui;
	}
	
	/**
	 * Stellt fest welches Geschlecht der User
	 * ausgewaehlt hat
	 *
	 * @return strSelectedSex : {@link String} : Geschlecht
	 */
	private String getSelectedSex() {
		String strSelectedSex = Person.DEFAULT_VALUE_STR;
		
		if (rdbMale.isChecked()) {
			strSelectedSex = rdbMale.getText().toString();
		}
		
		if (rdbFemale.isChecked()) {
			strSelectedSex = rdbFemale.getText().toString();
		}
		
		if (rdbOther.isChecked()) {
			strSelectedSex = rdbOther.getText().toString();
		}
		
		return strSelectedSex;
	}
	
	/**
	 * Zeigt Daten einer Person
	 * auf der GUI an.
	 */
	private void displayPersonDataOnGui(Person personToEdit) {
		
		//Geschlecht setzt und die Methode setSelectedSex unter dieser auch implementieren
		this.setSelectedSex(personToEdit.getSex());
		
		//Andere Persondaten anzeigen
		this.txtFirstName.setText(personToEdit.getFirstName());
		this.txtLastName.setText(personToEdit.getLastName());
		this.txtBday.setText(personToEdit.getBday());
		this.txtHeight.setText(personToEdit.getHeight().toString());
		this.txtWeight.setText(personToEdit.getWeight().toString());
		this.txtBmi.setText(personToEdit.getBeautifulOutputBmi());
	}
	
	/**
	 * Setzt das ausgewaehlte Geschlecht
	 *
	 * @param strSelectedSex : {@link String} : Geschlecht
	 */
	private void setSelectedSex(String strSelectedSex) {
		if (this.rdbMale.getText().toString().equalsIgnoreCase(strSelectedSex)) {
			this.rdbMale.setChecked(true);
		}
		
		if (this.rdbFemale.getText().toString().equalsIgnoreCase(strSelectedSex)) {
			this.rdbFemale.setChecked(true);
		}
		
		if (this.rdbOther.getText().toString().equalsIgnoreCase(strSelectedSex)) {
			this.rdbOther.setChecked(true);
		}
	}
	
	/**
	 * Generieren der Widget Referenzen.
	 */
	private void generateWidgetReferences() {
		//Widgetreferenzen generieren
		this.rdbMale = this.mainActivity.findViewById(R.id.rdbMale);
		this.rdbFemale = this.mainActivity.findViewById(R.id.rdbFemale);
		this.rdbOther = this.mainActivity.findViewById(R.id.rdbOther);
		
		this.txtFirstName = this.mainActivity.findViewById(R.id.txtFirstName);
		this.txtLastName = this.mainActivity.findViewById(R.id.txtLastName);
		this.txtBday = this.mainActivity.findViewById(R.id.txtBday);
		
		this.txtHeight = this.mainActivity.findViewById(R.id.txtHeight);
		this.txtWeight = this.mainActivity.findViewById(R.id.txtWeight);
		this.txtBmi = this.mainActivity.findViewById(R.id.txtBmi);
		
		this.lvPersons = this.mainActivity.findViewById(R.id.lvPersons);
	}
	//endregion
}
