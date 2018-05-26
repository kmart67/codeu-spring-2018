<!DOCTYPE html>
<html>
<head>
  <title>Admin</title>
  <link rel="stylesheet" href="/css/main.css">
  <script src="main/java/codeu/controller/AdminServlet.java"></script>
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null) { %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else { %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <a href="/admin">Admin</a>
  </nav>

  <div id="container">
    <h1>Adminisration</h1>

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>
    <hr/>
    <h2>Site Statistics</h2>
    <b><ul id="adminList">
      <li>Users: ${numUsers}</li>
      <li>Conversations:</li>
      <li>Messages:</li>
      <li>Most active user:</li>
      <li>Newest user:</li>
      <li>Wordiest user:</li>
    </ul></b>
    <hr/>


  </div>
</body>
</html>
