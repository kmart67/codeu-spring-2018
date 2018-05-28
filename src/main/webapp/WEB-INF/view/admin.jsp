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
    <h1>Administration</h1>

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>
    <hr/>
    <h2>Site Statistics</h2>
    <ul id="adminList">
      <li><b>Users:</b> ${numUsers}</li>
      <li><b>Conversations:</b> ${numCons}</li>
      <li><b>Messages:</b> ${numMess}</li>
      <li><b>Most active user:</b> ${mostActive}</li>
      <li><b>Newest user:</b> ${newestUser}</li>
      <li><b>Wordiest user:</b> ${mostWords}</li>
    </ul>
    <hr/>
    <h2>Import Data</h2>
    From source:
    <br>
    <button type="submit">Submit</button>


  </div>
</body>
</html>
