<!DOCTYPE html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <#--<meta http-equiv="refresh" content="10">-->
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

      <#if aiPlayers??>
<#--Accordion button list-------------------------------------------------------------------------->
        <button class="accordion" name="normal" id="accNormal">Normal start</button>
        <#if configType = "normal">
          <div class="panel" style="display:block" id="normalPanel">
            <p>Start a normal game</p>
          </div>
        <#else>
          <div class="panel" style="display:none" id="normalPanel">
            <p>Start a normal game</p>
          </div>
        </#if>

        <button class="accordion" name="random" id="accSlider">Random placement</button>
        <#if configType="random">
          <div class="panel" style="display:block" id="sliderPanel">
            <p>Set the number of starting pieces for each player:</p>
            <div class="slidecontainer">
              <input type="range" min="1" max="12" value="${configNumRed}" class="slider" id="redPieceSlider">
              <p>Number of red pieces: </p>
              <output id="redPieceOutput">${configNumRed}</output>
              <br>
              <br>
              <input type="range" min="1" max="12" value="${configNumWhite}" class="slider" id="whitePieceSlider">
              <p>Number of white pieces: </p>
              <output id="whitePieceOutput">${configNumWhite}</output>
            </div>
          </div>
        <#else>
          <div class="panel" style="display:none" id="sliderPanel">
            <p>Set the number of starting pieces for each player:</p>
            <div class="slidecontainer">
              <input type="range" min="1" max="12" value="6" class="slider" id="redPieceSlider">
              <p>Number of red pieces: </p>
              <output id="redPieceOutput"></output>
              <br>
              <br>
              <input type="range" min="1" max="12" value="6" class="slider" id="whitePieceSlider">
              <p>Number of white pieces: </p>
              <output id="whitePieceOutput"></output>
            </div>
          </div>
        </#if>

        <button class="accordion" name="preset" id="accPreset">Preset boards</button>
        <#if configType="preset">
          <div class="panel" style="display:block" id="presetPanel">
            <label for="presetStartCheckbox">Start</label>
            <input type="checkbox" id="presetStartCheckbox" <#if configPreset?? && configPreset="start">checked</#if>/>

            <label for="presetMidCheckbox">Mid</label>
            <input type="checkbox" id="presetMidCheckbox" <#if configPreset?? && configPreset="middle">checked</#if>/>

            <label for="presetEndCheckbox">End</label>
            <input type="checkbox" id="presetEndCheckbox" <#if configPreset?? && configPreset="end">checked</#if>/>
          </div>
        <#else>
          <div class="panel" style="display:none" id="presetPanel">
            <label for="presetStartCheckbox">Start</label>
            <input type="checkbox" id="presetStartCheckbox"/>

            <label for="presetMidCheckbox">Mid</label>
            <input type="checkbox" id="presetMidCheckbox"/>

            <label for="presetEndCheckbox">End</label>
            <input type="checkbox" id="presetEndCheckbox"/>
          </div>
        </#if>

        <button class="accordion" name="custom" id="accCustom">Custom board</button>
        <div class="panel" style="display:none" id="customPanel">
<#--Custom board table----------------------------------------------------------------------------->
          <table id="game-board">
            <tbody>
              <#--<#list board.iterator() as row>-->
              <#--<tr data-row="${row.index}">-->
              <#--<#list row.iterator() as space>-->
                <#--<td data-cell="${space.cellIdx}"-->
                    <#--<#if space.isValid() >-->
                    <#--class="Space"-->
                    <#--</#if>-->
                <#-->-->
                <#--<#if space.piece??>-->
                  <#--<div class="Piece"-->
                       <#--id="piece-${row.index}-${space.cellIdx}"-->
                       <#--data-type="${space.piece.type}"-->
                       <#--data-color="${space.piece.color}">-->
                  <#--</div>-->
                <#--</#if>-->
                <#--</td>-->
              <#--</#list>-->
              <#--</tr>-->
              <#--</#list>-->
            </tbody>
          </table>
<#--Custom board table----------------------------------------------------------------------------->
        </div>
      </#if>
<#--Accordion button list-------------------------------------------------------------------------->

      <form action="/selectOpponent" method="POST">
        <div class="vertical-menu">

<#--Start AI display------------------------------------------------------------------------------->
          <#--If this list exists, then the player is signed in-->
          <#if aiPlayers??>
            <p> AI opponents:</p>
            <br>
            <#list aiPlayers as eachPlayerName>
              <button class="active" type="submit"
                      name="opponent" value="${eachPlayerName}">
                ${eachPlayerName}
              </button>
            </#list>
            <br>
            <br>
          </#if>
<#--End AI display--------------------------------------------------------------------------------->

<#--Start player display--------------------------------------------------------------------------->
          <#if allPlayers??>
            <#if allPlayers?size gt 0>
              <p> Other players currently signed in:</p>
              <br>
              <#list allPlayers as eachPlayerName>
                <button class="active" type="submit"
                        name="opponent" value="${eachPlayerName}">
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
<#--End player display----------------------------------------------------------------------------->

        </div>
      </form>
    </div>
  </div>

  <script data-main="/js/game/index" src="/js/home.js"></script>

</body>
</html>
