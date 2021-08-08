import java.util.ArrayList;


public class CourseOffering implements Comparable<CourseOffering> {
	
	// Section Number of the Course
	private int secNum;
	// Section Capacity of the Course
	private int secCap;
	// Course which is receiving a course offering (a course section)
	private Course theCourse;
	// Current students enlisted in the course offering.
	private ArrayList<Student> currentEnrolment;
	// Current and past students that possess a registration of this course offering. Past 
	// students who have completed the course would have a registration containing a Student object, a Course Section object, and a grade.
	// Currently enrolled students are similar, but lack a grade.
	private ArrayList<Registration> offeringRegRecord; // all students ever enrolled + currently enrolled students
	// An instance state field denoting if a course offering (section) has sufficient enrolment to guarantee its eventual delivery. 
	private boolean minEnrolmentMet;
	
	public CourseOffering (int secNum, int secCap) {
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		this.minEnrolmentMet = false;

		offeringRegRecord = new ArrayList <Registration>();
		currentEnrolment = new ArrayList<Student>();
		
	}

	public int getSecNum() {
		return secNum;
	}

	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}
	
	public int getSecCap() {
		return secCap;
	}

	public int getSectionEnrolment() {
		return this.currentEnrolment.size();
	}

	public ArrayList<Student> getClassList() {
		return this.currentEnrolment;
	}

	public void setSecCap(int secCap) {
		this.secCap = secCap;
	}

	public Course getTheCourse() {
		return theCourse;
	}

	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}
	public String getStatus() {
		String status;
		if(getOfferingStatus() == true) {
			status = "Confirmed";
		} else {
			status = "Tentative";
		}
		return status;
	}
	@Override
	public String toString () {
		String st = String.format(" %-4s  %-3s - Section: %-2s | Capacity: %-3s | Enrolment: %-3s | Status: %-12s\n", 
								  this.getTheCourse().getCourseName(),
								  this.getTheCourse().getCourseNum(),
								  this.secNum,
								  this.secCap,
								  this.getSectionEnrolment(),
								  this.getStatus());
		return st;
	}

	public int compareTo(CourseOffering other) {
		return this.secNum - other.getSecNum();
	}

	public void recordRegistration(Registration anEntry) {
		// TODO Auto-generated method stub
		offeringRegRecord.add(anEntry);
		
	}

	public void addToStudentEnrollment(Registration offerReg) {

		currentEnrolment.add(offerReg.getTheStudent());
		if(currentEnrolment.size() >= 8){
			this.minEnrolmentMet = true;
		} else {
			System.out.println("This course's enrolment is still too low to create a section. As of now, these registrations are tentative.\n");
		}
	}
	
	public boolean getOfferingStatus() {
		return this.minEnrolmentMet;
	}

	public void removeReg(Student student, Registration registration) {
		Student removeStudent = null;
		Registration removeRegistration = null;

		for(Student se : currentEnrolment)
		{
			if(se.equals(student))
				removeStudent = se;
		}

		for(Registration rr : offeringRegRecord)
		{
			if (rr.equals(registration))
				removeRegistration = rr;
		}
		currentEnrolment.remove(removeStudent);
		offeringRegRecord.remove(removeRegistration);
	}

	

	
	

}