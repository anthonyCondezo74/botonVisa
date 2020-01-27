package servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import model.TransactionClass.TransaccionAprobada;
import model.TransactionClass.TransaccionDenegada;
import service.IndexService;

/**
 * Servlet implementation class BotonServlet
 */
@WebServlet("/BotonServlet")
public class BotonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BotonServlet() {
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
		String transactionToken = (String)request.getParameter("transactionToken");
		String merchantId = request.getParameter("merchantId");
		String currency = request.getParameter("currency");
		String amount = request.getParameter("importe");
		String purchaseNumber = request.getParameter("purchaseNumber");
		
		IndexService indexService = new IndexService();
		
		String token = null;
		
		// Generar Autorización
		String urlSession = Configuration.URL_API_AUTORIZACION + merchantId;
		for(Cookie c:request.getCookies()) {
			if (c.getName().equals("token")) {
				token = c.getValue();
			}
		}
		
		String requestBody =  "{"
				+ "\"channel\": \"web\""+","
				+ "\"captureType\": \"manual\"" + ","
				+ "\"countable\": \"true\"" + ","
				+ "\"order\": {"
					+ "\"amount\": \""+amount+"\","
					+ "\"tokenId\": \""+transactionToken+"\","
					+ "\"currency\": \""+currency+"\","
					+ "\"purchaseNumber\": "+purchaseNumber
					+ "}"
				+ "}";
		
		System.out.println("json: " + requestBody);
		
		Gson gson = new Gson();
		Map<String, Object> mapRpta = indexService.postRequest(urlSession, requestBody, token);

		PrintWriter out = response.getWriter();
		
		if ((boolean) mapRpta.get("error")) {
			
			TransaccionDenegada denegada = gson.fromJson((String) mapRpta.get("data"), TransaccionDenegada.class);
			
			out.println("<br> Transacción Denegada <hr>");
			out.println("<br> Número de pedido: " + purchaseNumber);
			out.println("<br> Importe: " + amount);
			out.println("<br> Moneda: " + currency);
			out.println("<br> Brand: " + denegada.getData().getBRAND());
			out.println("<br> Descripción: " + denegada.getData().getACTION_DESCRIPTION());
			
		} else {
			TransaccionAprobada aprobada = gson.fromJson((String) mapRpta.get("data"), TransaccionAprobada.class);

			out.println("<br> Transacción Aprobada <hr>");
			out.println("<br> Número de pedido: " + purchaseNumber);
			out.println("<br> Brand: " + aprobada.getDataMap().getBRAND());
			out.println("<br> Card: " + aprobada.getDataMap().getCARD());
			out.println("<br> Descripción: " + aprobada.getDataMap().getACTION_DESCRIPTION());
			out.println("<br> Importe: " + amount);
			out.println("<br> Moneda: " + currency);
			out.println("<br> Transaction id: " + aprobada.getOrder().getTransactionId());
		}
		
	}

}
