<%
String title = (String) request.getAttribute("title");
String profileName = (String) request.getAttribute("profile");
String user = (String) request.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
  <title><%= profileName %></title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
      <% if (request.getSession().getAttribute("user") != null) { %>
    <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else { %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
  </nav>

  <div id="container">

    <h1><%= title %></h1>

    <hr/>

  </div>
</body>
</html>
