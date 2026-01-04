package com.ranjith.user.util;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExampleCodes {

	public static void main(String[] args) {
		
		List<String> strList = Arrays.asList("AAAAA","BB","CC","AA","Ran",null,"Shar");
		//List<String> strList1 = List.of("AA","BB","CC","AA","Ran",null,"Shar");
		for (String string : strList) {
			System.out.println(string);
		}
		String s = "Hello";
		Map<Character,Long> map2 = s.chars().mapToObj(c -> (char) c)
			    .collect(Collectors.groupingBy(e -> e ,
			    		LinkedHashMap::new,      // Use LinkedHashMap to preserve order
                        Collectors.counting()));
		
		Map<String,Long> map = strList.stream()
			    .collect(Collectors.groupingBy(e -> e == null ? "null" : e,
                        Collectors.counting()));

		map.entrySet().forEach(System.out::println);
		
		map2.forEach((k, v) -> System.out.println(k + " = " + v));
		
	}

}
