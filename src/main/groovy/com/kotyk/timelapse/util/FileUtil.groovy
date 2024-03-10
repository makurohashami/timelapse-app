package com.kotyk.timelapse.util

import groovy.util.logging.Slf4j
import groovyjarjarantlr4.v4.runtime.misc.NotNull

@Slf4j
class FileUtil {

    static void createFolders(String path) {
        File folder = new File(path)
        if (folder.exists()) {
            log.info("Folders with path '$path' already exists")
            return
        }
        log.info("Folders with path '$path' not exist. Creation folders...")
        if (folder.mkdirs()) {
            log.info("Created folders with path '$path'")
        } else {
            throw new RuntimeException("Can't create folders with path '$path'")
        }
    }

    static String toRightPath(@NotNull String path) {
        if (path.endsWith('/')) {
            return path
        }
        return "$path/"
    }
}
