package de.rkasper.rkbmi.model;

import android.content.Context;

import java.text.DecimalFormat;

import de.rkasper.rkbmi.helper.DateHelper;
import de.rkasper.rkbmi.gui.MainActivity;

/**
 * Diese Klasse ist eine Modelklasse<br>
 * Sie speichert die Daten einer Person<br>
 * zur Laufzeit. Die Personendaten werden so
 * gekapselt von Programmteil <b>A</b>
 * zu Programmteil <b>B</b> transferiert.
 * Sie enthaelt keinlei Logik.<br>
 * Modelklassen werden im Javaumfeld auch als<br>
 * {@link MainActivity}
 * <b>P</b>lain <b>O</b>ld <b>J</b>ava <b>O</b>bject-Klassen bezeichnet<br>
 */
public class Person {
	//region 0. Konstanten
	
	/**
	 * Standardwert fuer String Attribute
	 */
	public static final String DEFAULT_VALUE_STR = "noValue";
	
	/**
	 * Standardwert fuer Double Attribute
	 */
	public static final Double DEFAULT_VALUE_DBL = 0.1D;
	
	/**
	 * Definiert zwei Nachkommastellen.
	 * Genutzt wird es in der {@link Person#getBeautifulOutputBmi()}
	 */
	private static final String BMI_OUTPUT_PATTERN = "#.00";
	
	/**
	 * Dies ist das Trennzeichen, welches
	 * in {@link Person#getAllAttributesAsCsvLine()}
	 * verwendet wird um alle Attributwert mit einander
	 * zu verknuepfen.
	 */
	private static final String SPLIT_CHAR = ";";
	
	//1. SPLIT_INDIZES als Konstantne definieren
	private static final int SPLIT_INDEX_SEX        = 0;
	private static final int SPLIT_INDEX_FIRST_NAME = 1;
	private static final int SPLIT_INDEX_LAST_NAME  = 2;
	private static final int SPLIT_INDEX_BDAY       = 3;
	private static final int SPLIT_INDEX_EDIT_DATE  = 4;
	private static final int SPLIT_INDEX_HEIGHT     = 5;
	private static final int SPLIT_INDEX_WEIGHT     = 6;
	private static final int SPLIT_INDEX_BMI        = 7;
	//endregion
	
	//region 1. Delc. and Init Attribute
	
	/**
	 * Geschlecht einer Person
	 */
	private String strSex;
	
	/**
	 * Vorname einer Person
	 */
	private String strFirstName;
	
	/**
	 * Nachname einer Person
	 */
	private String strLastName;
	
	/**
	 * Geburtstag einer Person
	 */
	private String strBday;
	
	/**
	 * Zeitstempel wann dieses Personenobjekt geaendert wurde.<br>
	 * Der Zeitstempel sollte durch die Klasse<br>
	 * {@link DateHelper#getCurrentTimeStamp(Context)} generiert werden.<br>
	 */
	private String strEditDate;
	/**
	 * Koerpergroesse einer Person
	 */
	private Double dblHeight;
	
	/**
	 * Gewicht einer Person
	 */
	private Double dblWeight;
	
	/**
	 * Der BMI einer Person.
	 * Dieser wird in {@link Person#getBmi()}
	 * berechnet. Es gibt <b>keinen Setter</b>, da
	 * sich der BMI durch die Koerpergroesse
	 * {@link Person#dblHeight} und das Gewicht
	 * {@link Person#dblWeight} berechnet.
	 * <p>
	 * Fuer den BMI einer Person auf der GUI
	 * anzuzeigen steht die Funktion:<br>
	 * {@link Person#getBeautifulOutputBmi()}
	 * zur Verfuegung.
	 */
	private Double dblBmi;
	//endregion
	
	//region 2. Konstruktoren einfuegen mit alt+einfg --> Construtor
	
	/**
	 * Standard Konstruktor zum<br>
	 * direkten intialisieren aller<br>
	 * Attribute:<br>
	 * Fuer String Attribute wird<br>
	 * der folgende Wert genutzt:<br>
	 * {@link Person#DEFAULT_VALUE_STR}
	 * <br>
	 * Fuer Double Attribute wird<br>
	 * der folgende Wert genutzt:<br>
	 * {@link Person#DEFAULT_VALUE_DBL}
	 */
	public Person() {
		this.strSex = DEFAULT_VALUE_STR;
		this.strFirstName = DEFAULT_VALUE_STR;
		this.strLastName = DEFAULT_VALUE_STR;
		this.strBday = DEFAULT_VALUE_STR;
		this.strEditDate = DEFAULT_VALUE_STR;
		
		this.dblHeight = DEFAULT_VALUE_DBL;
		this.dblWeight = DEFAULT_VALUE_DBL;
		this.dblBmi = DEFAULT_VALUE_DBL;
	}
	
	/**
	 * 1. Ueberladener Konstruktor<br>
	 * Dieser initalisiert die Hauptattribute
	 * durch die mitgegebenen Parameter. Alle
	 * anderen Attribute werden mit dem Standard
	 * Konstruktor {@link Person#Person()} initalisiert
	 *
	 * @param strSex       : {@link String} : Geschlecht der Person
	 * @param strFirstName : {@link String} : Vorname der Person
	 * @param strLastName  : {@link String} : Nachname der Person
	 * @param strBday      : {@link String} : Geburtstag der Person
	 */
	public Person(String strSex, String strFirstName, String strLastName, String strBday) {
		//Standardkonstuktor zum init aller Attribute aufrufen.
		this();
		
		//Init der Hauptattribute mit Parametern.
		this.strSex = strSex;
		this.strFirstName = strFirstName;
		this.strLastName = strLastName;
		this.strBday = strBday;
	}
	
	//endregion
	
	//region 3. Getter und Setter einfuegen mit alt+einfg --> Getter und Setter
	
	public String getSex() {
		return strSex;
	}
	
	public void setSex(String strSex) {
		this.strSex = strSex;
	}
	
	public String getFirstName() {
		return strFirstName;
	}
	
	public void setFirstName(String strFirstName) {
		this.strFirstName = strFirstName;
	}
	
	public String getLastName() {
		return strLastName;
	}
	
	public void setLastName(String strLastName) {
		this.strLastName = strLastName;
	}
	
	public String getBday() {
		return strBday;
	}
	
	public void setBday(String strBday) {
		this.strBday = strBday;
	}
	
	public String getEditDate() {
		return strEditDate;
	}
	
	public void setEditDate(String strEditDate) {
		this.strEditDate = strEditDate;
	}
	
	public Double getHeight() {
		return dblHeight;
	}
	
	public void setHeight(Double dblHeight) {
		this.dblHeight = dblHeight;
	}
	
	public Double getWeight() {
		return dblWeight;
	}
	
	public void setWeight(Double dblWeight) {
		this.dblWeight = dblWeight;
	}
	
	/**
	 * Berechnet auf Basis der Angaben von
	 * {@link Person#dblHeight} und {@link Person#dblWeight}
	 * den BMI einer Person
	 *
	 * @return dblBmi : {@link Double} : BMI einer Person
	 */
	public Double getBmi() {
		
		if ((dblHeight >= DEFAULT_VALUE_DBL) && (dblWeight >= DEFAULT_VALUE_DBL)) {
			this.dblBmi = (dblWeight / (dblHeight * dblHeight));
		} else {
			this.dblBmi = DEFAULT_VALUE_DBL;
		}
		return dblBmi;
	}
	
	/**
	 * SchoenerBmi generieren.
	 * Diese Funktion sollte genutzt werden
	 * wenn der {@link Person#dblBmi} auf der GUI
	 * angezeigt wird.
	 * Gibt den BMI als String mit
	 * zwei Nachkommastellen zurueck
	 *
	 * @return strBmi : {@link String} : BMI mit zwei Nachkommastellen
	 */
	public String getBeautifulOutputBmi() {
		DecimalFormat df = new DecimalFormat(BMI_OUTPUT_PATTERN);
		
		return df.format(this.getBmi());
	}
	//endregion
	
	//region 4. String Funktionen toString einfeugen mit alt+einfg --> toString()
	
	/**
	 * Gibt alle Attribute als CSV-String
	 * zurueck. Dafuer wird das Trennzeichen {@link Person#SPLIT_CHAR}
	 * genutzt.<br>
	 * Folgende Attribute werden in den String mit aufgenommen:<br>
	 * <ul>
	 * <li>{@link Person#strSex}</li>
	 * <li>{@link Person#strFirstName}</li>
	 * <li>{@link Person#strLastName}</li>
	 * <li>{@link Person#strBday}</li>
	 * <li>{@link Person#strEditDate}</li>
	 * <li>{@link Person#dblHeight}</li>
	 * <li>{@link Person#dblWeight}</li>
	 * <li>{@link Person#getBmi()}</li>
	 * </ul>
	 * <p>
	 * male;hans;peter;01.01.1989;11.12.2018 - 15:02:30;1.78;80;25
	 *
	 * @return strCsvLine : {@link String} : CSV-String / Zeile
	 */
	public String getAllAttributesAsCsvLine() {
		//Decl. and Init
		String strCsvLine = "";
		
		//CSV-Line zusammenbauen
		strCsvLine += strSex + SPLIT_CHAR;
		strCsvLine += strFirstName + SPLIT_CHAR;
		strCsvLine += strLastName + SPLIT_CHAR;
		strCsvLine += strBday + SPLIT_CHAR;
		strCsvLine += strEditDate + SPLIT_CHAR;
		strCsvLine += dblHeight.toString() + SPLIT_CHAR;
		strCsvLine += dblWeight.toString() + SPLIT_CHAR;
		strCsvLine += this.getBmi().toString() + "\n";
		
		return strCsvLine;
	}
	
	/**
	 * Nimmt einen CSV-String entgegen welcher von
	 * der Funktion {@link Person#getAllAttributesAsCsvLine()}
	 * generiert worden ist. Und setzt alle Attribute der Klasse.<br>
	 * <p>
	 * Beispiel-String: male;hans;peter;01.01.1989;11.12.2018 - 15:02:30;1.78;80;25
	 * <p>
	 * Folgende Attribute werden gesetzt:<br>
	 * <ul>
	 * <li>{@link Person#strSex}</li>
	 * <li>{@link Person#strFirstName}</li>
	 * <li>{@link Person#strLastName}</li>
	 * <li>{@link Person#strBday}</li>
	 * <li>{@link Person#strEditDate}</li>
	 * <li>{@link Person#dblHeight}</li>
	 * <li>{@link Person#dblWeight}</li>
	 * <li>{@link Person#dblBmi}</li>
	 * </ul>
	 *
	 * @param strCsvLine
	 */
	public void setAllAttributesFromCsvLine(String strCsvLine) {
		//CSV-Line mit dem Trennzeichen auf splitten
		String[] strAllAttributes = strCsvLine.split(SPLIT_CHAR);
		
		//Attributewerte setzen
		this.strSex = strAllAttributes[SPLIT_INDEX_SEX];
		this.strFirstName = strAllAttributes[SPLIT_INDEX_FIRST_NAME];
		this.strLastName = strAllAttributes[SPLIT_INDEX_LAST_NAME];
		this.strBday = strAllAttributes[SPLIT_INDEX_BDAY];
		this.strEditDate = strAllAttributes[SPLIT_INDEX_EDIT_DATE];
		
		this.dblHeight = Double.valueOf(strAllAttributes[SPLIT_INDEX_HEIGHT]);
		this.dblWeight = Double.valueOf(strAllAttributes[SPLIT_INDEX_WEIGHT]);
		this.dblBmi = Double.valueOf(strAllAttributes[SPLIT_INDEX_BMI]);
	}
	
	@Override
	public String toString() {
		return "\n\n\nPerson{" +
				"strSex='" + strSex + '\'' +
				", strFirstName='" + strFirstName + '\'' +
				", strLastName='" + strLastName + '\'' +
				", strBday='" + strBday + '\'' +
				", strEditDate='" + strEditDate + '\'' +
				", dblHeight=" + dblHeight.toString() +
				", dblWeight=" + dblWeight.toString() +
				", dblBmi=" + dblBmi.toString() +
				'}';
	}
	
	//endregion
}
