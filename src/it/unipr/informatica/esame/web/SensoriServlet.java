package it.unipr.informatica.esame.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unipr.informatica.esame.Configuration;
import it.unipr.informatica.esame.model.ModelManager;
import it.unipr.informatica.esame.model.ModelStato;

@SuppressWarnings("serial")
@WebServlet("/sensori")
public class SensoriServlet extends HttpServlet {
    public static final String STATO = "stato";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Configuration conf = Configuration.getConfiguration();
			ModelManager model = conf.getModelManager();
			
			ModelStato obtainedStato = model.getStato();
			
			request.getSession().setAttribute(SensoriServlet.STATO, obtainedStato);
			
			request.getRequestDispatcher("visualizza_stato.jsp").forward(request, response);
		}
		catch (Throwable e) {
			e.printStackTrace(System.out);
			
			request.getRequestDispatcher("errore.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
