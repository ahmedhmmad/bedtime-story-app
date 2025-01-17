import bedtimeStory.OpenAIAdapter;
import bedtimeStory.OpenAIStoryGenerator;
import bedtimeStory.StoryService;


public class Main {
    public static void main(String[] args) {
        StoryService openAiAdapter=new OpenAIAdapter(new OpenAIStoryGenerator());
        String prompt = "Tell me a story about a brave knight.";
        String story = String.valueOf(openAiAdapter.generateStory(prompt));
        System.out.println(story);

    }
}