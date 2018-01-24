package org.corgiking.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JsonTest {

	public static void main(String[] args) throws IOException {

		List<User> us = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			us.add(new User("user" + i, i, "sex" + i));
		}
		
		//java序列化
		long s1 = System.currentTimeMillis();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(us);
		byte[] bytes = bos.toByteArray();
		System.out.println("java序列化用时："+(System.currentTimeMillis() - s1)+"---大小："+bytes.length);
		
		//gson序列化
		long s2 = System.currentTimeMillis();
		Gson gson = new Gson();
		String json = gson.toJson(us);
//		byte[] jbytes = json.getBytes();
		System.out.println("gson序列化用时："+(System.currentTimeMillis() - s2)+"---大小：");

		//jackson
		long s3 = System.currentTimeMillis();
		ObjectMapper om = new ObjectMapper();
//		String json2 = om.writeValueAsString(us);
//		byte[] jbytes2 = json2.getBytes();
		byte[] jbytes2 = om.writeValueAsBytes(us);
		System.out.println("jackson序列化用时："+(System.currentTimeMillis() - s3)+"---大小："+jbytes2.length);
		
	}

}
class User implements Serializable{
	public User(String name, int age, String sex) {
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	private String name;
	private int age;
	private String sex;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
}