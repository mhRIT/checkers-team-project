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
    <a href="/">Home</a>
  </div>

  <div class="body">


    <form action="./signin" method="POST">
    <input name="username" required>
    <button type="submit">Enter</button>
      </form>

    <#if message??>
        <div class="message ${messageType}">${message}</div>
    </#if>

  </div>

</div>
</body>
</html>