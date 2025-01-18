package bedtimeStory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.*;
import swiss.ameri.gemini.api.Content;
import swiss.ameri.gemini.api.GenAi;

import swiss.ameri.gemini.api.GenerativeModel;
import swiss.ameri.gemini.api.ModelVariant;
import swiss.ameri.gemini.gson.GsonJsonParser;
import swiss.ameri.gemini.spi.JsonParser;


public class OpenAIStoryGenerator {
    private String apiKey;
    private String geminiApiKey;
    private OpenAIClient openAiClient;
    private GenAi genAi;
    JsonParser parser = new GsonJsonParser();

    public OpenAIStoryGenerator() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("src/main/java/bedtimeStory/config.properties"));
            this.apiKey = properties.getProperty("api.key");
            this.geminiApiKey = properties.getProperty("gemini.api.key");
            if (this.apiKey == null || this.apiKey.isEmpty()) {
                throw new RuntimeException("API key not found in config.properties");
            }
            if (this.geminiApiKey == null || this.geminiApiKey.isEmpty()) {
                throw new RuntimeException("Gemini API key not found in config.properties");
            }

            this.openAiClient = OpenAIOkHttpClient.builder()
                    .apiKey(apiKey)
                    .build();

            this.genAi = new GenAi(
                    geminiApiKey,
                    parser
            );


        } catch (IOException e) {
            System.err.println("Error loading config.properties: " + e.getMessage());
            throw new RuntimeException("Failed to initialize OpenAIStoryGenerator", e);
        }

    }

    public Optional<String> generateStory(String prompt) {
        try {

            ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                    .model(ChatModel.GPT_3_5_TURBO)
                    .maxTokens(1024)
                    .addMessage(ChatCompletionUserMessageParam.builder()
                            .role(ChatCompletionUserMessageParam.Role.USER)
                            .content(prompt)
                            .build())
                    .build();
            ChatCompletion chatCompletion = openAiClient.chat().completions().create(params);
            if (chatCompletion != null && chatCompletion.choices() != null && !chatCompletion.choices().isEmpty()) {
                return chatCompletion.choices().get(0).message().content();
            } else {
                throw new RuntimeException("No response from the API");
            }

        }
        catch(Exception e){
            System.err.println("Error generating story: " + e.getMessage());
            throw new RuntimeException("Failed to generate story", e);
        }
    }

    public String generateStoryGemini(String prompt) throws ExecutionException, InterruptedException, TimeoutException {
        var model = GenerativeModel.builder()
                .modelName(ModelVariant.GEMINI_1_0_PRO)
                .addContent(new Content.TextContent(
                        Content.Role.USER.roleName(),
                        prompt
                ))
                .build();

        // Execute the prompt and wait for the full response
        String fullResponse = genAi.generateContent(model)
                .thenApply(response -> {

                    return response.toString();
                })
                .get(20, TimeUnit.SECONDS); // Block here until the response arrives

        return fullResponse;
    }




}
