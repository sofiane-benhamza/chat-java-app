/*
 * 
 * @author Sofiane0x626
 * 
 */
import java.io.*;
import java.net.Socket;
import java.util.*;

class PeerHandler extends Thread{
	              /*
	
	             Needed for peer connections 
	             
	             */
	private Socket client;
	                          //to reference clients's sockets
	private Scanner input;     
	                         //communication input
	private PrintWriter output;  
	                       //Communication output
	private String nameOfClient, messageFromClient;
	                            //save clients names and temporary messages	                           
	private int Id; 
	                //save id of client  an
	private static ArrayList<PeerHandler> users = new ArrayList<PeerHandler>();

	private static ArrayList<String>      names = new ArrayList<String>();

	private static int numberOfUsers=0;
	                         

	public PeerHandler(Socket socket, int id){
	   	         //Set up reference and id to associated sockets ...
		   numberOfUsers++;
		   Id = id;
		   client = socket;
		   users.add(this);
		   
		try{
			//Create links for the client
			input   =  new Scanner(    client.getInputStream());
			output =  new PrintWriter(client.getOutputStream(), true);

		}catch(IOException ioEx){
			System.out.print("Something went wrong ... 710");
			System.exit(1);
		}
		//Get name
		output.println(Store.getAskForNameString()); //ask name String encrypted
		input.nextLine();   
		nameOfClient = input.nextLine();        //read name of client 1
		names.add(nameOfClient);
		this.start();
	}

	public void run(){
       if(numberOfUsers < 2){
		   this.output.println(Store.getNoClientAvailableString());
	   }else{
		for(PeerHandler user:users){
		 if(!user.equals(this)){
			this.output.println(Privacy.encryption("to speak with \"")+user.nameOfClient+
			             Privacy.encryption("\" type : "+Integer.toString(user.Id)));
		 }
		}
           
		   String tmp = Privacy.decryption(this.input.nextLine());
		   int choice = Integer.valueOf(tmp);
	       Communicate(this, users.get(choice));
	   }
	}

	public static void Communicate(PeerHandler client1,PeerHandler client2){

		users.remove(client1.Id);
		users.remove(client2.Id);

		Scanner input1 = client1.input, input2 = client2.input;
		PrintWriter output1 = client1.output, output2 = client2.output;
		String nameOfClient1 = client1.nameOfClient, nameOfClient2 = client2.nameOfClient;
		                         // reference names and input output streams

		try { 
    	//Thread to get messages from client 1 and send them to client 2
		    	Thread sendToClient2 = new Thread(new Runnable() { 
			 	public void run(){
					   	output2.print(Store.getChatStartedString());
					do{
						String messageFromClient1 = Store.getCloseString();  //***EXIT*** encrypted
						try {
						messageFromClient1 = input1.nextLine(); 
						 //read the incoming message from client 1
						System.out.println(nameOfClient1+"\n"+messageFromClient1+"\n-------------------------------");
						 // server spy listener, to check encryption
						output2.println("("+nameOfClient1+")d4d"+ messageFromClient1);
						 //send the message to client 2
						}catch(Exception e) {
						}
					}while(true); // if client 1 send ***EXIT*** 
				}}); //end of thread
         //Thread to get messages from client 2 and send them to client 1
 	        Thread sendToClient1 = new Thread(new Runnable() { 
				public void run() {
				   	  output1.print(Store.getChatStartedString());
					do{
						String messageFromClient2 = Store.getCloseString();   //***EXIT*** encrypted
						try {
							messageFromClient2 = input2.nextLine();
							        //get message from client 2
							System.out.println(nameOfClient2 +"\n"+messageFromClient2+"\n-------------------------------"); 
						         	// server spy listener to check encryption
						    output1.println("("+nameOfClient2+")d4d"+ messageFromClient2);
							        //send the message to client 1    
					}catch(Exception e) {
					}
					}while(true); // if client 2 send ***EXIT***
				}}); //end of thread
			sendToClient1.start();
			sendToClient2.start();    
		}catch(NoSuchElementException e) {
			System.out.println("connection lost, listening ...");	
		}catch(Exception e) {
			System.out.println("Something went wrong ... 981");
			
		}
	     }   
}