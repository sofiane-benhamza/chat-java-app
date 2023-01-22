import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	private static ServerSocket serverSocketPeer, serverSocketGroupe;
	private static final int peerPORT = 1411,groupePORT=1511; //port
	private static int peerConnectionId=0, groupeConnectionId=0;
	public static void main(String[] args){
		try{
			serverSocketPeer      =   new ServerSocket(peerPORT);
			serverSocketGroupe    =   new ServerSocket(groupePORT);
		}catch (IOException ioEx){
			System.out.println("\nPort is currently in use ..."); //handle port error
			System.exit(1);
		}
		
		 System.out.println("Z-chat Server Started private chats on port "+Integer.toString(peerPORT)+" ...");
		 System.out.println("Z-chat Server Started groupe  chats on port "+Integer.toString(groupePORT)+" ...");          //port is available
	    	
		 Thread peer = new Thread(new Runnable(){
		 public void run(){
		 try {
			 do{
				    try {
			         //waiting for clients...
			    Socket client = serverSocketPeer.accept();
			        System.out.println("[ peerLink ] : a new User gotten !");
			    PeerHandler handler = new PeerHandler(client,peerConnectionId); 
			    peerConnectionId++;       
			     //augmente id for the next connection
				   }catch(Exception e) {
						 System.out.println("Something went wrong ... 781");
					 }
	
		        }while(true);
			
	     }catch(NoSuchElementException e){
			System.out.println("Something went wrong ... 127");
			System.exit(1);
	     }}});
		peer.start();
	

		Thread groupe = new Thread(new Runnable() {
		public void run(){
				 try {
					 do{
						   Socket client = serverSocketGroupe.accept();
						   System.out.println("[groupeLink] : a User has joined the chat");
						   GroupeHandler handler = new GroupeHandler(client, groupeConnectionId);
						   groupeConnectionId++;
;					 }while(true);
				 }catch(Exception e) {
					 System.out.println("something went down ... 425");
				 }
				 
			 }		  
		});
		groupe.start();
	    }
}

