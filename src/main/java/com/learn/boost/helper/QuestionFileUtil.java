package com.learn.boost.helper;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class QuestionFileUtil {

    @Value("${question.json.path}")
     static String BASE_DIR;

    public static Path saveAsText(
            String userId,
            String noteId,
            String content
    ) throws IOException {

        Path userFolder=Paths.get(BASE_DIR+userId);
        Files.createDirectories(userFolder);
        String fileName="Question_"+noteId+".json";

        Path filePath=userFolder.resolve(fileName);
        Files.write(filePath,content.getBytes());
        return filePath;

    }
}

