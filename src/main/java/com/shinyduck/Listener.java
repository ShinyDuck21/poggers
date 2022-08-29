package com.shinyduck;

import com.github.blad3mak3r.memes4j.Meme;
import com.github.blad3mak3r.memes4j.Memes4J;
import com.github.blad3mak3r.memes4j.PendingRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

public class Listener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        if (event.getAuthor().isBot()) {
            return;
        }

        Random rand = new Random();
        int j = rand.nextInt(10);

        if (j == 9) {
            try {
                URL url = new URL("https://meme-api.herokuapp.com/gimme");
                HttpURLConnection http = null;
                http = (HttpURLConnection)url.openConnection();
                http.setRequestProperty("Accept", "application/json");

                if (http.getResponseCode() == 200) {
                    Scanner scanner = new Scanner(url.openStream());

                    String inline = "";
                    while (scanner.hasNext()) {
                        inline += scanner.nextLine();
                    }
                    scanner.close();

                    JsonParser parse = new JsonParser();
                    JsonObject data = (JsonObject) parse.parse(inline);

                    String tempn = String.valueOf(data.get("nsfw"));

/*
                    if (!Boolean.parseBoolean(tempn.replaceAll("^\"|\"$", ""))) {
                        return;
                    }
*/

                    String linkt = String.valueOf(data.get("postLink"));
                    String link = linkt.replaceAll("^\"|\"$", "");

                    String send = String.valueOf(data.get("url"));
                    String better = send.replaceAll("^\"|\"$", "");

                    String tempTitle = String.valueOf(data.get("title"));
                    String title = tempTitle.replaceAll("^\"|\"$", "");

                    String subt = String.valueOf(data.get("subreddit"));
                    String sub = subt.replaceAll("^\"|\"$", "");

                    String authort = String.valueOf(data.get("author"));
                    String author = authort.replaceAll("^\"|\"$", "");

                    EmbedBuilder b = new EmbedBuilder()
                            .setTitle(title, link)
                            .setImage(better)
                            .setDescription("This post is found on https://www.reddit.com/r/" + sub)
                            .setAuthor(author);

                    event.getChannel().sendMessageEmbeds(b.build()).queue();
                }

                http.disconnect();
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
