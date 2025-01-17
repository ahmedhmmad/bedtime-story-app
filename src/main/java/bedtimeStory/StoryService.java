package bedtimeStory;

import java.util.Optional;

public interface StoryService {
    public Optional<String> generateStory(String prompt);
    public String convertToAudio(String text);
}
