import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*; // Import for database operations

@WebServlet("/Movie_Form")
public class servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        resp.setContentType("text/html");
        PrintWriter pr = resp.getWriter();

        String url = "jdbc:mysql://localhost:3306/movies";
        String user = "root";
        String pw = "student";

        try {


            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pw);

            String name_received = req.getParameter("movie"); // we need name of the movie

            String query = "SELECT * FROM movie WHERE movie_name = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name_received);


            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String actor = rs.getString("actor");
                String actress = rs.getString("actress");
                String director = rs.getString("director");
                String rating = rs.getString("rating");
                String about_movie = rs.getString("about_movie");

                pr.print("<h1>Movie Details:</h1>");
                pr.print("<p><strong>Name:</strong> " + name_received + "</p>");
                pr.print("<p><strong>Actor:</strong> " + actor + "</p>");
                pr.print("<p><strong>Actress:</strong> " + actress + "</p>");
                pr.print("<p><strong>Director:</strong> " + director + "</p>");
                pr.print("<p><strong>Rating:</strong> " + rating + "</p>");
                pr.print("<p><strong>About Movie:</strong> " + about_movie + "</p>");

            } else {
                pr.print("<h1>No movie found with the name: " + name_received + "</h1>");
            }

            con.close();

        } catch (Exception e){

            pr.print("<p>" + e +"</p>");
        }
    }
}