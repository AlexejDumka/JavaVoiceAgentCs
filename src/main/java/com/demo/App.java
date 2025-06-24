package com.demo;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;
import org.json.JSONObject;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
public class App {
    public static void main(String[] args) throws IOException, LineUnavailableException, InterruptedException {
        LogLevel level = LogLevel.INFO;
        LibVosk.setLogLevel(level);
        Model model = new Model("models/vosk-model-small-cs-0.4-rhasspy");
        AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line not supported");
            System.exit(1);
        }
        TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);
        microphone.start();
        System.out.println("🎤 Mluvte česky (řekněte 'konec' pro ukončení)...");
        byte[] buffer = new byte[4096];
        String answer = "";
        Recognizer recognizer = new Recognizer(model, 16000.0f);
        HttpClient httpClient = HttpClient.newHttpClient();

        while (true) {
            int bytesRead = microphone.read(buffer, 0, buffer.length);
            if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                String resultJson = recognizer.getResult();
                String text = resultJson.replaceAll(".*\"text\":\"(.*?)\".*", "$1").trim();
                
                if (!text.isEmpty()) {
                    System.out.println("Rozpoznáno: " + text);

                    if (text.toLowerCase().contains("konec")) {
                        System.out.println( "Ukončuji...");
                        break;
                    }
                     String safePrompt = text
                            .replace("\\", "\\\\")   
                            .replace("\"", "\\\"")   
                            .replace("\n", "\\n");   
                    String ollamaPayload = """
                            {
                                "model": "llama3.2:1b",
                                "messages": [
                                    {"role": "user", "content": "%s"}
                                ]
                            }
                            """.formatted(safePrompt);

                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:11434/api/chat"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(ollamaPayload, StandardCharsets.UTF_8))
                            .build();

                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    JSONObject json = new JSONObject(response.body());
                    if (json.has("message")) {
                        answer = json.getJSONObject("message").getString("content");
                        System.out.println("Odpověď: " + answer);
                    } else if (json.has("error")) {
                        System.err.println("Chyba od Ollama: " + json.getString("error"));
                    } else {
                        System.err.println("Neočekávaný formát odpovědi: " + json.toString(2));
                    }
                    System.out.println("Odpověď: " + answer);
                    try {
                        String speakCommand = String.format("espeak -v cs \"%s\"", answer);
                        String command = "\"C:\\Program Files (x86)\\eSpeak\\command_line\\espeak.exe\" -v cs \"" + answer + "\"";
                        Runtime.getRuntime().exec(command);
                    } catch (IOException e) {
                        System.err.println("Chyba při syntéze řeči: " + e.getMessage());
                    }
                }
            }
        }

        recognizer.close();
        microphone.stop();
        microphone.close();
    }
}





