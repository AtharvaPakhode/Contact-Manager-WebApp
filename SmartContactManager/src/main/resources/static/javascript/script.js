const sidebar = document.querySelector(".sidebar");
const contentArea = document.querySelector(".content-area-user-dashboard");
const inOutButton = document.querySelector(".toggle-button");

const toggleSideBar = () => {
    if (sidebar.style.display !== "none") {
        sidebar.style.display = "none";
        contentArea.style.marginLeft = "0%";
        inOutButton.classList.add("fa-sign-out");
        inOutButton.classList.remove("fa-sign-in");
        inOutButton.classList.remove("in-button");
        inOutButton.classList.add("out-button");
    } else {
        sidebar.style.display = "block";
        contentArea.style.marginLeft = "20%";
        inOutButton.classList.remove("fa-sign-out");
        inOutButton.classList.add("fa-sign-in");
        inOutButton.classList.add("in-button");
        inOutButton.classList.remove("out-button");
    }
};

// Ensure proper state when resizing the window
window.addEventListener("resize", () => {
    if (window.innerWidth > 780) {
        // Show the sidebar and reset to default state
        sidebar.style.display = "block";
        contentArea.style.marginLeft = "20%";
        inOutButton.classList.remove("fa-sign-out");
        inOutButton.classList.add("fa-sign-in");
        inOutButton.classList.add("in-button");
        inOutButton.classList.remove("out-button");
    } else {
        // Ensure sidebar is hidden for small screens
        sidebar.style.display = "none";
        contentArea.style.marginLeft = "0%";
        inOutButton.classList.add("fa-sign-out");
        inOutButton.classList.remove("fa-sign-in");
        inOutButton.classList.remove("in-button");
        inOutButton.classList.add("out-button");
    }
});






document.addEventListener("DOMContentLoaded", function () {
    console.log("Script loaded");
    const toggleButton = document.querySelector(".navbar-toggler");
    const navbarLinks = document.querySelector("#navbarNavAltMarkup");
    const navbarList = document.querySelector("#navbarList");

    if (toggleButton && navbarLinks) {
        console.log("Elements found");
        toggleButton.addEventListener("click", function () {
            navbarLinks.classList.toggle("gradient-custom-2");
            navbarList.classList.toggle("ms-3");
            navbarList.classList.toggle("ms-auto");
            console.log("Class toggled:", navbarLinks.classList);
            console.log("Class toggled:", navbarList.classList);
        });
    } else {
        console.error("Required elements not found");
    }
});



