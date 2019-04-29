package com.marksduarte.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	
	public static String decodeParam(final String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {			
			return "";
		}
	}

	public static List<Integer> decodeIntList(String str) {		
		return Arrays.asList(str.split(","))
				.stream().map(x -> Integer.parseInt(x))
				.collect(Collectors.toList());
	}
}
