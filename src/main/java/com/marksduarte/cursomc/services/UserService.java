package com.marksduarte.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.marksduarte.cursomc.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		try {
//			/**
//			 * Caso não tenha usuário logado retorna nulo
//			 */
//			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		} catch (Exception e) {
//			return null;
//		}
	}
}
