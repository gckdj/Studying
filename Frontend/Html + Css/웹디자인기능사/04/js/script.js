$(document).ready(function () {
    $('.lv1').mouseenter(function () {
        $('.lv2').stop().slideDown();
        $('.headerBg').stop().slideDown();
    });
    
    $('.lv1').mouseleave(function () {
        $('.lv2').stop().slideUp();
        $('.headerBg').stop().slideUp();
    });
    
    $('.slideImg:gt(0)').hide();
    
    setInterval(function () {
        $('.slideImg').eq(0).fadeOut(1000).next().fadeIn(1000);
        $('.slideImg').eq(0).appendTo('.slide');
    }, 2000);
    
    $('.closeBtn').click(function () {
        $('.popupBg').hide();
    })
    
    $('.noticeContent li').eq(0).click(function () {
        $('.popupBg').show();
    });
});