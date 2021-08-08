import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

//This class is simulating a database for our
//program

//Assumption, courses are offered every year in the same number of
//sections. An unlimited number of students may possess registration
//for any course offering (grade records + current). Currently enrolled
//Students are limited to section caps.
public class DBManager {
	
	private ArrayList<Course> courseList;
	private ArrayList<Student> currentStudents;
	private static CourseCatalogue catalogue;
	private static final String OFFERINGS_PATHNAME = ".\\database\\offerings.dat";
	private static final String OFFERINGS_TEST = ".\\database\\testfile.dat";
	private 

	public DBManager (CourseCatalogue aCatalogue) {
		this.courseList = new ArrayList<Course>();
		catalogue = aCatalogue;
		currentStudents = new ArrayList<Student>();
	}

	public ArrayList<Course> readFromDataBase() {
		retrieveCourses();
		setPreReqs();
		setCurrentStudents();
		batchOfferingCreation();
		batchRegAttempts();
		batchEnrolAttempts();
		File newFile = new File(OFFERINGS_PATHNAME);
		RandomAccessFile raFile = null;
		Scanner scan= new Scanner(System.in);
		System.out.println(newFile.exists());
		if(!newFile.exists() || !newFile.isFile()) {
			try {
				raFile = new RandomAccessFile(OFFERINGS_PATHNAME,"rw");
				for(Course c: courseList) {
					for (int i = 0; i < c.getSections().size(); i++) {
						StringBuffer courseName = new StringBuffer(c.getCourseName());
						courseName.setLength(4);
						raFile.writeChars(courseName.toString());
						int courseNum = c.getCourseNum();
						raFile.writeInt(courseNum);
						int secNum = c.getSections().get(i).getSecNum();
						raFile.writeInt(secNum);
						int secCap = c.getSections().get(i).getSecCap();
						raFile.writeInt(secCap);
						int secEnrolment = c.getSections().get(i).getSectionEnrolment();
						raFile.writeInt(secEnrolment);
						StringBuffer secStatus = new StringBuffer(c.getSections().get(i).getStatus());
						secStatus.setLength(10);
						raFile.writeChars(secStatus.toString());
					}
				}
			} catch(IOException e) {
				System.out.println("error opening file");
			} try {
				raFile.close();
			} catch(IOException e) {
				System.out.println("error closing file");
			}

			try {
				raFile = new RandomAccessFile(OFFERINGS_PATHNAME,"rw");
				int reccordSize = 4 * Character.BYTES + 4 * Integer.BYTES + 10 * Character.BYTES;
				System.out.println("record size is "+ reccordSize);
				System.out.println("current file pointer position "+ raFile.getFilePointer());
				System.out.println("3rd course on file is...");
				raFile.seek(2*reccordSize);
				char scanName[]= new char[4];
				for (int i = 0; i < 4; i++)
					scanName[i] = raFile.readChar();
				String cName = new String(scanName);
				System.out.print("3rd course on file: " + cName + " " + raFile.readInt() + " Section: " + raFile.readInt() + " Capacity: " + raFile.readInt() + " Enrolment: " + raFile.readInt() + " Status: ");
				char scanStatus[]= new char[10];
				for (int i = 0; i < 10; i++)
					scanStatus[i] = raFile.readChar();
				String status = new String(scanStatus);
				System.out.println(status);
			}
			catch(IOException e){
				System.out.println("error opening file");	
			}
			
			try {
				raFile.close();
			} catch(IOException e) {
				System.out.println("error closing file");
			}
		}
		return courseList;
	}

	public void printAllSectionsRA() {
		RandomAccessFile raFile;
		int sectionAmount = 0;
		for(Course c: courseList) {
			for (int i = 0; i < c.getSections().size(); i++) {
				sectionAmount++;
			}
		}
		try {
			raFile = new RandomAccessFile(OFFERINGS_PATHNAME,"rw");
			for (int i = 0; i < sectionAmount; i++) {
				int reccordSize = 4 * Character.BYTES + 4 * Integer.BYTES + 10 * Character.BYTES;
				raFile.seek(i * reccordSize);
				char scanName[]= new char[4];
				for (int j = 0; j < 4; j++)
					scanName[j] = raFile.readChar();
				String cName = new String(scanName);
				System.out.print("[index: " + i + "] course on file: " + cName + " " + raFile.readInt() + " Section: " + raFile.readInt() + " Capacity: " + raFile.readInt() + " Enrolment: " + raFile.readInt() + " Status: ");
				char scanStatus[]= new char[10];
				for (int j = 0; j < 10; j++)
					scanStatus[j] = raFile.readChar();
				String status = new String(scanStatus);
				System.out.println(status);	
			}
			raFile.close();
		} catch(IOException e) {
			System.out.println("error opening file");	
		}
	}



	public ArrayList<Student> getStudents() {
		return currentStudents;
	}

	private void retrieveCourses() {
		courseList.add(new Course ("CHEM", 209));
		courseList.add(new Course ("ENGG", 233));
		courseList.add(new Course ("MATH", 275));
		courseList.add(new Course ("MATH", 211));
		courseList.add(new Course ("ENGG", 225));
		courseList.add(new Course ("ENGG", 201));
		courseList.add(new Course ("ENGG", 202));
		courseList.add(new Course ("MATH", 277));
		courseList.add(new Course ("PHYS", 259));
		courseList.add(new Course ("ENGG", 200));
		courseList.add(new Course ("MATH", 177));
		courseList.add(new Course ("PHYS", 365));
		courseList.add(new Course ("MATH", 375));
		courseList.add(new Course ("ENCM", 335));
		courseList.add(new Course ("ENSF", 337));
		courseList.add(new Course ("ENEL", 353));
		courseList.add(new Course ("ENEL", 101));
		courseList.add(new Course ("ENEL", 361));
		courseList.add(new Course ("ENEL", 343));
		courseList.add(new Course ("ENEL", 327));
		courseList.add(new Course ("ENEL", 300));
		courseList.add(new Course ("ENCM", 369));
		courseList.add(new Course ("ENEL", 475));
		courseList.add(new Course ("ENEL", 469));
		courseList.add(new Course ("ENEL", 487));
		courseList.add(new Course ("ENCM", 467));
		courseList.add(new Course ("ENEL", 453));
		courseList.add(new Course ("ENEL", 419));
		courseList.add(new Course ("ENCM", 511));
		courseList.add(new Course ("ENEL", 102));
		courseList.add(new Course ("ENEL", 441));
		courseList.add(new Course ("ENEL", 400));
		courseList.add(new Course ("ENEL", 471));
		courseList.add(new Course ("ENSF", 409));
		courseList.add(new Course ("CPSC", 319));
		courseList.add(new Course ("ENEL", 567));
		courseList.add(new Course ("CPSC", 457));
		courseList.add(new Course ("ENEL", 573));
		courseList.add(new Course ("ENCM", 501));
	}

	
	private void setPreReqs() {
		addPreReq("MATH", 277, "MATH", 275);
		addPreReq("MATH", 277, "MATH", 211);
		addPreReq("ENEL", 101, "ENGG", 233);
		addPreReq("ENEL", 300, "ENGG", 233);
		addPreReq("ENEL", 300, "ENGG", 225);
		addPreReq("ENCM", 335, "ENGG", 233);
		addPreReq("ENSF", 337, "ENGG", 233);
		addPreReq("PHYS", 259, "MATH", 375);
	}

	private void batchOfferingCreation () {
		catalogue.createCourseOffering(searchDBcourse("CHEM", 209), 1, 120);
		catalogue.createCourseOffering(searchDBcourse("ENGG", 233), 1, 115);
		catalogue.createCourseOffering(searchDBcourse("MATH", 275), 1, 60);
		catalogue.createCourseOffering(searchDBcourse("MATH", 275), 2, 60);
		catalogue.createCourseOffering(searchDBcourse("MATH", 211), 1, 45);
		catalogue.createCourseOffering(searchDBcourse("ENGG", 225), 1, 200);
		catalogue.createCourseOffering(searchDBcourse("ENGG", 201), 1, 110);
		catalogue.createCourseOffering(searchDBcourse("ENGG", 202), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("MATH", 277), 1, 80);
		catalogue.createCourseOffering(searchDBcourse("PHYS", 259), 1, 180);
		catalogue.createCourseOffering(searchDBcourse("ENGG", 200), 1, 180);
		catalogue.createCourseOffering(searchDBcourse("MATH", 177), 1, 8); // LOW CAP FOR TESTING PURPOSES
		catalogue.createCourseOffering(searchDBcourse("PHYS", 365), 1, 80);
		catalogue.createCourseOffering(searchDBcourse("MATH", 375), 1, 80);
		catalogue.createCourseOffering(searchDBcourse("ENCM", 335), 1, 80);
		catalogue.createCourseOffering(searchDBcourse("ENSF", 337), 1, 80);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 353), 1, 120);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 101), 1, 150);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 361), 1, 80);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 343), 1, 80);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 327), 1, 80);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 300), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENCM", 369), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 475), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 469), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 487), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENCM", 467), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 453), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 419), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENCM", 511), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 102), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 441), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 400), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 471), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENSF", 409), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("CPSC", 319), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 567), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("CPSC", 457), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENEL", 573), 1, 75);
		catalogue.createCourseOffering(searchDBcourse("ENCM", 501), 1, 75);
	}

	
	private void batchRegAttempts() {
		attemptRegistration(searchDBstudent("Nestor Chacin", 1), 
		                    searchDBcourse("MATH", 211).getCourseOfferingAt(0),
							'B');
		attemptRegistration(searchDBstudent("Nestor Chacin", 1), 
		                    searchDBcourse("ENGG", 225).getCourseOfferingAt(0),
							'A');
		attemptRegistration(searchDBstudent("Nestor Chacin", 1), 
		                    searchDBcourse("PHYS", 259).getCourseOfferingAt(0),
							'B');
		attemptRegistration(searchDBstudent("Nasih Nazeem", 2), 
		                    searchDBcourse("ENGG", 202).getCourseOfferingAt(0),
							'A');
		attemptRegistration(searchDBstudent("Nasih Nazeem", 2), 
		                    searchDBcourse("ENEL", 300).getCourseOfferingAt(0),
							'A');
		attemptRegistration(searchDBstudent("Nasih Nazeem", 2), 
		                    searchDBcourse("ENSF", 337).getCourseOfferingAt(0),
							'A');
	}

	private void batchEnrolAttempts () {
		attemptEnrol(searchDBstudent("Timmy", 1001),
		             searchDBcourse("ENEL", 300).getCourseOfferingAt(0));
		attemptEnrol(searchDBstudent("Lizzy", 1010),
		             searchDBcourse("ENEL", 300).getCourseOfferingAt(0));
		attemptEnrol(searchDBstudent("Daenerys", 1011),
		             searchDBcourse("ENEL", 300).getCourseOfferingAt(0));
		attemptEnrol(searchDBstudent("Bob", 100),
		             searchDBcourse("ENEL", 300).getCourseOfferingAt(0));
		attemptEnrol(searchDBstudent("Steve", 101),
		             searchDBcourse("ENEL", 300).getCourseOfferingAt(0));
	    attemptEnrol(searchDBstudent("Rambo", 110),
		             searchDBcourse("ENEL", 300).getCourseOfferingAt(0));
		attemptEnrol(searchDBstudent("Mo", 111),
		             searchDBcourse("ENEL", 300).getCourseOfferingAt(0));
		attemptEnrol(searchDBstudent("Jim", 000),
		             searchDBcourse("ENEL", 300).getCourseOfferingAt(0));
	 
	}
	
	private void attemptRegistration (Student aStudent, CourseOffering anOffering, char aGrade) {
		Registration regAttempt = new Registration(aStudent, anOffering, aGrade);
		regAttempt.completeRegistration(aStudent, anOffering);
	}

	private void attemptEnrol (Student aStudent, CourseOffering anOffering) {
		var enrolAttempt = new Registration();
		enrolAttempt.completeRegistration(aStudent, anOffering);
		aStudent.addToSchedule(enrolAttempt);
		anOffering.addToStudentEnrollment(enrolAttempt);
	}

	private void setCurrentStudents() {
		var student1 = new Student("Nestor Chacin", 1);
		currentStudents.add(student1);
		var student2 = new Student("Nasih Nazeem", 2);
		currentStudents.add(student2);
		var student3 = new Student("Timmy", 1001);
		currentStudents.add(student3);
		var student4 = new Student("Lizzy", 1010);
		currentStudents.add(student4);
		var student5 = new Student("Daenerys", 1011);
		currentStudents.add(student5);
		var student6 = new Student("Bob", 100);
		currentStudents.add(student6);
		var student7 = new Student("Steve", 101);
		currentStudents.add(student7);
		var student8 = new Student("Rambo", 110);
		currentStudents.add(student8);
		var student9 = new Student("Mo", 111);
		currentStudents.add(student9);
		var student10 = new Student("Jim", 0);
		currentStudents.add(student10);
	}


	private void addPreReq (String targetCourseName, int targetCourseNum, String preReqName, int preReqNum) {
		Course target = searchDBcourse(targetCourseName, targetCourseNum);
		if (target != null) {
			target.addCoursePreReq(searchDBcourse(preReqName, preReqNum));
		} else {
			System.out.println("DB failed to add " + targetCourseName + targetCourseNum + "to prereqs.");
		}
		
	}
 	public Course searchDBcourse (String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) &&
					courseNum == c.getCourseNum()) {
				return c;
			}	
		}
		displayDBsetUpError();
		return null;
	}

	public Student searchDBstudent (String studentName, int ID) {
		for (Student s : currentStudents) {
			if (studentName.equals(s.getStudentName()) &&
			    ID == s.getStudentId()) {
					return s;
				}
		}
		System.out.println("Student not found in Database search");
		return null;
	}


	private void displayDBsetUpError() {
		// TODO Auto-generated method stub
		System.err.println("Database was set up wrong!");
		
	}

}
