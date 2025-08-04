package com.pos.Service;

import com.pos.Data.Connection.DataBaseManager;
import com.pos.Data.Dao.CheckDao;
import com.pos.Data.Dao.GoodsDao;
import com.pos.Data.Dao.UserDao;
import com.pos.Data.Dao.impl.CheckDaoImpl;
import com.pos.Data.Dao.impl.GoodsDaoImpl;
import com.pos.Data.Dao.impl.UserDaoImpl;
import com.pos.Data.Entities.Check;
import com.pos.Data.Entities.Goods;
import com.pos.Service.Dto.CheckDto;
import com.pos.Service.Dto.UserDto;
import com.pos.Utils.PropertiesReader;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

class POS_serv {
	private final PropertiesReader pR = new PropertiesReader();
	private final static String MIN_START_DATE_STR = "2000-01-01 00:00:00";

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
							writer.write(hash +"\n");
							System.out.println(hash);
							writer.flush();
						break;
						case "READ_CHECK":
							break; //FIXME need code
						case "READ_GOODS":
							String goodsListStr;
							goodsListStr = getGoodsListStr(readedStr);
							writer.write(goodsListStr +"\n");
							System.out.println("Отправляем данные:" + goodsListStr);
							writer.flush();
							break;
						case "WRITE_USER":
							break; //FIXME need code
						case "READ_USER":
							break;//FIXME need code
						case "READ_DAY_ITOG":
							BigDecimal sum;
							sum = getSummByDate(readedStr);
							writer.write( sum + "\n");
							writer.flush();
							System.out.println(sum.toString());
							break;
						case "COMPARE_USER":
							userDto = new UserDto(readedStr);
							String strUserDb = compareUserDb(userDto).toString();
							writer.write(strUserDb + "\n");
							writer.flush();
							System.out.println(strUserDb);
							break;
					}
				}
			} catch (Exception ex) {ex.printStackTrace();}
		}
	}// закрываем вложенный класс

	private String getGoodsListStr (String readedStr){
		String[] arrayStr = readedStr.split("#");
		String goodsArrayListStr = "";
		if (arrayStr.length>2 && !arrayStr[1].isBlank() && !arrayStr[2].isBlank()){
			GoodsDao checkDao = new GoodsDaoImpl(new DataBaseManager());
			ArrayList<Goods> goodsArrayList = checkDao.findGoods(Integer.parseInt(arrayStr[1]), arrayStr[2]);

			for (Goods goods : goodsArrayList) {
				goodsArrayListStr = goodsArrayListStr + goods.toString() + "#";
			}
		}
		return goodsArrayListStr;
	}

	private BigDecimal getSummByDate(String readedStr) {
		String[] arrayStr = readedStr.split("#");
		CheckDao checkDao = new CheckDaoImpl(new DataBaseManager());

        Calendar currStartDay = new GregorianCalendar(TimeZone.getDefault());
        currStartDay.setTimeZone(TimeZone.getDefault());
        currStartDay.set(Calendar.HOUR_OF_DAY, 00);
        currStartDay.set(Calendar.MINUTE, 00);
        currStartDay.set(Calendar.SECOND, 01);
        currStartDay.set(Calendar.MILLISECOND, 00);

        Calendar currEndDay = new GregorianCalendar(TimeZone.getDefault());
        currEndDay.setTimeZone(TimeZone.getDefault());
        currEndDay.set(Calendar.HOUR_OF_DAY, 23);
        currEndDay.set(Calendar.MINUTE, 59);
        currEndDay.set(Calendar.SECOND, 59);
        currEndDay.set(Calendar.MILLISECOND, 999);

        Timestamp startDate = new Timestamp(currStartDay.getTimeInMillis());
        Timestamp endDate = new Timestamp(currEndDay.getTimeInMillis());

		ArrayList<Check> checkArrayList;
		BigDecimal sum = new BigDecimal(0);

		if (arrayStr.length>1 && !arrayStr[1].isBlank()){
			String[] dateStartArr = arrayStr[1].split("/");
			String dateStartStr = dateStartArr[2] + "-" + dateStartArr[1] + "-" + dateStartArr[0] + " 00:00:01";
			startDate = Timestamp.valueOf(dateStartStr);
		} else if (arrayStr.length>1 && arrayStr[1].isBlank()) {
			startDate = Timestamp.valueOf(MIN_START_DATE_STR);
		}

		if (arrayStr.length>2 && !arrayStr[2].isBlank()){
			String[] dateEndArr = arrayStr[2].split("/");
			String dateEndtStr = dateEndArr[2] + "-" + dateEndArr[1] + "-" + dateEndArr[0] + " 23:59:59";
			endDate = Timestamp.valueOf(dateEndtStr);
		}

		checkArrayList = checkDao.findCheckByDate(startDate,endDate);

		if (checkArrayList!=null){
			for (Check check : checkArrayList){
				sum = sum.add(check.getSum());
			}
		}
		return sum;
	}

	private String compareUserDb(UserDto userDto) {
		UserDao userDao = new UserDaoImpl(new DataBaseManager());

		try {
			String answer = userDao.compareUser(userDto).toString();
			return answer;}
		catch (RuntimeException e){
			System.out.println("Ошибка сети! \n" + e.getMessage());
			return "-1";
		}
	}

	private int writeCheckToDb(CheckDto checkDto) {
		CheckDao checkDao = new CheckDaoImpl(new DataBaseManager());

		try { return checkDao.writeCheck(checkDto);
		} catch (RuntimeException e) {
			System.out.println("Network error!!! \n" + e.getMessage());
			return 0;
		}
	}

	private boolean isHasTrue(String inputStr) {
		boolean accepted = false;
		String[] arrayInputStr = inputStr.split("#");
		if (arrayInputStr[0].equals("true")){
			accepted = true;
		}
		return accepted;
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
		} catch (Exception ex) {ex.printStackTrace();
		}
	}// закрываем go

}