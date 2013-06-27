package eu.trentorise.smartcampus.ifame.model;

import java.util.Date;

public class Transazione {

	private Long transazione_id;

	private Long user_id;

	private Date data;

	private Float importo;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Float getImporto() {
		return importo;
	}

	public void setImporto(Float importo) {
		this.importo = importo;
	}

	public Long getTransazione_id() {
		return transazione_id;
	}

	public void setTransazione_id(Long transazione_id) {
		this.transazione_id = transazione_id;
	}

}
