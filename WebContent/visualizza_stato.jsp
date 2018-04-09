<%@page import="it.unipr.informatica.esame.web.SensoriServlet" %>
<%@page import="it.unipr.informatica.esame.model.ModelStato" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page errorPage="errore.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Stato Sensori</title>
	</head>
	<body>
	
		<h1>Stato Sensori</h1>
		<%
			ModelStato st = (ModelStato)session.getAttribute(SensoriServlet.STATO);
			session.removeAttribute(SensoriServlet.STATO);
			
			if(st == null){
				out.print("<p>Errore, visita <a href='sensori'>questa pagina<a> per lo stato dei sensori</p>");
			}
			else{
				out.print("<table border='1'>");
				out.print("	<thead>");
				out.print("		<tr>");
				out.print("<td>Temperatura</td><td>Purezza</td>");
				out.print("</tr>");
				out.print("	</thead>");
				out.print("	<tbody>");
				out.print("		<tr>");
				out.print("			<td>"+st.getTemperatura());
				out.print("			</td>");
				out.print("			<td>"+st.getPurezza());
				out.print("			</td>");
				out.print("		</tr>");
				out.print("	</tbody>");
				out.print("</table>");
			}
		%>
		
	</body>
</html>