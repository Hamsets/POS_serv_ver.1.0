package com.pos.Service.Dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.Data.Entities.Goods;
import com.pos.Data.Entities.Pos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDto {

    private int id;
    private int forPos;
    private String imageName;
    private String publicName;
    private String pathImage;
    private BigDecimal prize = new BigDecimal(0);
    private Boolean isActive;
    private List<Pos> posIds;
    private Boolean deleted;

    public GoodsDto (Goods goods){
        this.id = goods.getGoodsId();
        this.forPos = goods.getForPos();
        this.imageName = goods.getImageName();
        this.publicName = goods.getPublicName();
        this.pathImage = goods.getPathImage();
        this.prize = goods.getPrize();
        this.isActive = goods.getIsActive();
        this.posIds = goods.getPosIds();
        this.deleted = goods.getDeleted();
    }

    public static String convertToJson (Goods goods) throws NullPointerException{
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
            System.out.println("Ошибка сериализации Goods в JSON.");
            e.printStackTrace();
        }
        return s;
    };

    public static Goods convertFromJson (String s){
        Goods goods = new Goods ();
        try {
            StringReader reader = new StringReader(s);
            ObjectMapper mapper = new ObjectMapper();
            goods = mapper.readValue(reader, Goods.class);
        }catch (IOException e){
            System.out.println("Ошибка десериализации Goods из JSON.");
            e.printStackTrace();
        }
        return goods;
    }





    public Goods getEntity (){
        return new Goods(this.id, this.forPos, this.imageName, this.publicName, this.pathImage, this.prize,
                this.isActive, this.posIds, this.deleted );
    }
}
