$(function () {

    // 네비게이션
    $("header nav ul.gnb > li").mouseenter(function () {
        $(".nav_bg, ul.sub").stop().slideDown();
    });
    
    $("header nav ul.gnb > li").mouseleave(function () {
        $(".nav_bg, ul.sub").stop().slideUp();
    });
    
    // 슬라이드
});

var slideId = 0;
setInterval(function () {
    if (slideId < 2) {
        slideId++;
    } else {
        slideId = 0;
    }
    
    $("ul.slide li").eq(slideId).siblings().animate({
        left: "-1200px"
    }, 500);
    
    $("ul.slide li").eq(slideId).animate({
        left: "0"
    }, 500);
    
}, 3000);