package org.bmx.parcial;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet("/competitors")
public class CompetitorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final List<Competitor> DEFAULT_COMPETITORS = List.of(
            new Competitor("john", "DOE", "USA", 8, 7, 6),
            new Competitor("jane", "SMITH", "CAN", 9, 8, 7),
            new Competitor("alice", "JOHNSON", "GBR", 7, 6, 9),
            new Competitor("bob", "BROWN", "AUS", 6, 8, 7),
            new Competitor("carol", "WILSON", "NZL", 5, 7, 8),
            new Competitor("dave", "MARTINEZ", "BRA", 8, 6, 8),
            new Competitor("eve", "DAVIS", "ARG", 7, 9, 6),
            new Competitor("frank", "MILLER", "GER", 6, 7, 9),
            new Competitor("grace", "WILSON", "FRA", 8, 8, 8),
            new Competitor("hank", "LEWIS", "JPN", 9, 7, 6)
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<Competitor> competitors = (List<Competitor>) session.getAttribute("competitors");
        if (competitors == null) {
            competitors = new ArrayList<>(DEFAULT_COMPETITORS);
            session.setAttribute("competitors", competitors);
        }

        String sort = req.getParameter("sort");
        if (sort != null) {
            competitors.sort((c1, c2) -> {
                switch (sort) {
                    case "name":
                        return c1.getFirstName().compareTo(c2.getFirstName());
                    case "country":
                        return c1.getCountry().compareTo(c2.getCountry());
                    case "r1":
                        return Integer.compare(c1.getR1(), c2.getR1());
                    case "r2":
                        return Integer.compare(c1.getR2(), c2.getR2());
                    case "r3":
                        return Integer.compare(c1.getR3(), c2.getR3());
                    case "totalScore":
                        return Integer.compare(c1.getTotalScore(), c2.getTotalScore());
                    default:
                        return 0;
                }
            });
        }

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<Competitor> competitors = (List<Competitor>) session.getAttribute("competitors");
        if (competitors == null) {
            competitors = new ArrayList<>(DEFAULT_COMPETITORS);
            session.setAttribute("competitors", competitors);
        }
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(convertToJson(competitors));


        String action = req.getParameter("action");
        if (action == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
            return;
        }

        switch (action) {
            case "add":
                addCompetitor(req, competitors);
                break;
            case "delete":
                deleteCompetitor(req, competitors);
                break;
            case "update":
                updateCompetitor(req, competitors);
                break;
            case "simulate":
                simulateRace(competitors);
                break;
            case "generateRandom":
                generateRandomCompetitor(competitors);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
                return;
        }

        session.setAttribute("competitors", competitors);
        resp.sendRedirect("competitors");
    }

    private void addCompetitor(HttpServletRequest req, List<Competitor> competitors) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String country = req.getParameter("country");
        String r1Param = req.getParameter("r1");
        String r2Param = req.getParameter("r2");
        String r3Param = req.getParameter("r3");

        if (firstName == null || lastName == null || country == null || r1Param == null || r2Param == null || r3Param == null) {
            throw new IllegalArgumentException("Missing parameters");
        }

        int r1, r2, r3;
        try {
            r1 = Integer.parseInt(r1Param);
            r2 = Integer.parseInt(r2Param);
            r3 = Integer.parseInt(r3Param);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format");
        }

        firstName = firstName.trim().toLowerCase();
        lastName = lastName.trim().toUpperCase();
        country = country.trim();

        Competitor competitor = new Competitor(firstName, lastName, country, r1, r2, r3);
        competitors.add(competitor);
    }

    private void deleteCompetitor(HttpServletRequest req, List<Competitor> competitors) {
        String idParam = req.getParameter("id");

        if (idParam == null) {
            throw new IllegalArgumentException("ID parameter is missing");
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID format");
        }

        competitors.removeIf(c -> c.getId() == id);
    }

    private void updateCompetitor(HttpServletRequest req, List<Competitor> competitors) {
        String idParam = req.getParameter("id");
        String r1Param = req.getParameter("r1");
        String r2Param = req.getParameter("r2");
        String r3Param = req.getParameter("r3");

        if (idParam == null || r1Param == null || r2Param == null || r3Param == null) {
            throw new IllegalArgumentException("Missing parameters");
        }

        int id = Integer.parseInt(idParam);
        Competitor competitor = competitors.stream()
                .filter(c -> c.getId() == id)
                .findFirst().orElse(null);

        if (competitor != null) {
            try {
                int r1 = Integer.parseInt(r1Param);
                int r2 = Integer.parseInt(r2Param);
                int r3 = Integer.parseInt(r3Param);
                competitor.setR1(r1);
                competitor.setR2(r2);
                competitor.setR3(r3);
                competitor.updateTotalScore();
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number format", e);
            }
        }
    }

    private void simulateRace(List<Competitor> competitors) {
        Random rand = new Random();
        for (Competitor competitor : competitors) {
            int r1 = rand.nextInt(11); // Random value between 0 and 10
            int r2 = rand.nextInt(11);
            int r3 = rand.nextInt(11);
            competitor.setR1(r1);
            competitor.setR2(r2);
            competitor.setR3(r3);
            competitor.updateTotalScore();
        }
    }

    private void generateRandomCompetitor(List<Competitor> competitors) {
        Random rand = new Random();
        String[] names = {"Juan", "Felipe", "Andres", "Carlos", "Karen", "Andrea", "Ximena", "Fabian", "Esteban", "Camilo"};
        String[] countries = {"USA", "CAN", "GBR", "AUS", "NZL", "BRA", "ARG", "GER", "FRA", "JPN"};

        String firstName = names[rand.nextInt(names.length)].toLowerCase();
        String lastName = names[rand.nextInt(names.length)].toUpperCase();
        String country = countries[rand.nextInt(countries.length)];
        int r1 = rand.nextInt(11);
        int r2 = rand.nextInt(11);
        int r3 = rand.nextInt(11);

        Competitor competitor = new Competitor(firstName, lastName, country, r1, r2, r3);
        competitors.add(competitor);
    }

    private String convertToJson(List<Competitor> competitors) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < competitors.size(); i++) {
            Competitor c = competitors.get(i);
            json.append("{")
                    .append("\"firstName\":\"").append(c.getFirstName()).append("\",")
                    .append("\"lastName\":\"").append(c.getLastName()).append("\",")
                    .append("\"country\":\"").append(c.getCountry()).append("\",")
                    .append("\"r1\":").append(c.getR1()).append(",")
                    .append("\"r2\":").append(c.getR2()).append(",")
                    .append("\"r3\":").append(c.getR3())
                    .append("}");
            if (i < competitors.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }
}
