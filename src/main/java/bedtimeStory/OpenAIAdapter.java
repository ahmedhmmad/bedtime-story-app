package bedtimeStory;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class OpenAIAdapter implements StoryService{
    private OpenAIStoryGenerator storyGenerator;

    public OpenAIAdapter(OpenAIStoryGenerator storyGenerator) {
        this.storyGenerator = storyGenerator;
    }

    @Override
    public Optional<String> generateStory(String prompt) {
        return storyGenerator.generateStory(prompt);
    }

    @Override
    public String generateStoryGemini(String text) throws ExecutionException, InterruptedException, TimeoutException {
       return storyGenerator.generateStoryGemini(text);
    }
}
