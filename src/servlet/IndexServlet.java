package servlet;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
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
	
	  public static void connectProxy() {
          
		  final String authUser = "2473011@bn.com.pe"; 
		  final String authPassword ="ene2020.";
		  
          Authenticator.setDefault( new Authenticator() {
          
          @Override public PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(
          authUser,authPassword.toCharArray()); } } );
          
          
          System.setProperty("http.proxyHost", "10.7.41.10");
          System.setProperty("http.proxyPort", "80");
          System.setProperty("http.proxyUser", authUser);
          System.setProperty("http.proxyPassword", authPassword);
          
          System.setProperty("http.nonProxyHosts", "localhost|10.|192.168.");
          System.setProperty("http.proxySet", "true");
          
          
          System.setProperty("https.proxyHost", "10.7.41.10");
          System.setProperty("https.proxyPort", "80");
          System.setProperty("https.proxyUser", authUser);
          System.setProperty("https.proxyPassword", authPassword);
          System.setProperty("https.proxySet", "true");
          
          System.setProperty("java.net.useSystemProxies", "true");
        
  }

   public static void disconnectProxy() {
          
          System.clearProperty("http.proxyHost");
          System.clearProperty("http.proxyPort");
          System.clearProperty("http.proxyUser");
          System.clearProperty("http.proxyPassword");
          System.clearProperty("http.proxySet");
          
          System.clearProperty("https.proxyHost");
          System.clearProperty("https.proxyPort");
          System.clearProperty("https.proxyUser");
          System.clearProperty("https.proxyPassword");
          System.clearProperty("https.proxySet");
          
          
          System.clearProperty("java.net.useSystemProxies");
          System.clearProperty("http.nonProxyHosts");
        
        // System.setProperty("http.proxySet", "false");
        // System.setProperty("java.net.useSystemProxies", "false");
  }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IndexService indexService = new IndexService();
		
		// Generar Token de Seguridad
		String token = indexService.generarToken();
		
		connectProxy();
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
        
        disconnectProxy();
	}

}
