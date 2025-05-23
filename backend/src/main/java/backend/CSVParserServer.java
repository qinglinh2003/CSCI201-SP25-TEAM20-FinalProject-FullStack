package backend;

import java.io.*;
import java.sql.*;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.servlet.http.Part;
/*<form method="POST" action="/CSVParserServer" enctype="multipart/form-data">
  <input type="email" name="email" placeholder="Enter your email" required /><br/>
  File:
  <input type="file" name="file" id="file" required /> <br/>
  <input type="submit" value="Upload CSV" />
</form>
    //The enctype attribute must be set to a value of multipart/form-data.
    //Its method must be POST.*/
@WebServlet(name = "CSVParserServer", urlPatterns = {"/CSVParserServer"})
@MultipartConfig
    public class CSVParser extends HttpServlet {
   /* private DBManager dbManager;

    @Override
    public void init() throws ServletException {
        dbManager = new DBManager();
    }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    String email = request.getParameter("email");
    if (email == null || email.isEmpty()) {
            out.println("Email parameter is missing.");
            return;
        }

    int successCount = 0;

    try {
        Connection conn = dbManager.connection();
        if (conn == null) {
            out.println("Database connection failed.");
            return;
        }

        // Get user_id based on email
        int userId = -1;
        PreparedStatement getUserStmt = conn.prepareStatement("SELECT user_id FROM Users WHERE email = ?");
        getUserStmt.setString(1, email);
        ResultSet rs = getUserStmt.executeQuery();

        if (rs.next()) {
            userId = rs.getInt("user_id");
        }
        rs.close();
        getUserStmt.close();

        if (userId == -1) {
            out.println("No user found with email: " + email);
            return;
        }

        // Get uploaded CSV file
        Part filePart = request.getPart("file");
        if (filePart == null || filePart.getSize() == 0) {
            out.println("No file uploaded.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 4) continue;

                int courseId = Integer.parseInt(fields[0].trim());
                String courseName = fields[1].trim();
                String courseDates = fields[2].trim();
                String courseTime = fields[3].trim();

                // Insert into Course
                PreparedStatement courseStmt = conn.prepareStatement(
                        "INSERT IGNORE INTO Course (courseID, courseName, courseDates, courseTime) VALUES (?, ?, ?, ?)");
                courseStmt.setInt(1, courseId);
                courseStmt.setString(2, courseName);
                courseStmt.setString(3, courseDates);
                courseStmt.setString(4, courseTime);
                courseStmt.executeUpdate();
                courseStmt.close();

                // Insert into UserCourse
                PreparedStatement userCourseStmt = conn.prepareStatement(
                        "INSERT IGNORE INTO UserCourse (user_id, courseID) VALUES (?, ?)");
                userCourseStmt.setInt(1, userId);
                userCourseStmt.setInt(2, courseId);
                int inserted = userCourseStmt.executeUpdate();
                userCourseStmt.close();

                if (inserted > 0) {
                    successCount++;
                }
            }

            dbManager.disconnection();
            out.println("CSV upload complete. Courses joined: " + successCount);
        }

    } catch (Exception e) {
        e.printStackTrace();
        out.println("Error processing CSV: " + e.getMessage());
    }
 }
}
