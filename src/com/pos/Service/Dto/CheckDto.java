package com.pos.Service.Dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.Data.Dao.CheckDao;
import com.pos.Data.Dao.impl.CheckDaoImpl;
import com.pos.Data.Entities.Check;
import com.pos.Data.Entities.Goods;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.pos.Data.Entities.Pos;
import com.pos.Data.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckDto {

    private int id;
    private Pos pos;
    private User user;
    private BigDecimal sum;
    //    private Date date;
    private Timestamp dateStamp;
    private String checkCode;
    private List<Goods> goodsDtoList;
    private Boolean deleted;
    private static final String TAG = "logsCheckDto";

    public CheckDto (Check check){
        this.id = check.getCheckId();
        this.pos = check.getPos();
        this.user = check.getUser();
        this.sum = check.getSum();
        this.dateStamp = check.getDateStamp();
//        this.checkCode = check.getCheckCode();
        this.goodsDtoList = check.getGoodsArrayList();
        this.deleted = check.getDeleted();
    }

    public Check getEntity() throws NullPointerException{
        return new Check(this.id, this.pos, this.user, this.sum, this.dateStamp,
                this.goodsDtoList, this.deleted);
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
        CheckDao checkDao = new CheckDaoImpl();
        checkArrayList = checkDao.findCheckByDate(startDate, endDate, posId);
        return checkArrayList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckDto checkDto1 = (CheckDto) o;
        return id == checkDto1.id && pos == checkDto1.pos && user == checkDto1.user
                && goodsDtoList.equals(checkDto1.goodsDtoList) && dateStamp.equals(checkDto1.dateStamp);
    }

    @Override
    public int hashCode() {
        int hash;
        String str = pos.getPosId() + user.getUserId() + sum.toString() + dateStamp.toString() + deleted.toString();
        hash = Objects.hash(str);
        return hash;
    }
}
