package io.github.yaowenbin.dbai.db;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class DbServiceTest {

    DbService dbService = new DbService(new DbInitializer());

    @Test
    void convertToResource() throws IOException {
        Resource resource = dbService.convertToResource("tmp");

        File tmpFile = ResourceUtils.getFile(resource.getURL());
        String res = Files.readString(tmpFile.toPath());
        assertThat(res).isEqualTo("tmp");
    }

}