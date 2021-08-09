import java.util.ArrayList;
import java.util.Collections;

public class CourseCatalogue {
	
	private ArrayList <Course> courseList;
	private final String DASHES = "--------------------"
								+ "--------------------"
								+ "--------------------"
								+ "--------------------";
	public CourseCatalogue () {
		loadFromDataBase ();
	}
	
	private void loadFromDataBase() {
		// TODO Auto-generated method stub
		DBManager db = new DBManager(this);
		setCourseList(db.readFromDataBase());
		OutgoingMenu.setDBManager(db);
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
			if(courseSubjectList.contains(c.getCourseName() + "\n")) {
				continue;
			} else {
				courseSubjectList.add(c.getCourseName() + "\n");
			}
		}
		return courseSubjectList;
	}

	public ArrayList<Course> getSubjectCourses(String subject) {
		var subjectCourses = new ArrayList<Course>();
		for (Course c: courseList) {
			if(c.getCourseName().equals(subject.trim())) {
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
	public String toString() {
		StringBuilder catalogue = new StringBuilder("");
		var sortedCourseList = courseList;
		Collections.sort(sortedCourseList);
		for (Course aCourse: sortedCourseList) {
			catalogue.append(aCourse.toString());
		}
		return catalogue.toString();
	}

	public String allSectionsToString() {
		StringBuilder sections = new StringBuilder("");
		sections.append(" Subject | Number | Section | Capacity | Enrolment | Status\n"
						+ DASHES + "\n");
		var sortedCourseList = courseList;
		Collections.sort(sortedCourseList);
		for (Course c: sortedCourseList) {
			sections.append(c.getTabulatedSections());
		}
		return sections.toString();
	}
}
