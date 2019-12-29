package elearning.modules;

public class IntructorCourseSecContent {
	private int id;
	private String title;
	private int contentTypeId;
	private String contentLocation;
	private int titleOrderNo;
	
	public IntructorCourseSecContent() {
		super();
	}
	public IntructorCourseSecContent(int id, String title, int contentTypeId, String contentLocation,
			int titleOrderNo) {
		super();
		this.id = id;
		this.title = title;
		this.contentTypeId = contentTypeId;
		this.contentLocation = contentLocation;
		this.titleOrderNo = titleOrderNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getContentTypeId() {
		return contentTypeId;
	}
	public void setContentTypeId(int contentTypeId) {
		this.contentTypeId = contentTypeId;
	}
	public String getContentLocation() {
		return contentLocation;
	}
	public void setContentLocation(String contentLocation) {
		this.contentLocation = contentLocation;
	}
	public int getTitleOrderNo() {
		return titleOrderNo;
	}
	public void setTitleOrderNo(int titleOrderNo) {
		this.titleOrderNo = titleOrderNo;
	}
	@Override
	public String toString() {
		return "IntructorCourseSecContent [id=" + id + ", title=" + title + ", contentTypeId=" + contentTypeId
				+ ", contentLocation=" + contentLocation + ", titleOrderNo=" + titleOrderNo + "]";
	}
	
}
