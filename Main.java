import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

/**
 * Created by Nate Watson on 4/29/2017.
 */
public class Main {

    public static JDA jda;

    public static String BOT_TOKEN = "MzA3OTgyMzg0NjcyOTMxODQx.C-aOeA.TLJVeGxkqU-xO1_DYKgZdWVsT2E";

    public static void main(String[] args) {
        try {
        jda = new JDABuilder(AccountType.BOT).addEventListener(new BotListener()).setToken(BOT_TOKEN).buildBlocking(); }
        catch (LoginException | IllegalArgumentException | InterruptedException | RateLimitedException e) {
            e.printStackTrace();
        }

        
    }
}
