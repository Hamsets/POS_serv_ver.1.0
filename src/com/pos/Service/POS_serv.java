package com.pos.Service;

import com.pos.Utils.PropertiesReader;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

class POS_serv {
	private final PropertiesReader pR = new PropertiesReader();

	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket sock;

		public ClientHandler (Socket clientSocket) {
			try {
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader (sock.getInputStream());
				reader = new BufferedReader(isReader);

			} catch (Exception ex) { ex.printStackTrace ();};
		} //закрываем конструктор

		public void run () {
			String checkStr;
			ArrayList<String> checkArrStr = new ArrayList<String>();

			try {
				while ((checkStr = reader.readLine ()) != null) {
					System.out.println ("Sending data:" + checkStr);

					checkArrStr.add (checkStr);

//					writeFileCheck (id, checkStr);
				} // закрываем while
					if (!checkArrStr.isEmpty()){
					writeCheckToFile(checkArrStr);
					}
			} catch (Exception ex) {ex.printStackTrace();}
		}// закрываем run
	}// закрываем вложенный класс

	public static void main (String [] args) {
		new POS_serv().go();
	}

	public void go () {
		System.out.println ("Wating for connection...");
		try	(ServerSocket serverSock = new ServerSocket (Integer.parseInt(this.pR.getMapProperties().get("portServer")))) {

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
	public void writeCheckToFile(ArrayList<String> checkStr) {

		File dir;
		File file;
		Date dateNow = new Date();
		Date timeNow = new Date ();
		SimpleDateFormat dateDirConf = new SimpleDateFormat ("yyyy.MM.dd");
		SimpleDateFormat timeFileConf = new SimpleDateFormat ("HH-mm-ss");
		String dirName;
		String fileName;

		dirName = this.pR.getMapProperties().get("dbText.path") + "\\" + dateDirConf.format(dateNow) + "\\";
		fileName = dirName + timeFileConf.format(timeNow) + ".txt";

		dir = new File (dirName);
		file = new File (fileName);

		String line;
		try {
			if (!dir.exists()) {dir.mkdir();}

			//file.createNewFile(); //следующая строка уже создает файл
			BufferedWriter writer = new BufferedWriter (new FileWriter (file));
			for (String x: checkStr) {
				writer.write(x + "\n");
			}

			writer.flush();
			writer.close();
		} catch (Exception ex) {ex.printStackTrace();}
	}// закрываем метод записи чека в файл
}//закрываем класс