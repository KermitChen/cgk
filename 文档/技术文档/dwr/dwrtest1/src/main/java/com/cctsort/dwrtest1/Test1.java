package com.cctsort.dwrtest1;

import java.util.ArrayList;
import java.util.List;

import com.cctsort.dwrtest1.entities.People;

public class Test1 {
	public void method() {
		System.out.println("==method==无参数及返回值");
	}
	
	public String method1() {
		System.out.println("==method1==");
		return "==method1==";
	}
	
	public String method2(String msg) {
		System.out.println("==method2==" + msg);
		return "==method2==" + msg;
	}
	
	public String method3() {
		System.out.println("==method3==");
		return "==method3==";
	}
	
	public People method4() {
		System.out.println("==method4==");
		People people = new People();
		people.setId(1);
		people.setName("张三");
		return people;
	}
	
	public String method5() {
		System.out.println("==method5==");
		return "==method5==";
	}
	
	public String method6(People people) {
		System.out.println(people.getId() + "==method6==" + people.getName());
		return people.getId() + "==method6==" + people.getName();
	}
	
	public List<People> method7() {
		System.out.println("==method7==");
		List<People> list = new ArrayList<People>(); 
		People people = new People();
		people.setId(10);
		people.setName("王武");
		list.add(people);
		
		people = new People();
		people.setId(11);
		people.setName("陈七");
		list.add(people);
		return list;
	}
}
