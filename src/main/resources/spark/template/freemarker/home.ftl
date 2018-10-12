<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
    <h1>Web Checkers</h1>
    
    <div class="navigation">
      <a href="/">Home</a> |
      <#if currentPlayer??>
        <a href="/signout">sign out [${currentPlayer.name}]</a>
      <#else>
        <a href="/signin">sign in</a>
      </#if>
    </div>

    <#if message??>
      <p style="color: red">${message}</p>
    </#if>

    <div class="body">
      <#if players??>
        <#if players?size gt 1>
        <p>Other players currently signed in:</p>
        <br>
        <form action="/selectOpponent" method="POST">
          <#list players as eachPlayerName>
            <#if currentPlayer.name != eachPlayerName>
            <button type="submit" name="opponent" style="min-width: 20%" value="${eachPlayerName}">${eachPlayerName}</button>
            <br><br>
            </#if>
          <#else>
            <p style="color: red">No other players are currently signed in</p>
          </#list>
        </form>
        <#else>
          <p style="color: red">No other players are currently signed in</p>
        </#if>
      <#else>
        <#if numPlayers??>
          <p>Number of players signed in: ${numPlayers}</p>
        <#else>
          <p style="color: red">Unknown number of players</p>
        </#if>
      </#if>
    </div>
    
  </div>
</body>
</html>
