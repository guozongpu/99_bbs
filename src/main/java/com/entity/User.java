package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

/** 
* @ClassName: User 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author (黄志强)  
* @date 2016年9月7日 下午3:00:05 
* @version V1.0 
*/
@Entity
@Table(name="test_user")
public class User {
	
    // --- 基本信息
    @Id
    @Column(unique=true)
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid")
    private String uuid;
    @NotNull
    @Column(name="login_name",unique=true)
	private String loginName;// 登录名
    @NotNull
	private String password;// 密码
	private String email;// Email地址

	private int logoutNumber;//存储上次登出用户的帖子的总回复数
	
	private int nowNumber;//存储现在用户的帖子的总回复数
	
	public int getLogoutNumber() {
		return logoutNumber;
	}

	public void setLogoutNumber(int logoutNumber) {
		this.logoutNumber = logoutNumber;
	}

	public int getNowNumber() {
		return nowNumber;
	}

	public void setNowNumber(int nowNumber) {
		this.nowNumber = nowNumber;
	}

	public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
