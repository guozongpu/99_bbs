package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

/** 
* @ClassName: Forum 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author (黄志强)  
* @date 2016年9月7日 下午2:58:16 
* @version V1.0 
*/
@Entity
@Table
public class Forum {
    
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid")
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private int floor;
        
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getfloor() {
        return floor;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }
}
