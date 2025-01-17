package bedtimeStory;

import java.util.Optional;

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
    public String convertToAudio(String text) {
        return "";
    }
}
