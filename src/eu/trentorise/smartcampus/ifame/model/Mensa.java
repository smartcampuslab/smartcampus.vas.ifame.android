package eu.trentorise.smartcampus.ifame.model;

import java.io.Serializable;
import java.util.List;

public class Mensa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mensa_id;

	private String mensa_nome;

	private String mensa_link_online;

	private String mensa_link_offline;
	
	private List<CanteenOpeningTimes> times;
	
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

	public String getMensa_id() {
		return mensa_id;
	}

	public void setMensa_id(String mensa_id) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mensa_id == null) ? 0 : mensa_id.hashCode());
		result = prime
				* result
				+ ((mensa_link_offline == null) ? 0 : mensa_link_offline
						.hashCode());
		result = prime
				* result
				+ ((mensa_link_online == null) ? 0 : mensa_link_online
						.hashCode());
		result = prime * result
				+ ((mensa_nome == null) ? 0 : mensa_nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mensa other = (Mensa) obj;
		if (mensa_id == null) {
			if (other.mensa_id != null)
				return false;
		} else if (!mensa_id.equals(other.mensa_id))
			return false;
		if (mensa_link_offline == null) {
			if (other.mensa_link_offline != null)
				return false;
		} else if (!mensa_link_offline.equals(other.mensa_link_offline))
			return false;
		if (mensa_link_online == null) {
			if (other.mensa_link_online != null)
				return false;
		} else if (!mensa_link_online.equals(other.mensa_link_online))
			return false;
		if (mensa_nome == null) {
			if (other.mensa_nome != null)
				return false;
		} else if (!mensa_nome.equals(other.mensa_nome))
			return false;
		return true;
	}

	public List<CanteenOpeningTimes> getTimes() {
		return times;
	}

	public void setTimes(List<CanteenOpeningTimes> times) {
		this.times = times;
	}

}
