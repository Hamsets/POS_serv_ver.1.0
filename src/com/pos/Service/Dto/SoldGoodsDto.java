package com.pos.Service.Dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.Data.Entities.Goods;
import com.pos.Data.Entities.SoldGoods;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class SoldGoodsDto {
    public static String convertToJson (SoldGoods goods) throws NullPointerException{
        String s = "";
        try {
            //писать результат сериализации будем во Writer(StringWriter)
            StringWriter writer = new StringWriter();

            //это объект Jackson, который выполняет сериализацию
            ObjectMapper mapper = new ObjectMapper();

            // сама сериализация: 1-куда, 2-что
            mapper.writeValue(writer, goods);

            //преобразовываем все записанное во StringWriter в строку
            s = writer.toString();
        } catch (IOException e){
            System.out.println("Ошибка сериализации SoldGoods в JSON.");
            e.printStackTrace();
        }
        return s;
    };

    public static SoldGoods convertFromJson (String s){
        SoldGoods goods = new SoldGoods ();
        try {
            StringReader reader = new StringReader(s);
            ObjectMapper mapper = new ObjectMapper();
            goods = mapper.readValue(reader, SoldGoods.class);
        }catch (IOException e){
            System.out.println("Ошибка десериализации SoldGoods из JSON.");
            e.printStackTrace();
        }
        return goods;
    }
}
