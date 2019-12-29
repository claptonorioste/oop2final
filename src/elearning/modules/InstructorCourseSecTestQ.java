package elearning.modules;

public class InstructorCourseSecTestQ {
	private int id;
	private int inscoursesecTestId;
	private int questionNo;
	private String question;
	private String answer;
	
	public InstructorCourseSecTestQ() {
		super();
	}

	public InstructorCourseSecTestQ(int id, int inscoursesecTestId, int questionNo, String question, String answer) {
		super();
		this.id = id;
		this.inscoursesecTestId = inscoursesecTestId;
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

	public int getInscoursesecTestId() {
		return inscoursesecTestId;
	}

	public void setInscoursesecTestId(int inscoursesecTestId) {
		this.inscoursesecTestId = inscoursesecTestId;
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
		return "InstructorCourseSecTestQ [id=" + id + ", inscoursesecTestId=" + inscoursesecTestId + ", questionNo="
				+ questionNo + ", question=" + question + ", answer=" + answer + "]";
	}
	
}
