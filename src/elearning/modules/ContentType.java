package elearning.modules;

public class ContentType {
	private int id;
	private String contentType;
	public ContentType() {
		super();
	}
	public ContentType(int id, String contentType) {
		super();
		this.id = id;
		this.contentType = contentType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	@Override
	public String toString() {
		return "ContentType [id=" + id + ", contentType=" + contentType + "]";
	}
	
}
