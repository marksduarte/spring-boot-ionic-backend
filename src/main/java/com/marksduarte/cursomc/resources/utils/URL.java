package com.marksduarte.cursomc.resources.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

	public static List<Integer> decodeIntList(String str) {		
		return Arrays.asList(str.split(","))
				.stream().map(x -> Integer.parseInt(x))
				.collect(Collectors.toList());
	}
}
