package com.joyance.springsecurity.authorize;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.joyance.springsecurity.mapper.AuthorityMapper;
import com.joyance.springsecurity.mapper.UserMapper;
import com.joyance.springsecurity.persistence.Authority;
import com.joyance.springsecurity.persistence.User;


@Service
public class SystemUserSerivce implements UserDetailsService {
	
//    @Autowired
//    private UserMapper userMapper;
//    
//    @Autowired
//    private AuthorityMapper authorityMapper;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User myUser =userMapper.selectUserByUsername(username);
//        List<Authority> authorityList = authorityMapper.selectByRoleId(myUser.getRole_id());
		 User myUser = new User();
		 myUser.setUsername("joyance");
		 myUser.setPassword("123456");
		 List<Authority> authorityList = new ArrayList<Authority>();
		 Authority authority1 = new Authority();
		 authority1.setCode("ROLE_USER");
		 authority1.setUrl("/admin/authority/show");
		 authorityList.add(authority1);
		 Authority authority2 = new Authority();
		 authority2.setCode("ADMIN");
		 authority2.setUrl("/admin/authority/save");
		 authorityList.add(authority2);
		
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for(Authority authority:authorityList){
            String permissionCode = authority.getCode();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permissionCode);
            grantedAuthorities.add(grantedAuthority);
        }
        //这里provider需要的是springSecurity的user对象
        return new org.springframework.security.core.userdetails.User(username,myUser.getPassword(),grantedAuthorities);
	}
    
    
}

