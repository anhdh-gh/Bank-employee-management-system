const Notify = {
    showSuccess: message => {
        DevExpress.ui.notify(message, "success", 3000);
    },

    showError: message => {
        DevExpress.ui.notify(message, "error", 3000);
    },

    showWarning: message => {
        DevExpress.ui.notify(message, "warning", 3000);
    },

    showInfo: message => {
        DevExpress.ui.notify(message, "success", 3000);
    }
}