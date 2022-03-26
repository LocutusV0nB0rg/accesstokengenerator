package borg.locutus.accesstokengenerator.authentication;

import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.auth.service.AuthenticationService;
import com.github.steveice10.mc.auth.service.MsaAuthenticationService;
import com.github.steveice10.mc.auth.service.SessionService;
import lombok.Getter;

import java.io.IOException;
import java.net.Proxy;
import java.util.UUID;

import borg.locutus.accesstokengenerator.Launcher;

public class Authentication {
  private static final Proxy PROXY = Proxy.NO_PROXY;

  @Getter
  private static String accessToken = "";

  @Getter
  private static String username = "";

  @Getter
  private static UUID uuid = UUID.randomUUID();

  public static void authenticate(String email, String password) {
    SessionService service = new SessionService();
    service.setProxy(PROXY);

    String clientToken = UUID.randomUUID().toString();
    AuthenticationService auth = login(clientToken, email, password);

    uuid = auth.getSelectedProfile().getId();
    accessToken = auth.getAccessToken();
    username = auth.getSelectedProfile().getName();
    System.out.printf("Completed authentication for account %s%n", uuid);
    Launcher.application.statusLabel.setText("Success");
  }

  private static synchronized AuthenticationService login(String clientToken, String email, String password) {
    AuthenticationService auth = null;
    try {
      auth = new MsaAuthenticationService(clientToken);
    } catch (IOException exception) {
      System.out.println("Authentication with upload account failed.");
      Launcher.application.statusLabel.setText("Authentication with upload account failed.");
      exception.printStackTrace();
    }
    assert auth != null;
    auth.setProxy(PROXY);
    auth.setUsername(email);
    auth.setPassword(password);

    try {
      auth.login();
    } catch(RequestException e) {
      System.out.println("Failed to log in with password!");
      Launcher.application.statusLabel.setText("Failed to log in with password!");
      e.printStackTrace();
    }

    return auth;
  }
}