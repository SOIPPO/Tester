var getFirstElement = function (obj) {
    for (var key in obj) {
        return obj[key];
    }
};

var displayError = function (element, message) {
    clearMessages();
    $('#' + element).tooltip({'title': message, 'placement': 'bottom'});
    $('#' + element).tooltip('show');
};

var clearMessages = function (element) {
    $('#' + element).tooltip();
    $('#' + element).tooltip('destroy');
};