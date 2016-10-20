package in.manrajsingh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.manrajsingh.dao.RoleDaoInterface;
import in.manrajsingh.entities.Role;

@Service
public class RoleServiceImpl implements RoleServiceInterface {
	
	@Autowired
	RoleDaoInterface roleDaoImpl;
	
	@Override
	public List<Role> getAllRoles() {
		
		return roleDaoImpl.getAll();
	}

}
