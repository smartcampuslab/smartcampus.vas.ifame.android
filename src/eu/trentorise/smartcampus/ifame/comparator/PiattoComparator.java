package eu.trentorise.smartcampus.ifame.comparator;

import java.util.Comparator;

import eu.trentorise.smartcampus.ifame.model.Piatto;

public class PiattoComparator implements Comparator<Piatto> {

	public PiattoComparator() {
	}

	@Override
	public int compare(Piatto lhs, Piatto rhs) {
		final String s1 = (String) lhs.getPiatto_nome();
		final String s2 = (String) rhs.getPiatto_nome();
		return s1.compareToIgnoreCase(s2);
	}

}