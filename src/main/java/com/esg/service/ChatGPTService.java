package com.esg.service;

import com.esg.component.KeyComponent;
import com.esg.controller.ChatGptController;
import com.esg.util.PdfUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.esg.util.PdfUtil.extractTextFromPdf;

@Service
public class ChatGPTService {

    private final static Logger log = LoggerFactory.getLogger(ChatGPTService.class);

    public String chatGPT(MultipartFile file,String prompt) throws IOException {
        String extractedText = "";
        if(!file.isEmpty()) {
             extractedText = PdfUtil.extractTextFromPdf(file);
             log.info("PDF檔提取的文字是:{}",extractedText);
        }
        String url = "https://api.openai.com/v1/chat/completions";
        String model = "gpt-3.5-turbo";
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            String apiKey = System.getenv("GPT_API_KEY");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            JSONObject messageObj = new JSONObject()
                    .put("role", "user")
                    .put("content", prompt + (extractedText == null? "":extractedText));
            JSONObject body = new JSONObject()
                    .put("model", model)
                    .put("messages", new JSONArray().put(messageObj))
                    .put("max_tokens", 500);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = body.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            // 讀 response
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            // 從 response 抽出 content
            JSONObject jsonResponse = new JSONObject(response.toString());
            String content = jsonResponse
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String handleUpload(MultipartFile file, String prompt) throws IOException {
        String extractedText = PdfUtil.extractTextFromPdf(file);
        log.info("PDF檔提取的文字是:{}",extractedText);
        return extractedText;
//        return chatGPT(file, prompt);
    }
}
