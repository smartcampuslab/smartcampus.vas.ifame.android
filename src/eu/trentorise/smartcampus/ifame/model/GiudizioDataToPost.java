package eu.trentorise.smartcampus.ifame.model;

public class GiudizioDataToPost {

	public String commento;
	public Float voto;
	public Long userId;

	public GiudizioDataToPost() {
	}

	public GiudizioDataToPost(String commento, Float voto, Long userId) {
		super();
		this.commento = commento;
		this.voto = voto;
		this.userId = userId;
	}

}
