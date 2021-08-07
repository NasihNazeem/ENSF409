import java.util.ArrayList;
import java.util.Collections;

public class CourseCatalogue {
	
	private ArrayList <Course> courseList;
	private final String DASHES = "--------------------"
								+ "--------------------"
								+ "--------------------";
	public CourseCatalogue () {
		loadFromDataBase ();
	}
	
	private void loadFromDataBase() {
		// TODO Auto-generated method stub
		DBManager db = new DBManager(this);
		setCourseList(db.readFromDataBase());
		Client.setDBManager(db);
		guiClient.setDBManager(db);
		
		
	}
	public void createCourseOffering (Course c, int secNum, int secCap) {
		if (c!= null) {
			CourseOffering theOffering = new CourseOffering (secNum, secCap);
			
			c.addOffering(theOffering);
		}
	}
	public Course searchCat (String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equalsIgnoreCase(c.getCourseName()) &&
					courseNum == c.getCourseNum()) {
				return c;
			}	
		}
		displayCourseNotFoundError();
		return null;
	}

	//Typically, methods that are called from other methods of the class
	//are private and are not exposed for use by other classes.
	//These methods are refereed to as helper methods or utility methods
	private void displayCourseNotFoundError() {
		// TODO Auto-generated method stub
		System.err.println("Course was not found!");
		
	}
	public ArrayList <Course> getCourseList() {
		return courseList;
	}
	public ArrayList<String> getSubjects() {
		var courseSubjectList = new ArrayList<String>();
		for (Course c : courseList) {
			if(courseSubjectList.contains(c.getCourseName())) {
				continue;
			} else {
				courseSubjectList.add(c.getCourseName());
			}
		}
		return courseSubjectList;
	}

	public ArrayList<Course> getSubjectCourses(String subject) {
		var subjectCourses = new ArrayList<Course>();
		for (Course c: courseList) {
			if(c.getCourseName().equals(subject)) {
				subjectCourses.add(c);
			} else {
				continue;
			}
		}
		return subjectCourses;
	}

	public ArrayList<CourseOffering> getCourseSections(Course course) {
		var courseSections = new ArrayList<CourseOffering>();
		for (CourseOffering section: course.getSections()) {
			courseSections.add(section);
		}
		return courseSections;
	}


	public void setCourseList(ArrayList <Course> courseList) {
		this.courseList = courseList;
	}

	@Override
	public String toString () {
		String st = "All courses in the catalogue: \n";
		for (Course c : courseList) {
			st += c;  //This line invokes the toString() method of Course
			st += "\n";
		}
		return st;
	}

	public void printCatalogue() {
		var sortedCourseList = courseList;
		Collections.sort(sortedCourseList);
		for (Course c: sortedCourseList) {
			System.out.println(c);
		}
	}

	public void printCourseSections(Course course) {
		System.out.printf(" %-7s | %-6s | %-7s | %-8s | %-9s | %-9s\n","Subject", "Number", "Section", "Capacity", "Enrolment", "Status");
		System.out.println(DASHES);
		for (int i = 0; i < course.getSections().size(); i++) {
			System.out.printf(" %-7s | %-6s | %-7s | %-8s | %-9s | %-9s\n",
								  course.getCourseName(),
								  course.getCourseNum(),
								  course.getSections().get(i).getSecNum(),
								  course.getSections().get(i).getSecCap(),
								  course.getSections().get(i).getSectionEnrolment(),
								  course.getSections().get(i).getStatus());
		}
	}

	public void printAllSections() {
		System.out.printf(" %-7s | %-6s | %-7s | %-8s | %-9s | %-9s\n","Subject", "Number", "Section", "Capacity", "Enrolment", "Status");
		System.out.println(DASHES);
		var sortedCourseList = courseList;
		Collections.sort(sortedCourseList);
		for (Course c: sortedCourseList) {
			for (int i = 0; i < c.getSections().size(); i++) {
				System.out.printf(" %-7s | %-6s | %-7s | %-8s | %-9s | %-9s\n",
								  c.getCourseName(),
								  c.getCourseNum(),
								  c.getSections().get(i).getSecNum(),
								  c.getSections().get(i).getSecCap(),
								  c.getSections().get(i).getSectionEnrolment(),
								  c.getSections().get(i).getStatus());
			}
		}
	}
}
