package de.rkasper.rkbmi.logic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import de.rkasper.rkbmi.R;
import de.rkasper.rkbmi.model.Person;

/**
 *
 * Speichert die {@link de.rkasper.rkbmi.model.Person}en
 * in einem CSV-File auf dem Gereaet. Und liest sie
 * von dort auch wieder aus.
 */
public class FileHandler {
	//region 0. Konstanten
	
	/**
	 * Loggingkonstante
	 */
	private static final String TAG = FileHandler.class.getSimpleName();
	//endregion
	
	//region 1. Decl. and Init Attribute
	private Context context;
	private String  strDefaultFileNameWithFileExtension;
	private String  strCharSetUtfEight;
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Setzt den Context und
	 * generiert den Dateinamen,
	 * als auch den zu verwendenden
	 * Zeichsatz:
	 *
	 * @param context : {@link Context} : Aktuelle Activity
	 */
	public FileHandler(Context context) {
		
		//1. Context merken
		this.context = context;
		
		//2. Dateinamen aus res/values/strings.xml definieren dann auslesen
		this.strDefaultFileNameWithFileExtension = this.
				context.getString(R.string.strDefaultFileNameWithFileExtension);
				
		//3. Zeichensatz aus res/values/strings.xml definieren dann auslesen
		this.strCharSetUtfEight = this.context.getString(R.string.strCharSetUtfEight);
	}
	
	//endregion
	
	//region Personen speichern
	
	/**
	 * Speichert Personen auf dem Geraet in einem
	 * CSV-File<br>
	 * Die Datei ist nacher unter dem folgenden
	 * Pfad zu finden:<br>
	 * <b>data/data/de.rkasper.rkbmi/files/persons.csv</b><br>
	 * <b>data/data/eurePackageStrukturNichtMeine/files/persons.csv</b>
	 *
	 * @param personList : {@link List} - {@link Person} : Personen die gespeichert werden sollen.
	 */
	public void savePersonsToCsvFile(@NonNull List<Person> personList) {
		
		if ((!personList.isEmpty()) && (personList != null)) {
			
			try {
				/*
				 * 1.
				 * Der FileOutputStream wird von
				 * der Klasse Context generiert
				 * und bekommt als Parameter
				 * den Dateinamen und den Datei Modus.
				 * Der Modus muss uber die Konstanten
				 * der Klasse Context festgelegt werden.
				 * Context.MODE_APPEND = neuer Inhalt an
				 * den bereits bestehenden Inhalter der
				 * alter Datei anhaegen,
				 * Context.MODE_PRIVATE = Jedes mal eine
				 * neue Datei erzeugen, welche die alte
				 * ueberschreibt.
				 */
				FileOutputStream fos = this.context.openFileOutput(
						this.strDefaultFileNameWithFileExtension,
						Context.MODE_PRIVATE
				);
				
				//2. Objekt welches in die Datei schreibt generieren
				OutputStreamWriter out = new OutputStreamWriter(fos, this.strCharSetUtfEight);
				
				//3. Alle Personen rausschreiben
				for (Person p : personList) {
					out.write(p.getAllAttributesAsCsvLine());
				}
				
				//4. Schliessen der Dateiverbindung
				out.close();
				
			} catch (Exception e) {
				Log.e(TAG, e.getMessage() + "\nStackTrace:\n" + e.getStackTrace().toString());
			}
		}
	}
	//endregion
	
	//region Personen lesen
	
	/**
	 * Liest Personen aus der persons.csv Datei aus.
	 * @return personListFromCsvFile : {@link List} {@link Person} : Personen aus der Datei
	 */
	public List<Person> readPersonsFromCsvFile(){
		//Decl. and Init
		List<Person> personListFromCsvFile = new ArrayList<>();
		
		try {
			
			//1. FileInputStream mit openFileInput generieren
			FileInputStream fis = this.context.openFileInput(this.strDefaultFileNameWithFileExtension);
			
			//2. InputStreamReader aus dem fis mit Zeichensatz generieren
			InputStreamReader isr = new InputStreamReader(fis,this.strCharSetUtfEight);
			
			//3. Leseobjekt mit isr Generieren
			BufferedReader in = new BufferedReader(isr);
			
			/**
			 * 4. Arbeitsvariablen
			 * strCsvLine eine gelesene CSV-Zeile
			 * eof = End Of File
			 */
			String strCsvLine;
			boolean eof = false;
			
			while(!eof){
				
				//Zeile auselesen
				strCsvLine = in.readLine();
				
				//Checken ob das Ende der Datei erreicht ist
				if(strCsvLine == null){
					eof = true;
				}else{
					//Neue Person generieren
					Person personFromCsvFile = new Person();
					
					//Alle Attribute mit ausgelesener CSV Zeile setzen
					personFromCsvFile.setAllAttributesFromCsvLine(strCsvLine);
					
					personListFromCsvFile.add(personFromCsvFile);
				}
			}
			
			//Schliessen
			in.close();
			
			
		} catch (Exception e) {
			Log.e(TAG, e.getMessage() + "\nStackTrace:\n" + e.getStackTrace().toString());
		}
		
		return personListFromCsvFile;
	}
	//endregion
}
