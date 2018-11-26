var acc = document.getElementsByClassName("accordion");

var accSlider = document.getElementById("accSlider");
var accCustom = document.getElementById("accCustom");
var accTable = document.getElementById("accTable");

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

function collapse(){
  var sliderPanel = accSlider.nextElementSibling;
  var customPanel = accCustom.nextElementSibling;
  var tablePanel = accTable.nextElementSibling;

  sliderPanel.style.display = "none";
  customPanel.style.display = "none";
  tablePanel.style.display = "none";
}

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
