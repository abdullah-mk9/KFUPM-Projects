import java.io.*;
import java.util.*;

public class StudentDriver {
    private static Scanner scn = new Scanner(System.in);
    private static String options = "1. Display Grade Info for all students\n2. Display Grade Info for a particular student\n3. Display quiz averages for all students\n4. Modify a particular quiz grade for a particular student\n5. Add quiz grades for a particular quiz for all students\n6. Add New Student\n7. Delete Student \n8. Exit\n\nPlease select your choice: ";
    private static Student[] students;

    static {
        try {
            students = readFromFile();
        } catch (IOException e) {
            System.out.print("");
        }
    }

    public static void main(String[] args) throws IOException {
        int choice = 0;
        do {
            try {
                if (doubleLines() > 4) {
                    System.out.println("Invalid number of quizzes. Terminating program...");
                    System.exit(0);
                } else if (SearchID() > 1) {
                    System.out.println("Duplicate ID found. Terminating program...");
                    System.exit(0);
                } else {
                    System.out.print(options);
                    choice = scn.nextInt();
                    switch (choice) {
                        case 1:
                            displayGradeAll(choice);
                            pressEnter();
                            break;
                        case 2:
                            displayGradeIndividual(choice);
                            pressEnter();
                            break;
                        case 3:
                            displayAllAvg(choice);
                            pressEnter();
                            break;
                        case 4:
                            modifyIndividualGrade(choice);
                            pressEnter();
                            break;
                        case 5:
                            addNextQuiz(choice);
                            pressEnter();
                            break;
                        case 6:
                            addStudent(choice);
                            pressEnter();
                            break;
                        case 7:
                            deleteStudent(choice);
                            pressEnter();
                            break;
                        case 8:
                            saveChanges();
                            System.out.print("Thank you for using our program please give us 6/6");
                            System.exit(0);
                        default:
                            System.out.println("\nWrong choice. please enter new choice\n");
                            scn.nextLine();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input.");
                scn.nextLine();
            } catch (IOException e2) {
                System.out.println("File not found. Terminating program...");
                System.exit(0);
            }
        } while (true);
    }

    private static void pressEnter() {
        System.out.println("Press Enter key to continue . . .");
        Scanner enter = new Scanner(System.in);
        String ent = enter.nextLine();
        if (ent.equals("")) {
        } else {
            System.out.println("Press Enter key to continue . . .");
            ent = enter.nextLine();
            while (!ent.equals("")) {
                System.out.println("Press Enter key to continue . . .");
                ent = enter.nextLine();
            }
        }
    }

    private static void saveChanges() throws IOException {
        PrintWriter pwriter = new PrintWriter("grades.txt");
        for (int i = 0; i < studentsLength(); ++i) {
            String grades = "";
            double[] gradeArray = students[i].getGrades();
            for (int k = 0; k < gradesLength(k); ++k) {
                grades += gradeArray[k] + " ";
            }
            String format = "%-10s%-20s %-20s";
            pwriter.printf(format, students[i].getStudentID(), students[i].getName(), grades);
            pwriter.println();
        }
        pwriter.close();
    }

    private static Student[] readFromFile() throws IOException {
        FileInputStream inStream = new FileInputStream("grades.txt");
        Scanner filescn = new Scanner(inStream);
        Student[] Student = new Student[fileLines()];
        for (int i = 0; i < fileLines(); ++i) {
            int ID = filescn.nextInt();
            String name = filescn.next() + " " + filescn.next();
            double[] grade = new double[doubleLines()];
            for (int k = 0; k < doubleLines(); ++k) {
                grade[k] = filescn.nextDouble();
            }
            Student[i] = new Student(ID, name, grade);
        }
        return Student;
    }

    private static int doubleLines() throws IOException {
        FileInputStream instream = new FileInputStream("grades.txt");
        Scanner filescn = new Scanner(instream);
        int count = 0;
        filescn.next();
        filescn.next();
        filescn.next();
        filescn.next();
        while (filescn.hasNextDouble()) {
            filescn.nextDouble();
            ++count;
        }
        return count;
    }

    private static int fileLines() throws IOException {
        FileInputStream instream = new FileInputStream("grades.txt");
        Scanner filescn = new Scanner(instream);
        int count = 0;
        while (filescn.hasNextLine()) {
            filescn.nextLine();
            ++count;
        }
        return count;
    }

    private static void displayGradeAll(int choice) throws IOException {
        System.out.println(SearchID());
        printHeader();
        System.out.println("");
        printArray(students);
    }

    private static void printArray(Student[] array) throws IOException {
        for (int i = 0; i < students.length; ++i) {
            System.out.print(array[i].toString());
            array[i].printGrade();
            System.out.println("");
        }
    }

    private static void displayGradeIndividual(int choice) throws IOException {
        System.out.print("Enter ID to search for: ");
        int dummyID = scn.nextInt();
        int line = search(students, dummyID);
        if (line != -1) {
            printHeader();
            System.out.println();
            System.out.print(students[line].toString());
            students[line].printGrade();
            System.out.println();
        } else {
            System.out.println("Incorrect ID");
        }
    }

    public static int search(Student[] student, int ID) {
        for (int k = 0; k <= students.length - 1; k++) {
            int foundID = student[k].getStudentID();
            if (Objects.equals(foundID, ID)) {
                return k;
            }
        }
        return -1;
    }

    private static void displayAllAvg(int choice) throws IOException {
        String id = "StudentID";
        String name = "Student Name";
        String average = "Average";
        String format = "%-20s%-20s%-20s";
        System.out.printf(format, id, name, average);
        System.out.println();
        for (int i = 0; i < students.length; ++i) {
            System.out.print(students[i].toString());
            double[] grades = students[i].getGrades();
            double grade = 0;
            for (int k = 0; k < gradesLength(k); ++k) {
                grade += grades[k];
            }
            double avg = grade / gradesLength(i);
            System.out.printf(" %.1f", avg);
            System.out.println();
        }
    }

    private static void modifyIndividualGrade(int choice) throws IOException {
        System.out.println("Please enter studentID: ");
        int ID = scn.nextInt();
        int resultOfSearch = search(students, ID);
        if (resultOfSearch == -1) {
            System.out.println("Error: Invalid student ID ");
        } else {
            System.out.println("Please enter quiz number to modify: ");
            int num = scn.nextInt();
            while (num > 4 || num > gradesLength(num) || num < 0) {
                System.out.println("There is no quiz number " + num);
                System.out.println("Please enter quiz number to modify: ");
                num = scn.nextInt();
            }
            System.out.printf("Please enter new quiz %d grade: \n", num);
            double grade = scn.nextDouble();
            if (grade > 100 || grade < 0) {
                System.out.println("Error: Invalid quiz grade ");
            } else {
                System.out.print("Before grade modification:");
                System.out.print(students[resultOfSearch].toString());
                students[resultOfSearch].printGrade();
                System.out.println("");
                System.out.print("After grade modification: ");
                System.out.print(students[resultOfSearch].toString());
                students[resultOfSearch].modifyGrade(grade, num);
                students[resultOfSearch].printGrade();
                System.out.println("");
            }
        }
    }


    private static void addNextQuiz(int choice) throws IOException {
        if ((gradesLength(0)) >= 4) {
            System.out.println("Cannot add more than 4 quizzes.");
        } else {
            double grade;
            System.out.println("Please enter quiz grades for Quiz#" + (gradesLength(0) + 1));
            for (int i = 0; i < students.length; i++) {
                do {
                    System.out.println("Please enter grade for student :" + students[i].getStudentID());
                    grade = scn.nextDouble();
                    if (grade > 100 || grade < 0)
                        System.out.println("Error: Invalid grade.");
                    System.out.println("");
                }
                while (grade > 100 || grade < 0);
                students[i].setGradeArray(arrayCopy(i));
                students[i].addGrade(grade);
                students[i].printGrade();
                System.out.println();
            }
        }
    }

    private static void addStudent(int choice) throws IOException {
        int ID = 0;
        do {
            try {
                System.out.print("Please enter studentID: ");
                ID = scn.nextInt();
                while (search(students, ID) != -1) {
                    System.out.println("Error: Student Already exists.");
                    System.out.println("Please enter studentID: ");
                    ID = scn.nextInt();
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input.");
                System.out.print("Please enter studentID: ");
                scn.nextLine();
            }
        } while (ID < 91000);
        System.out.print("Please Enter Student's name: ");
        String firstName = scn.next();
        String lastName = scn.next();
        String fullName = firstName + " " + lastName;
        System.out.print("Please Enter Student's quiz grades: ");
        int length = students.length - 1;
        double[] newGrades = new double[gradesLength(length)];
        for (int i = 0; i < gradesLength(i); ++i) {
            System.out.printf("Quiz 0%d%n", i + 1);
            newGrades[i] = scn.nextDouble();
            while (newGrades[i] < 0) {
                System.out.println("Wrong input. Please enter positive number.");
                System.out.printf("Quiz 0%d%n", i + 1);
                newGrades[i] = scn.nextDouble();
            }
        }
        students = studentCopy();
        students[students.length - 1] = new Student(ID, fullName, newGrades);
    }

    private static void deleteStudent(int choice) throws IOException {
        System.out.print("Please enter student's ID to delete: ");
        int ID = scn.nextInt();
        int line = search(students, ID);
        while (line == -1) {
            System.out.println("Error: Student Does not exists.");
            System.out.println("Please enter studentID: ");
            ID = scn.nextInt();
            line = search(students, ID);
        }
        if (line == 0) {
            for (int i = 0; i < studentsLength() - 1; ++i) {
                students[i] = students[i + 1];
            }
            studentDel();
        } else {
            for (int i = line; i < studentsLength() - 1; ++i) {
                students[line] = students[line + 1];
                ++line;
            }
            students[studentsLength() - 1] = students[line];
            studentDel();
        }
    }

    private static void printHeader() throws IOException {
        String id = "StudentID";
        String name = "Student Name";
        String quiz = "Quiz";
        String space = "";
        String format = "%-20s%-20s";
        System.out.printf(format, id, name);
        for (int i = 0; i < gradesLength(i); i++) {
            String formatt = "%s0%d";
            System.out.printf(formatt, quiz, i + 1);
            String formattt = "%-5s";
            System.out.printf(formattt, space);
        }
    }

    public static double[] arrayCopy(int i) throws IOException {
        double[] copy = Arrays.copyOf(students[i].getGrades(), (gradesLength(i) + 1));
        return copy;
    }

    public static Student[] studentCopy() throws IOException {
        students = Arrays.copyOf(students, (studentsLength() + 1));
        return students;
    }

    public static Student[] studentDel() throws IOException {
        students = Arrays.copyOf(students, (studentsLength() - 1));
        return students;
    }

    public static int gradesLength(int i) {
        return students[i].getGradeLength();
    }

    public static int studentsLength() {
        return students.length;
    }

    public static int SearchID() throws IOException {
        int count = 0;
        for (int i = 0; i < students.length; i++) {
            count = 0;
            for (int k = 0; k < students.length; k++) {
                if (students[i].getStudentID() == students[k].getStudentID())
                    count++;
            }
        }
        return count;
    }
}