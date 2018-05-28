package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.data.Message;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.persistence.PersistentDataStore;
import java.io.IOException;
import java.io.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.*;
import java.util.ArrayList;
import java.lang.*;
import javax.servlet.ServletException;
import codeu.model.store.persistence.PersistentDataStoreException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminServlet extends HttpServlet {

  private UserStore userStore;
  private ConversationStore conStore;
  private MessageStore messStore;

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
    setConStore(ConversationStore.getInstance());
    setMessStore(MessageStore.getInstance());
  }

  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  void setConStore(ConversationStore conStore) {
    this.conStore = conStore;
  }

  void setMessStore(MessageStore messStore) {
    this.messStore = messStore;
  }

  //puts users into a map and counts how many messages they've sent
  Map<UUID, Integer> numMess(MessageStore messStore) {
    Map<UUID, Integer> messCount = new TreeMap<UUID, Integer>();
    List<Message> messages = messStore.getAllMessages();

    for(Message m : messages) {
      UUID id = m.getAuthorId();
      if(!messCount.containsKey(id)) {
        messCount.put(id, 0);
      }
      messCount.put(id, messCount.get(id) + 1);
    }
    return messCount;
  }

  //takes in the message count map and returns the user that has
  //the highest message count
  String getMostActive() {
    Map<UUID, Integer> messCount = numMess(messStore);
    int most = 0;
    UUID id = null;
    for(UUID user : messCount.keySet()) {
      int val = messCount.get(user);
      if(val > most) {
        most = val;
        id = user;
      }
    }
    User u = userStore.getUser(id);
    return u.getName();
  }

  //returns the name of the newest user
  String getNewestUser(UserStore userStore) {
    List<User> users = userStore.getAllUsers();
    //returns the name of the user if there's only one
    if(users.size() < 2) {
      return users.get(0).getName();
    } else { //compares creation times to find the newest user
      User u = users.get(0);
      UUID id = null;
      Instant creationTime = u.getCreationTime();
      for(User user : users) {
        Instant uTime = user.getCreationTime();
        if(uTime.compareTo(creationTime) > 0) {
          creationTime = uTime;
          id = user.getId();
        }
      }
      User us = userStore.getUser(id);
      return us.getName();
    }
  }

  //puts users into a map and counts the words in their messages
  Map<UUID, Integer> numWords(MessageStore messStore) {
    Map<UUID, Integer> wordCount = new TreeMap<UUID, Integer>();
    List<Message> messages = messStore.getAllMessages();

    for(Message m : messages) {
      UUID id = m.getAuthorId();
      if(!wordCount.containsKey(id)) {
        wordCount.put(id, 0);
      }
      wordCount.put(id, wordCount.get(id) + m.getContent().trim().split("\\s+").length);
    }
    return wordCount;
  }

  String getMostWords() {
    Map<UUID, Integer> wordCount = numWords(messStore);
    int most = 0;
    UUID id = null;
    for(UUID user : wordCount.keySet()) {
      int val = wordCount.get(user);
      if(val > most) {
        most = val;
        id = user;
      }
    }
    User u = userStore.getUser(id);
    return u.getName();
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

      numUsers = userStore.getAllUsers().size();
      numCons = conStore.getAllConversations().size();
      numMess = messStore.getAllMessages().size();

      mostActive = getMostActive();
      newestUser = getNewestUser(userStore);
      mostWords = getMostWords();

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
