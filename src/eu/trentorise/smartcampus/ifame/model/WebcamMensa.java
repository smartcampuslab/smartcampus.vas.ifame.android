package eu.trentorise.smartcampus.ifame.model;

public class WebcamMensa {
	private Mensa nome_mensa; 
	private String link_webcam;
	private long mensa_id; 
	
	public WebcamMensa(){
		/* No arg constructor*/
	}
	
	public WebcamMensa(Mensa nome_mensa, String link_webcam) {
		this.nome_mensa = nome_mensa; 
		this.link_webcam = link_webcam; 
	}
	public long getMensa_id() {
		return mensa_id;
	}
	public void setMensa_id(long mensa_id) {
		this.mensa_id = mensa_id;
	}
	public Mensa getTipo_mensa() {
		return nome_mensa;
	}
	public void setTipo_mensa(Mensa tipo_mensa) {
		this.nome_mensa = tipo_mensa;
	}
	public String getLink_webcam() {
		return link_webcam;
	}
	public void setLink_webcam(String link_webcam) {
		this.link_webcam = link_webcam;
	} 
	
}
