// Select required DOM elements
const sidebar = document.querySelector(".sidebar");
const contentArea = document.querySelector(".content-area-user-dashboard");
const inOutButton = document.querySelector(".toggle-button");

// Toggle sidebar visibility and adjust content area layout
const toggleSideBar = () => {
    if (sidebar.style.display !== "none") {
        // Hide sidebar and adjust content area when sidebar is visible
        sidebar.style.display = "none";
        contentArea.style.marginLeft = "0%"; // Adjust content margin for full-width display
        // Update button appearance for sidebar hidden state
        inOutButton.classList.add("fa-sign-out");
        inOutButton.classList.remove("fa-sign-in");
        inOutButton.classList.remove("in-button");
        inOutButton.classList.add("out-button");
    } else {
        // Show sidebar and adjust content area when sidebar is hidden
        sidebar.style.display = "block";
        contentArea.style.marginLeft = "20%"; // Adjust content margin to make room for sidebar
        // Update button appearance for sidebar visible state
        inOutButton.classList.remove("fa-sign-out");
        inOutButton.classList.add("fa-sign-in");
        inOutButton.classList.add("in-button");
        inOutButton.classList.remove("out-button");
    }
};

// Adjust sidebar visibility on window resize for responsiveness
window.addEventListener("resize", () => {
    if (window.innerWidth > 780) {
        // Show the sidebar and reset layout for larger screens
        sidebar.style.display = "block";
        contentArea.style.marginLeft = "20%";
        // Update button state for larger screens
        inOutButton.classList.remove("fa-sign-out");
        inOutButton.classList.add("fa-sign-in");
        inOutButton.classList.add("in-button");
        inOutButton.classList.remove("out-button");
    } else {
        // Ensure sidebar is hidden and layout is adjusted for smaller screens
        sidebar.style.display = "none";
        contentArea.style.marginLeft = "0%";
        // Update button state for smaller screens
        inOutButton.classList.add("fa-sign-out");
        inOutButton.classList.remove("fa-sign-in");
        inOutButton.classList.remove("in-button");
        inOutButton.classList.add("out-button");
    }
});

// Wait for the DOM to fully load before executing the script
document.addEventListener("DOMContentLoaded", function () {
    console.log("Script loaded");
    
    // Select navbar toggle button and links
    const toggleButton = document.querySelector(".navbar-toggler");
    const navbarLinks = document.querySelector("#navbarNavAltMarkup");
    const navbarList = document.querySelector("#navbarList");

    // Ensure required elements exist before adding event listeners
    if (toggleButton && navbarLinks) {
        console.log("Elements found");
        // Toggle classes for navbar on button click
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

// Function to confirm contact deletion with a popup
function deleteContact(id) {
    // Display confirmation popup using SweetAlert
    swal({
        title: "Are you sure?",
        text: "Once deleted, you will not be able to recover this contact.",
        icon: "warning",
        buttons: true,
        dangerMode: true, // Highlight the danger mode for deletion
    })
    .then((willDelete) => {
        // If user confirms deletion, redirect to the deletion endpoint
        if (willDelete) {
            window.location = "/user/delete-contact/" + id;
        } else {
            swal("Your Contact is safe"); // Show message if user cancels deletion
        }
    });
}
