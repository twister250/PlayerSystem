package br.com.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static java.nio.file.StandardOpenOption.READ;

/**
 * Servlet implementation class VideoServlet
 */
@WebServlet(urlPatterns = { "/VideoServlet", "/movie" }, initParams = { @WebInitParam(name = "videoPath", value = "/media/HD-Windows/Torrents/teste") })
public class VideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final int BUFFER_LENGTH = 1024 * 1024 * 2;
	private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24;
	private static final Pattern RANGE_PATTERN = Pattern.compile("bytes=(?<start>\\d*)-(?<end>\\d*)");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VideoServlet() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		//this.videoPath = config.getInitParameter("videoPath");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String media = request.getParameter("media");
		String path = request.getParameter("path");
		String track = request.getParameter("track");
		
		if(track != null){
			Path subtitle = Paths.get(path, track);
			if(subtitle != null){
				byte[] data = Files.readAllBytes(subtitle);
				response.reset();
				response.setBufferSize(BUFFER_LENGTH);
				response.setHeader("Content-Disposition", String.format("inline;filename=\"%s\"", media));						
				response.setContentType(Files.probeContentType(subtitle));
				OutputStream output = response.getOutputStream();
				output.write(data);
			}						
		}else{
			Path movie = Paths.get(path, media);
			
			int length = (int) Files.size(movie);
			int start = 0;
			int end = length - 1;
			String range = request.getHeader("Range");
			Matcher matcher = RANGE_PATTERN.matcher(range);
	
			if (matcher.matches()) {
				String startGroup = matcher.group("start");
				start = startGroup.isEmpty() ? start : Integer.valueOf(startGroup);
				start = start < 0 ? 0 : start;
	
				String endGroup = matcher.group("end");
				end = endGroup.isEmpty() ? end : Integer.valueOf(endGroup);
				end = end > length - 1 ? length - 1 : end;
			}
	
			int contentLength = end - start + 1;
	
			response.reset();
			response.setBufferSize(BUFFER_LENGTH);
			response.setHeader("Content-Disposition", String.format("inline;filename=\"%s\"", media));
			response.setHeader("Accept-Ranges", "bytes");
			response.setDateHeader("Last-Modified", Files.getLastModifiedTime(movie).toMillis());
			response.setDateHeader("Expires", System.currentTimeMillis() + EXPIRE_TIME);
			response.setContentType(Files.probeContentType(movie)); response.setHeader("Content-Range", String.format("bytes %s-%s/%s", start, end, length));
			response.setHeader("Content-Length", String.format("%s", contentLength));
			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
	
			int bytesRead;
			int bytesLeft = contentLength;
			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_LENGTH);
	
			try (SeekableByteChannel input = Files.newByteChannel(movie, READ);
					OutputStream output = response.getOutputStream()) {
	
				input.position(start);
	
				while ((bytesRead = input.read(buffer)) != -1 && bytesLeft > 0) {
					buffer.clear();
					output.write(buffer.array(), 0, bytesLeft < bytesRead ? bytesLeft : bytesRead);
					bytesLeft -= bytesRead;
				}
			}
		}
	}
}