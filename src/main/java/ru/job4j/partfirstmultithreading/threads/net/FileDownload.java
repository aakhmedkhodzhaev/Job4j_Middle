package ru.job4j.partfirstmultithreading.threads.net;

import java.io.*;
import java.net.URL;


public class FileDownload implements Runnable {

    private long v; // скорость скачивания
    private String url; // адрес, ссылка на скачиваемый объект

    public FileDownload(String url, long v) {
        this.url = url;
        this.v = v;

    }

    @Override
    public void run() {
        File file = new File("pom_tmp." + url.substring(url.lastIndexOf(".") + 1, url.length())); // url.substring(url.length()-3, url.length())
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream()); // InputStream in = new URL(url).openConnection().getInputStream(); Можно ли так!?
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.pdf")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long startDate = System.nanoTime();
            int count = 0;
            long downloaded = 0;
            while ((bytesRead = in.read(dataBuffer)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                System.out.println(String.format("%,.2fkb", (double) file.length() / 1024));
                long spentDate = System.nanoTime() - startDate;
                double differ = (double) spentDate / 1000000000.0;
                System.out.println(String.format("%s секунд", differ));
                if (differ >= 1) {
                    downloaded = (file.length() - downloaded) / 1024;
                    if (downloaded >= v) {
                        long endDate = downloaded - v;
                        long timeout = (endDate / v) * 1000;
                        System.out.println(String.format("Время ожидания %s сек.", timeout / 1000));
                        Thread.sleep(timeout);
                        count += timeout / 1000;
                        startDate = System.nanoTime();
                    }
                }
            }
            System.out.println("Файл скачен. Время ожидания составило:" + count + " секунд");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileDownload download = new FileDownload("https://pdfs.semanticscholar.org/3650/4bc31d3b2c5c00e5bfee28ffc5d403cc8edd.pdf", 200);
        download.run();
    }
}