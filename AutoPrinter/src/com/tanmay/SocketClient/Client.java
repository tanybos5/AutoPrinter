package com.tanmay.SocketClient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public static void main(String[] args)
    {

        Scanner sc = new Scanner(System.in);
        // Create Client Socket connect to port 900
        try (Socket socket = new Socket("192.168.124.1", 900)) {

            dataInputStream = new DataInputStream(
                    socket.getInputStream());
            dataOutputStream = new DataOutputStream(
                    socket.getOutputStream());
            // Call SendFile Method
            System.out.print("File path: ");
            String sendFileName = sc.nextLine();
            sendFile(sendFileName);
            System.out.println(
                    "Sending the File to the Server");


            dataInputStream.close();
            dataInputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // sendFile function define here
    private static void sendFile(String path)
            throws Exception
    {
        int bytes = 0;
        // Open the File where he located in your pc
        File file = new File(path);
        FileInputStream fileInputStream
                = new FileInputStream(file);

        // Here we send the File to Server
        dataOutputStream.writeLong(file.length());
        // Here we  break file into chunks
        byte[] buffer = new byte[4 * 1024];
        while ((bytes = fileInputStream.read(buffer))
                != -1) {
            // Send the file to Server Socket
            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();
        }
        // close the file here
        fileInputStream.close();
    }
//    public static void main(String[] args)
//    {
//        Socket socket = null;
//        DataInputStream din = null;
//        DataOutputStream dout = null;
//        BufferedReader br = null;
//
//        try
//        {
//            /*
//             * Creates a stream socket and connects it to the
//             * specified port number at the specified IP address.
//             */
//            socket = new Socket("localhost", 6666);
//            din = new DataInputStream(socket.getInputStream());
//
//            /*
//             * returns the OutputStream attached with this socket.
//             */
//            OutputStream outputStream = socket.getOutputStream();
//            dout = new DataOutputStream(outputStream);
//
//            br = new BufferedReader(new InputStreamReader(System.in));
//
//            String strFromServer = "", strToClient = "";
//            while (!strFromServer.equals("stop"))
//            {
//                strFromServer = br.readLine();
//                dout.writeUTF(strFromServer);
//                dout.flush();
//                strToClient = din.readUTF();
//                System.out.println("Server says: " + strToClient);
//            }
//
//        }
//        catch (Exception exe)
//        {
//            exe.printStackTrace();
//        }
//        finally
//        {
//            try
//            {
//
//                if (br != null)
//                {
//                    br.close();
//                }
//
//                if (din != null)
//                {
//                    din.close();
//                }
//
//                if (dout != null)
//                {
//                    dout.close();
//                }
//                if (socket != null)
//                {
//                    /*
//                     * closes this socket
//                     */
//                    socket.close();
//                }
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }
}