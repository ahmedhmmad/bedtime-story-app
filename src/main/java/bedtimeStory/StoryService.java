package bedtimeStory;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface StoryService {
    public Optional<String> generateStory(String prompt);
    public String generateStoryGemini(String text) throws ExecutionException, InterruptedException, TimeoutException;
}
