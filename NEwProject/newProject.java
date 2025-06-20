import java.util.*;

// Course class
class Course {
    String courseName;
    List<Subject> subjects = new ArrayList<>();

    Course(String name) {
        this.courseName = name;
    }

    void addSubject(Subject subject) {
        subjects.add(subject);
    }

    void showSubjects() {
        for (Subject sub : subjects) {
            System.out.println("- " + sub.subjectName);
        }
    }
}

// Subject class
class Subject {
    String subjectName;

    Subject(String name) {
        this.subjectName = name;
    }
}

// Student class
class Student {
    String name, email;
    int age;
    Course selectedCourse;
    List<Subject> chosenSubjects = new ArrayList<>();
    int examScore = -1;

    Student(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age + ", Email: " + email);
        if (selectedCourse != null) {
            System.out.println("Course: " + selectedCourse.courseName);
        }
    }
}

// Employee class (added to fix errors)
class Employee {
    String empName;
    int empId;

    Employee(String empName, int empId) {
        this.empName = empName;
        this.empId = empId;
    }

    void display() {
        System.out.println("\n--- Employee Info ---");
        System.out.println("Name: " + empName + ", ID: " + empId);
    }
}

// EmployeePanel class
class EmployeePanel {
    List<Course> courses;
    List<Student> students;

    EmployeePanel(List<Course> courses, List<Student> students) {
        this.courses = courses;
        this.students = students;
    }

    void employeeMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--- Employee Panel ---");
            System.out.println("1. Add Course");
            System.out.println("2. Add Subject to Course");
            System.out.println("3. View Registered Students");
            System.out.println("4. View Student Exam Results");
            System.out.println("5. Calculate Salary & Tax");
            System.out.println("6. Exit");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter course name: ");
                    String cname = sc.nextLine();
                    courses.add(new Course(cname));
                    System.out.println("Course added.");
                    break;
                case 2:
                    if (courses.isEmpty()) {
                        System.out.println("No courses available.");
                        break;
                    }
                    for (int i = 0; i < courses.size(); i++)
                        System.out.println(i + 1 + ". " + courses.get(i).courseName);
                    System.out.print("Choose course: ");
                    int idx = sc.nextInt() - 1;
                    sc.nextLine();
                    if (idx >= 0 && idx < courses.size()) {
                        System.out.print("Enter subject name: ");
                        String subName = sc.nextLine();
                        courses.get(idx).addSubject(new Subject(subName));
                        System.out.println("Subject added.");
                    }
                    break;
                case 3:
                    if (students.isEmpty()) {
                        System.out.println("No students registered.");
                    } else {
                        for (Student s : students)
                            s.displayInfo();
                    }
                    break;
                case 4:
                    if (students.isEmpty()) {
                        System.out.println("No exam data.");
                    } else {
                        for (Student s : students) {
                            System.out.println(s.name + " - Score: " +
                                    (s.examScore >= 0 ? s.examScore + (s.examScore >= 3 ? " (Pass)" : " (Fail)") : "Not Taken"));
                        }
                    }
                    break;
                case 5:
                    System.out.print("Enter employee name: ");
                    String ename = sc.nextLine();
                    System.out.print("Enter employee ID: ");
                    int eid = sc.nextInt();
                    System.out.print("Enter Basic Salary: ₹");
                    double basic = sc.nextDouble();

                    Employee emp = new Employee(ename, eid);
                    IncomeTax tax = new IncomeTax(basic);
                    Salary salary = new Salary(basic);

                    emp.display();
                    tax.showTax(emp);
                    salary.showSalary(emp, tax.getTax());
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid.");
            }
        } while (choice != 6);
    }
}

// Salary class
class Salary {
    double basic;
    double hra, da, ta, pf, gross, net;

    Salary(double basic) {
        this.basic = basic;
        calculate();
    }

    void calculate() {
        hra = basic * 0.20;
        da = basic * 0.10;
        ta = basic * 0.05;
        pf = basic * 0.12;
        gross = basic + hra + da + ta;
    }

    void computeNet(double incomeTax) {
        net = gross - pf - incomeTax;
    }

    void showSalary(Employee e, double incomeTax) {
        calculate();
        computeNet(incomeTax);
        System.out.println("\n--- Salary Details ---");
        System.out.println("Employee: " + e.empName + " | ID: " + e.empId);
        System.out.printf("Basic Salary: ₹%.2f\n", basic);
        System.out.printf("HRA (20%%): ₹%.2f\n", hra);
        System.out.printf("DA (10%%): ₹%.2f\n", da);
        System.out.printf("TA (5%%): ₹%.2f\n", ta);
        System.out.printf("PF (12%%): ₹%.2f\n", pf);
        System.out.printf("GROSS Salary: ₹%.2f\n", gross);
        System.out.printf("Income Tax (10%%): ₹%.2f\n", incomeTax);
        System.out.printf("NET Salary: ₹%.2f\n", net);
    }
}

// IncomeTax class
class IncomeTax {
    double taxAmount;

    IncomeTax(double basic) {
        this.taxAmount = basic * 0.10;  // 10% of basic
    }

    double getTax() {
        return taxAmount;
    }

    void showTax(Employee e) {
        System.out.printf("Income Tax for %s: ₹%.2f\n", e.empName, taxAmount);
    }
}

// StudentPanel class
class StudentPanel {
    List<Course> courses;
    List<Student> students;

    StudentPanel(List<Course> courses, List<Student> students) {
        this.courses = courses;
        this.students = students;
    }

    void studentMenu(Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();

        Student student = new Student(name, age, email);
        students.add(student);

        System.out.println("Available Courses:");
        for (int i = 0; i < courses.size(); i++)
            System.out.println(i + 1 + ". " + courses.get(i).courseName);
        System.out.print("Choose course: ");
        int cidx = sc.nextInt() - 1;
        sc.nextLine();
        if (cidx >= 0 && cidx < courses.size()) {
            student.selectedCourse = courses.get(cidx);
            student.selectedCourse.showSubjects();

            System.out.println("Choose subjects (comma separated numbers):");
            for (int i = 0; i < student.selectedCourse.subjects.size(); i++) {
                System.out.println((i + 1) + ". " + student.selectedCourse.subjects.get(i).subjectName);
            }

            String[] choices = sc.nextLine().split(",");
            for (String ch : choices) {
                int si = Integer.parseInt(ch.trim()) - 1;
                if (si >= 0 && si < student.selectedCourse.subjects.size()) {
                    student.chosenSubjects.add(student.selectedCourse.subjects.get(si));
                }
            }

            System.out.println("Take Exam (5 MCQs)");
            int score = 0;
            String[][] questions = {
                {"Java is ___?", "1. Platform", "2. Compiler", "3. OS", "4. None", "1"},
                {"Which is not a language?", "1. Java", "2. HTML", "3. Python", "4. C++", "2"},
                {"JVM stands for?", "1. Java Virtual Machine", "2. Just Very Mad", "3. Joint Version Module", "4. None", "1"},
                {"int is of size?", "1. 2", "2. 4", "3. 8", "4. None", "2"},
                {"Which is OOP concept?", "1. Inheritance", "2. Looping", "3. Compilation", "4. None", "1"}
            };
            for (String[] q : questions) {
                System.out.println(q[0]);
                for (int i = 1; i <= 4; i++)
                    System.out.println(q[i]);
                System.out.print("Answer: ");
                String ans = sc.nextLine();
                if (ans.equals(q[5])) score++;
            }
            student.examScore = score;
            System.out.println("Exam Finished. Score: " + score + " => " + (score >= 3 ? "Pass" : "Fail"));
        }
    }
}

// Main class
public class newProject {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Course> courses = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        EmployeePanel employeePanel = new EmployeePanel(courses, students);
        StudentPanel studentPanel = new StudentPanel(courses, students);

        int opt;
        do {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Admin Login");
            System.out.println("2. Student Register/Login");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1:
                    employeePanel.employeeMenu(sc);
                    break;
                case 2:
                    studentPanel.studentMenu(sc);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (opt != 3);
    }
}
