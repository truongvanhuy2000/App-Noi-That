package com.huy.appnoithat.Handler;

import com.huy.appnoithat.Configuration.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class HandleOldBackupFile {
    final static Logger LOGGER = LogManager.getLogger(HandleOldBackupFile.class);
    private static final Duration maximumBackupTime = Duration.ofDays(10);

    public static void start() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try (Stream<Path> stream = Files.list(Paths.get(Config.FILE_EXPORT.TEMP_NT_FILE_DIRECTORY))) {
                stream.filter(file -> !Files.isDirectory(file)).forEach(HandleOldBackupFile::markOldFileToDelete);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
    }

    private static void markOldFileToDelete(Path file) {
        try {
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
            long timeDifference = System.currentTimeMillis() - attr.lastModifiedTime().toMillis();
            if (timeDifference > maximumBackupTime.toMillis()) {
                file.toFile().deleteOnExit();
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
