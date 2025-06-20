import java.awt.*;
import java.util.*;
import javax.swing.*;

// ==== Question Class ====
class Question {
    String questionText;
    String[] options;
    int correctAnswer;

    public Question(String questionText, String[] options, int correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

// ==== Subject Class ====
class Subject {
    String subjectName, subjectCode;
    java.util.List<Question> questions = new java.util.ArrayList<>();

    public Subject(String name, String code) {
        this.subjectName = name;
        this.subjectCode = code;
        addDefaultQuestions();
    }

    private void addDefaultQuestions() {
        questions.add(new Question("What is Java?", new String[]{"Lang", "Island", "Tea", "Bird"}, 0));
        questions.add(new Question("Which is not OOP?", new String[]{"Inheritance", "Encapsulation", "Polymorphism", "Compilation"}, 3));
    }

    public String toString() {
        return subjectCode + " - " + subjectName;
    }
}

// ==== Course Class ====
class Course {
    String courseName, courseCode;
    java.util.List<Subject> subjects = new java.util.ArrayList<>();

    public Course(String name, String code) {
        this.courseName = name;
        this.courseCode = code;
    }

    public void addSubject(Subject s) {
        subjects.add(s);
    }

    public java.util.List<Subject> getSubjects() {
        return subjects;
    }

    public String toString() {
        return courseCode + " - " + courseName;
    }
}

// ==== Student Class ====
class Student {
    String name, email;
    int age;
    Course selectedCourse;
    java.util.List<Subject> selectedSubjects = new java.util.ArrayList<>();
    java.util.Map<String, Integer> results = new java.util.HashMap<>();

    public Student(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public void addSubject(Subject s) {
        selectedSubjects.add(s);
    }

    public String toString() {
        return name + " (" + email + ", Age: " + age + ")";
    }
}

// ==== Management System ====
class StudentSystem {
    java.util.List<Student> students = new java.util.ArrayList<>();
    java.util.List<Course> courses = new java.util.ArrayList<>();

    public StudentSystem() {
        initDefaults();
    }

    private void initDefaults() {
        Course java = new Course("Java Programming", "JAVA101");
        Subject core = new Subject("Core Java", "JCORE");
        Subject adv = new Subject("Advanced Java", "JADV");
        java.addSubject(core);
        java.addSubject(adv);
        courses.add(java);
    }

    public void registerStudent(Student s) {
        students.add(s);
    }

    public Student findStudent(String email) {
        for (Student s : students) {
            if (s.email.equalsIgnoreCase(email)) return s;
        }
        return null;
    }

    public java.util.List<Course> getCourses() {
        return courses;
    }

    public java.util.List<Student> getStudents() {
        return students;
    }
}

// ==== GUI Application ====
public class StudentManagementApp {
    private JFrame frame;
    private StudentSystem system = new StudentSystem();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementApp().createGUI());
    }

    private void createGUI() {
        frame = new JFrame("Student Management System GUI");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Student Management System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        frame.add(title, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton regBtn = new JButton("Register Student");
        JButton viewBtn = new JButton("View Students");
        JButton examBtn = new JButton("Take Exam");
        JButton exitBtn = new JButton("Exit");

        btnPanel.add(regBtn);
        btnPanel.add(viewBtn);
        btnPanel.add(examBtn);
        btnPanel.add(exitBtn);
        frame.add(btnPanel, BorderLayout.CENTER);

        regBtn.addActionListener(e -> showRegisterDialog());
        viewBtn.addActionListener(e -> showStudentList());
        examBtn.addActionListener(e -> takeExamDialog());
        exitBtn.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    private void showRegisterDialog() {
        JTextField nameField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField ageField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);

        int res = JOptionPane.showConfirmDialog(frame, panel, "Register", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String email = emailField.getText();
                int age = Integer.parseInt(ageField.getText());

                if (system.findStudent(email) != null) {
                    JOptionPane.showMessageDialog(frame, "❌ Already registered.");
                    return;
                }

                Student student = new Student(name, email, age);

                java.util.List<Course> courses = system.getCourses();
                Course selectedCourse = (Course) JOptionPane.showInputDialog(frame, "Select Course:",
                        "Courses", JOptionPane.QUESTION_MESSAGE, null,
                        courses.toArray(), courses.get(0));

                if (selectedCourse == null) return;
                student.selectedCourse = selectedCourse;

                java.util.List<Subject> subjectList = selectedCourse.getSubjects();
                Subject subject = (Subject) JOptionPane.showInputDialog(frame, "Choose Subject:",
                        "Subjects", JOptionPane.QUESTION_MESSAGE, null,
                        subjectList.toArray(), subjectList.get(0));
                if (subject == null) return;

                student.addSubject(subject);
                system.registerStudent(student);
                JOptionPane.showMessageDialog(frame, "✅ Student registered!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "❌ Error: " + e.getMessage());
            }
        }
    }

    private void showStudentList() {
        java.util.List<Student> list = system.getStudents();
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No students found.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Student s : list) {
            sb.append(s.toString()).append("\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(frame, new JScrollPane(area), "Registered Students", JOptionPane.INFORMATION_MESSAGE);
    }

    private void takeExamDialog() {
        String email = JOptionPane.showInputDialog(frame, "Enter your registered email:");
        Student student = system.findStudent(email);
        if (student == null) {
            JOptionPane.showMessageDialog(frame, "❌ Student not found!");
            return;
        }

        if (student.selectedSubjects.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No subjects selected.");
            return;
        }

        Subject subject = (Subject) JOptionPane.showInputDialog(frame, "Choose subject to take exam:",
                "Exam", JOptionPane.QUESTION_MESSAGE, null,
                student.selectedSubjects.toArray(), student.selectedSubjects.get(0));

        if (subject == null) return;

        int score = 0;
        for (Question q : subject.questions) {
            String ans = (String) JOptionPane.showInputDialog(frame, q.questionText, "MCQ",
                    JOptionPane.QUESTION_MESSAGE, null, q.options, q.options[0]);

            if (ans != null && Arrays.asList(q.options).indexOf(ans) == q.correctAnswer) {
                score++;
            }
        }

        student.results.put(subject.subjectCode, score);
        JOptionPane.showMessageDialog(frame, "✅ Exam Completed!\nScore: " + score + "/" + subject.questions.size());
    }
}

