$(function () {
    $('.menu > li').mouseover(function() {
    console.log("222")
        $(this).find(ul).show();
    });
});