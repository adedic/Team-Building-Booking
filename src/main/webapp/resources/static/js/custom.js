$(function() {
    $('input[name="date"]').daterangepicker({
        opens: 'down',
        singleDatePicker: true,
        locale: {
            cancelLabel: 'Odustani'
        }
    }, function(start, end, label) {
        console.log("A new date selection was made: " + start.format('DD-MM-YYYY') + ' to ' + end.format('DD-MM-YYYY'));
    });
});