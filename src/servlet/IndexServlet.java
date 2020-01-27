package servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import config.Configuration;
import model.SessionClass;
import service.IndexService;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IndexService indexService = new IndexService();
		
		// Generar Token de Seguridad
		String token = indexService.generarToken();
		
		// Generar Sessión de Comunicación
		String merchantId = request.getParameter("merchantId");
		String currency = request.getParameter("currency");
		
		String urlSession = Configuration.URL_API_SESSION + merchantId;	
		String requestBody =  "{"
				+ "\"amount\": "+request.getParameter("importe")+","
				+ "\"channel\": \"web\""
				+ "}";
		
		Gson gson = new Gson();
		Map<String, Object> mapRpta = indexService.postRequest(urlSession, requestBody, token);
		
		String session = null;
		
		if ((boolean) mapRpta.get("error")) {
			
		} else {
			SessionClass sc = gson.fromJson((String) mapRpta.get("data"), SessionClass.class);
			session = sc.getSessionKey();
		}
		request.setAttribute("json", requestBody);
		request.setAttribute("session", session);
		request.setAttribute("merchantId", merchantId);
		request.setAttribute("currency", currency);
		
		Cookie tokenCookie = new Cookie("token", token);
		response.addCookie(tokenCookie);
		
		request.setAttribute("token", token);
        request.getRequestDispatcher("/boton.jsp").forward(request, response);		
	}

}
