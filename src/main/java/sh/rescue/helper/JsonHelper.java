package sh.rescue.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import sh.rescue.entity.FileInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonHelper() {
    }

    public static <T> T toObj(byte[] data, Class<T> clz) {
        if (data.length == 0) {
            return null;
        }

        T obj = null;
        try {
            obj = objectMapper.readValue(data, clz);
        } catch (Exception e) {
//            System.out.println(e.getMessage());
        }
        return obj;
    }

    public static String toStr(Object obj) {
        if (obj == null) {
            return null;
        }

        String str = null;
        try {
            str = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
//            System.out.println(e.getMessage());
        }
        return str;
    }

    public static List<FileInfo> fileToList(String filename) {
        List<FileInfo> list = new ArrayList<>();
        try {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, FileInfo.class);
            list = objectMapper.readValue(new File(filename), listType);
        } catch (Exception e) {
//            System.out.println("ERROR " + e.getMessage());
//            e.printStackTrace();
        }
        return list;
    }
}
