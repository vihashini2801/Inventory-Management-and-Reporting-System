
/* PARTICLES */

particlesJS("particles-js",{
particles:{
number:{value:80},
size:{value:3},
move:{speed:2},
line_linked:{enable:true},
color:{value:"#4facfe"}
}
});


/* DARK LIGHT MODE */

let themeIndex = 0;

function toggleTheme(){

const themes = [
"linear-gradient(135deg,#141E30,#243B55)",
"linear-gradient(135deg,#1e3c72,#2a5298)",
"linear-gradient(135deg,#42275a,#734b6d)"
];

document.body.style.background = themes[themeIndex];

themeIndex++;

if(themeIndex>=themes.length){
themeIndex=0;
}

}


/* CHART */

const ctx = document.getElementById('inventoryChart');

new Chart(ctx, {

type: 'bar',

data: {

labels: ['Electronics','Furniture','Accessories'],

datasets: [{

label: 'Products',

data: [12,5,8]

}]

}

});


/* PDF DOWNLOAD */

function downloadPDF(){

window.print();

}