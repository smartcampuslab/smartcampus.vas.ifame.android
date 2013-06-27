package eu.trentorise.smartcampus.ifame.model;

import java.util.Date;
import java.util.List;

public class GiudizioNew {

	private Long giudizio_id;

	private Float voto;

	private String commento;

	private Date ultimo_aggiornamento;

	private Long user_id;

	private Long mensa_id;

	private Long piatto_id;
	
	private List<Likes> likes; 

	/**
	 * @return the likes
	 */
	public List<Likes> getLikes() {
		return likes;
	}

	/**
	 * @param likes the likes to set
	 */
	public void setLikes(List<Likes> likes) {
		this.likes = likes;
	}

	public GiudizioNew() {
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

	public Long getMensa_id() {
		return mensa_id;
	}

	public void setMensa_id(Long mensa_id) {
		this.mensa_id = mensa_id;
	}

	public Long getPiatto_id() {
		return piatto_id;
	}

	public void setPiatto_id(Long piatto_id) {
		this.piatto_id = piatto_id;
	}

}
