package eu.trentorise.smartcampus.ifame.model;


public class Mensa {

	private Long mensa_id;

	private String mensa_nome;

	private String mensa_link;

	public Mensa() {
		super();
	}

	public Mensa(Long mensa_id, String mensa_name, String mensa_link) {
		super();
		this.mensa_id = mensa_id;
		this.mensa_nome = mensa_name;
		this.mensa_link = mensa_link;
	}

	public Mensa(String mensa_name, String mensa_link) {
		super();
		this.mensa_nome = mensa_name;
		this.mensa_link = mensa_link;
	}

	public Long getMensa_id() {
		return mensa_id;
	}

	public void setMensa_id(Long mensa_id) {
		this.mensa_id = mensa_id;
	}

	public String getMensa_link() {
		return mensa_link;
	}

	public void setMensa_link(String mensa_link) {
		this.mensa_link = mensa_link;
	}

	public String getMensa_nome() {
		return mensa_nome;
	}

	public void setMensa_nome(String mensa_nome) {
		this.mensa_nome = mensa_nome;
	}

}
