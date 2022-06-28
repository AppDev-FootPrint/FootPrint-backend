package com.footprint.auth.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * Created by ShinD on 2022/05/31.
 */
@Service
public class SecurityAuthService implements AuthService{

	@Override
	public Long getLoginMemberId(){
		return Long.parseLong(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
	};
}
