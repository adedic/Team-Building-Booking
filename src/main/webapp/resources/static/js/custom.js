$(function() {
    $('input[name="date"]').daterangepicker({
        opens: 'down',
        singleDatePicker: true,
        locale: {
            cancelLabel: 'Odustani',
            format: 'DD.MM.YYYY.'
        }
    }, function(start, end, label) {
        console.log("A new date selection was made: " + start.format('DD-MM-YYYY') + ' to ' + end.format('DD-MM-YYYY'));
    });
});

$(function() {
    $('input[name="dateFrom"]').daterangepicker({
        opens: 'down',
        singleDatePicker: true,
        locale: {
            cancelLabel: 'Odustani',
            format: 'DD.MM.YYYY.'
        }
    }, function(start, end, label) {
        console.log("A new date selection was made: " + start.format('DD-MM-YYYY') + ' to ' + end.format('DD-MM-YYYY'));
    });
});

$(function() {
    $('input[name="dateTo"]').daterangepicker({
        opens: 'down',
        singleDatePicker: true,
        locale: {
            cancelLabel: 'Odustani',
            format: 'DD.MM.YYYY.'
        }
    }, function(start, end, label) {
        console.log("A new date selection was made: " + start.format('DD-MM-YYYY') + ' to ' + end.format('DD-MM-YYYY'));
    });
});