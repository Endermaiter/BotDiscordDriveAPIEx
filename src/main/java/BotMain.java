import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class BotMain {

    public static void main(String[] args) {
        final String token = "OTUzNjMwNzE1MTA5NjA1Mzk2.YjHX0A.6fzY6SZIsfqLBBJXGBjJ859CWfs";
        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("!ping".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                channel.createMessage("Pong!").block();
            }
        });

        gateway.onDisconnect().block();
    }
}


