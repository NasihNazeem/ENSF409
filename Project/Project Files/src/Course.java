import java.util.ArrayList;

public class Course implements Comparable<Course> {

	// Declared as a string to hold Course name input.
	private String courseName;
	// Declared as an integer to hold Course number input.
	private int courseNum;
	// ArrayList of prerequisites for a course.
	private ArrayList<Course> preReqs;
	// ArrayList of sections for a course.
	private ArrayList<CourseOffering> sections;

	public Course(String courseName, int courseNum) {
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		// Both of the following are only association

		preReqs = new ArrayList<Course>();
		sections = new ArrayList<CourseOffering>();
	}

	public void addOffering(CourseOffering offering) {
		if (offering != null && offering.getTheCourse() == null) {
			offering.setTheCourse(this);
			if (!offering.getTheCourse().getCourseName().equals(courseName)
					|| offering.getTheCourse().getCourseNum() != courseNum) {
				System.err.println("Error! This section belongs to another course!");
				return;
			}
			sections.add(offering);
		}
	}

	public ArrayList<CourseOffering> getSections() {
		return this.sections;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}

	public void addCoursePreReq (Course preReq) {
		this.preReqs.add(preReq);
	}

	public int compareTo(Course other) {
		if (this.courseName.compareTo(other.getCourseName()) == 0) {
			return this.courseNum - other.getCourseNum();
		} else {
			return this.courseName.compareTo(other.getCourseName());
		}
	}

	
	@Override
	public String toString () {
		String st = getCourseName() + " " + getCourseNum() + " - Available Sections: " + sections.size() + "\n";
		return st;
	}

	public String getTabulatedSections() {
		String st = String.format(" %-7s | %-6s | %-7s | %-8s | %-9s | %-9s\n", "Subject", "Number", "Section", "Capacity", "Enrolment", "Status");
		for (int i = 0; i < this.getSections().size(); i++) {
			st += String.format(" %-7s | %-6s | %-7s | %-8s | %-9s | %-9s\n",
								  this.getCourseName(),
								  this.getCourseNum(),
								  this.getSections().get(i).getSecNum(),
								  this.getSections().get(i).getSecCap(),
								  this.getSections().get(i).getSectionEnrolment(),
								  this.getSections().get(i).getStatus());
		}
		return st;
	}


	public CourseOffering getCourseOfferingAt(int i) {
		// TODO Auto-generated method stub
		if (i < 0 || i >= sections.size() )
			return null;
		else
			return sections.get(i);
	}
}