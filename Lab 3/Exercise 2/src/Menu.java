import java.util.List;
import java.util.Scanner;


public class Menu {
    // Exit is a boolean used to exit the program.
    private static boolean exit;
    // Declares the catalogue of courses in the database.
    private static CourseCatalogue catalogue;
    // Kb is used to take in user input Strings.
    private static Scanner kb;
    // Establish current user
    private static Student user;
    // Stores the student name of the user.
    private static DBManager dbmanager;
    // Initalized privately to register and remove registrations of a student in a course (In cases 2 and 3).
    private static final int MAIN_MENU_CHOICES = 8;
    private final static String DASHES = "--------------------"
                                + "--------------------"
                                + "--------------------";


    public static void setDBManager(DBManager dbManager){
        dbmanager = dbManager;
    }

    public static void runMenu(CourseCatalogue aCatalogue) {
        printHeader();
        catalogue = aCatalogue;
        kb = new Scanner(System.in);
        user = chooseObject("Plese enter a number to select your name on the list.", dbmanager.getStudents());
        while(!exit)
        {
            printMenu();
            int choice = getInput(MAIN_MENU_CHOICES);
            performAction(choice);
        }
    }

    private static void printHeader() {
        System.out.println("");
        System.out.println("+----------------------------------------------------------+");
        System.out.println("|                   Course Registration                    |");
        System.out.println("+----------------------------------------------------------+");
        System.out.println("Message for grader of this assignment: The code in its current state is a bit janky.\n");
        System.out.println("Please use \"Nestor Chacin\" with a student number of \"1\" or \"Nasih Nazeem\" with a student number of \"2\" to enter the menu.");
        System.out.println("These students will pre-loaded with courses they have taken in the past and have a grade for. These courses would \n"
                         + "allow for testing for prerequisites during enrolment although that feature is not implemented yet.\n");
        System.out.println("ALSO: A course's status is indicative of whether it has reached an enrolment threshold of 8, which is defined as \n"
                         + "the minimum number of students a course needs to have to be offered. At this time, only ENEL 300 has been preloaded \n"
                         + "with sufficient students to test this feature conveniently.\n");

    }

    private static void printMenu(){
        System.out.println("\nChoose from the following choices:");
        System.out.println("1 - Browse course catalogue");
        System.out.println("2 - Enrol into a course");
        System.out.println("3 - Drop a course");
        System.out.println("4 - View current enrolled courses");
        System.out.println("5 - View student record");
        System.out.println("6 - View entire catalogue");
        System.out.println("7 - View all sections offered");
        System.out.println("8 - View classlist for a course");
        System.out.println("0 - Quit");
    }

    private static <T extends Comparable<? super T>> T chooseObject(String prompt, List<T> choices) {
        System.out.println(prompt + "\n");
        var sortedChoices = choices;
        sortedChoices.sort(null);
        int i = 1;
        for (T choice: sortedChoices) {
            System.out.println(i + " - " + choice);
            i++;
        }
        int selection = getInput(choices.size());
        return choices.get(selection - 1);
    }

    private static int getInput(int choiceNumber) {
        int choice = -1;

        while(choice < 0 || choice > choiceNumber) {
            try {
                System.out.print("\nEnter your choice: ");
                choice = Integer.parseInt(kb.nextLine());
                System.out.print("\n");
            } catch(NumberFormatException e) {
                System.out.println("Invalid Selection. Please try again.");
            }
        }
        return choice;
    }

    private static void performAction(int choice) {
        String nameChoice;
        Course courseChoice;
        CourseOffering sectionChoice;

        switch(choice) {
            case 0:
                //Quit
                exit = true;
                System.out.println("Registration Application Terminated.");
                break;

            case 1:
                //Search catalogue courses
                nameChoice = chooseObject("What course subject are looking for?", catalogue.getSubjects());
                courseChoice = chooseObject("Choose a course from the following to see more information", catalogue.getSubjectCourses(nameChoice));
                catalogue.printCourseSections(courseChoice);
                System.out.print("\n");
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
                    System.out.println("You have succesfully enrolled in " 
                                      + courseChoice.getCourseName() + " " 
                                      + courseChoice.getCourseNum() + " - Section: " 
                                      + sectionChoice.getSecNum());
                } else {
                    System.out.println("You have reached the maximum amount of courses you can enroll in.");
                }
                break;

            case 3:
                //Remove course from student courses
                if (user.getScheduleSize() == 0) {
                    System.out.println("You have no courses to drop!");
                } else {
                    sectionChoice = chooseObject("Select the course you wish drop.", user.getSchedule());
                    user.unenroll(sectionChoice);
                    sectionChoice.removeReg(user, user.getStudentRegistration(sectionChoice));
                    System.out.println("You have succesfully dropped " 
                                      + sectionChoice.getTheCourse().getCourseName() + " " 
                                      + sectionChoice.getTheCourse().getCourseNum() + " - Section: " 
                                      + sectionChoice.getSecNum());
                }
                break;

            case 4:
                //View all courses taken by student
                //TODO print out a message here "current courses are"
                if (user.getScheduleSize() != 0) {
                    System.out.println("Here is your schedule: \n");
                    user.printSchedule();
                } else {
                    System.out.println("You are not currently enrolled in any courses!");
                }
                break;

            case 5:
                //View student record (user)
                var studentRecords = user.getStudentRecords();
                System.out.println(user + "    RECORDS\n");
                System.out.printf(" %-7s | %-6s | %-7s | %-9s\n","Subject", "Number", "Section", "Grade");
                System.out.println(DASHES);
                for (Registration reg: studentRecords) {
                    System.out.println(reg);
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
                System.out.println("The following students are enrolled in "
                                  + sectionChoice.getTheCourse().getCourseName() + " " 
                                  + sectionChoice.getTheCourse().getCourseNum() + " - Section: " 
                                  + sectionChoice.getSecNum() );
                for (Student s: sectionChoice.getClassList()) {
                    System.out.println(s);
                }
                break;
            
            default:
                break;
        }
    }
}
