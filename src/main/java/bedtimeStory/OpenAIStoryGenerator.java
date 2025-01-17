package bedtimeStory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatCompletion;
import com.openai.models.ChatCompletionCreateParams;
import com.openai.models.ChatCompletionUserMessageParam;
import com.openai.models.ChatModel;



public class OpenAIStoryGenerator {
    private String apiKey;
    private OpenAIClient openAiClient;

    public OpenAIStoryGenerator() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("src/main/java/bedtimeStory/config.properties"));
            this.apiKey = properties.getProperty("api.key");
            if (this.apiKey == null || this.apiKey.isEmpty()) {
                throw new RuntimeException("API key not found in config.properties");
            }

            this.openAiClient = OpenAIOkHttpClient.builder()
                    .apiKey(apiKey)
                    .build();
        } catch (IOException e) {
            System.err.println("Error loading config.properties: " + e.getMessage());
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


}
