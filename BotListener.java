import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageDeleteEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.event.ActionEvent;
import java.util.HashMap;

/**
 * Created by Nate Watson on 4/29/2017.
 */
public class BotListener extends ListenerAdapter {
    private HashMap<User, Integer> tracker;

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (tracker == null) {
            tracker = new HashMap<User, Integer>();
        }
        if (tracker.keySet().contains(e.getAuthor())) {
            int i = tracker.get(e.getAuthor());
            tracker.remove(e.getAuthor());
            tracker.put(e.getAuthor(), i+1);
        }
        if (!tracker.keySet().contains(e.getAuthor())) {
            tracker.put(e.getAuthor(), 1);
        }
        if (e.getMessage().getRawContent().equalsIgnoreCase("-introduceyourself")) {
            e.getChannel().sendMessage("Hi there, I'm wisebot! I'm a small project by Unwise, currently in development.").queue();
        }

        if (e.getMessage().getRawContent().equalsIgnoreCase("-help")) {
            e.getChannel().sendMessage("I'm currently programmed to respond to the following commands:\n\n-help: lists all currently usable commands" +
                    "\n-introduceyourself: states my name and current development status"
                    + "\n-serverinfo: retrieves basic information about the current server"
                    + "\n-analytics: retrieves message analytics info").queue();
        }

        if(e.getMessage().getRawContent().equalsIgnoreCase("-serverinfo")) {
            String text = "\n";
            for (TextChannel t : e.getGuild().getTextChannels()) {
                text+=t.getName();
                text+=", ";
            }
            int i = text.lastIndexOf(",");
            text = text.substring(0, i);
            e.getChannel().sendMessage("**Name: **" + e.getGuild().getName() + "\n\n**Owner: **" + e.getGuild().getOwner().getEffectiveName() +
                    "\n\n**Number of Members: **" + e.getGuild().getMembers().size() + "\n\n**Channels: **" + text).queue();
        }

        if (e.getMessage().getRawContent().equalsIgnoreCase("-analytics") && e.getAuthor().toString().equals("U:Unwise(166205900456067072)")) {
            String i = "";
            for (User user : tracker.keySet()) {
                i+=(user.getName() + " has sent " + tracker.get(user) + " messages recently.\n");
            }
            e.getChannel().sendMessage(i).queue();
        }

        if (e.getMessage().getRawContent().equalsIgnoreCase("-clearcache") && e.getAuthor().toString().equals("U:Unwise(166205900456067072)")) {
            tracker.clear();
        }

        /*if (e.getMessage().getRawContent().equalsIgnoreCase("!debug")) {
            e.getChannel().sendMessage(e.getAuthor().toString()).queue();
        }*/
        if(e.getMessage().getRawContent().contains("tick")) {
            try {
            e.getMessage().delete().queue();
            }
            catch (PermissionException p) {
                p.printStackTrace();
            }
        }
    }
}
