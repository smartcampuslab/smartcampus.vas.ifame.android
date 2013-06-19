package eu.trentorise.smartcampus.ifame.model;

public class Piatto_Mensa {

	private Long piatto_mensa_id;

	private Long numero_giudizi;

	private Float voto_medio;

	private Mensa mensa;

	private Piatto piatto;

	public Piatto_Mensa() {
		super();
	}

	public Piatto_Mensa(Long numero_giudizi, Float voto_medio, Mensa mensa,
			Piatto piatto) {
		super();
		this.numero_giudizi = numero_giudizi;
		this.voto_medio = voto_medio;
		this.mensa = mensa;
		this.piatto = piatto;
	}

	public Long getPiatto_mensa_id() {
		return piatto_mensa_id;
	}

	public void setPiatto_mensa_id(Long piatto_mensa_id) {
		this.piatto_mensa_id = piatto_mensa_id;
	}

	public Long getNumero_giudizi() {
		return numero_giudizi;
	}

	public void setNumero_giudizi(Long numero_giudizi) {
		this.numero_giudizi = numero_giudizi;
	}

	public Float getVoto_medio() {
		return voto_medio;
	}

	public void setVoto_medio(Float voto_medio) {
		this.voto_medio = voto_medio;
	}

	public Mensa getMensa() {
		return mensa;
	}

	public void setMensa(Mensa mensa) {
		this.mensa = mensa;
	}

	public Piatto getPiatto() {
		return piatto;
	}

	public void setPiatto(Piatto piatto) {
		this.piatto = piatto;
	}

}
