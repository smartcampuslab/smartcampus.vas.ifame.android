package eu.trentorise.smartcampus.ifame.model;

import java.util.Date;
import java.util.List;

public class Giudizio {

	private Long id;

	private Float voto;

	private String testo;

	private boolean approved;

	private Date ultimo_aggiornamento;

	private Long user_id;

	private String user_name;

	private String mensa_id;

	private Long piatto_id;

	private List<Likes> likes;

	public List<Likes> getLikes() {
		return likes;
	}

	public void setLikes(List<Likes> likes) {
		this.likes = likes;
	}

	public Giudizio() {
		super();
	}

	public Float getVoto() {
		return voto;
	}

	public void setVoto(Float voto) {
		this.voto = voto;
	}

	public Date getUltimo_aggiornamento() {
		return ultimo_aggiornamento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
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

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getMensa_id() {
		return mensa_id;
	}

	public void setMensa_id(String mensa_id) {
		this.mensa_id = mensa_id;
	}

	public Long getPiatto_id() {
		return piatto_id;
	}

	public void setPiatto_id(Long piatto_id) {
		this.piatto_id = piatto_id;
	}

}
