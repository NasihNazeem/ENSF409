/**
 * Instances of this class create a link between a Student object,
 * a CourseOffering (section) object, and a grade. Registration objects
 * belong to Students and comprise a Student's academic record
 */
public class Registration {
	private Student theStudent;
	private CourseOffering theOffering;
	private char grade;

	//first constructor - with no params
	public Registration () {
		this.theStudent = null;
		this.theOffering = null;
		this.grade = '\u0000';
	}
	//second constructor - wi params
	public Registration (Student aStudent, CourseOffering anOffering, char aGrade) {
		this.theStudent = aStudent;
		this.theOffering = anOffering;
		this.grade = aGrade;
	}
	
	void completeRegistration (Student st, CourseOffering of) {
		// prereq check implement here.
		theStudent = st;
		theOffering = of;
		this.addRegistration ();
	}
	
	private void addRegistration () {
		theStudent.addToRecords(this);
		theOffering.recordRegistration(this);
	}
	
	public Student getTheStudent() {
		return this.theStudent;
	}
	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}
	public CourseOffering getTheOffering() {
		return this.theOffering;
	}
	public void setTheOffering(CourseOffering theOffering) {
		this.theOffering = theOffering;
	}
	public char getGrade() {
		return grade;
	}
	public void setGrade(char grade) {
		this.grade = grade;
	}
	
	@Override
	public String toString () {
		String st = String.format(" %-7s | %-6s | %-7s | %-9s\n",
								this.getTheOffering().getTheCourse().getCourseName(),
								this.getTheOffering().getTheCourse().getCourseNum(),
								this.getTheOffering().getSecNum(),
								this.getGrade());
		return st;
		
	}
	

}
