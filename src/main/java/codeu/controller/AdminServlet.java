package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminServlet extends HttpServlet {

  private UserStore userStore;
  public static final ArrayList<String> ADMINS = new ArrayList<String>(Arrays.asList("chloe", "ileana", "ean", "karina"));


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
      if (username == null || !ADMINS.contains(username)) {
        // user is not logged in or one of the approved users, redirects to the login page
        response.sendRedirect("/login");
        return;
      }
      request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);

  }


  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
      request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
  }


}
