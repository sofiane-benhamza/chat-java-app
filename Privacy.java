public class Privacy {
	
	private static String encryption = "ABCDEFGH IJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz></-,._:;?=!*+[](){}"
	,                     decryption = ";5klXcO_d:6hisjpef -r8aVbY/tQuF>GvRxWyCK<zAgBT7U1D,EqH.I0JL3MSmZn9o2NP4w!?*=+(){}[]";
	
	// this class is for encryption, it uses cesar methode to encrypt and decrypt messages, every character is
	//associated with another character, what gives privacy	if messages where seen by someone from outside users class
	
		public static String encryption(String message){
			char[] tmpEncryption = new char[message.length()];
			char[] tmpMessage = message.toCharArray();
			for(int i=0;i<message.length();i++) {
				  for(int j=0;j<encryption.length();j++) {
				        if(tmpMessage[i] == encryption.charAt(j)) {
				        	 tmpEncryption[i] = decryption.charAt(j);
				        }
			}}
			String encryptedMessage = new String(tmpEncryption);
			return encryptedMessage;
		}
		
		public static String decryption(String message){
			char[] tmpDecryption = new char[message.length()];
			char[] tmpMessage = message.toCharArray();
			for(int i=0;i<message.length();i++) {
				  for(int j=0;j<encryption.length();j++) {
				        if(tmpMessage[i] == decryption.charAt(j)) {
				        	tmpDecryption[i] = encryption.charAt(j);
				        }
			}}
			String encryptedMessage = new String(tmpDecryption);
			return encryptedMessage;

	}
		public static void main(String[] args) {
			String test="{}";
			System.out.print(encryption(test));
		}
}
