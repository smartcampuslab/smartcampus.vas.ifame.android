package eu.trentorise.smartcampus.ifame.model;

public class WebcamMensa {
	private Mensa tipo_mensa; 
	private String link_webcam;
	private long mensa_id; 
	
	public WebcamMensa(Mensa povoO, String string) {
		tipo_mensa = povoO; 
		link_webcam = string; 
	}
	public long getMensa_id() {
		return mensa_id;
	}
	public void setMensa_id(long mensa_id) {
		this.mensa_id = mensa_id;
	}
	public Mensa getTipo_mensa() {
		return tipo_mensa;
	}
	public void setTipo_mensa(Mensa tipo_mensa) {
		this.tipo_mensa = tipo_mensa;
	}
	public String getLink_webcam() {
		return link_webcam;
	}
	public void setLink_webcam(String link_webcam) {
		this.link_webcam = link_webcam;
	} 
	
}
