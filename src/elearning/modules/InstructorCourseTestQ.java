package elearning.modules;

public class InstructorCourseTestQ {
	private int id;
	private int inscourseTestId;
	private int questionNo;
	private String question;
	private String answer;
	
	public InstructorCourseTestQ() {
		super();
	}
	public InstructorCourseTestQ(int id, int inscourseTestId, int questionNo, String question, String answer) {
		super();
		this.id = id;
		this.inscourseTestId = inscourseTestId;
		this.questionNo = questionNo;
		this.question = question;
		this.answer = answer;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInscourseTestId() {
		return inscourseTestId;
	}
	public void setInscourseTestId(int inscourseTestId) {
		this.inscourseTestId = inscourseTestId;
	}
	public int getQuestionNo() {
		return questionNo;
	}
	public void setQuestionNo(int questionNo) {
		this.questionNo = questionNo;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "InstructorCourseTestQ [id=" + id + ", inscourseTestId=" + inscourseTestId + ", questionNo=" + questionNo
				+ ", question=" + question + ", answer=" + answer + "]";
	}
	
}
