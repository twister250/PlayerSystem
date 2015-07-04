package br.com.action;

import br.com.entity.Movie;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

/**
 * Servlet implementation class VideoAction
 */
@WebServlet("/video")
public class VideoAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VideoAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");	
		String type = request.getParameter("type");
		Path path = Paths.get(request.getParameter("path"));
		Movie movie = new Movie();
		movie.setFileName(name);
		movie.setPath(path);
		movie.setType(type);
		movie.setMovieName(name.substring(0, name.lastIndexOf(".")));
		request.setAttribute("movie", movie);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/video.jsp");
		rd.forward(request,response);
	}
}