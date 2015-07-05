package br.com.action;

import br.com.entity.Movie;
import br.com.resource.PlayerSystemProperties;
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

@WebServlet(description = "Videos",urlPatterns = {"/home"})
public class HomeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static PlayerSystemProperties PROPERTIES = new PlayerSystemProperties();
    private static String ROOT;
    private static String VIDEO_PATH = "videoPath";
    private static String MOVIE_TYPE = "mp4";
    
    public HomeAction() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String param = request.getParameter("path");
		Service service = new Service();
		List<Movie> movies = new ArrayList<Movie>();
		List<Path> entries;
		Path sub = null;
		
		if(param == null){
			//entries = service.list(Paths.get("/media/HD-Windows/Videos"));
			entries = service.list(Paths.get(getRoot()));
		}else{
			service.findPath(Paths.get(getRoot()), param);
			entries = service.list(service.getPath());
			sub = Paths.get(param, "sub");
			if(entries.contains(sub)){
				entries.remove(sub);
			}
		}

		if(entries.isEmpty()){
			entries = service.getFiles();
			for(Path p: entries){
				String type = p.getFileName().toString().substring(p.getFileName().toString().lastIndexOf(".")+1);
				if(type.equals(MOVIE_TYPE)){
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
	
	public PlayerSystemProperties getProperties(){
		return PROPERTIES;
	}
	
	public String getRoot() throws IOException{
		ROOT = getProperties().getProperty("root");
		return ROOT;
	}
	
	public String getVideoPath(String type) throws IOException{
		VIDEO_PATH = getProperties().getProperty(type);
		return VIDEO_PATH;
	}
	
}