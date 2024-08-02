$(document).ready(function () {


    $('.nav-tabs > li a[title]').tooltip();

    // Wizard
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        var target = $(e.target);
        if (target.parent().hasClass('disabled')) {

            return false;
        }
    });
    // Detect hash in URL and open the corresponding tab
    if(window.location.hash) {
        var hash = window.location.hash;
        var activeTab = $('.wizard .nav-tabs li a[href="' + hash + '"]');
        if(activeTab.length > 0) {
            activeTab.click();
        }
    }


    $(".next-step").click(function (e) {
        var active = $('.wizard .nav-tabs li.active');
        if (active.length > 0) {
            active.next().removeClass('disabled');
            nextTab(active);
        } else {
            console.log('No active tab found');
        }
    });

    $(".prev-step").click(function (e) {
        var active = $('.wizard .nav-tabs li.active');
        if (active.length > 0) {
            prevTab(active);
        } else {
            console.log('No active tab found');
        }
    });
});

function nextTab(elem) {
    if ($(elem).length > 0) {
        var next = $(elem).next();
        if (next.length > 0) {
            var nextTabLink = next.find('a[data-toggle="tab"]');
            if (nextTabLink.length > 0) {
                nextTabLink.click();
            } else {
                console.log('Next tab link not found');
            }
        } else {
            console.log('No next tab found');
        }
    } else {
        console.log('Current element not found');
    }
}

function prevTab(elem) {
    if ($(elem).length > 0) {
        var prev = $(elem).prev();
        if (prev.length > 0) {
            var prevTabLink = prev.find('a[data-toggle="tab"]');
            if (prevTabLink.length > 0) {
                prevTabLink.click();
            } else {
                console.log('Previous tab link not found');
            }
        } else {
            console.log('No previous tab found');
        }
    } else {
        console.log('Current element not found');
    }
}

$('.nav-tabs').on('click', 'li', function() {
    $('.nav-tabs li.active').removeClass('active');
    $(this).addClass('active');
});

function validoPaso1() {
    var nombreEvento = $("input[name='nombre']").val();
    if (!nombreEvento.trim()) {
        alert('El nombre del evento no puede estar vacío. Por favor, ingrese un nombre.');
        return;
    }
    console.log('Event name:', nombreEvento);

    $.ajax({
        url: '/validarNombreEvento',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data: { name: nombreEvento },
        success: function(response) {
            if (response.valido) {
                var active = $('.wizard .nav-tabs li.active');
                if (active.length > 0) {
                    active.removeClass('disabled');
                    nextTab(active);
                }
            } else {
                alert('El nombre del evento ya existe. Por favor, elige otro nombre.');
            }
        },
        error: function(xhr, status, error) {
            alert('Hubo un error en la validación. Intenta nuevamente.');
        }
    });
}


