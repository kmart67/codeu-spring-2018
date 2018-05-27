package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.persistence.PersistentDataStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import codeu.model.store.persistence.PersistentDataStoreException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminServlet extends HttpServlet {

  private UserStore userStore;
  private PersistentDataStore dataStore;

  private int numUsers;
  private int numCons;
  private int numMess;
  private String mostActive;
  private String newestUser;
  private String mostWords;

  public static final ArrayList<String> ADMINS = new ArrayList<String>(Arrays.asList("chloe", "ileana", "ean", "karina"));


  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
  }

  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  void setNumUsers(PersistentDataStore dataStore) throws PersistentDataStoreException {
    this.numUsers = dataStore.loadUsers().size();
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

      // try {
      //   setNumUsers(dataStore);
      // } catch (Exception e) {
      //   throw new PersistentDataStoreException(e);
      // }

      request.setAttribute("numUsers", numUsers);
      request.setAttribute("numCons", numCons);
      request.setAttribute("numMess", numMess);
      request.setAttribute("mostActive", mostActive);
      request.setAttribute("newestUser", newestUser);
      request.setAttribute("mostWords", mostWords);
      
      request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);

  }


  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
      request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
  }


}
