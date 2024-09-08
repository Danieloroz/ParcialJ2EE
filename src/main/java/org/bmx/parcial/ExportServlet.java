package org.bmx.parcial;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/export")
public class ExportServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Competitor> competitors = (List<Competitor>) session.getAttribute("competitors");

        if (competitors == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No competitors found");
            return;
        }

        response.setContentType("application/json");
        response.setHeader("Content-Disposition", "attachment; filename=competitors.json");

        StringBuilder json = new StringBuilder();
        json.append("[");

        for (int i = 0; i < competitors.size(); i++) {
            Competitor c = competitors.get(i);
            if (i > 0) {
                json.append(",");
            }
            json.append("{")
                    .append("\"id\":").append(c.getId()).append(",")
                    .append("\"firstName\":\"").append(escapeJson(c.getFirstName())).append("\",")
                    .append("\"lastName\":\"").append(escapeJson(c.getLastName())).append("\",")
                    .append("\"country\":\"").append(escapeJson(c.getCountry())).append("\",")
                    .append("\"scoreR1\":").append(c.getR1()).append(",")
                    .append("\"scoreR2\":").append(c.getR2()).append(",")
                    .append("\"scoreR3\":").append(c.getR3()).append(",")
                    .append("\"totalScore\":").append(c.getTotalScore())
                    .append("}");
        }

        json.append("]");

        try (PrintWriter out = response.getWriter()) {
            out.print(json.toString());
            out.flush();
        }
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}