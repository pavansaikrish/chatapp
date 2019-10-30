package com.infy.socket;
 
import java.io.*;
import java.net.*;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import com.infy.client.ui.MyChatFrame;

public class Download implements Runnable{
    
    public ServerSocket server;
    public Socket socket;
    public int port;
    public String saveTo = "";
    public InputStream In;
    public FileOutputStream Out;
    public MyChatFrame ui;
    public JProgressBar progressBar;
    public JDialog dialog;
    public JFrame frame;
    
    public Download(String saveTo, MyChatFrame ui){
        try {
            server = new ServerSocket(0);
            port = server.getLocalPort();
            this.saveTo = saveTo;
            this.ui = ui;
            progressBar = new JProgressBar(0, 100);
            progressBar.setValue(0);
            progressBar.setStringPainted(false);
           progressBar.setBounds(0, 0, 300, 30);
            progressBar.setIndeterminate(true);
            
            frame = new JFrame();
            frame.setLayout(null);
            frame.setTitle("Downloading......");
            frame.add(progressBar);
            frame.setSize(300, 60);
            frame.setResizable(false);
            frame.setVisible(true);
        } 
        catch (IOException ex) {
            System.out.println("Exception [Download : Download(...)]");
        }
    }

    @Override
    public void run() {
        try {
            socket = server.accept();
            System.out.println("Download : "+socket.getRemoteSocketAddress());
            
            In = socket.getInputStream();
            Out = new FileOutputStream(saveTo);
            
            byte[] buffer = new byte[1024];
            int count;
            int i=0;
            int j=0;
           
            while((count = In.read(buffer)) >= 0){
            	i++;
            	Out.write(buffer, 0, count);
            }
            progressBar.setIndeterminate(false);
            progressBar.setStringPainted(true);
            final int k=i;
            while(j<=i){
            	
            	
            final int co=j;
            SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					int value=(co*100)/k;
					progressBar.setValue(value);
					
				}});
           
            j++;
            }
            System.out.println(i);
             

            Out.flush();
            
            ui.commentTextArea.append("[Application > Me] : Download complete\n");
            //frame.setVisible(false);
            if(Out != null){ Out.close(); }
            if(In != null){ In.close(); }
            if(socket != null){ socket.close(); }
        } 
        catch (Exception ex) {
            System.out.println("Exception [Download : run(...)]");
        }
    }
}