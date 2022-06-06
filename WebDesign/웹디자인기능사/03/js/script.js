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
    
    $('.tab-menu > li').click(function () {
        $('.tab-menu > li').removeClass('on');
        $('.tabItem > ul').removeClass('on')
        var idx = $(this).index();
        $(this).addClass('on');
        
        $('.tabItem > ul').eq(idx).addClass('on');
    });
    
    $('.notice li').eq(0).click(function () {
        $('.popupBg').show();
    });
    
    $('.closeBtn').click(function () {
        $('.popupBg').hide();
    });
    
});