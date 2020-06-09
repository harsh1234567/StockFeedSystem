package com.filestockstorageservice;

public class Test {

	public static void main(String[] args) {
	
     String str="2000.00";
    str=str.replaceAll("\\.0*$", "");
    System.out.println(str);

	}

}
