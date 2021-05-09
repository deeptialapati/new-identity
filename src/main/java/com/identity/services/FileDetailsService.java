package com.identity.services;

import com.google.common.collect.Lists;
import com.identity.pojos.FileDetails;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileDetailsService {
//    This method returns all the files in a directory as a Map, key is file extension and File details pojo contains all the file details
    public Map<String, List<FileDetails>> getFileDetailsFromADirector(String directoryName) {
        Map<String, List<FileDetails>> fileData = new HashMap<>();
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        for (File file : fList) {
            String absolutePath = file.getAbsolutePath();
            if (file.isFile()) {
                String extension = FilenameUtils.getExtension(absolutePath);
                List<FileDetails> fileDetailsList = fileData.get(extension);
                if (fileDetailsList == null) {
                    fileData.put(extension, Lists.newArrayList(getFilesDetails(absolutePath)));
                } else {
                    fileDetailsList.add(getFilesDetails(absolutePath));
                    fileData.put(extension, fileDetailsList);
                }
            } else if (file.isDirectory()) {
//                do nothing
            }
        }
        return fileData;
    }

//    this method returns file details object for a single file
    private FileDetails getFilesDetails(String absolutePath) {
        FileDetails fileDetails = new FileDetails();
        String mimeType = getMimeType(absolutePath);
        fileDetails.setFileMimeType(mimeType);
        fileDetails.setFileSize(new File(absolutePath).length());
        fileDetails.setFilename(absolutePath);
        return fileDetails;
    }

    private String getMimeType(final String fileName) {
        Tika mimeTika = new Tika();
        String fileType;
        try {
            final File file = new File(fileName);
            fileType = mimeTika.detect(file);
        } catch (IOException exp) {
            fileType = "Unknown";
        }
        return fileType;
    }
}
