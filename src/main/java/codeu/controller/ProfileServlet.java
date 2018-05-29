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

public class ProfileServlet extends HttpServlet {

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

      String requestUrl = request.getRequestURI();
      String profileName = requestUrl.substring("/users/".length());
      User profile = userStore.getUser(profileName);
      if (profile == null) {
        // user was not found
        System.out.println("User was not found: " + profileName);
        response.sendRedirect("/conversations");
        return;
      }
      String title;
      String user = (String) request.getSession().getAttribute("user");
      if (user != null && user.equals(profileName)) {
        title = "This is Your Profile";
      } else {
        title = profileName + "'s Profile";
      }
      request.setAttribute("profile", profileName);
      request.setAttribute("title", title);
      request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
  }
}
