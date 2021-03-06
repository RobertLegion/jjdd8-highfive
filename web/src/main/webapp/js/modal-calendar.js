$(function () {

    $('#modal-calendar-button').click(function(){
        $('#calendarModal').modal('show');
    });

    $('#calendar-modal').calendar({
        enableContextMenu: true,
        enableRangeSelection: false,
        weekStart: 1,
        mouseOnDay: function (e) {
            if (e.events.length > 0) {
                var content = '';

                for (var i in e.events) {
                    content += '<div class="event-tooltip-content">'
                        + '<div class="event-name" style="color:' + e.events[i].color + '">' + e.events[i].name + '</div>'
                        + '<div class="event-location">' + e.events[i].location + '</div>'
                        + '<div class="event-location">' + e.events[i].type + '</div>'
                        + '</div>';
                }

                $(e.element).popover({
                    trigger: 'manual',
                    container: 'body',
                    html: true,
                    content: content
                });

                $(e.element).popover('show');
            }
        },
        mouseOutDay: function (e) {
            if (e.events.length > 0) {
                $(e.element).popover('hide');
            }
        },
        dayContextMenu: function (e) {
            $(e.element).popover('hide');
        },
        dataSource: null
    });

    var dataS = [];
    $.ajax({
        type: "GET",
        url: "/api/holiday/list",
        enctype: 'application/json;charset=UTF-8',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8'
    })
        .done(
            function (data) {
                $.each(data, function (i, item) {
                    dataS.push({
                        id: item.id,
                        name: item.name,
                        location: item.description,
                        type: item.type,
                        typeStyle: 'box',
                        startDate: new Date(item.year, item.month - 1, item.day),
                        endDate: new Date(item.year, item.month - 1, item.day)
                    });
                });
                $('#calendar-modal').data('calendar').setDataSource(dataS);
            })
        .fail(
            function () {
                $('#modal-info-body').html("Ups... Operation failed!!!");
                $('#infoModal').modal('show');
            });

});