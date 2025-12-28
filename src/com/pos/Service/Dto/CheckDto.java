package com.pos.Service.Dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.Data.Dao.CheckDao;
import com.pos.Data.Dao.impl.CheckDaoImpl;
import com.pos.Data.Entities.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckDto {

    private int checkId;
    private Pos pos;
    private User user;
    private BigDecimal sum;
    private Timestamp dateStamp;
    private List<SoldGoods> soldGoodsList;
    private String listOfSoldGoodsStr;
    private Boolean deleted;
    private static final String TAG = "logsCheckDto";

    public CheckDto (Check check){
        this.checkId = check.getCheckId();
        this.pos = check.getPos();
        this.user = check.getUser();
        this.sum = check.getSum();
        this.dateStamp = check.getDateStamp();
        this.soldGoodsList = check.getSoldGoodsList();//При создании из чека - в чеке нет стринги товаров
        this.listOfSoldGoodsStr = convListOfSoldGoodsTosStr(soldGoodsList); //Генерируем стрингу товаров из списка товаров
        this.deleted = check.getDeleted();
    }

    public Check getEntity() throws NullPointerException{
        return new Check(this.checkId, this.pos, this.user, this.sum, this.dateStamp,
                this.soldGoodsList, this.listOfSoldGoodsStr, this.deleted);
    }

    //Получение в чеке стринги проданных товаров
    //Метод будет работатть только если стринга списка товаров пуста
    private String convListOfSoldGoodsTosStr(List<SoldGoods> list){
        String s ="";
        if (listOfSoldGoodsStr == null || listOfSoldGoodsStr.isEmpty()){
            for (SoldGoods soldGoods: list){
                s = s + SoldGoodsDto.convertToJson(soldGoods) + "<*>";
            }
        }
        return s;
    }

    //Получение в чеке восстановленного списка проданных товаров
    public static List<SoldGoods> convStrToListOfSoldGoods(String s){
        List<SoldGoods> list = new ArrayList<>();

        String[] arr =  s.split("<*>");

        for (String str : arr){
            SoldGoods goods = new SoldGoods();
            goods = SoldGoodsDto.convertFromJson(str);
            list.add(goods);
        }
        return list;
    }

    public static String convertToJson (Check check) throws NullPointerException{
        String s = "";
        try {
            //писать результат сериализации будем во Writer(StringWriter)
            StringWriter writer = new StringWriter();

            //это объект Jackson, который выполняет сериализацию
            ObjectMapper mapper = new ObjectMapper();

            // сама сериализация: 1-куда, 2-что
            mapper.writeValue(writer, check);

            //преобразовываем все записанное во StringWriter в строку
            s = writer.toString();
        } catch (IOException e){
            System.out.println("Ошибка сериализации Check в JSON.");
            e.printStackTrace();
        }
        return s;
    };

    public static Check convertFromJson (String s){
        Check check = new Check ();
        try {
            StringReader reader = new StringReader(s);
            ObjectMapper mapper = new ObjectMapper();
            check = mapper.readValue(reader, Check.class);
        }catch (IOException e){
            System.out.println("Ошибка десериализации Check из JSON.");
            e.printStackTrace();
        }
        return check;
    }

    public boolean writeCheck(){
        CheckDao checkDao = new CheckDaoImpl();
        Check check = this.getEntity();
        return checkDao.createCheck(check);
    }

    public static ArrayList<Check> getCheckByDatePos (Timestamp startDate, Timestamp endDate, int posId){
        ArrayList<Check> checkArrayList;
        ArrayList<Check> fullChrckArrayList = new ArrayList<>();
        CheckDao checkDao = new CheckDaoImpl();

        //Получаем из базы чек с пустым списком товаров
        checkArrayList = checkDao.findCheckByDate(startDate, endDate, posId);

        //Создаем новый список чеков с восстановленным списком товаров
        for (Check check: checkArrayList){
            check.setSoldGoodsList(CheckDto.convStrToListOfSoldGoods(check.getListOfSoldGoodsStr()));
            fullChrckArrayList.add(check);
        }

        return fullChrckArrayList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckDto checkDto1 = (CheckDto) o;
        return checkId == checkDto1.checkId && pos == checkDto1.pos && user == checkDto1.user
                && soldGoodsList.equals(checkDto1.soldGoodsList) && dateStamp.equals(checkDto1.dateStamp);
    }

    @Override
    public int hashCode() {
        int hash;
        String str = pos.getPosId() + user.getUserId() + sum.toString() + dateStamp.toString() + deleted.toString();
        hash = Objects.hash(str);
        return hash;
    }
}
