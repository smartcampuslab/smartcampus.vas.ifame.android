package eu.trentorise.smartcampus.ifame.model;

public class Piatto {

	private String piatto;
	private String kcal;

	public Piatto() {
	}

	public Piatto(String piatto, String kcal) {
		this.piatto = piatto;
		this.kcal = kcal;
	}

	public String getPiatto() {
		return piatto;
	}

	public void setPiatto(String piatto) {
		this.piatto = piatto;
	}

	public String getKcal() {
		return kcal;
	}

	public void setKcal(String kcal) {
		this.kcal = kcal;
	}

}
