var acc = document.getElementsByClassName("accordion");

var accNormal = document.getElementById("accNormal");
var accSlider = document.getElementById("accSlider");
var accCustom = document.getElementById("accCustom");
var accTable = document.getElementById("accTable");

var normalPanel = accNormal.nextElementSibling;
normalPanel.style.display = "block";

var redSlider = document.getElementById("redPieceSlider");
var whiteSlider = document.getElementById("whitePieceSlider");

var redPieceOutput = document.getElementById("redPieceOutput");
var whitePieceOutput = document.getElementById("whitePieceOutput");

redPieceOutput.innerHTML = redSlider.value; // Display the default slider value
whitePieceOutput.innerHTML = whiteSlider.value; // Display the default slider value

// Update the current slider value (each time you drag the slider handle)
redSlider.oninput = function() {
  redPieceOutput.innerHTML = this.value;
};

whiteSlider.oninput = function() {
  whitePieceOutput.innerHTML = this.value;
};

var i;

for (i = 0; i < acc.length; i++) {
  acc[i].addEventListener("click", function() {
    collapse();

    // /* Toggle between adding and removing the "active" class,
    // to highlight the button that controls the panel */
    // this.classList.toggle("active");
    //
    // /* Toggle between hiding and showing the active panel */
    var panel = this.nextElementSibling;
    if (panel.style.display === "block") {
      panel.style.display = "none";
    } else {
      panel.style.display = "block";
    }
  });
}

/**
 * A handler function to prevent default submission and run our custom script.
 * @param  {Event} oppName  the name of the selected opponent
 * @return {void}
 */
function handleFormSubmit(oppName) {
  var normalPanel = accNormal.nextElementSibling;
  var sliderPanel = accSlider.nextElementSibling;
  var customPanel = accCustom.nextElementSibling;
  var tablePanel = accTable.nextElementSibling;
  var data;

  if(normalPanel.style.display === "block"){
    data = {"opponent":oppName};
  } else if (sliderPanel.style.display === "block") {
    data = {"opponent":oppName,
            "numRedPieces":redPieceOutput.innerHTML,
            "numWhitePieces":whitePieceOutput.innerHTML};
  } else if(customPanel.style.display === "block") {
    alert('customPanel');
  } else if(tablePanel.style.display === "block") {
    alert('tablePanel');
  }

  // var data = {"opponent":oppName};
  var dataStr = JSON.stringify(data);
  var http = new XMLHttpRequest();
  var url = '/selectOpponent';
  http.open('POST', url, true);
  // http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  http.setRequestHeader('Content-type', 'application/json');
  http.send(dataStr);
  // document.location.reload(true)
  setTimeout("location.reload(true);",500);
}


function collapse(){
  var normalPanel = accNormal.nextElementSibling;
  var sliderPanel = accSlider.nextElementSibling;
  var customPanel = accCustom.nextElementSibling;
  var tablePanel = accTable.nextElementSibling;

  normalPanel.style.display = "none";
  sliderPanel.style.display = "none";
  customPanel.style.display = "none";
  tablePanel.style.display = "none";
}
