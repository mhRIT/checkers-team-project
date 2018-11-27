var acc = document.getElementsByClassName("accordion");

var accNormal = document.getElementById("accNormal");
var accSlider = document.getElementById("accSlider");
var accPreset = document.getElementById("accPreset");
var accCustom = document.getElementById("accCustom");

var normalPanel = accNormal.nextElementSibling;
var sliderPanel = accSlider.nextElementSibling;
var presetPanel = accPreset.nextElementSibling;
var customPanel = accCustom.nextElementSibling;

normalPanel.style.display = "block";
handleSetConfig();

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

var presetSelected = "start";

redPieceOutput.innerHTML = redSlider.value; // Display the default slider value
whitePieceOutput.innerHTML = whiteSlider.value; // Display the default slider value

// Update the current slider value (each time you drag the slider handle)
redSlider.oninput = function() {
  redPieceOutput.innerHTML = this.value;
  setConfigSlider();
};

whiteSlider.oninput = function() {
  whitePieceOutput.innerHTML = this.value;
  setConfigSlider();
};

var i;
for (i = 0; i < acc.length; i++) {
  acc[i].addEventListener("click", function() {
    collapse();
    var panel = this.nextElementSibling;
    panel.style.display = "block";
    handleSetConfig();
  });
}

function handleSetConfig() {
  if(normalPanel.style.display === "block"){
    setConfigNormal();
  } else if(sliderPanel.style.display === "block"){
    setConfigSlider()
  } else if(presetPanel.style.display === "block"){
    setConfigPreset();
  } else if(customPanel.style.display === "block"){
    setConfigCustom();
  }
}

function setConfigNormal(){
  if(normalPanel.style.display === "block"){
    var data = {"type":accNormal.name};
    sendData("/buildConfig", data)
  }
}

function setConfigSlider(){
  if(sliderPanel.style.display === "block"){
    var data = {"type":accSlider.name,
                "numRedPieces":redSlider.value,
                "numWhitePieces":whiteSlider.value};
    sendData("/buildConfig", data)
  }
}

function setConfigPreset(){
  if(presetPanel.style.display === "block"){
    var data = {"type":accPreset.name, "preset":presetSelected};
    sendData("/buildConfig", data)
  }
}

function setConfigCustom(){
  if(customPanel.style.display === "block"){
    var data = {"type":accCustom.name};
    sendData("/buildConfig", data)
  }
}

/**
 * A handler function to prevent default submission and run our custom script.
 * @param  {Event} oppName  the name of the selected opponent
 * @return {void}
 */
function handleFormSubmit(oppName) {
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

  sendData("/selectOpponent", data);
}

function sendData(url, data){
  var dataStr = JSON.stringify(data);
  var http = new XMLHttpRequest();
  http.open('post', url, true);
  // http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  http.setRequestHeader('Content-type', 'application/json');
  http.send(dataStr);
  // document.location.reload(true)
  // setTimeout(location.reload(true),500);
}

function collapse(){
  normalPanel.style.display = "none";
  sliderPanel.style.display = "none";
  presetPanel.style.display = "none";
  customPanel.style.display = "none";
}

function checkPresetStart() {
  presetSelected = "start";
  presetMidCheckbox.checked = false;
  presetEndCheckbox.checked = false;

  setConfigPreset();
}

function checkPresetMid() {
  presetSelected = "middle";
  presetStartCheckbox.checked = false;
  presetEndCheckbox.checked = false;

  setConfigPreset();
}

function checkPresetEnd() {
  presetSelected = "end";
  presetStartCheckbox.checked = false;
  presetMidCheckbox.checked = false;

  setConfigPreset();
}
