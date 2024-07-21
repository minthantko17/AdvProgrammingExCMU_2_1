package se233.chapter3downloadmanager.model;

import javafx.concurrent.Task;

import java.io.*;
import java.util.concurrent.CountDownLatch;

public class Merger extends Task<Void> {
    private String fileName;
    private int totalParts;
    private CountDownLatch startSignal;

    public Merger(String fileName, int totalParts, CountDownLatch startSignal){
        this.fileName=fileName;
        this.totalParts=totalParts;
        this.startSignal=startSignal;
    }

    @Override
    protected Void call() throws Exception {
        byte[] buf= new byte[4096];
        OutputStream out=null;
        InputStream in= null;
        File f=null;
        try{
            startSignal.await();
            out = new FileOutputStream(this.fileName);
            for(int i=0; i<totalParts; i++){
                int toWrite;
                f=new File(this.fileName+"-part"+(i+1));    //points to the existing file that is created as download1  or 2
                in=new FileInputStream(f);      //use the existing file to inputStream and write to output
                while((toWrite=in.read(buf))!=-1){
                    out.write(buf, 0, toWrite);
                }
                f.deleteOnExit();
                updateProgress((i+1), totalParts);
                in.close();
            }
            out.close();
        }catch(Exception e){e.printStackTrace();}

        return null;
    }
}
