package src;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Scanner;

public class Main {
    // private static String USERNAME = new String("N01509014");
    private static String USERNAME = new String("G16");
    // private static String PASSWORD = new String("Fall20229014");
    private static String PASSWORD = new String("Fall2022G16");
    private static String DB_URL = new String("jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl");

    public static String getString() {
        try {
            StringBuffer buffer = new StringBuffer();

            int c = System.in.read();
            while (c != '\n' && c != -1) {
                buffer.append((char) c);
                c = System.in.read();
            }

            return buffer.toString().trim();

        } catch (IOException e) {
            return "";
        }
    }

    public static int getInt() {
        return Integer.parseInt(getString());
    }

    public static float getFloat() {
        return Float.parseFloat(getString());
    }

    private static boolean printSQLExceptions(SQLException ex) {
        boolean rc = false;
        if (ex != null) {
            System.out.println("\nSQL EXCEPTION\n");
            rc = true;
            while (ex != null) {
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("Message:  " + ex.getMessage());
                System.out.println("Vendor:   " + ex.getErrorCode());
                System.out.println("");
                ex = ex.getNextException();
            }
        }
        return rc;
    }

    static public void printDriverInfo(Connection conn) throws SQLException {
        DatabaseMetaData dma = conn.getMetaData();
        System.out.println("Database\t" + dma.getDatabaseProductVersion());
        System.out.println("Driver\t\t" + dma.getDriverVersion());
        System.out.println("URL\t\t" + dma.getURL() + ", user '" +
                dma.getUserName() + "'");
        System.out.println("-------------------------------------");
    }

    static public void displayResultSet(ResultSet rs) throws SQLException {
        int i;
        ResultSetMetaData rsmd = rs.getMetaData();
        int numCols = rsmd.getColumnCount();
        for (i = 1; i <= numCols; i++) {
            if (i > 1)
                System.out.print(",");
            System.out.print(rsmd.getColumnLabel(i));
        }
        System.out.println("\n-------------------------------------");
        while (rs.next()) {
            for (i = 1; i <= numCols; i++) {
                if (i > 1)
                    System.out.print(",");
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }
    }

    static public boolean printSQLWarnings(SQLWarning warn) throws SQLException {
        boolean rc = false;
        if (warn != null) {
            System.out.println("\n *** Warning ***\n");
            rc = true;
            while (warn != null) {
                System.out.println("SQLState: " + warn.getSQLState());
                System.out.println("Message:  " + warn.getMessage());
                System.out.println("Vendor:   " + warn.getErrorCode());
                System.out.println("");
                warn = warn.getNextWarning();
            }
        }
        return rc;
    }

    public static PreparedStatement addSomeEntity(String entityType, Connection conn)
            throws SQLException {

        Scanner scanner = new Scanner(System.in);

        PreparedStatement pstmt = null;

        switch (entityType.toLowerCase()) {
            case "student":
                addStudent(conn);
                break;
            case "department":
                addDepartment(conn);
                break;
            case "course":
                addCourse(conn);
                break;
            case "instructor":
                addInstructor(conn);
                break;
            case "section":
                addSection(conn);
                break;
            case "person":
                addPerson(conn);
                break;
            default:
                System.out.println("Invalid input!");
                break;
        }

        return pstmt;

    }

    public static PreparedStatement addSection(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO SECTION (COURSE_NO, SECTION_NO, INSTRUCTOR, SEMESTER, YEAR)" + "VALUES (?, ?, ?, ?, ?)");
        System.out.println("Enter the Course Number: ");
        String courseNo = getString();
        System.out.println("Enter the Section Number");
        String sectionNo = getString();
        System.out.println("Enter the N_Number of the Instructor:");
        String nNumber = getString();
        System.out.println("Enter the Semester");
        String semester = getString();
        System.out.println("Enter the Year");
        String year = getString();

        pstmt.setString(1, courseNo);
        pstmt.setString(2, sectionNo);
        pstmt.setString(3, nNumber);
        pstmt.setString(4, semester);
        pstmt.setString(5, year);

        int rowsAdded = pstmt.executeUpdate();
        System.out.println("\n" + rowsAdded + " section added to the table");
        return pstmt;
    }

    public static PreparedStatement addInstructor(Connection conn) throws SQLException {
        addPerson(conn);

        PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO INSTRUCTOR (SSN, N_NUMBER, DEPARTMENT, OFFICE_NO)" +
                        "VALUES (?, ?, ?, ?)");

        System.out.println("Enter the instructor's SSN: ");
        String ssn = getString();
        System.out.println("Enter the instructor's N-Number: ");
        String nNumber = getString();
        System.out.println("Enter the instructor's department: ");
        String department = getString();
        System.out.println("Enter the instructor's office number: ");
        int officeNumber = getInt();
        pstmt.setString(1, ssn);
        pstmt.setString(2, nNumber);
        pstmt.setString(3, department);
        pstmt.setInt(4, officeNumber);

        int rowsAdded = pstmt.executeUpdate();
        System.out.println("\n" + rowsAdded + " instructor added to the table.\n");

        return pstmt;
    }

    public static PreparedStatement addCourse(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO COURSE (COURSE_NO, COURSE_NAME, DESCRIPTION, OFFERING_DEPT, COURSE_LEVEL, COURSE_HOURS)" +
                        "VALUES (?, ?, ?, ?, ?, ?)");

        System.out.println("Enter the course's course number: ");
        String courseNumber = getString();
        System.out.println("Enter the course's name: ");
        String courseName = getString();
        System.out.println("Enter the course's description: ");
        String description = getString();
        System.out.println("Enter the course's offering department: ");
        String offeringDepartment = getString();
        System.out.println("Enter the course's level: ");
        int courseLevel = getInt();
        System.out.println("Enter the course's hours: ");
        int courseHours = getInt();

        pstmt.setString(1, courseNumber);
        pstmt.setString(2, courseName);
        pstmt.setString(3, description);
        pstmt.setString(4, offeringDepartment);
        pstmt.setInt(5, courseLevel);
        pstmt.setInt(6, courseHours);

        int rowsAdded = pstmt.executeUpdate();
        System.out.println("\n" + rowsAdded + " course added to the table.\n");

        return pstmt;
    }

    public static PreparedStatement addDepartment(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO DEPARTMENT (DEPT_CODE, DEPT_NAME, OFFICE_NO, OFFICE_PHONE, COLLEGE) " +
                        "VALUES (?, ?, ?, ?, ?)");

        System.out.println("Enter the department's code: ");
        String deptCode = getString();
        System.out.println("Enter the department's name: ");
        String deptName = getString();
        System.out.println("Enter the department's office number: ");
        String officeNumber = getString();
        System.out.println("Enter the department's office phone number: ");
        String officePhone = getString();
        System.out.println("Enter the department's college: ");
        String college = getString();

        pstmt.setString(1, deptCode);
        pstmt.setString(2, deptName);
        pstmt.setString(3, officeNumber);
        pstmt.setString(4, officePhone);
        pstmt.setString(5, college);

        int rowsAdded = pstmt.executeUpdate();
        System.out.println("\n" + rowsAdded + " department added to the table\n");

        return pstmt;
    }

    public static PreparedStatement addPerson(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO PERSON (SSN, PNAME, BIRTH_DATE, SEX, N_NUMBER, STREET_NO, ADDRESS_STREET, ZIP_CODE, STATE, CITY, PHONE_NUMBER, CURRENT_ADDRESS, PERMANENT_PHONE)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        System.out.println("Enter the person's SSN: ");
        String ssn = getString();
        System.out.println("Enter the person's name: ");
        String name = getString();
        System.out.println("Enter the person's birth date (yyyy-[m]m-[d]d): ");
        String sbirthDate = getString();
        Date birthDate = Date.valueOf(sbirthDate);
        System.out.println("Enter the person's sex: ");
        String sex = getString();
        System.out.println("Enter the person's N-Number: ");
        String nNumber = getString();
        System.out.println("Enter the person's street number: ");
        int streetNumber = getInt();
        System.out.println("Enter the person's street name: ");
        String streetName = getString();
        System.out.println("Enter the person's zip code: ");
        int zipCode = getInt();
        System.out.println("Enter the person's state: ");
        String state = getString();
        System.out.println("Enter the person's city: ");
        String city = getString();
        System.out.println("Enter the person's phone number: ");
        String phoneNumber = getString();
        System.out.println("Enter the person's current address: ");
        String currentAddress = getString();
        System.out.println("Enter the person's permanent phone number: ");
        String permanentPhone = getString();

        pstmt.setString(1, ssn);
        pstmt.setString(2, name);
        pstmt.setDate(3, birthDate);
        pstmt.setString(4, sex);
        pstmt.setString(5, nNumber);
        pstmt.setInt(6, streetNumber);
        pstmt.setString(7, streetName);
        pstmt.setInt(8, zipCode);
        pstmt.setString(9, state);
        pstmt.setString(10, city);
        pstmt.setString(11, phoneNumber);
        pstmt.setString(12, currentAddress);
        pstmt.setString(13, permanentPhone);

        int rowsAdded = pstmt.executeUpdate();
        System.out.println("\n" + rowsAdded + " person added to the table\n");

        return pstmt;
    }

    public static PreparedStatement addStudent(Connection conn) throws SQLException {

        addPerson(conn);

        PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO STUDENT (N_NUMBER, CLASS, DEGREE_TYPE)" +
                        "VALUES (?, ?, ?)");

        System.out.println("Enter student n-number: ");
        String snNumber = getString();
        System.out.println("Enter student class: ");
        String className = getString();
        System.out.println("Enter student degree type: ");
        String degreeType = getString();

        pstmt.setString(1, snNumber);
        pstmt.setString(2, className);
        pstmt.setString(3, degreeType);

        int rowsAdded = pstmt.executeUpdate();
        System.out.println("\n" + rowsAdded + " student added to the table\n");

        return pstmt;

    }

    private static void addStudentToClass(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO ENROLLED_IN  (N_NUMBER, COURSE_NUMBER)" +
                        "VALUES (?, ?)");

        System.out.println("Enter n-number: ");
        String nNumber = getString();
        System.out.println("Enter course number: ");
        String courseName = getString();
        System.out.println("Enter grade earned: ");
        String sectionNumber = getString();
        System.out.println("Enter semester: ");
        String semester = getString();
        System.out.println("Enter year: ");
        String year = getString();

        pstmt.setString(1, nNumber);
        pstmt.setString(2, courseName);
        pstmt.setString(3, sectionNumber);
        pstmt.setString(4, semester);
        pstmt.setString(5, year);

        int rowsAdded = pstmt.executeUpdate();
        System.out.println("\n" + rowsAdded + " student added to the table\n");
    }

    public static PreparedStatement getStudentGPA(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("");

        return pstmt;
    }

    private static PreparedStatement listTaughtSections(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SECTION WHERE instructor = ?");
        System.out.print("Enter the Instructor's N_Number: ");
        String nNumber = getString();
        pstmt.setString(1, nNumber);

        return pstmt;
    }

    private static PreparedStatement listDeptCourses(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("Select * from COURSE where Offering_Dept = ?");
        System.out.print("Enter the department code: ");
        String deptName = getString();
        pstmt.setString(1, deptName);

        displayResultSet(pstmt.executeQuery());

        return pstmt;
    }

    private static PreparedStatement addGrade(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE ENROLLED_IN SET grade_earned = ? " +
                        "WHERE N_NUMBER = ?" +
                        "and course_number = ?" +
                        "and semester = ?" +
                        "and year = ?");
        return pstmt;
    }

    private static void userOptions(Connection conn) {
        String option = "";

        PreparedStatement pstmt = null;

        Scanner in = new Scanner(System.in);

        while (!option.equals("7")) {
            try {

                System.out.print("What would you like to do?\n" +
                        "1. Add a new entity to the database\n" +
                        "2. Add a student to a course\n" +
                        "3. Given a student's Nnumber generate their grade report. \n" +
                        "4. Given an instructor's Nnumber list all the course sections they have taught.\n" +
                        "5. Given a department name or code find the courses offered.\n" +
                        "6. Add a grade to a given student for a given course/section.\n" +
                        "7. Exit\n");

                System.out.print("-> ");
                option = in.nextLine();

                switch (option) {
                    case "1":
                        System.out.println("What kind of entity would you like to add?");
                        String entityType = in.nextLine();
                        addSomeEntity(entityType, conn);
                        continue;
                    case "2":
                        addStudentToClass(conn);
                        break;
                    case "3":
                        getStudentGPA(conn);
                        break;
                    case "4":
                        // TODO
                        pstmt = listTaughtSections(conn);
                        pstmt.executeQuery();
                        break;
                    case "5":
                        // COMPLETED
                        pstmt = listDeptCourses(conn);
                        pstmt.executeQuery();
                        break;
                    case "6":
                        // COMPLETED
                        pstmt = addGrade(conn);
                        pstmt.executeUpdate();
                        break;
                    case "7":
                        System.out.println("Exiting");
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            } catch (SQLException e) {
                printSQLExceptions(e);
            }
            in.close();
        }
    }

    public static void main(String[] args) {

        try {
            // load JDBC driver
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            // establish connection
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            printDriverInfo(conn);

            userOptions(conn);
            conn.close();
        } catch (SQLException e) {
            printSQLExceptions(e);
        }

    }

}
