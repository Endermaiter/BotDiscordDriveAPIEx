package main;

// IMPORTACIONES DE DISCORD

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.rest.util.Color;

import java.io.File;
import java.time.Instant;

//IMPORTACIONES DE GOOGLE

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

//CODE

public class BotMain {

    //GOOGLE CREDENTIALS

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "resources";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     * <p>
     * En el ejemplo original esta readonly metadatos, por lo tanto si lo dejamos asi
     * no podremos descargar ficheros, solo listarlos
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {

        // Load client secrets.

        InputStream in = BotMain.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("736476175694-qnbtukpdshk30equ1rl43fb20g8fabsn.apps.googleusercontent.com");

        //returns an authorized Credential object.

        return credential;
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {

        //***DISCORD CODE***

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

        String directorio = "/home/dam1/Documentos/ENDERMAITER/COD/ImagenesBot";
        File carpeta = new File(directorio);
        String[] listado = carpeta.list();
        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("/list".equals(message.getContent())) {
                for (int i = 0; i < listado.length; i++) {
                    final MessageChannel channel = message.getChannel().block();
                    channel.createMessage(listado[i]).block();
                }
            }
        });

        //OBTENER IMAGEN(BETA)

        //embeds

        EmbedCreateSpec embedImagen1 = EmbedCreateSpec.builder()
                .color(Color.GREEN)
                .title("shreck.jpeg")
                .image("attachment://home/dam1/Documentos/ENDERMAITER/COD/ImagenesBot/shreck.jpeg")
                .build();

        //comandos /get

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("/get shreck.jpeg".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();

                InputStream fileAsInputStream = null;
                try {
                    fileAsInputStream = new FileInputStream("/home/dam1/Documentos/ENDERMAITER/COD/ImagenesBot/shreck.jpeg");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ;

                channel.createMessage(MessageCreateSpec.builder()
                        .addFile("shreck.jpeg", fileAsInputStream)
                        .addEmbed(embedImagen1)
                        .build()).subscribe();
            }
        });

        //COMANDO HELP(Funcional)

        EmbedCreateSpec embedHelp = EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title("AYUDA")
                .addField("Comandos", "/ping\n/embed\n/gif\n/list\n/get [imagen]\n/listado (Lista los archivos de una carpeta de tu drive)", true)
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

        //GOOGLE CODE

        // Build a new authorized API client service.

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Filtra para encontrar la carpeta que se llama imagenesBot

        FileList result = service.files().list()
                .setQ("name contains 'imagenesBot' and mimeType = 'application/vnd.google-apps.folder'")
                .setPageSize(100)
                .setSpaces("drive")
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<com.google.api.services.drive.model.File> files = result.getFiles();

        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            String dirImagenes = null;
            System.out.println("Files:");
            for (com.google.api.services.drive.model.File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
                dirImagenes = file.getId();
            }

            // busco la imagen en el directorio

            FileList resultImagenes = service.files().list()
                    .setQ("name contains 'shreck' and parents in '" + dirImagenes + "'")
                    .setSpaces("drive")
                    .setFields("nextPageToken, files(id, name)")
                    .execute();
            List<com.google.api.services.drive.model.File> filesImagenes = resultImagenes.getFiles();

            //GOOGLE + DISCORD(Funcional)

            for (com.google.api.services.drive.model.File file : filesImagenes) {

                // guardamos el 'stream' en el fichero shreck.jpeg que tiene que existir

                OutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream("/home/dam1/shreck.jpeg");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    service.files().get(file.getId())
                            .executeMediaAndDownloadTo(outputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            InputStream fileAsInputStream = null;
            try {
                fileAsInputStream = new FileInputStream("/home/dam1/shreck.jpeg");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            EmbedCreateSpec embedDrive = EmbedCreateSpec.builder()
                    .color(Color.BLUE)
                    .title("Imagenes Drive")
                    .image("attachment://home/dam1/shreck.jpeg")
                    .timestamp(Instant.now())
                    .build();

            InputStream finalFileAsInputStream = fileAsInputStream;
            gateway.on(MessageCreateEvent.class).subscribe(event -> {
                final Message message = event.getMessage();
                if ("/drive".equals(message.getContent())) {
                    final MessageChannel channel = message.getChannel().block();
                    channel.createMessage(MessageCreateSpec.builder()
                            .addFile("shreck.jpeg", finalFileAsInputStream)
                            .addEmbed(embedDrive)
                            .build()).subscribe();
                }
            });
        }
        gateway.onDisconnect().block();
    }
}


