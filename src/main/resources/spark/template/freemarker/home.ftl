<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/home.css">

</head>
<body>
  <div class="page">
    <h1>Web Checkers</h1>
    
    <div class="navigation">
      <a href="/">Home</a> |
      <#if player??>
        <a href="/signout">sign out [${player.name}]</a>
      <#else>
        <a href="/signin">sign in</a>
      </#if>
    </div>

    <div class="body">
      <#if oppMessage??>
        <div>
          <p style="color: red">${oppMessage}</p>
        </div>
      </#if>

      <#if gameMessage??>
        <div>
          <p style="color: red">${gameMessage}</p>
        </div>
      </#if>

  <#------------------------------------------------------------------------------------------------->
      <table id="game-board">
        <tbody>
          <tr data-row="0">
            <td data-cell="0" class="Space">
              <div class="Piece"
                   id="piece-0-0"
                   data-type="SINGLE"
                   data-color="RED">
              </div>
            </td>
            <td data-cell="1" class="Space">
              <div class="Piece"
                   id="piece-0-0"
                   data-type="SINGLE"
                   data-color="RED">
              </div>
            </td>
            <td data-cell="2" class="Space">
              <div class="Piece"
                   id="piece-0-0"
                   data-type="SINGLE"
                   data-color="RED">
              </div>
            </td>
            <td data-cell="3" class="Space">
              <div class="Piece"
                   id="piece-0-0"
                   data-type="SINGLE"
                   data-color="RED">
              </div>
            </td>
          </tr>
        </tbody>
      </table>
<#------------------------------------------------------------------------------------------------->
      <#if aiPlayers??>
        <button class="accordion" id="accNormal">Normal start</button>
        <div class="panel">
          <p>Start a normal game</p>
        </div>

        <button class="accordion" id="accSlider">Random placement</button>
        <div class="panel">
          <p>Set the number of starting pieces for each player:</p>
          <div class="slidecontainer">
            <input type="range" min="1" max="12" value="6" class="slider" id="redPieceSlider">
            <p>Number of red pieces: </p>
            <output id="redPieceOutput" ></output>
            <br>
            <br>
            <input type="range" min="1" max="12" value="6" class="slider" id="whitePieceSlider">
            <p>Number of white pieces: </p>
            <output id="whitePieceOutput" ></output>
          </div>
        </div>

        <button class="accordion" id="accPreset">Preset boards</button>
        <div class="panel">
          <label for="presetStartCheckbox">Start</label>
          <input type="checkbox" id="presetStartCheckbox" />

          <label for="presetMidCheckbox">Mid</label>
          <input type="checkbox" id="presetMidCheckbox" />

          <label for="presetEndCheckbox">End</label>
          <input type="checkbox" id="presetEndCheckbox" />
        </div>

        <button class="accordion" id="accCustom">Custom board</button>
        <div class="panel">
          <p>Lorem ipsum...</p>
        </div>
      </#if>
<#------------------------------------------------------------------------------------------------->

      <form action="/selectOpponent" method="POST">
        <div class="vertical-menu">

          <#--Start AI display---->
          <#--If this list exists, then the player is signed in-->
          <#if aiPlayers??>
            <p> AI opponents:</p>
            <br>
            <#list aiPlayers as eachPlayerName>
                <#--<button class="active" type="submit" name="opponent" value="${eachPlayerName}">${eachPlayerName}</button>-->
              <button class="active" type="button"
                      name="opponent" value="${eachPlayerName}"
                      onclick="handleFormSubmit(this.value)">
                ${eachPlayerName}
              </button>
            </#list>
            <br>
            <br>
          </#if>
          <#--End AI display---->

          <#--Start player display-->
          <#if allPlayers??>
            <#if allPlayers?size gt 0>
              <p> Other players currently signed in:</p>
              <br>
              <#list allPlayers as eachPlayerName>
                <button class="active" type="button"
                        name="opponent" value="${eachPlayerName}"
                        onclick="handleFormSubmit(this.value)">
                  ${eachPlayerName}
                </button>
              </#list>
            <#else>
              <p style="color: red"> No other players are currently signed in</p>
            </#if>
          <#else>
            <#if numPlayers??>
              <p> Number of players: ${numPlayers}</p>
            <#else>
              <p style="color: red"> Unknown number of players</p>
            </#if>
          </#if>
          <#--End player display-->

        </div>
      </form>
    </div>
  </div>

  <script data-main="/js/game/index" src="/js/home.js"></script>

</body>
</html>
