<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/test.css">

    <style>
      .vertical-menu {
        width: 250px;
        /*height: 250px;*/
        overflow-y: auto;
      }

      .vertical-menu button{
        width: 250px;
        padding:  10px 0;
        border: 1px white;
        font-size: 16px;
        background-color: #F0FAFF;
        color:  black;
        display:  inline-block;
        text-align: center;
        text-decoration: none;
      }

      .vertical-menu button:hover {
        background-color: #888888;
        color: white;
      }
    </style>
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

<#------------------------------------------------------------------------------------------------->
    <button class="accordion" id="accSlider">Section 1</button>
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

    <button class="accordion" id="accCustom">Section 2</button>
    <div class="panel">
      <p>Lorem ipsum...</p>
    </div>

    <button class="accordion" id="accTable">Section 3</button>
    <div class="panel">
      <p>Lorem ipsum...</p>
    </div>
<#------------------------------------------------------------------------------------------------->

    <div class="body">
      <#if message??>
      <div>
        <p style="color: red">${message}</p>
      </div>
      </#if>

      <button id="sendButton" type="button" value="opponent" onclick="handleFormSubmit(this.value)">Click Me!</button>

      <form action="/selectOpponent" method="POST">
        <div class="vertical-menu">

          <#--Start AI display---->
          <#if aiPlayers??>
            <p> AI opponents:</p>
            <br>
            <#list aiPlayers as eachPlayerName>
                <button class="active" type="submit" name="opponent" value="${eachPlayerName}">${eachPlayerName}</button>
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
                <button class="active" type="submit" name="opponent" value="${eachPlayerName}">${eachPlayerName}</button>
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

  <script data-main="/js/game/index" src="/js/test.js"></script>

</body>
</html>
