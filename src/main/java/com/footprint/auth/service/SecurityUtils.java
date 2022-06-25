package com.footprint.auth.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * Created by ShinD on 2022/05/31.
 */
public class SecurityUtils {
	public static Long getLoginMemberId(){
		return Long.parseLong(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
	};
}
