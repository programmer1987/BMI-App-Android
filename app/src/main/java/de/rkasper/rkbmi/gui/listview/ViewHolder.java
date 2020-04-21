package de.rkasper.rkbmi.gui.listview;

import android.widget.TextView;

/**
 * Diese Klasse haelt die generiert Widgets
 * des res/layout/list_view_item_layout.xml
 * zur Laufzeit vor. Damit der ListViewAdapter
 * die Daten bei Bedarf aktualisieren kann.
 */
public class ViewHolder {
	
	//region 1. Decl. and Init Widgets und Attribute
	/**
	 * Vom ListViewAdapter generierts
	 * Widgets. Definiert wurde es im
	 * res/layout/list_view_item_layout.xml
	 */
	private TextView txtvPersonInfo;
	private TextView txtvPersonBmi;
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Standardkonstruktor zum direkten
	 * setzen des im ListViewAdapter generierten
	 * Widgets.
	 * @param txtvPersonInfo : {@link TextView} : Personen infos
	 */
	public ViewHolder(TextView txtvPersonInfo,TextView txtvPersonBmi) {
		this.txtvPersonInfo = txtvPersonInfo;
		this.txtvPersonBmi = txtvPersonBmi;
	}
	//endregion
	
	//region 3. Getter und Setter
	public TextView getTxtvPersonInfo() {
		return txtvPersonInfo;
	}
	
	public TextView getTxtvPersonBmi() {
		return txtvPersonBmi;
	}
	
	//endregion
}
