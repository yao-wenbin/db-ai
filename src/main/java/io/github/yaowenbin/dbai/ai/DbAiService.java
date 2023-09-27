package io.github.yaowenbin.dbai.ai;

import io.github.yaowenbin.commons.string.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

@Service
@Slf4j
public class DbAiService extends BaseAiService {

    private final String prompt;

    public DbAiService() {
        final String promptSource = "classpath:prompt.txt";
        File promptTxt;
        try {
            promptTxt = ResourceUtils.getFile(promptSource);
             prompt = String.join("\n", Files.readAllLines(promptTxt.toPath()));

             log.info("db ai service 's prompt from {} has been loaded: {}", promptSource, promptTxt);
        } catch (IOException e) {
            throw new RuntimeException(Strings.format("prompt.txt cannot be read, please check exists or privileges of file.", promptSource));
        }
    }

    @Override
    public String generate(String keyword) {
        return super.generate(Strings.format(prompt, keyword));
    }

    /**
     * using keyword to generate DDL SQL file and store into file system.
     * @param keyword in prompt.
     * @return path of sql file stored.
     */
    public File generateSQLFile(String keyword) {
        var sql = generate(keyword);
        return storeToFile(sql);
    }

    public File storeToFile(String sql) {
        File sqlFile = new File("./schema.sql");
        if (!sqlFile.exists()) {
            try {
                sqlFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sqlFile))) {
            writer.write(sql);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sqlFile;
    }
}
