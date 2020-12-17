window.onscroll = function() {scrollFunction()};
function scrollFunction() {
    if (document.body.scrollTop > 99 || document.documentElement.scrollTop > 99) {
        document.getElementById("navbar").style.backgroundColor="rgba(255,255,255,0.9)";
    } else {
        document.getElementById("navbar").style.backgroundColor="rgba(0,0,0,0)";
    }
}