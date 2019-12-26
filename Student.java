public class Student {
    private int studentID;
    private String name;
    private double[] grade;

    public Student(int studentID, String name, double[] grade) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Error");
        } else if (studentID < 91000) {
            throw new IllegalArgumentException("Error");
        } else {
            this.studentID = studentID;
            this.name = name;
            this.grade = grade;
        }
    }

    public int getStudentID() {
        return this.studentID;
    }


    public String getName() {
        return this.name;
    }


    public void printGrade(){
        for (int i = 0 ; i<this.grade.length; i++){
            System.out.print(this.grade[i]);
            String space = "";
            String formattt = "%-8s";
            System.out.printf(formattt,space);
        }
    }


    public double[] getGrades(){
        return this.grade;
    }
    public void addGrade(double grade) throws IllegalArgumentException {
        if (grade < 0 || grade>100) {
            throw new IllegalArgumentException("Invalid grade");
        } else { this.grade[getGradeLength()-1]= grade;
        }
    }

    public void setGradeArray (double[] gradeCopy) throws IllegalArgumentException{
        this.grade = gradeCopy;
    }


    public void modifyGrade(double grade, int quizNum) throws IllegalArgumentException {
        this.grade[quizNum-1] = grade;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentID == student.studentID;
    }

    public String toString() {
        return String.format("%-20d %-20s", this.studentID, this.name);
    }

    public int getGradeLength(){
        return this.grade.length;
    }

}