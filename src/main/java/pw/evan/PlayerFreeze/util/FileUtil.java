package pw.evan.PlayerFreeze.util;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

public class FileUtil
{
    public static String getFileExtension(File file){
        String extension = "";
        String fileName = file.getName();
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }

    public static String stripExtension(File file){
        String fileName = "";
        String fullName = file.getName();
        int i = fullName.lastIndexOf('.');
        if (i > 0) {
            fileName = fullName.substring(0,i);
        }
        return fileName;
    }

    public static boolean validateUUID(String uuidString){
        try
        {
            UUID parsed = UUID.fromString(uuidString);
            return parsed.toString().equalsIgnoreCase(uuidString);
        }
        catch(IllegalArgumentException e)
        {
            return false;
        }
    }
}
