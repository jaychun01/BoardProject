package boardsys;

public class BoardDTO {
	private String num;
	private String title;
	private String contents;
	private String id;
	private String date;
	private String clicked;
	private String rec;
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getClicked() {
		return clicked;
	}
	public void setClicked(String clicked) {
		this.clicked = clicked;
	}
	public String getRec() {
		return rec;
	}
	public void setRec(String rec) {
		this.rec = rec;
	}
	
	@Override
	public String toString() {
		return "BoardDTO [num=" + num + ", title=" + title + ", contents=" + contents + ", id=" + id + ", date=" + date
				+ ", clicked=" + clicked + ", rec=" + rec + "]";
	}
	
	
	
}
