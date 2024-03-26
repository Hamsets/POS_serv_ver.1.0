package com.pos.Service;

import com.pos.Data.Connection.DataBaseManager;
import com.pos.Data.Dao.CheckDao;
import com.pos.Data.Dao.impl.CheckDaoImpl;
import com.pos.Data.Entities.Check;
import com.pos.Data.Entities.Goods;
import com.pos.Service.Dto.CheckDto;
import com.pos.Service.Dto.UserDto;
import com.pos.Utils.PropertiesReader;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.sql.Timestamp;

class POS_serv {
	private final PropertiesReader pR = new PropertiesReader();
//	protected int hashForCheckAcception;

	public class ClientHandler implements Runnable {
		BufferedReader reader;
		BufferedWriter writer;
		Socket sock;

		public ClientHandler (Socket clientSocket) {
			try {
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader (sock.getInputStream());
				OutputStreamWriter isWriter = new OutputStreamWriter (sock.getOutputStream());
				reader = new BufferedReader(isReader);
				writer = new BufferedWriter(isWriter);

			} catch (Exception ex) { ex.printStackTrace ();};
		} //закрываем конструктор

		public void run () {
			String readedStr;
			CheckDto checkDto;
			UserDto userDto;

			try {
				while ((readedStr = reader.readLine ()) != null) {
					System.out.println ("Sending data:" + readedStr);
					String[] arrayReadedStr = readedStr.split("#");


					switch (arrayReadedStr[0]) {
						case "WRITE_CHECK":
							checkDto = new CheckDto(readedStr);
							int hash = writeCheckToDb(checkDto);
//							Thread.sleep(3000);
							writer.write(Integer.toString(hash) +"\n");
							System.out.println(hash);
							writer.flush();
						break;
						case "READ_CHECK":
							break; //FIXME need code
						case "WRITE_USER":
							break; //FIXME need code
						case "READ_USER":
							break;//FIXME need code
						case "COMPARE_USER":
							userDto = new UserDto(readedStr);
							break;
					}
				}
			} catch (Exception ex) {ex.printStackTrace();}
		}
	}// закрываем вложенный класс

	private boolean isHasTrue(String inputStr) {
		boolean accepted = false;
		String[] arrayInputStr = inputStr.split("#");
		if (arrayInputStr[0].equals("true")){
			accepted = true;
		}
		return accepted;
	}

	private int writeCheckToDb(CheckDto checkDto) {
		CheckDao checkDao = new CheckDaoImpl(new DataBaseManager());

		try { return checkDao.writeCheck(checkDto);
		} catch (RuntimeException e) {
			System.out.println("Network error!!! \n" + e.getMessage());
			return 0;
		}
	}

	private void updateAcceptedCheckToDb(String inputStr) {
		CheckDao checkDao = new CheckDaoImpl(new DataBaseManager());

		try { checkDao.updateCheckAccepted(inputStr);
		} catch (RuntimeException e) {
			System.out.println("Network error!!! \n" + e.getMessage());
		}
	}

	public static void main (String [] args) {
		new POS_serv().go();
	}

	public void go () {
		System.out.println ("Waiting for connection...");
		try	(ServerSocket serverSock = new ServerSocket (Integer.parseInt(this.pR.getMapProperties().get("portServer")))) {

			while (true) {
				Socket clientSocket = serverSock.accept();
				Thread t = new Thread (new ClientHandler (clientSocket));
				t.start();
//				t.join();
 				System.out.println ("Connected!");
			}
		} catch (Exception ex) {ex.printStackTrace();}
	}// закрываем go


//	private Check createCheck (String checkStr){
//		String[] arrayCheckCode = checkStr.split("#");
//		return new Check(Long.parseLong(arrayCheckCode[0]),arrayCheckCode[1],Long.parseLong(arrayCheckCode[2]),
//				Goods.createGoodsListFromStr(arrayCheckCode[3]), new BigDecimal(arrayCheckCode[4]),
//				Timestamp.valueOf(arrayCheckCode[5]), Boolean.parseBoolean(arrayCheckCode[6]));
//	}
}