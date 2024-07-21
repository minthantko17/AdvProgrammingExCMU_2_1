/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se233.chapter3downloadmanager.model;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;

import javafx.concurrent.Task;

/**
 *
 * @author mhrimaz
 */
public class Downloader extends Task<Void> {

    private URL url;
    private String fileName;
    private long startByte,endByte;
    private CountDownLatch doneSignal;

    /**
     *
     * @param url URL of file to download
     * @param fileName name of the file for saving
     */
    public Downloader(URL url, String fileName, long startByte, long endByte, CountDownLatch doneSignal) {
        this.url = url;
        this.fileName = fileName;
        this.startByte=startByte;
        this.endByte=endByte;
        this.doneSignal=doneSignal;
    }

    @Override
    protected Void call() throws Exception {
        try {
            //Code to download
            URLConnection openConnection = url.openConnection();
            openConnection.setRequestProperty("Range", "bytes"+startByte+"-"+endByte);  //range for each chunk
            int fileSize = openConnection.getContentLength();

            try (OutputStream out=new FileOutputStream(this.fileName);
                InputStream in=openConnection.getInputStream();){

                byte[] buf = new byte[5120];
                long downloaded = 0;
                while (downloaded<fileSize) {
                    int n=in.read(buf,0,buf.length);    //will read the next bytes automatically in next iteration
                    if(n!=1){
                        downloaded += n;
                        out.write(buf, 0, n);
                        updateProgress(downloaded, fileSize);
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        doneSignal.countDown();
        return null;
    }

}
