package eu.trentorise.smartcampus.ifame.model;

public class WebcamMensa {
	private Mensa tipo_mensa; 
	private String link_webcam;
	
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
