package service;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import entity.Admin;

@Repository
public interface IAdminService extends IBaseService<Admin>{
	
	public Admin checkLogin(String username) throws IOException;
	
}
