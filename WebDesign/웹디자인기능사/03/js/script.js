$(function () {
    $('li.lv1').mouseenter(function() {
        $(this).find('ul.lv2').stop().slideDown();
    });
    
    $('li.lv1').mouseleave(function() {
        $('ul.lv2').stop().slideUp();
    });
    
    $('.slide:nth-child(2)').hide();
    $('.slide:nth-child(3)').hide();
    setInterval(function () {
        $('.slide:first').fadeOut(1500).next().fadeIn(1500);
        $('.slide:first').appendTo('.slideWrap');
    }, 3000);
});