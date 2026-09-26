const currentUser =
    JSON.parse(
        localStorage.getItem("lilithUser")
    );

if (!currentUser) {
    window.location.href = "/login.html";
}


async function loadDashboard() {

    try {

        const response =
            await fetch("/api/dashboard");

        if (!response.ok) {
            throw new Error(
                "Failed to load dashboard"
            );
        }

        const data = await response.json();


        // ------------------
        // Stats
        // ------------------

        document.getElementById(
            "total-reports"
        ).textContent = data.totalReports;

        document.getElementById(
            "affected-areas"
        ).textContent = data.affectedAreas;


        // ------------------
        // Severity Chart
        // ------------------

        new Chart(
            document.getElementById(
                "severity-chart"
            ),
            {
                type: "bar",

                data: {
                    labels: [
                        "Low",
                        "Medium",
                        "High"
                    ],

                    datasets: [{
                        label: "Reports",

                        data: [
                            data.reportsBySeverity.LOW,
                            data.reportsBySeverity.MEDIUM,
                            data.reportsBySeverity.HIGH
                        ]
                    }]
                },

                options: {
                    responsive: true,
                    maintainAspectRatio: false
                }
            }
        );


        // ------------------
        // Reason Chart
        // ------------------

        new Chart(
            document.getElementById(
                "reason-chart"
            ),
            {
                type: "doughnut",

                data: {
                    labels: [
                        "Poor Lighting",
                        "Harassment",
                        "Theft",
                        "Isolated Area",
                        "Other"
                    ],

                    datasets: [{
                        data: [
                            data.reportsByReason.POOR_LIGHTING,
                            data.reportsByReason.HARASSMENT,
                            data.reportsByReason.THEFT,
                            data.reportsByReason.ISOLATED_AREA,
                            data.reportsByReason.OTHER
                        ]
                    }]
                },

                options: {
                    responsive: true,
                    maintainAspectRatio: false
                }
            }
        );

    } catch (error) {

        console.error(error);
    }
}


document
    .getElementById("logout-button")
    .addEventListener("click", function () {

        localStorage.removeItem("lilithUser");

        window.location.href =
            "/login.html";
    });

async function loadRecentReports() {

    try {

        const response =
            await fetch("/api/dashboard/recent");

        const reports =
            await response.json();

        const container =
            document.getElementById(
                "recent-reports"
            );

        container.innerHTML = "";

        if (reports.length === 0) {

            container.innerHTML =
                "<p>No reports yet.</p>";

            return;
        }

        reports.forEach(report => {

            const item =
                document.createElement("div");

            item.className = "report-item";

            const readableReason =
                report.reason
                    .replaceAll("_", " ");

            item.innerHTML = `
                <div class="report-details">

                    <strong>
                        ${readableReason}
                    </strong>

                    <p>
                        ${report.description || "No description"}
                    </p>

                </div>

                <span class="report-severity">
                    ${report.severity}
                </span>
            `;

            container.appendChild(item);
        });

    } catch (error) {

        console.error(
            "Could not load recent reports",
            error
        );
    }
}


loadRecentReports();


loadDashboard();