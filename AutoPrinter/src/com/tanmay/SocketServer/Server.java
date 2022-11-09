package com.tanmay.SocketServer;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;

public class Server
{
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    static Robot rb;

    static {
        try {
            rb = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws AWTException {
        // Here we define Server Socket running on port 900
        try (ServerSocket serverSocket
                     = new ServerSocket(900)) {
            System.out.println(
                    "Server is Starting in Port 900");
            // Accept the Client request using accept method
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected");
            dataInputStream = new DataInputStream(
                    clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(
                    clientSocket.getOutputStream());
            // Here we call receiveFile define new for that
            // file
            receiveFile("print.pdf");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // receive file function is start here

    private static void receiveFile(String fileName)
            throws Exception
    {
        int bytes = 0;
        FileOutputStream fileOutputStream
                = new FileOutputStream(fileName);

        long size
                = dataInputStream.readLong(); // read file size
        byte[] buffer = new byte[4 * 1024];
        while (size > 0
                && (bytes = dataInputStream.read(
                buffer, 0,
                (int)Math.min(buffer.length, size)))
                != -1) {
            // Here we write the file using write method
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes; // read upto file size
        }
        // Here we received file
        System.out.println("File is Received");
        fileOutputStream.close();
    }

    private static void printOut() throws InterruptedException {
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_ALT);
        rb.keyPress(KeyEvent.VK_T);
        rb.keyRelease(KeyEvent.VK_T);
        rb.keyRelease(KeyEvent.VK_ALT);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(500);
        String text = "lp -d Canon_MG2900_series print.pdf";
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_SHIFT);
        rb.keyPress(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_SHIFT);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(100);
        rb.keyRelease(KeyEvent.VK_ENTER);
    }


}

