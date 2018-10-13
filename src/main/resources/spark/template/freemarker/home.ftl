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
<<<<<<< HEAD
      <a href="/">Home</a> |
      <#if currentPlayer??>
        <a href="/signout">sign out [${currentPlayer.name}]</a>
      <#else>
        <a href="/signin">sign in</a>
      </#if>
||||||| merged common ancestors
      <a href="/">my home</a>
=======
      <a href="/">Home</a>
    <#if allPlayers??>
      <a href="/signout">Sign-Out</a>
    <#else>
      <a href="/signin">Sign-In</a>
    </#if>
>>>>>>> PlayerSignInStory
    </div>

    <#if message??>
      <p style="color: red">${message}</p>
    </#if>

    <div class="body">
<<<<<<< HEAD
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
||||||| merged common ancestors
      <p>Welcome to the world of online Checkers.</p>
=======
      <p>Welcome to the world of online Checkers.</p>

      <#if allPlayers??>
      <div class="vertical-menu">
        <#list allPlayers as player>
        <a href={player} class = "active">${player}</>
          <p></p>
      </#list>

      </div>
      <#else>
        <p>Number of players: ${numPlayers}</p>
      </#if>



>>>>>>> PlayerSignInStory
    </div>
    
  </div>
</body>
</html>
