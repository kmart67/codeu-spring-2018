package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminServlet extends HttpServlet {

  private UserStore userStore;

  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
  }

  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

        String username = (String) request.getSession().getAttribute("user");
        if (username == null ||
            (username != "chloe" &&
            username != "ileana" &&
            username != "ean" &&
            username != "karina")) {
          // user is not logged in, redirects to the login page
          response.sendRedirect("/login");
          return;
        }

        User user = userStore.getUser(username);
        if (user == null ||
            (user != "chloe" &&
            user != "ileana" &&
            user != "ean" &&
            user != "karina")) {
          // user was not found, redirects to the admin page
          System.out.println("Access Denied: " + username);
          response.sendRedirect("/login");
          return;
        }
    request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);

  }


  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {


  }


}
