package cn.projs.myweb.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * @date 2022/9/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "cars")
public class Car implements Serializable {
    @Id
    private String id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String color;

    @Field(type = FieldType.Keyword)
    private String make;

    @Field(type = FieldType.Keyword)
    private String price;

    @Field(type = FieldType.Keyword)
    private String sold;
}
