package main;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import java.io.File;

import java.time.Instant;

public class BotMain {

    public static void main(String[] args) {
        final String token = args[0];
        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        //ping pong

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("/ping".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                channel.createMessage("Pong").block();
            }
        });

        //EMBED

        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title("Title")
                .url("https://discord4j.com")
                .author("EL MARCOS TODO UN CAPO", "https://discord4j.com", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDFFi8xZQpVW3GJRv2geK9u54vbbGlXYn4IA&usqp=CAU")
                .description("FORTINAITI LABABAYÈ")
                .thumbnail("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDFFi8xZQpVW3GJRv2geK9u54vbbGlXYn4IA&usqp=CAU")
                .addField("FUCHEBOL", "fuchebol", false)
                .addField("\u200B", "\u200B", false)
                .addField("MESSI", "messi", true)
                .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDFFi8xZQpVW3GJRv2geK9u54vbbGlXYn4IA&usqp=CAU")
                .timestamp(Instant.now())
                .footer("footer", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDFFi8xZQpVW3GJRv2geK9u54vbbGlXYn4IA&usqp=CAU")
                .build();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("/embed".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                channel.createMessage(embed).block();
            }
        });

        //GIFS



        //LISTAR FICHEROS

        String directorio = System.getProperty("/home/dam1/Documentos/ENDERMAITER/COD/BotDiscord/src/main/java/images");
        File carpeta = new File(directorio);
        String[] listado = carpeta.list();
        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("/list".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                channel.createMessage().block();
                }
            });

        //OBTENER IMAGEN

        gateway.onDisconnect().block();
    }

}


