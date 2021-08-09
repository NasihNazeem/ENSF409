import java.util.ArrayList;


public class Student implements Comparable<Student> {
	
	private String studentName;
	private int studentId;
	// schedule is an ArrayList of course offerings (i.e. sections) that a student has enrolled in.
	private ArrayList<CourseOffering> schedule;
	// A collection of Registration objects that serves as both a record of previously completed courses
	// AND for currently enrolled courses.
	private ArrayList<Registration> studentRegList; // Past + Current
	
	
	public Student (String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		studentRegList = new ArrayList<Registration>();
		schedule = new ArrayList<CourseOffering>();
	}

	public void addToSchedule(Registration rStu) {
		
		schedule.add(rStu.getTheOffering());

	}

	public ArrayList<CourseOffering> getSchedule() {
		return this.schedule;
	}

	public Registration getStudentRegistration(CourseOffering section) {
		// TODO Check for past grades
		for (Registration r: studentRegList) {
			if (r.getTheOffering() == section) {
				return r;
			} else {
				continue;
			}
		}
		return null;
	}

	public int getScheduleSize() {
		return this.schedule.size();
	}

	public String getScheduleString() {
		StringBuilder sched = new StringBuilder("");
		for(CourseOffering e : this.schedule) {
			sched.append(e.getTheCourse().getCourseName() + " "
						  + e.getTheCourse().getCourseNum() + ", Section No: "
						  + e.getSecNum() + ", Status: ");
			if(e.getOfferingStatus() == true){
				sched.append("Confirmed\n");
			} else {
				sched.append("Tentative\n");
			}
		}
		return sched.toString();
	}

	public String getStudentName() {
		return this.studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	@Override
	public String toString () {
		String st = String.format("%-20s ID: %s", this.getStudentName(), this.getStudentId());
		return st;
	}
	public int compareTo(Student other) {
		if (this.studentName.compareTo(other.getStudentName()) == 0) {
			return this.studentId - other.getStudentId();
		} else {
			return this.studentName.compareTo(other.getStudentName());
		}
	}

	public ArrayList<Registration> getStudentRecords() {
		return this.studentRegList;
	}

	public void addToRecords(Registration registration) {
		// TODO Auto-generated method stub
		studentRegList.add(registration);
		
	}

	public void unenroll(CourseOffering offeringToDel) {
		var registrationToDel = this.getStudentRegistration(offeringToDel);
		if (registrationToDel == null) {
			System.out.println("Error: Something wrong happened with unenroll");
		} else { 
			schedule.remove(offeringToDel);
			studentRegList.remove(registrationToDel);
		}
	}
}
