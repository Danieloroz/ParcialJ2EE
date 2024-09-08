document.addEventListener('DOMContentLoaded', function() {
    var modal = document.getElementById("competitorModal");
    var span = document.getElementsByClassName("close")[0];

    document.querySelectorAll(".competitor-name").forEach(item => {
        item.addEventListener("click", function(event) {
            var content = `
                <p>Nombre: ${this.dataset.name}</p>
                <p>Edad: ${this.dataset.age}</p>
                <p>Descripción: ${this.dataset.description}</p>
                <p>País: ${this.dataset.country}</p>
                <p>Puntajes: ${this.dataset.scores}</p>
            `;
            document.getElementById("modalContent").innerHTML = content;
            modal.style.display = "block";
        });
    });

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
    function sortTable(n) {
        var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
        table = document.querySelector("table");
        switching = true;
        dir = "asc";
        while (switching) {
            switching = false;
            rows = table.rows;
            for (i = 1; i < (rows.length - 1); i++) {
                shouldSwitch = false;
                x = rows[i].getElementsByTagName("TD")[n];
                y = rows[i + 1].getElementsByTagName("TD")[n];
                if (dir == "asc") {
                    if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                        shouldSwitch = true;
                        break;
                    }
                } else if (dir === "desc") {
                    if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                        shouldSwitch = true;
                        break;
                    }
                }
            }
            if (shouldSwitch) {
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
                switchcount++;
            } else {
                if (switchcount === 0 && dir === "asc") {
                    dir = "desc";
                    switching = true;
                }
            }
        }
    }
    function showDetails(id) {
        fetch(`/competitor-details?id=${id}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok.');
                }
                return response.json();
            })
            .then(data => {
                let details = `
                Nombre: ${data.firstName} ${data.lastName}\n
                País: ${data.country}\n
                R1: ${data.r1}\n
                R2: ${data.r2}\n
                R3: ${data.r3}
            `;
                alert(details);
            })
            .catch(error => console.error('Error:', error));
    }
    function applyRowColors() {
        const rows = document.querySelectorAll('tbody tr');
        rows.forEach((row, index) => {
            if (index === 0) row.classList.add('gold');
            else if (index === 1) row.classList.add('silver');
            else if (index === 2) row.classList.add('bronze');
        });
    }
    window.onload = function() {
        applyRowColors();
    };
});