package com.janbask.training3;

//spring imports
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;

public class ConfigurationEx {
    public static void main(String[] args) {
        Log("Started ClassPathXmlApplicationContext loading...");
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        Log("ClassPathXmlApplicationContext loaded!");
        Log("Loading download configuration...");
        SimpleConfiguration simpleConfiguration = (SimpleConfiguration) context.getBean("simpleConfiguration");
        Log("download configuration loaded!");
        Log(String.format("Configuration\n****\nUrl: %s\nFile Prefix: %s\n****", simpleConfiguration.getDownloadUrl(), simpleConfiguration.getFileNamePrefix()));
        Log("starting download...");
        downloadFile(simpleConfiguration);
        Log("download finished!");
    }

    static void Log(String event){
        String message = String.format("%s: %s", new Date(), event);
        System.out.println(message);
    }

    static void downloadFile(SimpleConfiguration configuration){
        BufferedInputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            Log("opening HTTP stream...");
            inputStream = new BufferedInputStream(new URL(configuration.getDownloadUrl()).openStream());
            String fileName = String.format(configuration.getFileNamePrefix(), new Date());
            Log(String.format("opening file output stream to file %s...", fileName));
            fileOutputStream = new FileOutputStream(fileName);
            byte data[] = new byte[1024];
            int count;
            int totalBytes=0;
            Log(String.format("reading HTTP stream to file %s...", fileName));
            while ((count = inputStream.read(data, 0, 1024)) != -1) {
                fileOutputStream.write(data, 0, count);
                totalBytes+=count;
                Log(String.format("read bytes: %s", count));
            }
            Log("finished reading!");
            Log(String.format("total bytes downloaded: %s", totalBytes));
            Log(String.format("file downloaded [%s]",fileName));
        }catch (MalformedURLException exception){
            System.out.println("Invalid Url!");
        }catch (IOException exception){
            exception.printStackTrace();
        }finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if(fileOutputStream!=null)
                    fileOutputStream.close();
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
    }
}
