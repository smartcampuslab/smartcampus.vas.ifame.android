package eu.trentorise.smartcampus.ifame.utils;

import java.util.Comparator;

import eu.trentorise.smartcampus.ifame.model.Giudizio;

public class GiudizioComparator implements Comparator<Giudizio> {

	public GiudizioComparator() {
	}

	@Override
	public int compare(Giudizio g1, Giudizio g2) {
		return g2.getUltimo_aggiornamento().compareTo(
				g1.getUltimo_aggiornamento());
	}

}
