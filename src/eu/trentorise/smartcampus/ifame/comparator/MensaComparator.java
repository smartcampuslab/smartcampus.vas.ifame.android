package eu.trentorise.smartcampus.ifame.comparator;

import java.util.Comparator;

import eu.trentorise.smartcampus.ifame.model.Mensa;

public class MensaComparator implements Comparator<Mensa> {

	@Override
	public int compare(Mensa m1, Mensa m2) {
		final String nomeM1 = (String) m1.getMensa_nome();
		final String nomeM2 = (String) m2.getMensa_nome();
		return nomeM1.compareToIgnoreCase(nomeM2);
	}

}
