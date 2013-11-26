package eu.trentorise.smartcampus.ifame.utils;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import eu.trentorise.smartcampus.ifame.activity.ComponiMenu.chosenMenu;
import android.widget.CheckBox;

public class BuildMenuController {

	boolean isCompatibleIntero1 = true;
	boolean isCompatibleRidotto1 = true;
	boolean isCompatibleRidotto2 = true;
	boolean isCompatibleRidotto3 = true;
	boolean isCompatibleRidotto4 = true;
	boolean isCompatibleSnack1 = true;
	boolean isCompatibleSnack2 = true;
	boolean isCompatibleSnack3 = true;
	boolean isCompatibleSnack4 = true;

	public CheckBox primo;
	public CheckBox pasta_station;
	public CheckBox insalatona;
	public CheckBox panino;
	public CheckBox trancio_pizza;
	public CheckBox pizza;
	public CheckBox piatto_freddo;
	public CheckBox secondo;
	public CheckBox contorno1;
	public CheckBox contorno2;
	public CheckBox dessert;
	public CheckBox caffe;
	public CheckBox acqua;
	public CheckBox salsa2;
	public CheckBox pane1;
	public CheckBox pane2;

	public static final String PRIMO_TEXT = "primo";
	public static final String PASTA_STATION_TEXT = "pasta_station";
	public static final String INSALATONA_TEXT = "insalatona";
	public static final String PANINO_TEXT = "panino";
	public static final String TRANCIO_PIZZA_TEXT = "trancio_pizza";
	public static final String PIZZA_TEXT = "pizza";
	public static final String PIATTO_FREDDO_TEXT = "piatto_freddo";
	public static final String SECONDO_TEXT = "secondo";
	public static final String CONTORNO1_TEXT = "contorno1";
	public static final String CONTORNO2_TEXT = "contorno2";
	public static final String DESSERT_TEXT = "dessert";
	public static final String CAFFE_TEXT = "caffe";
	public static final String ACQUA_TEXT = "acqua";
	public static final String SALSA2_TEXT = "salsa2";
	public static final String PANE1_TEXT = "pane1";
	public static final String PANE2_TEXT = "pane2";

	public enum elements_menu {
		primo, pasta_station, insalatona, panino, trancio_pizza, pizza, piatto_freddo, secondo, contorno1, contorno2, dessert, caffe, salsa1, salsa2, pane1, pane2
	}

	public static Map<String, Boolean> mapCheckedMenu;
	public static HashMap<String, Boolean> mapCheckedMenuTrue;
	public static Map<String, Boolean> mapCheckedIntero1;
	
	
	public HashMap<String, Boolean> mapFinal;
	
	public ArrayList<String> menuCompatibles;

	// public enum intero1 {
	// primo, secondo, contorno2, dessert, pane1
	// }
	//
	// public enum ridotto1 {
	// primo, contorno2, dessert, pane1
	// }
	//
	// public enum ridotto2 {
	// secondo, contorno2, dessert, pane1
	// }
	//
	// public enum ridotto3 {
	// piatto_freddo, pasta_station, contorno2, dessert, pane1
	// }

	// ScriptEngineManager manager = new ScriptEngineManager();
	// ScriptEngine engine = manager.getEngineByName("js");
	// Object result = engine.eval("4*5");
	// System.out.println("..result..."+String.valueOf(result));

	/**
	 * 
	 * @param primo
	 * @param pasta_station
	 * @param insalatona
	 * @param panino
	 * @param trancio_pizza
	 * @param pizza
	 * @param piatto_freddo
	 * @param secondo
	 * @param contorno1
	 * @param contorno2
	 * @param dessert
	 * @param caffe
	 * @param acqua
	 * @param salsa2
	 * @param pane1
	 * @param pane2
	 */
	public BuildMenuController(CheckBox primo, CheckBox pasta_station,
			CheckBox insalatona, CheckBox panino, CheckBox trancio_pizza,
			CheckBox pizza, CheckBox piatto_freddo, CheckBox secondo,
			CheckBox contorno1, CheckBox contorno2, CheckBox dessert,
			CheckBox caffe, CheckBox acqua, CheckBox salsa2, CheckBox pane1,
			CheckBox pane2) {

		this.primo = primo;
		this.pasta_station = pasta_station;
		this.insalatona = insalatona;
		this.panino = panino;
		this.trancio_pizza = trancio_pizza;
		this.pizza = pizza;
		this.piatto_freddo = piatto_freddo;
		this.secondo = secondo;
		this.contorno1 = contorno1;
		this.contorno2 = contorno2;
		this.dessert = dessert;
		this.caffe = caffe;
		this.acqua = acqua;
		this.salsa2 = salsa2;
		this.pane1 = pane1;
		this.pane2 = pane2;

		mapCheckedMenu = new HashMap<String, Boolean>();

		mapCheckedMenu.put(PRIMO_TEXT, primo.isChecked());
		mapCheckedMenu.put(PASTA_STATION_TEXT, pasta_station.isChecked());
		mapCheckedMenu.put(INSALATONA_TEXT, insalatona.isChecked());
		mapCheckedMenu.put(PANINO_TEXT, panino.isChecked());
		mapCheckedMenu.put(TRANCIO_PIZZA_TEXT, trancio_pizza.isChecked());
		mapCheckedMenu.put(PIZZA_TEXT, pizza.isChecked());
		mapCheckedMenu.put(PIATTO_FREDDO_TEXT, piatto_freddo.isChecked());
		mapCheckedMenu.put(SECONDO_TEXT, secondo.isChecked());
		mapCheckedMenu.put(CONTORNO1_TEXT, contorno1.isChecked());
		mapCheckedMenu.put(CONTORNO2_TEXT, contorno2.isChecked());
		mapCheckedMenu.put(DESSERT_TEXT, dessert.isChecked());
		mapCheckedMenu.put(CAFFE_TEXT, caffe.isChecked());
		mapCheckedMenu.put(ACQUA_TEXT, acqua.isChecked());
		mapCheckedMenu.put(SALSA2_TEXT, salsa2.isChecked());
		mapCheckedMenu.put(PANE1_TEXT, pane1.isChecked());
		mapCheckedMenu.put(PANE2_TEXT, pane2.isChecked());

		mapCheckedMenuTrue = new HashMap<String, Boolean>();

		Iterator it = mapCheckedMenu.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, Boolean> element = (Map.Entry<String, Boolean>) it
					.next();

			if (element.getValue() == true) {
				mapCheckedMenuTrue.put(element.getKey(), element.getValue());
			}
		}

	}

	public List<CheckBox> updatePossibilitiesMenu() {
		List<CheckBox> listCheckBoxUpdated = new ArrayList<CheckBox>();
		
		checkIntero1();
		checkRidotto1();
		checkRidotto2();
		checkRidotto3();
		checkRidotto4();
		checkSnack1();
		checkSnack2();
		checkSnack3();
		checkSnack4();
		
		mapFinal = new HashMap<String, Boolean>();
		
		
		primo.setEnabled(false);
		pasta_station.setEnabled(false);
		insalatona.setEnabled(false);
		panino.setEnabled(false);
		pizza.setEnabled(false);
		trancio_pizza.setEnabled(false);
		piatto_freddo.setEnabled(false);
		secondo.setEnabled(false);
	    contorno1.setEnabled(false);
		contorno2.setEnabled(false);
		dessert.setEnabled(false);
		caffe.setEnabled(false);
		acqua.setEnabled(false);;
		salsa2.setEnabled(false);
		pane1.setEnabled(false);
		pane2.setEnabled(false);
		

		if (isCompatibleIntero1) {
			primo.setEnabled(true);
			secondo.setEnabled(true);
			contorno2.setEnabled(true);
			dessert.setEnabled(true);
			pane1.setEnabled(true);
		}

		if (isCompatibleRidotto1) {
			primo.setEnabled(true);
			contorno2.setEnabled(true);
			dessert.setEnabled(true);
			pane1.setEnabled(true);
		}

		if (isCompatibleRidotto2) {
			secondo.setEnabled(true);
			contorno1.setEnabled(true);
			dessert.setEnabled(true);
			pane1.setEnabled(true);
		}

		if (isCompatibleRidotto3) {
			if(piatto_freddo.isChecked()){
				piatto_freddo.setEnabled(true);
				pasta_station.setEnabled(false);
				insalatona.setEnabled(false);
			}else if(pasta_station.isChecked()){
				piatto_freddo.setEnabled(false);
				pasta_station.setEnabled(true);
				insalatona.setEnabled(false);
			}else if(insalatona.isChecked()){
				piatto_freddo.setEnabled(false);
				pasta_station.setEnabled(false);
				insalatona.setEnabled(true);
			}else{
				piatto_freddo.setEnabled(true);
				pasta_station.setEnabled(true);
				insalatona.setEnabled(true);
			}
			
			contorno1.setEnabled(true);
			dessert.setEnabled(true);
			pane1.setEnabled(true);
		}

		if (isCompatibleRidotto4) {
			pizza.setEnabled(true);
			if(contorno1.isChecked()){
				contorno1.setEnabled(true);
				dessert.setEnabled(false);
			}else if(dessert.isChecked()){
				dessert.setEnabled(true);
				contorno1.setEnabled(false);
			}else{
				dessert.setEnabled(true);
				contorno1.setEnabled(true);
			}
			
			if(caffe.isChecked()){
				caffe.setEnabled(true);
				acqua.setEnabled(false);
			}else if(acqua.isChecked()){
				acqua.setEnabled(true);
				caffe.setEnabled(false);
			}else{
				acqua.setEnabled(true);
				caffe.setEnabled(true);
			}
		}

		if (isCompatibleSnack1) {
			panino.setEnabled(true);
			dessert.setEnabled(true);
			acqua.setEnabled(true);
			
			if(caffe.isChecked()){
				caffe.setEnabled(true);
				salsa2.setEnabled(false);
			}else if(salsa2.isChecked()){
				salsa2.setEnabled(true);
				caffe.setEnabled(false);
			}else{
				salsa2.setEnabled(true);
				caffe.setEnabled(true);
			}
		}

		if (isCompatibleSnack2) {
			if(primo.isChecked()){
				primo.setEnabled(true);
				pasta_station.setEnabled(false);
			}else if(pasta_station.isChecked()){
				pasta_station.setEnabled(true);
				primo.setEnabled(false);
			}else{
				pasta_station.setEnabled(true);
				primo.setEnabled(true);
			}
			
			if(contorno1.isChecked()){
				contorno1.setEnabled(true);
				dessert.setEnabled(false);
			}else if(dessert.isChecked()){
				dessert.setEnabled(true);
				contorno1.setEnabled(false);
			}else{
				dessert.setEnabled(true);
				contorno1.setEnabled(true);
			}
			
			pane2.setEnabled(true);
			
			if(caffe.isChecked()){
				caffe.setEnabled(true);
				salsa2.setEnabled(false);
			}else if(salsa2.isChecked()){
				salsa2.setEnabled(true);
				caffe.setEnabled(false);
			}else{
				salsa2.setEnabled(true);
				caffe.setEnabled(true);
			}

		}

		if (isCompatibleSnack3) {

			if(secondo.isChecked()){
				secondo.setEnabled(true);
				piatto_freddo.setEnabled(false);
			}else if(piatto_freddo.isChecked()){
				piatto_freddo.setEnabled(true);
				secondo.setEnabled(false);
			}else{
				piatto_freddo.setEnabled(true);
				secondo.setEnabled(true);
			}
			
			contorno1.setEnabled(true);
			pane1.setEnabled(true);
			
			if(caffe.isChecked()){
				caffe.setEnabled(true);
				salsa2.setEnabled(false);
			}else if(salsa2.isChecked()){
				salsa2.setEnabled(true);
				caffe.setEnabled(false);
			}else {
				salsa2.setEnabled(true);
				caffe.setEnabled(true);
			}
		}

		if (isCompatibleSnack4) {
			trancio_pizza.setEnabled(true);
			dessert.setEnabled(true);
			acqua.setEnabled(true);
			
			if(caffe.isChecked()){
				caffe.setEnabled(true);
				salsa2.setEnabled(false);
			}else if(salsa2.isChecked()){
				salsa2.setEnabled(true);
				caffe.setEnabled(false);
			}else {
				salsa2.setEnabled(true);
				caffe.setEnabled(true);
			}
			
		}
		
		
		listCheckBoxUpdated.add(primo);
		listCheckBoxUpdated.add(pasta_station);
		listCheckBoxUpdated.add(insalatona);
		listCheckBoxUpdated.add(panino);
		listCheckBoxUpdated.add(trancio_pizza);
		listCheckBoxUpdated.add(piatto_freddo);
		listCheckBoxUpdated.add(secondo);
		listCheckBoxUpdated.add(contorno1);
		listCheckBoxUpdated.add(contorno2);
		listCheckBoxUpdated.add(dessert);
		listCheckBoxUpdated.add(caffe);
		listCheckBoxUpdated.add(acqua);
		listCheckBoxUpdated.add(salsa2);
		listCheckBoxUpdated.add(pane1);
		listCheckBoxUpdated.add(pane2);

		return listCheckBoxUpdated;

	}

	public void checkIntero1() {

		Iterator it = mapCheckedMenuTrue.entrySet().iterator();

		isCompatibleIntero1 = true;

		while (it.hasNext()) {
			Map.Entry<String, Boolean> element = (Map.Entry<String, Boolean>) it
					.next();

			if ((element.getKey() != PRIMO_TEXT)
					&& (element.getKey() != SECONDO_TEXT)
					&& (element.getKey() != CONTORNO2_TEXT)
					&& (element.getKey() != DESSERT_TEXT)
					&& (element.getKey() != PANE1_TEXT)) {
				isCompatibleIntero1 = false;
				break;
			}
		}

	}

	public void checkRidotto1() {

		Iterator it = mapCheckedMenuTrue.entrySet().iterator();

		isCompatibleRidotto1 = true;

		while (it.hasNext()) {
			Map.Entry<String, Boolean> element = (Map.Entry<String, Boolean>) it
					.next();

			if ((element.getKey() != PRIMO_TEXT)
					&& (element.getKey() != CONTORNO2_TEXT)
					&& (element.getKey() != DESSERT_TEXT)
					&& (element.getKey() != PANE1_TEXT)) {
				isCompatibleRidotto1 = false;
				break;
			}
		}
	}

	public void checkRidotto2() {

		Iterator it = mapCheckedMenuTrue.entrySet().iterator();

		isCompatibleRidotto2 = true;

		while (it.hasNext()) {
			Map.Entry<String, Boolean> element = (Map.Entry<String, Boolean>) it
					.next();

			if ((element.getKey() != SECONDO_TEXT)
					&& (element.getKey() != CONTORNO1_TEXT)
					&& (element.getKey() != DESSERT_TEXT)
					&& (element.getKey() != PANE1_TEXT)) {
				isCompatibleRidotto2 = false;
				break;
			}
		}
	}

	public void checkRidotto3() {

		Iterator it = mapCheckedMenuTrue.entrySet().iterator();

		isCompatibleRidotto3 = true;

		while (it.hasNext()) {
			Map.Entry<String, Boolean> element = (Map.Entry<String, Boolean>) it
					.next();

			if (((element.getKey() != PIATTO_FREDDO_TEXT)
					&& (element.getKey() != PASTA_STATION_TEXT) && (element
					.getKey() != INSALATONA_TEXT))
					&& (element.getKey() != CONTORNO1_TEXT)
					&& (element.getKey() != DESSERT_TEXT)
					&& (element.getKey() != PANE1_TEXT)) {
				isCompatibleRidotto3 = false;
				break;
			}
		}
	}

	public void checkRidotto4() {

		Iterator it = mapCheckedMenuTrue.entrySet().iterator();

		isCompatibleRidotto4 = true;

		while (it.hasNext()) {
			Map.Entry<String, Boolean> element = (Map.Entry<String, Boolean>) it
					.next();

			if ((element.getKey() != PIZZA_TEXT)
					&& ((element.getKey() != CONTORNO1_TEXT) && (element
							.getKey() != DESSERT_TEXT))
					&& ((element.getKey() != CAFFE_TEXT)) && (element.getKey() != ACQUA_TEXT)) {
				isCompatibleRidotto4 = false;
				break;
			}
		}
	}

	public void checkSnack1() {

		Iterator it = mapCheckedMenuTrue.entrySet().iterator();

		isCompatibleSnack1 = true;

		while (it.hasNext()) {
			Map.Entry<String, Boolean> element = (Map.Entry<String, Boolean>) it
					.next();

			if ((element.getKey() != PANINO_TEXT)
					&& (element.getKey() != DESSERT_TEXT)
					&& (element.getKey() != ACQUA_TEXT)
					&& ((element.getKey() != CAFFE_TEXT))
					&& (element.getKey() != SALSA2_TEXT)) {
				isCompatibleSnack1 = false;
				break;
			}
		}
	}

	public void checkSnack2() {

		Iterator it = mapCheckedMenuTrue.entrySet().iterator();

		isCompatibleSnack2 = true;

		while (it.hasNext()) {
			Map.Entry<String, Boolean> element = (Map.Entry<String, Boolean>) it
					.next();

			if (((element.getKey() != PRIMO_TEXT) && (element.getKey() != PASTA_STATION_TEXT))
					&& ((element.getKey() != DESSERT_TEXT) && (element.getKey() != CONTORNO1_TEXT))
					&& ((element.getKey() != PANE2_TEXT))
					&& ((element.getKey() != CAFFE_TEXT) && (element.getKey() != SALSA2_TEXT))) {
				isCompatibleSnack2 = false;
				break;
			}
		}
	}

	public void checkSnack3() {

		Iterator it = mapCheckedMenuTrue.entrySet().iterator();

		isCompatibleSnack3 = true;

		while (it.hasNext()) {
			Map.Entry<String, Boolean> element = (Map.Entry<String, Boolean>) it
					.next();

			if (((element.getKey() != SECONDO_TEXT) && (element.getKey() != PIATTO_FREDDO_TEXT))
					&& ((element.getKey() != CONTORNO1_TEXT))
					&& ((element.getKey() != PANE1_TEXT))
					&& ((element.getKey() != CAFFE_TEXT) && (element.getKey() != SALSA2_TEXT))) {
				isCompatibleSnack3 = false;
				break;
			}
		}
	}

	public void checkSnack4() {

		Iterator it = mapCheckedMenuTrue.entrySet().iterator();

		isCompatibleSnack4 = true;

		while (it.hasNext()) {
			Map.Entry<String, Boolean> element = (Map.Entry<String, Boolean>) it
					.next();

			if ((element.getKey() != TRANCIO_PIZZA_TEXT)
					&& ((element.getKey() != DESSERT_TEXT))
					&& ((element.getKey() != ACQUA_TEXT))
					&& ((element.getKey() != CAFFE_TEXT) && (element.getKey() != SALSA2_TEXT))) {
				isCompatibleSnack4 = false;
				break;
			}
		}
	}

	public ArrayList<String> getCompatiblesMenu() {
		
		checkIntero1();
		checkRidotto1();
		checkRidotto2();
		checkRidotto3();
		checkRidotto4();
		checkSnack1();
		checkSnack2();
		checkSnack3();
		checkSnack4();
		
		menuCompatibles = new ArrayList<String>();
		
		if(isCompatibleIntero1)
			menuCompatibles.add(chosenMenu.Intero.toString());
		if(isCompatibleRidotto1)
			menuCompatibles.add(chosenMenu.Ridotto1.toString());
		if(isCompatibleRidotto2)
			menuCompatibles.add(chosenMenu.Ridotto2.toString());
		if(isCompatibleRidotto3)
			menuCompatibles.add(chosenMenu.Ridotto3.toString());
		if(isCompatibleRidotto4)
			menuCompatibles.add(chosenMenu.Ridotto4.toString());
		if(isCompatibleSnack1)
			menuCompatibles.add(chosenMenu.Snack1.toString());
		if(isCompatibleSnack2)
			menuCompatibles.add(chosenMenu.Snack2.toString());
		if(isCompatibleSnack3)
			menuCompatibles.add(chosenMenu.Snack3.toString());
		if(isCompatibleSnack4)
			menuCompatibles.add(chosenMenu.Snack4.toString());
		
		
		
		return menuCompatibles;
	}

	public HashMap<String, Boolean> getCheckedItems() {
		// TODO Auto-generated method stub
		
		return mapCheckedMenuTrue;
	}

}
