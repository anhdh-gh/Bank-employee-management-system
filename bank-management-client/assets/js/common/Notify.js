const Notify = {
    showSuccess: message => {
        DevExpress.ui.notify(message, "success", 5000);
    },

    showError: message => {
        DevExpress.ui.notify(message, "error", 5000);
    },

    showWarning: message => {
        DevExpress.ui.notify(message, "warning", 5000);
    },

    showInfo: message => {
        DevExpress.ui.notify(message, "success", 5000);
    }
}