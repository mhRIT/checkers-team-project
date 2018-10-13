<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">

    <style>
      .vertical-menu{
        width: 250px;
        height: 200px;

        overflow-y: auto;
      }

      .vertical-menu button{
        width: 240px;
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

      <div class="body">
      <#if allPlayers??>
        <#if allPlayers?size gt 0>
          <p> Other players currently signed in:</p>
          <br>
        <div class="vertical-menu">
          <form action="/selectOpponent" method="POST">
            <#list allPlayers as eachPlayerName>
                <button class="active" type="submit" name="opponent" value="${eachPlayerName}">${eachPlayerName}</button>
            </#list>
          </form>
        </div>
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

      </div>
  </div>
</body>
</html>
