package de.rkasper.rkbmi.gui.listview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import de.rkasper.rkbmi.R;
import de.rkasper.rkbmi.model.Person;

/**
 * Diese Klasse hat zur Aufgabe
 * die einzelen ListViewItems
 * innerhalb der {@link android.widget.ListView}
 * lvPersons aus der MainActivity zu geneireren
 * und diese mit Daten aus der Ihm uebergebenen
 * Datenliste zu befuellen. Er generiert nur
 * soviele ListViewItems wie Platz auf dem
 * Bildschirm fuer die ListView ist. Hat die
 * ListView Platz fuer 4 ListViewItems so wird
 * der Adapter auch nur 4 ListViewItems generieren.
 */
public class ListViewAdapter extends BaseAdapter {
	//region 0. Konstanten
	private static final String TAG = ListViewAdapter.class.getSimpleName();
	//endregion
	
	//region 1. Decl. and Init Attribute
	
	/**
	 * Aktuelle Activity bei uns hier die {@link de.rkasper.rkbmi.gui.MainActivity}
	 */
	private Context context;
	
	/**
	 * Der LayoutInflater baut {@link View}s
	 * zur Laufzeit auf. Diese tut er durch
	 * Angabe des {@link Context}es und einem
	 * ihm uebergebenen XML-Layout.
	 */
	private LayoutInflater layoutInflater;
	
	/**
	 * Datenliste von {@link Person}en, die
	 * in der {@link android.widget.ListView}
	 * angezeigt werden sollen.
	 */
	private List<Person> personList;
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Standardkonstruktor zum direkten setzen
	 * der Hauptattribute
	 *
	 * @param context    : {@link Context} : Akutelle Activity bei uns hier
	 *                   die {@link de.rkasper.rkbmi.gui.MainActivity}
	 * @param personList : {@link List} {@link Person} : Personen die in der
	 *                   ListView angezeigt werden sollen
	 */
	public ListViewAdapter(Context context, List<Person> personList) {
		//1. Context merken/setzen
		this.context = context;
		
		//2.Personenliste merken / setzen
		this.personList = personList;
		
		//3.LayoutInfalter mit Context generieren lassen
		this.layoutInflater = LayoutInflater.from(context);
		
		//DEBUG
		Log.d(TAG, "im Konstruktor:\ncontext und personList gesetzt."
				+ "\nDern layoutInflater mit der Angabe des contextes generiert.");
	}
	
	//endregion
	
	//region 3. Listenfunktionen
	
	/**
	 * Gibt die Groesse der Liste zureuck
	 *
	 * @return int : Groesse
	 */
	@Override
	public int getCount() {
		//DEBUG
		int iListSize = this.personList.size();
		
		//DEBUG
//		Log.d(TAG, "Diese Listengroesse von: " + iListSize + " wurde angefragt");
		
		return iListSize;
	}
	
	/**
	 * Gibt ein bestimmtes Objekt innheralb
	 * der Datenliste hier {@link ListViewAdapter#personList}
	 * zurueck. Die AdapterKlasse weis die Liste zu handeln
	 * und gibt hier schon einen gueltigen index mit.
	 *
	 * @param index : int : Index eines Elementes innerhalb der Datenliste
	 *
	 * @return object : {@link Object} : In unserem Fall steckt da eine
	 * {@link Person} drin die dann wieder mit:<br>
	 * Person p = (Person) object;<br>
	 * rausgecastet werden muss
	 */
	@Override
	public Object getItem(int index) {
		
		Person personToReturn = this.personList.get(index);
		//DEBUG
//		Log.d(TAG, "Eine Person wurde angefragt: " + personToReturn.getAllAttributesAsCsvLine());
		
		return personToReturn;
	}
	
	/**
	 * Die ItemId == mit dem Index.
	 * Unsere {@link Person}enklasse
	 * hatt keine Id die wir hier zurueck
	 * geben koennten. Deswegen geben wir
	 * einfach den Parameter index nochmal zurueck.
	 *
	 * @param index : int : Index eines konkreten Elementes innerhalb
	 *              der Liste.
	 *
	 * @return index : long : Parameter explizit in long gecastet
	 */
	@Override
	public long getItemId(int index) {
		/*
		 * Haetten hier eine Datenbank,
		 * implementiert. Und unsere
		 * Modelklasse haette eine id
		 * so wuerden wir diese zurueckgeben:
		 * Beispiel:
		 * return this.personList.get(index).getId();
		 * Das haben wir aber nicht. Wir moechten die
		 * Funktion hier aber nicht leer lassen und
		 * immer 0 bekommen, deswegen und nur deswegen
		 * geben wir den index zurueck.
		 */
		return ((long) index);
	}
	//endregion
	
	//region 4. ListViewItems generrieren bzw. aktualisieren
	
	/**
	 * Hier werden dynmaisch genau soviele grafische ListViewItems
	 * generiert wie die {@link android.widget.ListView} platz auf
	 * der Gui hat. Sprich hat sie Platz fuer 4 so werden 4 Elemente
	 * generiert. Diese 4 Elemente werden automatisch und autonom von
	 * dieser Klasse und dieser Funktion mit neuen Daten aus der Liste
	 * befuellt. Der ListViewAdapter kennt die Datenliste und weiss immer
	 * welche Datensaetze aus der DatenListe von Personen angezeigt werden,
	 * welche die letzte Datensaetze waren, und welche die naechsten sind.
	 * Daher gibt er uns hier in dieser Funktion schon alle Parameter passend
	 * befuellt zurueck.
	 *
	 * @param index               : int : Index der aktuell abzubildenen Person
	 * @param currentListViewItem : {@link View} : Aktuelle ListViewItem,
	 *                            welches entweder neu mit {@link ListViewAdapter#layoutInflater}
	 *                            geneirert werden muss, oder aber mit neuen Werten aus der
	 *                            Datenliste befuellt werden muss. Bei beiden Faellen muessen
	 *                            Daten in das {@link android.widget.TextView}Widget des ListViews
	 *                            eingetragen werden.
	 * @param parentListView      :{@link ViewGroup} : Der {@link ListViewAdapter}
	 *                            wird im {@link de.rkasper.rkbmi.logic.MainActivityListener}
	 *                            mit dem {@link android.widget.ListView} Widget lvPersons
	 *                            aus der MainActivity mit lvPerson.setAdapter(listViewAdapter)
	 *                            verbunden, so mit kenne sich beide .Und die Adapterklasse kann
	 *                            hier die {@link android.widget.ListView} als parent mitgeben.
	 *                            Dieses benoetigt um ein neues ListViewItem zu geneireren.
	 *
	 * @return currentListViewItem : {@link View} : Neu generiertes oder bereits bestehendes
	 * ListViewItem mit neuen Daten
	 */
	@Override
	public View getView(int index, View currentListViewItem, ViewGroup parentListView) {
		
		//1. ViewHolder deklarieren(Er ist fuer die aktualisierung Daten zustaending)
		ViewHolder viewHolder = null;
		
		//2. Checken ob ein ListViewItem neu generiert werden muss.
		if (currentListViewItem == null) {
			
			//3. Neues ListViewItem generieren
			currentListViewItem = this.layoutInflater.inflate(R.layout.list_view_item_layout,
					parentListView, false);
			
			//4. Kindelemente des ListViewItems generieren hier TextView
			TextView txtvPersonInfo = currentListViewItem.findViewById(R.id.txtvPersonInfo);
			TextView txtvPersonBmi = currentListViewItem.findViewById(R.id.txtvPersonBmi);
			
			/*
			 * 5. ViewHolder generieren und neue generiertes Kindelement
			 * des ListViewItems dem ViewHolder mitgeben
			 */
			viewHolder = new ViewHolder(txtvPersonInfo,txtvPersonBmi);
			
			//DEBUG
			Log.d(TAG,"Neues ListViewItem generiert: " + currentListViewItem.toString());
			
			//6. ViewHolder an currentListViewItem als Zusatzinfo anhaengen
			currentListViewItem.setTag(viewHolder);
		} else {
			/**
			 * 3. ViewHolder rauscasten. Dieses ListViewItem wurde
			 * ja schon einmal generiert und muss nur mit neuen
			 * Daten aus der Datenliste befuellt werden. Bei seiner
			 * damiligen Neugeneirerung bekam es den ViewHolder als
			 * Tag mit.
			 */
			viewHolder = (ViewHolder) currentListViewItem.getTag();
			Log.d(TAG,"Bereits vorhandes ListViewItem zum aktualiseren der Daten: " + currentListViewItem.toString());
		}
		
		/*
		 * 4./7. Daten eintragen/aktualisieren
		 */
		
		//Aktuelle Person welche angezeigt werden soll aus der List beschaffen
		Person personToShow = (Person) this.getItem(index);
		
		//3.7. ViewHolder nutzen um die Daten der aktuellen Person anzuzeigen
		viewHolder.getTxtvPersonInfo().setText(personToShow.getFirstName() + " "
				+ personToShow.getLastName() );
		viewHolder.getTxtvPersonBmi().setText(" BMI: " + personToShow.getBeautifulOutputBmi());
		
		
		//3.8 currentListViewItem zurueckgeben
		return currentListViewItem;
	}
	//endregion
}
