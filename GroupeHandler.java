import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class GroupeHandler{
    private static ArrayList<GroupeHandler> connexions = new ArrayList<GroupeHandler>();
    private static ArrayList<Thread> Listeners = new ArrayList<Thread>();
    private String clientName;
    private int Id;
    private Scanner  input;
    private PrintWriter output;
    
    public GroupeHandler(Socket socket, int id){
    	try { // unexpeced errors
    	this.Id = id;
    	connexions.add(this);
    	 try{
			output = new PrintWriter(socket.getOutputStream(), true);
			input  = new Scanner(socket.getInputStream());
    	 } catch (IOException e) {
			System.out.println("something went wrong ... 524");
		}
    
    	Thread Listener = new Thread(new Runnable(){
			public void run() {


	            output.println(Store.getAskForNameString());
				input.nextLine();
	            clientName = input.nextLine();
	            output.println(Store.getChatStartedString());
            try{
				  String Message = "";
				while(!Message.equals(Store.getCloseString())){
					Message = input.nextLine();
					MultiCast(Message,Id);	
				}
			}catch(Exception e){
				System.out.print("something went wrong ...");
			}	
			}});
    	Listeners.add(Listener);
    	Listener.start();
    	System.out.println("Listener to user "+Id+" started ...");
    	}catch(Exception e) {
    		System.out.println("something went wrong ...");
    	}
    	
    }
    
    public static void MultiCast(String MessageToBeSent, int NotSendId) {
    	String name = connexions.get(NotSendId).clientName; 
        for(GroupeHandler User:connexions) {
    		if(User.Id != NotSendId) {
    			 User.output.println("(" + name + ")d4dd"+MessageToBeSent);
         }
    System.out.println("Client "+Integer.toString(NotSendId) + " sent : " + MessageToBeSent);
	   //Spy 
    }

    

}}
