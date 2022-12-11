
	import java.io.*;
	import java.net.*;
	import java.util.*;
	import java.text.*;
	
	class POS_serv {
		
		public class ClientHandler implements Runnable {
			BufferedReader reader;
			Socket sock;
			String id = "Part";
			
			
			public ClientHandler (Socket clientSocket) {
				try {
					sock = clientSocket;
					InputStreamReader isReader = new InputStreamReader (sock.getInputStream());
					reader = new BufferedReader(isReader);
					
				} catch (Exception ex) { ex.printStackTrace ();};
			} //закрываем конструктор
			
			public void run () {
				String ticketStr;
				ArrayList<String> receiptStr = null;
				
				try {
					while ((ticketStr = reader.readLine ()) != null) {
						System.out.println ("Sending data:" + ticketStr);
						receiptStr.add (ticketStr);
//						writeFileReceipt (id, ticketStr);
					} // закрываем while
						writeFileReceipt (receiptStr);
				} catch (Exception ex) {ex.printStackTrace();}
			}// закрываем run
		}// закрываем вложенный класс
		
		public static void main (String [] args) {
			new POS_serv().go();
		}
		
		public void go () {
			System.out.println ("Wating for connection...");
			try {
				ServerSocket serverSock = new ServerSocket (5000);
				
				while (true) {
					Socket clientSocket = serverSock.accept();
					PrintWriter writer = new PrintWriter (clientSocket.getOutputStream());
					
					Thread t = new Thread (new ClientHandler (clientSocket));
					t.start();
					System.out.println ("Connected!");
				}
			} catch (Exception ex) {ex.printStackTrace();}
		}// закрываем go
		
		
		
		
		//пишем принятое сообщение в новый файл с названием (дата, время с секундами), нужно проверить существует ли папка с текущей датой, текущим id кафе
		public void writeFileReceipt(ArrayList<String> receiptStr) {
			
			File dir;
			File file;
			String way = "D:\\YandexDisk\\MyStudy\\Android\\POS_serv_ver.1.0\\";
			Date dateNow = new Date();
			Date timeNow = new Date ();
			SimpleDateFormat dateDirConf = new SimpleDateFormat ("yyyy.MM.dd");
			SimpleDateFormat timeFileConf = new SimpleDateFormat ("hh-mm-ss");
			String dirName;
			String fileName;
			
			dirName = way + "\\" + dateDirConf.format(dateNow) + "\\";
			fileName = dirName + timeFileConf.format(timeNow);
			
			dir = new File (dirName);
			file = new File (fileName);
			
			String line;
			try {
				if (!dir.exists()) {dir.mkdir();}	
					
				//file.createNewFile(); //следующая строка уже создает файл
				BufferedWriter writer = new BufferedWriter (new FileWriter (file));				
				for (String x:receiptStr) {

				writer.write(x + "\n");
									
				}

				writer.flush();
				writer.close();
			} catch (Exception ex) {ex.printStackTrace();}
		}// закрываем метод записи чека в файл
	}//закрываем класс	