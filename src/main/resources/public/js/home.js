var acc = document.getElementsByClassName("accordion");

var accNormal = document.getElementById("accNormal");
var accSlider = document.getElementById("accSlider");
var accPreset = document.getElementById("accPreset");
var accCustom = document.getElementById("accCustom");

var normalPanel = accNormal.nextElementSibling;
normalPanel.style.display = "block";

var redSlider = document.getElementById("redPieceSlider");
var whiteSlider = document.getElementById("whitePieceSlider");

var redPieceOutput = document.getElementById("redPieceOutput");
var whitePieceOutput = document.getElementById("whitePieceOutput");

var presetStartCheckbox = document.getElementById("presetStartCheckbox");
var presetMidCheckbox = document.getElementById("presetMidCheckbox");
var presetEndCheckbox = document.getElementById("presetEndCheckbox");

presetStartCheckbox.checked = true;

presetStartCheckbox.onchange = checkPresetStart;
presetMidCheckbox.onchange = checkPresetMid;
presetEndCheckbox.onchange = checkPresetEnd;

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
  var presetPanel = accPreset.nextElementSibling;
  var customPanel = accCustom.nextElementSibling;
  var data;

  if(normalPanel.style.display === "block"){
    data = {"opponent":oppName};
  } else if (sliderPanel.style.display === "block") {
    data = {"opponent":oppName,
            "numRedPieces":redPieceOutput.innerHTML,
            "numWhitePieces":whitePieceOutput.innerHTML};
  } else if(presetPanel.style.display === "block") {
    if(presetStartCheckbox.checked){
      data = {"opponent":oppName,
              "boardIdx":1};
    } else if(presetMidCheckbox.checked){
      data = {"opponent":oppName,
              "boardIdx":2};
    } else if(presetEndCheckbox.checked){
      data = {"opponent":oppName,
              "boardIdx":3};
    }
  } else if(customPanel.style.display === "block") {
    alert('customPanel');
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
  var presetPanel = accPreset.nextElementSibling;
  var customPanel = accCustom.nextElementSibling;

  normalPanel.style.display = "none";
  sliderPanel.style.display = "none";
  presetPanel.style.display = "none";
  customPanel.style.display = "none";
}

function checkPresetStart() {
  presetMidCheckbox.checked = false;
  presetEndCheckbox.checked = false;
}

function checkPresetMid() {
  presetStartCheckbox.checked = false;
  presetEndCheckbox.checked = false;
}

function checkPresetEnd() {
  presetStartCheckbox.checked = false;
  presetMidCheckbox.checked = false;
}
