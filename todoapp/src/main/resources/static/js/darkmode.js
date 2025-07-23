document.addEventListener("DOMContentLoaded", function () {
    // 🌙 Dark mode toggle
    const toggle = document.getElementById("darkModeToggle");
    const body = document.body;
    const mode = localStorage.getItem("mode");
   
    function applyMode(isLight) {
        if (isLight) {
            body.classList.add("light-mode");
            toggle.innerHTML = "🌙";
        } else {
            body.classList.remove("light-mode");
            toggle.innerHTML = "🌞";
        }
    }

    applyMode(mode === "light");

    if (toggle) {
        toggle.addEventListener("click", function () {
            const isNowLight = !body.classList.contains("light-mode");
            applyMode(isNowLight);
            localStorage.setItem("mode", isNowLight ? "light" : "dark");
        });
    }
     applyThemeToNewGlassCards();

    // 📱 Sidebar toggle

document.addEventListener("DOMContentLoaded", function () {
    const sidebar = document.getElementById('sidebar');
    const pageContent = document.getElementById('page-content-wrapper');
    const sidebarToggle = document.getElementById('sidebarToggle');

    function adjustLayout() {
        if (window.innerWidth < 992) {
            pageContent.style.marginLeft = "0";
        } else if (!sidebar.classList.contains("collapsed")) {
            pageContent.style.marginLeft = "250px";
        } else {
            pageContent.style.marginLeft = "0";
        }
    }

    sidebarToggle.addEventListener('click', function () {
        sidebar.classList.toggle('collapsed');
        adjustLayout();
    });

    window.addEventListener("resize", adjustLayout);

    adjustLayout(); // initial alignment
});





    // 📝 Attach edit button handlers
    document.querySelectorAll('.btn-edit-todo').forEach(button => {
        button.addEventListener('click', function () {
            const todoId = this.getAttribute('data-id');
            if (!todoId) return;

            fetch(`/dashboard/edit-form/${todoId}`)
                .then(response => {
                    if (!response.ok) throw new Error("Network error");
                    return response.text();
                })
                .then(html => {
                    document.querySelector('#editTodoModal .modal-body').innerHTML = html;
                    const modal = new bootstrap.Modal(document.getElementById('editTodoModal'));
                    modal.show();
                })
                .catch(error => {
                    console.error("Error loading modal:", error);
                    alert("Failed to load edit modal.");
                });
        });
    });

    // 📝 Attach delegated submit handler for dynamically loaded form
    document.addEventListener('submit', function (event) {
        if (event.target && event.target.id === 'editTodoForm') {
            event.preventDefault();
            const form = event.target;
            const formData = new FormData(form);
            const todoId = form.querySelector('input[name="id"]').value;

            fetch(`/dashboard/edit/${todoId}`, {
                method: 'POST',
                body: formData
            })
            .then(response => {
                if (!response.ok) throw new Error("Failed to update todo.");
                // ✅ Close modal after success
                const modalEl = document.getElementById('editTodoModal');
                const modalInstance = bootstrap.Modal.getInstance(modalEl);
                modalInstance.hide();

                // ✅ Update row without refresh or fallback to reload
                location.reload(); // Simplest stable fallback

            })
            .catch(error => {
                console.error("Error updating todo:", error);
                alert("Failed to update todo.");
            });
        }
    });


    // ✅ Completion toggle (no refresh)
    document.querySelectorAll(".btn-complete").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");

            fetch(`/dashboard/complete/${id}`, { method: "POST" })
                .then(response => {
                    if (!response.ok) {
                        console.error("Error toggling completion for ID:", id);
                        return;
                    }

                    // Toggle button appearance
                    this.classList.toggle("btn-success");
                    this.classList.toggle("btn-outline-success");

                    // Update status badge without refresh
                    const row = this.closest("tr");
                    const statusCell = row.querySelector("td:nth-child(5)");
                    if (statusCell) {
                        if (statusCell.textContent.includes("Completed")) {
                            statusCell.innerHTML = '<span class="badge bg-warning text-dark">Pending</span>';
                        } else {
                            statusCell.innerHTML = '<span class="badge bg-success">Completed</span>';
                        }
                    }
                })
                .catch(error => {
                    console.error("Error toggling completion:", error);
                });
        });
    });

});
function applyThemeToNewGlassCards() {
    const isDark = document.body.classList.contains('dark-mode');
    document.querySelectorAll('.glass-card').forEach(card => {
        card.classList.toggle('dark-mode-card', isDark);
    });
}


