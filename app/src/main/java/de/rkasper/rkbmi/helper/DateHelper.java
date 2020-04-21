package de.rkasper.rkbmi.helper;

import android.content.Context;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.rkasper.rkbmi.R;

/**
 * Diese Klasse gibt einen
 * Timestamp als String
 * zurueck.
 */
public class DateHelper {
	//region 0. Konstanten
	//endregion
	
	//region 1. Decl and Init
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Privater Standardkonstruktor
	 * um Objektinstanziierung
	 * von aussen zu unterbinden
	 */
	private DateHelper() {
		//Nur privat sein ist seine Aufgabe
	}
	
	//endregion
	
	//region 3. Getter
	
	/**
	 * Gibt uns den aktuellen Zeitstempel zurueck
	 * @param context : {@link Context} : Akutelle Activity die das Datum brauch
	 * @return strTimeStamp : {@link String} : Zeitstempel
	 */
	public static synchronized String getCurrentTimeStamp(@NonNull Context context){
		
		//1. StingResource aus res/values/strings.xml auslesen "dd.MM.yyyy"
		String strDatePattern = context.getString(R.string.strDatePattern);
		
		//2. StingResource aus res/values/strings.xml auslesen "HH:mm:ss"
		String strTimePattern = context.getString(R.string.strTimePattern);
		
		//3. Timestamp zusammenbauen aus Date und Time Pattern "dd.MM.yyy - HH:mm:ss";
		String strTimeStampPattern = strDatePattern + " - " + strTimePattern;
		
		//4. Datumsformatierer mit dem eigenen TimeStampPAttern erstellen
		DateFormat dateFormat = new SimpleDateFormat(strTimeStampPattern);
		
		//5. Akutelles Datum und aktuelle Zeitfestellen
		Date currentDateAndTime = new Date();
		
		//6. Aktueller Zeitstempel als String zurueckgeben
		return dateFormat.format(currentDateAndTime);
	}
	//endregion
}
