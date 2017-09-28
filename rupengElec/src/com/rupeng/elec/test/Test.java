package com.rupeng.elec.test;

import java.io.Serializable;

public class Test {

	public static void main(String[] args) {
		Person person = new Person();
		Animal dog = new Animal();
		dog.setName("旺财");
		person.setDog(dog);

		person.say();
	}

}

class Person implements Serializable {
	private Animal dog;

	public Animal getDog() {
		return dog;
	}

	public void setDog(Animal dog) {
		this.dog = dog;
	}

	public void say() {
		System.out.println("我的狗的名字是：" + dog.getName());
	}
}

class Animal {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}