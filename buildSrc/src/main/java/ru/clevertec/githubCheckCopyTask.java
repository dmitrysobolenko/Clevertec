package ru.clevertec;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class githubCheckCopyTask extends DefaultTask {

    public String getUrlStr() {
        return urlStr;
    }

    public String getFileStr() {
        return fileStr;
    }

    @Input
    public
    String urlStr;

    @OutputFile
    public
    String fileStr;

    @TaskAction
    void doTask() {
        try {
            download(urlStr, fileStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void download(String urlStr, String fileStr) throws IOException {
        URL url = new URL(urlStr);
        try (BufferedInputStream input = new BufferedInputStream(url.openStream());
             FileOutputStream output = new FileOutputStream(fileStr)) {
            byte[] buffer = new byte[1024];
            int count;
            while ((count = input.read(buffer, 0, 1024)) != -1) {
                output.write(buffer, 0, count);
            }
        }
    }
}
