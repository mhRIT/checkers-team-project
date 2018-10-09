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
      <a href="/">Home</a>
      <a href="/signin">Sign-In</a>
    </div>
    
    <div class="body">
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



    </div>
    
  </div>
</body>
</html>
