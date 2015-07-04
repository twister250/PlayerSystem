package br.com.action;

import br.com.entity.Movie;
import com.misc.service.Service;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HomeAction
 */
@WebServlet(description = "Videos",urlPatterns = {"/home"})
public class HomeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeAction() {
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
		String param = request.getParameter("path");
		Service service = new Service();
		List<Movie> movies = new ArrayList<Movie>();
		List<Path> entries;
		Path sub = null;
		
		if(param == null){
			entries = service.list(Paths.get("/media/HD-Windows/Videos"));
		}else{
			entries = service.list(Paths.get(param));			
			sub = Paths.get(param, "sub");
			if(entries.contains(sub)){
				entries.remove(sub);
			}
		}
		
		if(entries.isEmpty()){
			entries = service.getFiles();
			for(Path p: entries){
				String type = p.getFileName().toString().substring(p.getFileName().toString().lastIndexOf(".")+1);
				if(type.equals("mp4")){
					Movie m = new Movie();
					m.setPath(p);
					m.setFileName(p.getFileName().toString());				
					m.setType(type);
					movies.add(m);
				}
			}
			request.setAttribute("movies", movies);									
		}else{
			request.setAttribute("entries", entries);			
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/home.jsp");
		rd.forward(request,response);
	}
}