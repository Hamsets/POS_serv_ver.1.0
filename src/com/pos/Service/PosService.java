package com.pos.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.Data.Connection.DataBaseManager;
import com.pos.Data.Dao.CheckDao;
import com.pos.Data.Dao.GoodsDao;
import com.pos.Data.Dao.UserDao;
import com.pos.Data.Dao.impl.CheckDaoImpl;
import com.pos.Data.Dao.impl.GoodsDaoImpl;
import com.pos.Data.Dao.impl.UserDaoImpl;
import com.pos.Data.Entities.Check;
import com.pos.Data.Entities.Goods;
import com.pos.Data.Entities.Pos;
import com.pos.Data.Entities.User;
import com.pos.Service.Dto.CheckDto;
import com.pos.Service.Dto.GoodsDto;
import com.pos.Service.Dto.UserDto;
import com.pos.Utils.PropertiesReader;

import javax.transaction.Transactional;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.sql.Timestamp;
import java.util.*;


class PosService {
	private final PropertiesReader pR = new PropertiesReader();
	private final static String MIN_START_DATE_STR = "2000-01-01 00:00:00";
	private static final String TAG = "logsPosServ";

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
						case "WRITE_NEW_CHECK":
							int hash = writeCheck(arrayReadedStr[1]);
							writer.write(hash +"\n");
							System.out.println(hash);
							writer.flush();
						break;
						case "READ_CHECK_BY_DATE":
							String checksFullStr = getChecksByDate(arrayReadedStr[1],
									Integer.parseInt(arrayReadedStr[2]));
							writer.write(checksFullStr + "\n");
							System.out.println("Отправляем чеки по дате с сервера:" + checksFullStr);
							writer.flush();
							break;
						case "DELETE_CHECK_BY_ID":
							Boolean bool;
							bool = deleteCheck(readedStr);
							System.out.println("Отправка клиенту результат удаления в базе чека: " + bool.toString());
							writer.write(bool.toString() + "\n");
							writer.flush();
							System.out.println("Чек(и) удален(ы).");
							break;
						case "READ_GOODS": //TODO проверить клиент
							String goodsFullStr = getGoodsFullStr(Integer.parseInt(arrayReadedStr[1]));
							writer.write(goodsFullStr +"\n");
							System.out.println("Отправляем товары с сервера:" + goodsFullStr);
							writer.flush();
							break;
						case "WRITE_NEW_USER":
							break; //FIXME need code
						case "READ_USER":
							break;//FIXME need code
						case "READ_DAY_ITOG":
							BigDecimal sum;
							sum = getSummByDate(arrayReadedStr[1], arrayReadedStr[2],
									Integer.parseInt(arrayReadedStr[3]));
							writer.write( sum + "\n");
							writer.flush();
							System.out.println(sum.toString());
							break;
						case "COMPARE_USER":
							String strUserDb = compareUserDb(arrayReadedStr[1]);
							writer.write(strUserDb + "\n");
							writer.flush();
							System.out.println(strUserDb);
							break;
					}
				}
			} catch (Exception ex) {ex.printStackTrace();}
		}

	}// закрываем вложенный класс

	private BigDecimal getSummByDate(String startDateStr, String endDateStr, int posId) {

		CheckDao checkDao = new CheckDaoImpl();

        Calendar currStartDay = new GregorianCalendar(TimeZone.getDefault());
        currStartDay.setTimeZone(TimeZone.getDefault());
        currStartDay.set(Calendar.HOUR_OF_DAY, 0);
        currStartDay.set(Calendar.MINUTE, 0);
        currStartDay.set(Calendar.SECOND, 0);
        currStartDay.set(Calendar.MILLISECOND, 1);

        Calendar currEndDay = new GregorianCalendar(TimeZone.getDefault());
        currEndDay.setTimeZone(TimeZone.getDefault());
        currEndDay.set(Calendar.HOUR_OF_DAY, 23);
        currEndDay.set(Calendar.MINUTE, 59);
        currEndDay.set(Calendar.SECOND, 59);
        currEndDay.set(Calendar.MILLISECOND, 999);

        Timestamp startDate;
//				= new Timestamp(currStartDay.getTimeInMillis());
        Timestamp endDate = new Timestamp(currEndDay.getTimeInMillis());

		ArrayList<Check> checkArrayList;
		BigDecimal sum = new BigDecimal(0);

		if (!startDateStr.equals("null")){
			startDate = Timestamp.valueOf(getStrForStartTimestamp(startDateStr));
		} else {
			startDate = new Timestamp(currStartDay.getTimeInMillis()); //Если не указано время, делаем с текущего времени
//			startDate = Timestamp.valueOf(MIN_START_DATE_STR);//Если не указано время конца выборки делаем со стартового
		}

		if (!endDateStr.equals("null")){
			endDate = Timestamp.valueOf(getStrForEndTimestamp(endDateStr));
		} else {
			endDate = new Timestamp(currEndDay.getTimeInMillis()); //Если не указано время, делаем с текущего времени
//			endDate = startDate; //Если не указано время конца выборки делаем со стартового
		}

		checkArrayList = checkDao.findCheckByDate(startDate,endDate, posId);

		if (checkArrayList!=null && !checkArrayList.isEmpty()){
			for (Check check : checkArrayList){
				sum = sum.add(check.getSum());
			}
		}
		return sum;
	}

	//универсальный метод получения строки для Timestamp для начала выборки чеков
	private String getStrForStartTimestamp (String s){
		String[] dateStartArr = s.split("/");
		return dateStartArr[2] + "-" + dateStartArr[1] + "-" + dateStartArr[0] + " 00:00:00.001";
	}

	//универсальный метод получения строки для Timestamp для конца выборки чеков
	private String getStrForEndTimestamp (String s){
		String[] dateEndArr = s.split("/");
		return dateEndArr[2] + "-" + dateEndArr[1] + "-" + dateEndArr[0] + " 23:59:59.999";
	}

	private String compareUserDb(String readedStr) {
		String s = "-1";
		try {

		//Получаем десериализованного User и UserDto
			User user = UserDto.convertFromJson(readedStr);

			UserDto userDto = new UserDto(user);

			//Сравниваем логин/пароль с БД и возвращаем восстановленного User со своим ID
			//TODO внести в Новую таблицу (или в новый объект) сессию (с ограниченным сроком жизни)
			UserDto trueUserDto = UserDto.getTrueUserByLogin(userDto);
            if (trueUserDto != null) {
                s = UserDto.convertToJson(trueUserDto.getEntity());
            }
        } catch (NullPointerException e){
			System.out.println("Ошибка авторизации! \n" + e.getMessage());
		} catch (RuntimeException e) {
			System.out.println("Ошибка сети! \n" + e.getMessage());
		}
		return s;
	}

	private String getGoodsFullStr(int id){

		String goodsFullStr = "";
		GoodsDao goodsDao = new GoodsDaoImpl();

		try {
			if (id != -1) {
				Goods goods = goodsDao.findGoodsById(id);
				goodsFullStr = GoodsDto.convertToJson(goods);
			} else if (id == -1) {
				List<Goods> list;
				list = goodsDao.findAllGoods();
				for (Goods goods : list) {
					goodsFullStr = goodsFullStr + GoodsDto.convertToJson(goods) + "#";
				}
			}
		} catch (Exception e){
			System.out.println("Ошибка десериализации Goods из JSON.");
			e.printStackTrace();
		}

		return goodsFullStr;
	}

	private String getChecksByDate (String strDate, int pos){

		ArrayList<Check> checkArrayList;
		String checksArrStr = "";

			//Получаем весь перечень найденных по дате чеков
		if (!strDate.isBlank()){
			try{
				checkArrayList = CheckDto.getCheckByDatePos(Timestamp.valueOf(getStrForStartTimestamp(strDate)),
						Timestamp.valueOf(getStrForEndTimestamp(strDate)), pos);
				for (Check check : checkArrayList) {
					checksArrStr = checksArrStr + CheckDto.convertToJson(check)+"#";
				}
			} catch (NullPointerException e){
				System.out.println("Ошибка создания Check! \n" + e.getMessage());
			} catch (RuntimeException e) {
				System.out.println("Ошибка сети! \n" + e.getMessage());
			}
		}
		return checksArrStr;
	}

	private Boolean deleteCheck (String readedStr){
		String[] arrayReadedStr = readedStr.split("#");

		Boolean deleted = false;
		if (arrayReadedStr.length>1 && !arrayReadedStr[1].isBlank()){
			try{
				CheckDao checkDao = new CheckDaoImpl();

				for (int i = 1; i < arrayReadedStr.length; i++) {
					deleted = checkDao.deleteById(Integer.parseInt(arrayReadedStr[i]));
				}

				} catch (NullPointerException e){
				System.out.println("Ошибка удаления Check! \n" + e.getMessage());
			} catch (RuntimeException e) {
				System.out.println("Ошибка сети! \n" + e.getMessage());
			}
		}
		return deleted;
	}

	private int writeCheck(String readedStr) {
		boolean created = false;
		int hash = 0;
		try {

			//Получаем десериализированный Check и CheckDto
			Check check = CheckDto.convertFromJson(readedStr);
//			long currTimeZone = Long.parseLong(pR.getMapProperties().get("timeZoneMilliSec"));
//			long timeOfCheck = check.getDateStamp().getTime() - currTimeZone;
//			Timestamp timestamp = new Timestamp(timeOfCheck);
//			check.setDateStamp(timestamp);
			CheckDto checkDto = new CheckDto (check);

			//Вносим в БД, получаем хэш
			created = checkDto.writeCheck();
			if (created){
				hash = countHash(check);
			}

			} catch (NullPointerException e){
			System.out.println("Ошибка создания Check! \n" + e.getMessage());
			} catch (RuntimeException e) {
				System.out.println("Ошибка сети! \n" + e.getMessage());
			}
		return hash;
	}

	private boolean isHasTrue(String inputStr) {
		boolean accepted = false;
		String[] arrayInputStr = inputStr.split("#");
		if (arrayInputStr[0].equals("true")){
			accepted = true;
		}
		return accepted;
	}
	public int countHash(Check check) {
		int hash;
		long currTimeZone = Long.parseLong(pR.getMapProperties().get("timeZoneMilliSec"));
		long timeOfCheck = check.getDateStamp().getTime() - currTimeZone;
		Timestamp timestamp = new Timestamp(timeOfCheck);

		String str = check.getPos().getPosId() + check.getUser().getUserId() + check.getSum().toString() +
				timestamp + check.getDeleted().toString();
		hash = Objects.hash(str);
		return hash;
	}

//	private void updateAcceptedCheckToDb(String inputStr) {
//		CheckDao checkDao = new CheckDaoImpl(new DataBaseManager());
//
//		try { checkDao.updateCheckAccepted(inputStr);
//		} catch (RuntimeException e) {
//			System.out.println("Network error!!! \n" + e.getMessage());
//		}
//	}

//	//Конвертируем Object в JSON
//	private String objToJson (Object o){
//
//		String resStr = "";
//
//		//Пишем результат сериализации в writer
//		StringWriter writer = new StringWriter();
//
//		//это объект Jackson, который выполняет сериализацию
//		ObjectMapper mapper = new ObjectMapper();
//
//		//сериализация
//        try {
//            mapper.writeValue(writer, o);
//			resStr = writer.toString();
//        } catch (IOException e) {
//			System.out.println("Ошибка создания JSON.");
//            e.printStackTrace();
//        }
//		return resStr;
//    }

	public static void main (String [] args) {
		new PosService().go();
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