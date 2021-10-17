package sh.rescue.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileHelper {
    private FileHelper() {
    }

    public static List<String> getFiles(String path) {
        List<String> files = new ArrayList<>();

        try {
            Arrays.stream(Objects.requireNonNull(new File(path).listFiles()))
                    .filter(File::isFile)
                    .forEach(file -> files.add(file.getAbsolutePath()));
        } catch (Exception e) {
            System.out.println("没找到文件");
        }

        return files;
    }

    public static boolean save(String filename, byte[] data) {
        boolean bl = false;
        try {
            Files.write(Paths.get(filename), data);
            bl = true;
        } catch (IOException e) {
            System.out.println("save file error");
        }
        return bl;
    }

    public static boolean isExists(String filename) {
        boolean bl = false;
        try {
            bl = new File(filename).exists();
        } catch (Exception e) {
        }
        return bl;
    }
}
