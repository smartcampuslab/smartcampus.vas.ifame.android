package eu.trentorise.smartcampus.ifame.model;

import java.io.Serializable;

public class Mensa implements Serializable {

	private Long mensa_id;

	private String mensa_nome;

	private String mensa_link_online;

	private String mensa_link_offline;

	public Mensa() {
		super();
	}

	public Mensa(String mensa_nome, String mensa_link_online,
			String mensa_link_offline) {
		super();
		this.mensa_nome = mensa_nome;
		this.mensa_link_online = mensa_link_online;
		this.mensa_link_offline = mensa_link_offline;
	}

	public Long getMensa_id() {
		return mensa_id;
	}

	public void setMensa_id(Long mensa_id) {
		this.mensa_id = mensa_id;
	}

	public String getMensa_nome() {
		return mensa_nome;
	}

	public void setMensa_nome(String mensa_nome) {
		this.mensa_nome = mensa_nome;
	}

	public String getMensa_link_online() {
		return mensa_link_online;
	}

	public void setMensa_link_online(String mensa_link_online) {
		this.mensa_link_online = mensa_link_online;
	}

	public String getMensa_link_offline() {
		return mensa_link_offline;
	}

	public void setMensa_link_offline(String mensa_link_offline) {
		this.mensa_link_offline = mensa_link_offline;
	}

}
