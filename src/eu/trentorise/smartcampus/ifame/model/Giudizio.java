package eu.trentorise.smartcampus.ifame.model;

import java.util.Date;


public class Giudizio {

	private Long giudizio_id;
	
	private Float voto;


	private String commento;


	private Date ultimo_aggiornamento;


	private Long user_id;


	private Piatto_Mensa piatto_mensa;

	public Giudizio() {
		super();
	}

	public Long getGiudizio_id() {
		return giudizio_id;
	}

	public void setGiudizio_id(Long giudizio_id) {
		this.giudizio_id = giudizio_id;
	}

	public Float getVoto() {
		return voto;
	}

	public void setVoto(Float voto) {
		this.voto = voto;
	}

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}

	public Date getUltimo_aggiornamento() {
		return ultimo_aggiornamento;
	}

	public void setUltimo_aggiornamento(Date ultimo_aggiornamento) {
		this.ultimo_aggiornamento = ultimo_aggiornamento;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Piatto_Mensa getPiatto_mensa() {
		return piatto_mensa;
	}

	public void setPiatto_mensa(Piatto_Mensa piatto_mensa) {
		this.piatto_mensa = piatto_mensa;
	}

}