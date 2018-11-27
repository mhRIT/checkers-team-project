var accNormal = document.getElementById("accNormal");
var accSlider = document.getElementById("accSlider");
var accPreset = document.getElementById("accPreset");
var accCustom = document.getElementById("accCustom");

var normalPanel = document.getElementById("normalPanel");
var sliderPanel = document.getElementById("sliderPanel");
var presetPanel = document.getElementById("presetPanel");
var customPanel = document.getElementById("customPanel");

accNormal.addEventListener("click", function() {
  collapse();
  var panel = normalPanel;
  if(panel.style.display === "block"){
    panel.style.display = "none";
  } else {
    panel.style.display = "block";
  }
  handleSetConfig();
});

accSlider.addEventListener("click", function() {
  collapse();
  var panel = sliderPanel;
  if(panel.style.display === "block"){
    panel.style.display = "none";
  } else {
    panel.style.display = "block";
  }
  handleSetConfig();
});

accPreset.addEventListener("click", function() {
  collapse();
  var panel = presetPanel;
  if(panel.style.display === "block"){
    panel.style.display = "none";
  } else {
    panel.style.display = "block";
  }
  handleSetConfig();
});

accCustom.addEventListener("click", function() {
  collapse();
  var panel = customPanel;
  if(panel.style.display === "block"){
    panel.style.display = "none";
  } else {
    panel.style.display = "block";
  }
  handleSetConfig();
});

var redSlider = document.getElementById("redPieceSlider");
var whiteSlider = document.getElementById("whitePieceSlider");

var redPieceOutput = document.getElementById("redPieceOutput");
var whitePieceOutput = document.getElementById("whitePieceOutput");

var presetStartCheckbox = document.getElementById("presetStartCheckbox");
var presetMidCheckbox = document.getElementById("presetMidCheckbox");
var presetEndCheckbox = document.getElementById("presetEndCheckbox");

var presetSelected;

var customBoardTable = document.getElementById("game-board");

presetStartCheckbox.onchange = checkPresetStart;
presetMidCheckbox.onchange = checkPresetMid;
presetEndCheckbox.onchange = checkPresetEnd;

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

if(presetMidCheckbox.checked === true){
  presetSelected = "middle";
} else if(presetEndCheckbox.checked === true){
  presetSelected = "end";
} else {
  presetStartCheckbox.checked = true;
  presetSelected = "start";
}
handleSetConfig();

// for (i = 0, row; row = customBoardTable.rows[i]; i++) {
//   //iterate through rows
//   //rows would be accessed using the "row" variable assigned in the for loop
//   for (var j = 0, col; col = row.cells[j]; j++) {
//     //iterate through columns
//     //columns would be accessed using the "col" variable assigned in the for loop
//     col.addEventListener("click", function() {
//       tableCellClicked(this);
//     });
//   }
// }

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

function tableCellClicked(tableCell){
  alert(tableCell.id);
}
