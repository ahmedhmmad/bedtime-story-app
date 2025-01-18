import bedtimeStory.OpenAIAdapter;
import bedtimeStory.OpenAIStoryGenerator;
import bedtimeStory.StoryService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        StoryService openAiAdapter=new OpenAIAdapter(new OpenAIStoryGenerator());
        String prompt = "Tell me a story about a brave knight who saves a village from a dragon.";
        String story = String.valueOf(openAiAdapter.generateStory(prompt));
        String gemniStory=String.valueOf(openAiAdapter.generateStoryGemini(prompt));
        System.out.println(story);
        System.out.println("A new Script");
        System.out.println(gemniStory);

    }
}