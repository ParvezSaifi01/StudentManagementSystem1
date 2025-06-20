
// File: Main.java
import java.util.*;

// Base User class (Demonstrates Inheritance)
abstract class User {
    protected String name;
    protected String email;
    protected int age;
    
    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }
    
    // Abstract method (Polymorphism)
    public abstract void displayMenu();
    
    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
}

// Subject class (Encapsulation)
class Subject {
    private String subjectName;
    private String subjectCode;
    private List<Question> questions;
    
    public Subject(String subjectName, String subjectCode) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.questions = new ArrayList<>();
        initializeQuestions();
    }
    
    private void initializeQuestions() {
        // Adding sample questions for demonstration
        questions.add(new Question("What is Java?", 
            new String[]{"Programming Language", "Coffee", "Island", "None"}, 0));
        questions.add(new Question("What is OOP?", 
            new String[]{"Object Oriented Programming", "Open Office Project", "Online Ordering Platform", "None"}, 0));
        questions.add(new Question("Which is not a Java keyword?", 
            new String[]{"class", "interface", "main", "goto"}, 3));
        questions.add(new Question("What is encapsulation?", 
            new String[]{"Data hiding", "Inheritance", "Polymorphism", "Abstraction"}, 0));
        questions.add(new Question("Which access modifier is most restrictive?", 
            new String[]{"public", "protected", "default", "private"}, 3));
    }
    
    // Getters
    public String getSubjectName() { return subjectName; }
    public String getSubjectCode() { return subjectCode; }
    public List<Question> getQuestions() { return questions; }
    
    @Override
    public String toString() {
        return subjectCode + " - " + subjectName;
    }
}

// Question class for MCQs
class Question {
    private String questionText;
    private String[] options;
    private int correctAnswer;
    
    public Question(String questionText, String[] options, int correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
    
    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswer() { return correctAnswer; }
}

// Course class
class Course {
    private String courseName;
    private String courseCode;
    private List<Subject> subjects;
    
    public Course(String courseName, String courseCode) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.subjects = new ArrayList<>();
    }
    
    public void addSubject(Subject subject) {
        subjects.add(subject);
    }
    
    // Getters
    public String getCourseName() { return courseName; }
    public String getCourseCode() { return courseCode; }
    public List<Subject> getSubjects() { return subjects; }
    
    @Override
    public String toString() {
        return courseCode + " - " + courseName;
    }
}

// Exam Result class
class ExamResult {
    private String studentEmail;
    private String subjectCode;
    private int score;
    private int totalQuestions;
    private boolean passed;
    
    public ExamResult(String studentEmail, String subjectCode, int score, int totalQuestions) {
        this.studentEmail = studentEmail;
        this.subjectCode = subjectCode;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.passed = (score >= (totalQuestions * 0.6)); // 60% passing criteria
    }
    
    // Getters
    public String getStudentEmail() { return studentEmail; }
    public String getSubjectCode() { return subjectCode; }
    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public boolean isPassed() { return passed; }
    
    @Override
    public String toString() {
        return String.format("Student: %s | Subject: %s | Score: %d/%d | Status: %s", 
            studentEmail, subjectCode, score, totalQuestions, passed ? "PASSED" : "FAILED");
    }
}

// Student class (Inherits from User)
class Student extends User {
    private Course selectedCourse;
    private List<Subject> selectedSubjects;
    private List<ExamResult> examResults;
    
    public Student(String name, String email, int age) {
        super(name, email, age);
        this.selectedSubjects = new ArrayList<>();
        this.examResults = new ArrayList<>();
    }
    
    @Override
    public void displayMenu() {
        System.out.println("\n===== STUDENT MENU =====");
        System.out.println("1. View Available Courses");
        System.out.println("2. Select Course");
        System.out.println("3. Choose Subjects");
        System.out.println("4. Take Exam");
        System.out.println("5. View My Results");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
    }
    
    // Setters and Getters
    public void setSelectedCourse(Course course) { this.selectedCourse = course; }
    public Course getSelectedCourse() { return selectedCourse; }
    public List<Subject> getSelectedSubjects() { return selectedSubjects; }
    public List<ExamResult> getExamResults() { return examResults; }
    
    public void addSelectedSubject(Subject subject) {
        if (!selectedSubjects.contains(subject)) {
            selectedSubjects.add(subject);
        }
    }
    
    public void addExamResult(ExamResult result) {
        examResults.add(result);
    }
}

// Admin class (Inherits from User)
class Admin extends User {
    
    public Admin(String name, String email, int age) {
        super(name, email, age);
    }
    
    @Override
    public void displayMenu() {
        System.out.println("\n===== ADMIN MENU =====");
        System.out.println("1. Add Course");
        System.out.println("2. Add Subject to Course");
        System.out.println("3. View All Courses");
        System.out.println("4. View All Students");
        System.out.println("5. View Exam Results");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
    }
}

// Main System class (Composition and Aggregation)
class StudentManagementSystem {
    private List<Course> courses;
    private List<Student> students;
    private List<ExamResult> allExamResults;
    private Scanner scanner;
    
    // Default admin credentials
    private final String ADMIN_EMAIL = "admin@sms.com";
    private final String ADMIN_PASSWORD = "admin123";
    
    public StudentManagementSystem() {
        courses = new ArrayList<>();
        students = new ArrayList<>();
        allExamResults = new ArrayList<>();
        scanner = new Scanner(System.in);
        initializeDefaultData();
    }
    
    private void initializeDefaultData() {
        // Adding default courses and subjects
        Course javaCourse = new Course("Java Programming", "JAVA101");
        javaCourse.addSubject(new Subject("Core Java", "JAVA-CORE"));
        javaCourse.addSubject(new Subject("Advanced Java", "JAVA-ADV"));
        
        Course pythonCourse = new Course("Python Programming", "PY101");
        pythonCourse.addSubject(new Subject("Python Basics", "PY-BASIC"));
        pythonCourse.addSubject(new Subject("Python Advanced", "PY-ADV"));
        
        Course webCourse = new Course("Web Development", "WEB101");
        webCourse.addSubject(new Subject("HTML/CSS", "WEB-FRONT"));
        webCourse.addSubject(new Subject("JavaScript", "WEB-JS"));
        
        courses.add(javaCourse);
        courses.add(pythonCourse);
        courses.add(webCourse);
    }
    
    public void start() {
        System.out.println("🎓 Welcome to Student Management System 🎓");
        
        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Student Registration");
            System.out.println("3. Student Login");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    studentRegistration();
                    break;
                case 3:
                    studentLogin();
                    break;
                case 4:
                    System.out.println("Thank you for using Student Management System!");
                    return;
                default:
                    System.out.println("❌ Invalid choice! Please try again.");
            }
        }
    }
    
    private void adminLogin() {
        System.out.print("Enter admin email: ");
        String email = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        
        if (ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password)) {
            System.out.println("✅ Admin login successful!");
            Admin admin = new Admin("Administrator", email, 30);
            adminMenu(admin);
        } else {
            System.out.println("❌ Invalid admin credentials!");
        }
    }
    
    private void adminMenu(Admin admin) {
        while (true) {
            admin.displayMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    addSubjectToCourse();
                    break;
                case 3:
                    viewAllCourses();
                    break;
                case 4:
                    viewAllStudents();
                    break;
                case 5:
                    viewExamResults();
                    break;
                case 6:
                    System.out.println("👋 Admin logged out successfully!");
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
    
    private void addCourse() {
        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        
        Course newCourse = new Course(courseName, courseCode);
        courses.add(newCourse);
        System.out.println("✅ Course added successfully!");
    }
    
    private void addSubjectToCourse() {
        if (courses.isEmpty()) {
            System.out.println("❌ No courses available. Add a course first!");
            return;
        }
        
        System.out.println("Available Courses:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i));
        }
        
        System.out.print("Select course (enter number): ");
        int courseIndex = getIntInput() - 1;
        
        if (courseIndex >= 0 && courseIndex < courses.size()) {
            System.out.print("Enter subject name: ");
            String subjectName = scanner.nextLine();
            System.out.print("Enter subject code: ");
            String subjectCode = scanner.nextLine();
            
            Subject newSubject = new Subject(subjectName, subjectCode);
            courses.get(courseIndex).addSubject(newSubject);
            System.out.println("✅ Subject added successfully!");
        } else {
            System.out.println("❌ Invalid course selection!");
        }
    }
    
    private void viewAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("❌ No courses available!");
            return;
        }
        
        System.out.println("\n📚 ALL COURSES:");
        for (Course course : courses) {
            System.out.println("\n🎯 " + course);
            System.out.println("   Subjects:");
            for (Subject subject : course.getSubjects()) {
                System.out.println("   - " + subject);
            }
        }
    }
    
    private void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("❌ No students registered!");
            return;
        }
        
        System.out.println("\n👥 ALL REGISTERED STUDENTS:");
        for (Student student : students) {
            System.out.println("📧 " + student.getEmail() + " - " + student.getName() + 
                             " (Age: " + student.getAge() + ")");
            if (student.getSelectedCourse() != null) {
                System.out.println("   📖 Course: " + student.getSelectedCourse().getCourseName());
            }
        }
    }
    
    private void viewExamResults() {
        if (allExamResults.isEmpty()) {
            System.out.println("❌ No exam results available!");
            return;
        }
        
        System.out.println("\n📊 ALL EXAM RESULTS:");
        for (ExamResult result : allExamResults) {
            System.out.println("📋 " + result);
        }
    }
    
    private void studentRegistration() {
        System.out.println("\n===== STUDENT REGISTRATION =====");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your age: ");
        int age = getIntInput();
        
        // Check if student already exists
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                System.out.println("❌ Student with this email already exists!");
                return;
            }
        }
        
        Student newStudent = new Student(name, email, age);
        students.add(newStudent);
        System.out.println("✅ Student registered successfully!");
    }
    
    private void studentLogin() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        
        Student student = findStudentByEmail(email);
        if (student != null) {
            System.out.println("✅ Student login successful! Welcome, " + student.getName());
            studentMenu(student);
        } else {
            System.out.println("❌ Student not found! Please register first.");
        }
    }
    
    private Student findStudentByEmail(String email) {
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                return student;
            }
        }
        return null;
    }
    
    private void studentMenu(Student student) {
        while (true) {
            student.displayMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    viewAvailableCourses();
                    break;
                case 2:
                    selectCourse(student);
                    break;
                case 3:
                    chooseSubjects(student);
                    break;
                case 4:
                    takeExam(student);
                    break;
                case 5:
                    viewStudentResults(student);
                    break;
                case 6:
                    System.out.println("👋 Student logged out successfully!");
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
    
    private void viewAvailableCourses() {
        if (courses.isEmpty()) {
            System.out.println("❌ No courses available!");
            return;
        }
        
        System.out.println("\n📚 AVAILABLE COURSES:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i));
        }
    }
    
    private void selectCourse(Student student) {
        if (courses.isEmpty()) {
            System.out.println("❌ No courses available!");
            return;
        }
        
        viewAvailableCourses();
        System.out.print("Select a course (enter number): ");
        int courseIndex = getIntInput() - 1;
        
        if (courseIndex >= 0 && courseIndex < courses.size()) {
            student.setSelectedCourse(courses.get(courseIndex));
            System.out.println("✅ Course selected: " + courses.get(courseIndex).getCourseName());
        } else {
            System.out.println("❌ Invalid course selection!");
        }
    }
    
    private void chooseSubjects(Student student) {
        if (student.getSelectedCourse() == null) {
            System.out.println("❌ Please select a course first!");
            return;
        }
        
        List<Subject> availableSubjects = student.getSelectedCourse().getSubjects();
        if (availableSubjects.isEmpty()) {
            System.out.println("❌ No subjects available in this course!");
            return;
        }
        
        System.out.println("\n📖 AVAILABLE SUBJECTS:");
        for (int i = 0; i < availableSubjects.size(); i++) {
            System.out.println((i + 1) + ". " + availableSubjects.get(i));
        }
        
        System.out.print("Select subject (enter number): ");
        int subjectIndex = getIntInput() - 1;
        
        if (subjectIndex >= 0 && subjectIndex < availableSubjects.size()) {
            student.addSelectedSubject(availableSubjects.get(subjectIndex));
            System.out.println("✅ Subject added: " + availableSubjects.get(subjectIndex).getSubjectName());
        } else {
            System.out.println("❌ Invalid subject selection!");
        }
    }
    
    private void takeExam(Student student) {
        if (student.getSelectedSubjects().isEmpty()) {
            System.out.println("❌ Please select subjects first!");
            return;
        }
        
        System.out.println("\n📝 YOUR SELECTED SUBJECTS:");
        for (int i = 0; i < student.getSelectedSubjects().size(); i++) {
            System.out.println((i + 1) + ". " + student.getSelectedSubjects().get(i));
        }
        
        System.out.print("Select subject for exam (enter number): ");
        int subjectIndex = getIntInput() - 1;
        
        if (subjectIndex >= 0 && subjectIndex < student.getSelectedSubjects().size()) {
            Subject examSubject = student.getSelectedSubjects().get(subjectIndex);
            conductExam(student, examSubject);
        } else {
            System.out.println("❌ Invalid subject selection!");
        }
    }
    
    private void conductExam(Student student, Subject subject) {
        List<Question> questions = subject.getQuestions();
        int score = 0;
        
        System.out.println("\n🎯 EXAM: " + subject.getSubjectName());
        System.out.println("Answer the following questions (enter option number 1-4):\n");
        
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("Q" + (i + 1) + ". " + q.getQuestionText());
            
            String[] options = q.getOptions();
            for (int j = 0; j < options.length; j++) {
                System.out.println((j + 1) + ". " + options[j]);
            }
            
            System.out.print("Your answer: ");
            int answer = getIntInput() - 1;
            
            if (answer == q.getCorrectAnswer()) {
                score++;
                System.out.println("✅ Correct!\n");
            } else {
                System.out.println("❌ Wrong! Correct answer: " + 
                    options[q.getCorrectAnswer()] + "\n");
            }
        }
        
        ExamResult result = new ExamResult(student.getEmail(), subject.getSubjectCode(), 
                                         score, questions.size());
        student.addExamResult(result);
        allExamResults.add(result);
        
        System.out.println("🎊 EXAM COMPLETED!");
        System.out.println("📊 Your Score: " + score + "/" + questions.size());
        System.out.println("📈 Status: " + (result.isPassed() ? "PASSED ✅" : "FAILED ❌"));
    }
    
    private void viewStudentResults(Student student) {
        if (student.getExamResults().isEmpty()) {
            System.out.println("❌ No exam results available!");
            return;
        }
        
        System.out.println("\n📊 YOUR EXAM RESULTS:");
        for (ExamResult result : student.getExamResults()) {
            System.out.println("📋 " + result);
        }
    }
    
    private int getIntInput() {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                return input;
            } catch (NumberFormatException e) {
                System.out.print("❌ Please enter a valid number: ");
            }
        }
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        sms.start();
    }
}