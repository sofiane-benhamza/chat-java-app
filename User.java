/*
 * 
 * @author Sofiane0x626
 * 
 */

import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class User {
	private static InetAddress host;
	private static int PORT;
	private static Scanner keyboard = new Scanner(System.in), input;
	private static PrintWriter output;
	private static  Socket socket = null;
	
	public static void main(String[] args){
		try{
			host = InetAddress.getLocalHost();
		}catch(UnknownHostException uhEx){  //host Exception
			System.out.println("\nhost unavailable ...\n");
			System.exit(1);
		}
		 System.out.println(Store.getWelcomeString()); 
		 System.out.println("choose wanted connection\t:\t");
		 System.out.println("\t1 : private chat\n\t2 : groupe chat");
		 int connectionChoice = keyboard.nextInt();
		switch(connectionChoice){
		case 1:
			PORT = 1411;
			break;
		case 2:
			PORT = 1511;
			break;
		
		}
				  try {
				   	        socket = new Socket(host,PORT);
		                    // Welcome to Z-Chat
			                //create communication links
	                           input      =  new Scanner(     socket.getInputStream());
		          	           output    =  new PrintWriter( socket.getOutputStream(), true );
					} catch (IOException e) {
						    System.out.println("Server not available ...");
							System.exit(1);
					}
		Communicate();
	}
	

	private static void Communicate(){
		System.out.println(Store.getWaitString());
		try{        //Thread just like a listener for incoming messages
			Thread getMessage = new Thread(new Runnable() {
	              public void run() {
					String received;
					try {
	          		do{
	               			received = Privacy.decryption(input.nextLine());
                                //decrypt the message using Privacy class after getting it.        
		                    System.out.println(received);
	                            //show the incoming message in user's screen
	          		}while(!received.equals("***EXIT***"));
					  
					try {
						socket.close();
					} catch (Exception e) {
						System.out.println("Exception ... 110");
					}
					 System.out.println("Succesfully disconnected ... 32");
					 System.exit(1);
					}catch(Exception e) {
						System.out.println(" --- User disconnected ---");
						System.exit(0);
					}
					 
				}});
			    //Thread for out going messages
			Thread sendMessage = new Thread(new Runnable() {
	              public void run() {
	                  String send;      //message to be sent
	                  try {
					do{
	            	         send = keyboard.nextLine(); //read the message from user's keyboard
	            	         output.println(Privacy.encryption(send));   
							    //encrypt the message using Privacy class before sending it.        
	              	   }while(!send.equals("***EXIT***"));
										 try {
											socket.close();
										} catch (Exception e) {
											System.out.println("Exception ... ");
										}
										 System.out.println("Closing connection ... ");
						                 System.exit(1);
	                  }catch(Exception e) {
	                	  System.out.println("Exception ...");
	                  }
									}});
				
									getMessage.start();
									sendMessage.start();

		    
		}catch(Exception e) {
			System.out.println("Server Not Found ...");
			System.exit(1);
			
		}
}}
