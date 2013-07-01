package eu.trentorise.smartcampus.ifame.model;

public class Likes {

	private Long like_id;

	private Long giudizio_id;

	/**
	 * @return the giudizio_id
	 */
	public Long getGiudizio_id() {
		return giudizio_id;
	}

	/**
	 * @param giudizio_id
	 *            the giudizio_id to set
	 */
	public void setGiudizio_id(Long giudizio_id) {
		this.giudizio_id = giudizio_id;
	}

	private Long user_id;

	private Boolean is_like;

	public Likes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getLike_id() {
		return like_id;
	}

	public void setLike_id(Long like_id) {
		this.like_id = like_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Boolean getIs_like() {
		return is_like;
	}
	
	public Long getUser_id(){
		return user_id;
	}

	public void setIs_like(Boolean is_like) {
		this.is_like = is_like;
	}

}
