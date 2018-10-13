<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">

    <style>
      .vertical-menu{
        width: 200px;
        height: 200px;
        overflow-y: auto;
      }

      .vertical-menu a {
        background-color: #F0FAFF;
        color:  black;
        display:  flex;
        padding: 1px;
        align-items: center;
        justify-content: center;
        text-decoration: none;
      }

      .vertical-menu a:hover {
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

    <div class="vertical-menu">
      <#if allPlayers??>
        <#if allPlayers?size gt 0>
          <p>Other players currently signed in:</p>
          <br>
          <form action="/selectOpponent" method="POST">
            <#list allPlayers as eachPlayerName>
                <button class="active" type="submit" name="opponent" style="min-width: 20%" value="${eachPlayerName}">${eachPlayerName}</button>
                <br><br>
            </#list>
          </form>
        <#else>
          <p style="color: red">No other players are currently signed in</p>
        </#if>
      <#else>
        <#if numPlayers??>
          <p>Number of players: ${numPlayers}</p>
        <#else>
          <p style="color: red">Unknown number of players</p>
        </#if>
      </#if>
    </div>
    
  </div>
</body>
</html>
