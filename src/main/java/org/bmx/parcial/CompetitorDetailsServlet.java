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

@WebServlet("/competitor-details")
public class CompetitorDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        HttpSession session = req.getSession();
        List<Competitor> competitors = (List<Competitor>) session.getAttribute("competitors");

        Competitor competitor = competitors.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        if (competitor != null) {
            out.println("{"
                    + "\"firstName\":\"" + competitor.getFirstName() + "\","
                    + "\"lastName\":\"" + competitor.getLastName() + "\","
                    + "\"country\":\"" + competitor.getCountry() + "\","
                    + "\"r1\":" + competitor.getR1() + ","
                    + "\"r2\":" + competitor.getR2() + ","
                    + "\"r3\":" + competitor.getR3()
                    + "}");
        } else {
            out.println("{}");
        }
    }
}
