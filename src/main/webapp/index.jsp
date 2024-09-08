<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, org.bmx.parcial.Competitor" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <script src="js/scripts.js"></script>
    <title>Clasificación Olímpica de BMX</title>
</head>
<body>
<header>
    <h1>Clasificación Olímpica de BMX</h1>
</header>

<main>
    <h2>Competidores</h2>

    <form action="competitors" method="post">
        <input type="hidden" name="action" value="add">
        <label>Nombre: <input type="text" name="firstName" required></label><br>
        <label>Apellido: <input type="text" name="lastName" required></label><br>
        <label for="country">País:</label>
        <select id="country" name="country" required>
            <option value="USA">Estados Unidos</option>
            <option value="CAN">Canada</option>
            <option value="GBR">Reino Unido</option>
            <option value="AUS">Australia</option>
            <option value="NZL">Nueva Zelanda</option>
            <option value="BRA">Brasil</option>
            <option value="ARG">Argentina</option>
            <option value="GER">Alemania</option>
            <option value="FRA">Francia</option>
            <option value="JPN">Japon</option>
            <option value="ITA">Italia</option>
            <option value="ESP">España</option>
            <option value="MEX">Mexico</option>
            <option value="CHN">China</option>
            <option value="KOR">Corea del Sur</option>
            <option value="IND">India</option>
            <option value="ZAF">Sudafrica</option>
            <option value="NED">Paises Bajos</option>
            <option value="BEL">Belgica</option>
            <option value="SWE">Suecia</option>
            <option value="NOR">Noruega</option>
        </select><br>

        <label>Ronda 1: <input type="number" name="r1" min="0" max="10" required></label><br>
        <label>Ronda 2: <input type="number" name="r2" min="0" max="10" required></label><br>
        <label>Ronda 3: <input type="number" name="r3" min="0" max="10" required></label><br>
        <button type="submit">Agregar Competidor</button>

    </form>
    <div class="competitors-table">
    <table>
        <thead>
        <tr>
            <th><a href="competitors?sort=name">Nombre</a></th>
            <th><a href="competitors?sort=country">País</a></th>
            <th><a href="competitors?sort=r1">Ronda 1</a></th>
            <th><a href="competitors?sort=r2">Ronda 2</a></th>
            <th><a href="competitors?sort=r3">Ronda 3</a></th>
            <th><a href="competitors?sort=totalScore">Puntuación Total</a></th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Competitor> competitors = (List<Competitor>) session.getAttribute("competitors");
            if (competitors != null) {
                for (Competitor competitor : competitors) {

        %>
        <tr>
            <td>href="#" onclick="showDetails(<%= competitor.getId() %>)">
            <td><%= competitor.getFirstName() %></td>
            <td><%= competitor.getLastName() %></td>
            <td><%= competitor.getCountry() %></td>
            <td><%= competitor.getR1() %></td>
            <td><%= competitor.getR2() %></td>
            <td><%= competitor.getR3() %></td>
            <td><%= competitor.getTotalScore() %></td>
            <td class="actions">
                <form action="competitors" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="<%= competitor.getId() %>">
                    <button type="submit">Eliminar</button>
                </form>
                <form action="competitors" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="<%= competitor.getId() %>">
                    <input type="number" name="r1" value="<%= competitor.getR1() %>" min="0" max="10">
                    <input type="number" name="r2" value="<%= competitor.getR2() %>" min="0" max="10">
                    <input type="number" name="r3" value="<%= competitor.getR3() %>" min="0" max="10">
                    <button type="submit">Actualizar</button>
                </form>
            </td>
        </tr>
        <% } } %>
        </tbody>
    </table>

    <hr>
    <form id="sort-form" action="competitors" method="get">
        <label for="sort-select">Ordenar por:</label>
        <select id="sort-select" name="sort" onchange="sortTable()">
            <option value="name">Nombre</option>
            <option value="country">País</option>
            <option value="r1">Ronda 1</option>
            <option value="r2">Ronda 2</option>
            <option value="r3">Ronda 3</option>
            <option value="totalScore">Puntuación Acumulada</option>
        </select>
    </form>
    <form action="competitors" method="post">
        <input type="hidden" name="action" value="simulate">
        <button type="submit">Simular Carrera</button>
    </form>

    <form action="competitors" method="post">
        <input type="hidden" name="action" value="generateRandom">
        <button type="submit">Generar Competidor Aleatorio</button>
    </form>


    <hr>

    <form action="export" method="post">
        <button type="submit" name="format" value="excel">Exportar a Excel</button>
        <button type="submit" name="format" value="json">Exportar a JSON</button>
    </form>
    </div>
</main>

<footer>
    <p>&copy; <%= new java.util.Date().getYear() + 1900 %> - Daniel Ricardo Orozco Soler</p>
</footer>

<script>

</script>
</body>
</html>