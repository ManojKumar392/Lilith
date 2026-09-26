const currentUser =
    JSON.parse(localStorage.getItem("lilithUser"));

if (!currentUser) {
    window.location.href = "/login.html";
}

let selectedSeverity = "ALL";

document.getElementById("user-name").textContent =
    currentUser.name;

document
    .getElementById("logout-button")
    .addEventListener("click", function () {

        localStorage.removeItem("lilithUser");

        window.location.href = "/login.html";
    });

const map = L.map("map").setView(
    [20.2961, 85.8245],
    13
);

L.tileLayer(
    "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
    {
        maxZoom: 19,
        attribution: "&copy; OpenStreetMap contributors"
    }
).addTo(map);


// -----------------------
// Layers
// -----------------------

const areaLayer = L.layerGroup().addTo(map);

let selectedMarker = null;


// -----------------------
// Colors
// -----------------------

function getSeverityColor(level) {

    switch (level) {

        case "YELLOW":
            return "#f1c40f";

        case "ORANGE":
            return "#e67e22";

        case "RED":
            return "#e74c3c";

        default:
            return "#95a5a6";
    }
}


// -----------------------
// Load Areas
// -----------------------

async function loadAreas() {

    try {

        const response = await fetch("/api/areas");

        if (!response.ok) {
            throw new Error("Could not load areas");
        }

        const areas = await response.json();

        areaLayer.clearLayers();

        areas.forEach(area => {

            if (
                selectedSeverity !== "ALL" &&
                area.severityLevel !== selectedSeverity
            ) {
                return;
            }

            const bounds = [
                [
                    area.minLatitude,
                    area.minLongitude
                ],
                [
                    area.maxLatitude,
                    area.maxLongitude
                ]
            ];

            const color =
                getSeverityColor(area.severityLevel);

            const rectangle = L.rectangle(
                bounds,
                {
                    color: color,
                    weight: 2,
                    fillColor: color,
                    fillOpacity: 0.4,
                    bubblingMouseEvents: true
                }
            );

            rectangle.bindPopup(`
                <div>
                    <h3>Area Information</h3>

                    <p>
                        <strong>Severity:</strong>
                        ${area.severityLevel}
                    </p>

                    <p>
                        <strong>Severity Score:</strong>
                        ${area.severityScore}
                    </p>

                    <p>
                        <strong>Reports:</strong>
                        ${area.reportCount}
                    </p>
                </div>
                <small>
                    Click this area to select the location for a report.
                </small>
            `);
            rectangle.on("click", function(event) {

                selectLocation(
                    event.latlng.lat,
                    event.latlng.lng
                );
            });

            rectangle.addTo(areaLayer);
        });

    } catch (error) {

        console.error(
            "Failed to load areas:",
            error
        );
    }
}


// -----------------------
// Map Click
// -----------------------

function selectLocation(latitude, longitude) {

    document.getElementById("latitude").value =
        latitude.toFixed(6);

    document.getElementById("longitude").value =
        longitude.toFixed(6);

    if (selectedMarker) {
        map.removeLayer(selectedMarker);
    }

    selectedMarker = L.marker([
        latitude,
        longitude
    ]).addTo(map);
}


map.on("click", function(event) {

    selectLocation(
        event.latlng.lat,
        event.latlng.lng
    );
});


// -----------------------
// Submit Report
// -----------------------

document
    .getElementById("report-form")
    .addEventListener("submit", async function(event) {

        event.preventDefault();

        const message =
            document.getElementById("message");

        const latitude =
            document.getElementById("latitude").value;

        const longitude =
            document.getElementById("longitude").value;

        if (!latitude || !longitude) {
            message.textContent =
                "Please select a location on the map.";
            return;
        }

        const report = {

            // Temporary until login is implemented
            userId: currentUser.id,

            latitude: parseFloat(latitude),

            longitude: parseFloat(longitude),

            reason:
            document.getElementById("reason").value,

            severity:
            document.getElementById("severity").value,

            description:
            document.getElementById("description").value
        };

        try {

            const response = await fetch(
                "/api/reports",
                {
                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify(report)
                }
            );

            if (!response.ok) {
                throw new Error("Failed to submit report");
            }

            message.textContent =
                "Report submitted successfully.";

            document
                .getElementById("report-form")
                .reset();

            if (selectedMarker) {
                map.removeLayer(selectedMarker);
                selectedMarker = null;
            }

            await loadAreas();

        } catch (error) {

            console.error(error);

            message.textContent =
                "Could not submit report.";
        }
    });

document
    .querySelectorAll(".filter-button")
    .forEach(button => {

        button.addEventListener(
            "click",
            function () {

                document
                    .querySelectorAll(".filter-button")
                    .forEach(btn =>
                        btn.classList.remove("active")
                    );

                this.classList.add("active");

                selectedSeverity =
                    this.dataset.level;

                loadAreas();
            }
        );
    });


// Initial load
loadAreas();