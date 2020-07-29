package George;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Login implements Listener {

    private static Subscriber plugin;

    public Login(Subscriber plugin) {
        Login.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent playerLoginEvent) throws IOException, ParseException {
        Player player = playerLoginEvent.getPlayer();

        String url_str = "http://mcadventure.net:20001/subscriber?player=" +
                player.getName();
        URL url = new URL(url_str);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(content.toString());

        if ((boolean) jsonObject.get("flag")) {
            playerLoginEvent.allow();
        } else {
            String msg_list = Subscriber.color(plugin.getConfig().getString("message"));
            playerLoginEvent.disallow(PlayerLoginEvent.Result.KICK_OTHER, msg_list);
        }
    }
}