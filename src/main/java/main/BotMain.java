package main;


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

        //ping pong(funcional)

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("/ping".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                channel.createMessage("Pong").block();
            }
        });

        //EMBED(Funcional)

        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title("Title")
                .url("https://es.wikipedia.org/wiki/Lionel_Messi")
                .author("EL MARCOS TODO UN CAPO", "https://es.wikipedia.org/wiki/Lionel_Messi", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDFFi8xZQpVW3GJRv2geK9u54vbbGlXYn4IA&usqp=CAU")
                .description("FORTINAITI LABABAYÈ")
                .thumbnail("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDFFi8xZQpVW3GJRv2geK9u54vbbGlXYn4IA&usqp=CAU")
                .addField("FUCHEBOL", "fuchebol", true)
                .addField("\u200B", "\u200B", true)
                .addField("MESSI", "messi", true)
                .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDFFi8xZQpVW3GJRv2geK9u54vbbGlXYn4IA&usqp=CAU")
                .timestamp(Instant.now())
                .footer("Fuchebol", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDFFi8xZQpVW3GJRv2geK9u54vbbGlXYn4IA&usqp=CAU")
                .build();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("/embed".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                channel.createMessage(embed).block();
            }
        });

        //GIFS(Funcional)

        EmbedCreateSpec embedGIF = EmbedCreateSpec.builder()
                .image("https://c.tenor.com/ZU3owNOylsEAAAAC/el-baile-de-el-troleo.gif")
                .build();
        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("/gif".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                channel.createMessage(embedGIF).block();
            }
        });

        //LISTAR FICHEROS(Funcional)

        String directorio = "/home/dam1/Documentos/ENDERMAITER/COD/BotDiscord/src/main/java/images";
        File carpeta = new File(directorio);
        String[] listado = carpeta.list();
        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("/list".equals(message.getContent())) {
                for(int i=0;i< listado.length;i++){
                    final MessageChannel channel = message.getChannel().block();
                    channel.createMessage(listado[i]).block();
                    }
                }
            });

        //OBTENER IMAGEN(BETA)

        EmbedCreateSpec embedImagen = EmbedCreateSpec.builder()
                .color(Color.GREEN)
                .title("Yoda")
                .image("attachment:///home/dam1/Documentos/ENDERMAITER/COD/ImagenesBot/shreck2.jpeg")
                .build();


        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("/get shreck2.jpeg".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                channel.createMessage(embedImagen).block();
            }
        });

        //COMANDO HELP(Funcional)

        EmbedCreateSpec embedHelp = EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title("AYUDA")
                .addField("Comandos", "/ping\n/embed\n/gif\n/list\n/get [imagen]", true)
                .timestamp(Instant.now())
                .footer("COD", "")
                .build();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("/help".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                channel.createMessage(embedHelp).block();
            }
        });

        gateway.onDisconnect().block();
    }

}


