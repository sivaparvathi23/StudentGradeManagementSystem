import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class Student {
    String name;
    int grade;

    Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    void displayInfo() {
        String status = grade >= 50 ? "Pass" : "Fail";
        System.out.println("Name: " + name + ", Grade: " + grade + " (" + status + ")");
    }
}

public class StudentGradeManagementSystem {
    static ArrayList<Student> students = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("🎓 Welcome to Student Grade Management System! 🎓");

        while (true) {
            System.out.println("\nMenu Options:");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student Grade");
            System.out.println("4. Delete Student");
            System.out.println("5. Calculate Average Grade");
            System.out.println("6. Sort Students by Name");
            System.out.println("7. Sort Students by Grade");
            System.out.println("8. Search for a Student");
            System.out.println("9. Display Top Performer");
            System.out.println("10. Count Pass and Fail Students");
            System.out.println("11. Export Student Data to File");
            System.out.println("12. Clear All Student Records");
            System.out.println("13. Exit");
            System.out.print("Choose an option (1-13): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> updateStudentGrade();
                case 4 -> deleteStudent();
                case 5 -> calculateAverageGrade();
                case 6 -> sortStudentsByName();
                case 7 -> sortStudentsByGrade();
                case 8 -> searchStudent();
                case 9 -> displayTopPerformer();
                case 10 -> countPassAndFailStudents();
                case 11 -> exportDataToFile();
                case 12 -> clearAllRecords();
                case 13 -> {
                    System.out.println("See you againnn..👋");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty!");
            return;
        }

        System.out.print("Enter student grade (0-100): ");
        int grade = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (grade < 0 || grade > 100) {
            System.out.println("Grade must be between 0 and 100.");
            return;
        }

        students.add(new Student(name, grade));
        System.out.println("Student added successfully.");
    }

    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("Student Records:");
        for (Student student : students) {
            student.displayInfo();
        }
    }

    static void updateStudentGrade() {
        System.out.print("Enter student name to update grade: ");
        String name = scanner.nextLine();
        for (Student student : students) {
            if (student.name.equalsIgnoreCase(name)) {
                System.out.print("Enter new grade (0-100): ");
                int grade = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (grade < 0 || grade > 100) {
                    System.out.println("Grade must be between 0 and 100.");
                    return;
                }
                student.grade = grade;
                System.out.println("Grade updated successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    static void deleteStudent() {
        System.out.print("Enter student name to delete: ");
        String name = scanner.nextLine();
        students.removeIf(student -> student.name.equalsIgnoreCase(name));
        System.out.println("Student deleted successfully.");
    }

    static void calculateAverageGrade() {
        if (students.isEmpty()) {
            System.out.println("No students to calculate the average grade.");
            return;
        }
        int total = students.stream().mapToInt(student -> student.grade).sum();
        double average = (double) total / students.size();
        System.out.printf("The average grade of all students is: %.2f\n", average);
    }

    static void sortStudentsByName() {
        students.sort(Comparator.comparing(student -> student.name.toLowerCase()));
        System.out.println("Students sorted by name.");
    }

    static void sortStudentsByGrade() {
        students.sort(Comparator.comparingInt(student -> student.grade));
        System.out.println("Students sorted by grade.");
    }

    static void searchStudent() {
        System.out.print("Enter student name to search: ");
        String name = scanner.nextLine();
        students.stream()
                .filter(student -> student.name.equalsIgnoreCase(name))
                .forEach(Student::displayInfo);
    }

    static void displayTopPerformer() {
        students.stream()
                .max(Comparator.comparingInt(student -> student.grade))
                .ifPresentOrElse(
                        student -> {
                            System.out.println("Top Performer:");
                            student.displayInfo();
                        },
                        () -> System.out.println("No student records found.")
                );
    }

    static void countPassAndFailStudents() {
        long passCount = students.stream().filter(student -> student.grade >= 50).count();
        long failCount = students.size() - passCount;
        System.out.println("✅ " + passCount + " students passed, " + failCount + " students failed.");
    }

    static void exportDataToFile() {
        try (FileWriter writer = new FileWriter("StudentData.txt")) {
            for (Student student : students) {
                writer.write("Name: " + student.name + ", Grade: " + student.grade + "\n");
            }
            System.out.println("Student data exported to 'StudentData.txt'.");
        } catch (IOException e) {
            System.out.println("Error while exporting data: " + e.getMessage());
        }
    }

    static void clearAllRecords() {
        students.clear();
        System.out.println("All student records cleared.");
    }
}

