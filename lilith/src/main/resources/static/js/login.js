const loginTab =
    document.getElementById("login-tab");

const registerTab =
    document.getElementById("register-tab");

const loginForm =
    document.getElementById("login-form");

const registerForm =
    document.getElementById("register-form");

const message =
    document.getElementById("auth-message");


// -----------------------
// Tabs
// -----------------------

loginTab.addEventListener("click", function () {

    loginForm.classList.remove("hidden");
    registerForm.classList.add("hidden");

    loginTab.classList.add("active");
    registerTab.classList.remove("active");

    message.textContent = "";
});


registerTab.addEventListener("click", function () {

    registerForm.classList.remove("hidden");
    loginForm.classList.add("hidden");

    registerTab.classList.add("active");
    loginTab.classList.remove("active");

    message.textContent = "";
});


// -----------------------
// Login
// -----------------------

loginForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();

        const data = {

            email:
            document.getElementById(
                "login-email"
            ).value,

            password:
            document.getElementById(
                "login-password"
            ).value
        };

        try {

            const response = await fetch(
                "/api/users/login",
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body: JSON.stringify(data)
                }
            );

            if (!response.ok) {

                const errorData =
                    await response.json();

                throw new Error(
                    errorData.message ||
                    errorData.email ||
                    errorData.password ||
                    "Invalid email or password."
                );
            }
            const user = await response.json();

            localStorage.setItem(
                "lilithUser",
                JSON.stringify(user)
            );

            window.location.href = "/";

        } catch (error) {

            message.textContent =
                error.message;
        }
    }
);


// -----------------------
// Register
// -----------------------

registerForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();

        const data = {

            name:
            document.getElementById(
                "register-name"
            ).value,

            email:
            document.getElementById(
                "register-email"
            ).value,

            password:
            document.getElementById(
                "register-password"
            ).value
        };

        try {

            const response = await fetch(
                "/api/users/register",
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body: JSON.stringify(data)
                }
            );

            if (!response.ok) {

                const errorData =
                    await response.json();

                throw new Error(
                    errorData.message ||
                    errorData.email ||
                    errorData.password ||
                    errorData.name ||
                    "Could not create account."
                );
            }

            const user = await response.json();

            localStorage.setItem(
                "lilithUser",
                JSON.stringify(user)
            );

            window.location.href = "/";

        } catch (error) {

            message.textContent =
                error.message;
        }
    }
);