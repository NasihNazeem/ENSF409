import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class OutgoingMenu {
    // Exit is a boolean used to exit the program.
    private boolean exit;
    // Declares the catalogue of courses in the database.
    private static CourseCatalogue catalogue;
    // Establish current user
    private static Student user;
    // Stores the student name of the user.
    private static DBManager dbmanager;
    // Initalized privately to register and remove registrations of a student in a course (In cases 2 and 3).
    private static final int MAIN_MENU_CHOICES = 9;
    private final static String DASHES = "--------------------"
                                       + "--------------------"
                                       + "--------------------";
    private static final String menu = "Choose from the following choices:\n"
                                     + "1 - Browse course catalogue\n"
                                     + "2 - Enrol into a course\n"
                                     + "3 - Drop a course\n"
                                     + "4 - View current enrolled courses\n"
                                     + "5 - View student record\n"
                                     + "6 - View entire catalogue\n"
                                     + "7 - View all sections offered\n"
                                     + "8 - View classlist for a course\n"
                                     + "9 - View entire catalogue FROM RandomAccessFile\n"
                                     + "0 - Quit";

    
    // Networking fields
    private Socket aSocket;
	private ServerSocket serverSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	
    public OutgoingMenu(int port, CourseCatalogue aCatalogue) {
		try {
			this.serverSocket = new ServerSocket(port);
            this.setAsocket();
            this.socketIn = new BufferedReader (new InputStreamReader(this.aSocket.getInputStream()));
            this.socketOut = new PrintWriter((this.aSocket.getOutputStream()), true);

            
		} catch (IOException e) {
			System.out.println("IO Error occured when opening the socket");
			e.printStackTrace();
		}
	}

	public void relay(CourseCatalogue aCatalogue) {
        catalogue = aCatalogue;
		int slct;
        user = chooseObject("Plese enter a number to select your name on the list.", dbmanager.getStudents());
		while (!exit) {
			socketOut.print(menu);
            slct = getInput(MAIN_MENU_CHOICES);
            performAction(slct);
        }
        try {
            this.socketIn.close();
            this.socketOut.close();
        } catch (IOException e) {
            System.out.println("Error! socketIn.close() did not do its thing!!");
            e.printStackTrace();
        }
    }
    
    public static void setDBManager(DBManager dbManager){
        dbmanager = dbManager;
    }

    public void setAsocket() {
        try {
            this.aSocket = this.serverSocket.accept();
            System.out.println("Connection accepted by server!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T extends Comparable<? super T>> T chooseObject(String prompt, List<T> choices) {
        socketOut.println(prompt + "\n");
        var sortedChoices = choices;
        sortedChoices.sort(null);
        int i = 1;
        for (T choice: sortedChoices) {
            socketOut.println(i + " - " + choice);
            i++;
        }
        int selection = getInput(choices.size());
        return choices.get(selection - 1);
    }

    private int getInput(int choiceNumber) {
        int choice = -1;

        while(choice < 0 || choice > choiceNumber) {
            try {
                socketOut.println("\nEnter your choice: ");
                socketOut.println("\u0004");
                choice = socketIn.read();
            } catch(NumberFormatException e) {
                System.out.println("Invalid Selection. Please try again.");
            } catch (IOException e) {
                System.out.println("IO error happened with socketin.read()");
                e.printStackTrace();
            }
        }
        return choice;
    }

    private void performAction(int choice) {
        String nameChoice;
        Course courseChoice;
        CourseOffering sectionChoice;

        switch(choice) {
            case 0:
                //Quit
                exit = true;
                this.socketOut.println("Registration Application Terminated.");
                break;

            case 1:
                //Search catalogue courses
                nameChoice = chooseObject("What course subject are looking for?", catalogue.getSubjects());
                courseChoice = chooseObject("Choose a course from the following to see more information", catalogue.getSubjectCourses(nameChoice));
                catalogue.printCourseSections(courseChoice);
                this.socketOut.print("\n");
                break;
                
            case 2:
                //Add course to student courses
                if(user.getScheduleSize() < 6){
                    nameChoice = chooseObject("What's the subject of the course you want to enrol into?", catalogue.getSubjects());
                    courseChoice = chooseObject("Choose a course from the following to see available sections", catalogue.getSubjectCourses(nameChoice));
                    sectionChoice = chooseObject("Choose a section to enrol into", courseChoice.getSections());
                    Registration studentRegistration = new Registration();
                    studentRegistration.completeRegistration(user, sectionChoice);
                    user.addToSchedule(studentRegistration);
                    sectionChoice.addToStudentEnrollment(studentRegistration);
                    this.socketOut.println("You have succesfully enrolled in " 
                                      + courseChoice.getCourseName() + " " 
                                      + courseChoice.getCourseNum() + " - Section: " 
                                      + sectionChoice.getSecNum());
                } else {
                    this.socketOut.println("You have reached the maximum amount of courses you can enroll in.");
                }
                break;

            case 3:
                //Remove course from student courses
                if (user.getScheduleSize() == 0) {
                    this.socketOut.println("You have no courses to drop!");
                } else {
                    sectionChoice = chooseObject("Select the course you wish drop.", user.getSchedule());
                    user.unenroll(sectionChoice);
                    sectionChoice.removeReg(user, user.getStudentRegistration(sectionChoice));
                    this.socketOut.println("You have succesfully dropped " 
                                      + sectionChoice.getTheCourse().getCourseName() + " " 
                                      + sectionChoice.getTheCourse().getCourseNum() + " - Section: " 
                                      + sectionChoice.getSecNum());
                }
                break;

            case 4:
                //View all courses taken by student
                if (user.getScheduleSize() != 0) {
                    this.socketOut.println("Here is your schedule: \n");
                    user.printSchedule();
                } else {
                    this.socketOut.println("You are not currently enrolled in any courses!");
                }
                break;

            case 5:
                //View student record (user)
                var studentRecords = user.getStudentRecords();
                this.socketOut.println(user + "    RECORDS\n");
                this.socketOut.printf(" %-7s | %-6s | %-7s | %-9s\n","Subject", "Number", "Section", "Grade");
                this.socketOut.println(DASHES);
                for (Registration reg: studentRecords) {
                    this.socketOut.println(reg);
                }
                break;          

            case 6:
                //View all courses in catalogue
                catalogue.printCatalogue();
                break;
            

            case 7:
                // View all courses in catalogue and their sections
                catalogue.printAllSections();
                break;

            case 8:
                nameChoice = chooseObject("Choose a subject to view available options.", catalogue.getSubjects());
                courseChoice = chooseObject("Choose a course to view available sections.", catalogue.getSubjectCourses(nameChoice));
                sectionChoice = chooseObject("Choose a section to see its classlist.", courseChoice.getSections());
                this.socketOut.println("The following students are enrolled in "
                                  + sectionChoice.getTheCourse().getCourseName() + " " 
                                  + sectionChoice.getTheCourse().getCourseNum() + " - Section: " 
                                  + sectionChoice.getSecNum() );
                for (Student s: sectionChoice.getClassList()) {
                    this.socketOut.println(s);
                }
                break;
            case 9:
                dbmanager.printAllSectionsRA();
                break;
            default:
                break;
        }
    }
}

