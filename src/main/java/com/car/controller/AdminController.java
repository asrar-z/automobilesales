package com.car.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.spec.OfficeDao;
import com.car.dao.spec.TaxDao;
import com.car.dto.TaxDto;
import com.car.entity.RoleEntity;
import com.car.entity.RoleFetchEntity;
import com.car.entity.UserAccountCreationEntity;
import com.car.entity.UserAccountFetchEntity;
import com.car.entity.UserAccountListEntity;
import com.car.service.spec.UserRoleService;
import com.car.service.spec.UserService;
import com.google.common.base.Strings;
import com.car.entity.OfficeCreationEntity;
import com.car.entity.OfficeEntity;
import com.car.entity.OfficeFetchEntity;
import com.car.service.spec.OfficeService;





@Controller
public class AdminController {
	
	@Autowired 
	private TaxDao t;
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private OfficeDao officeDao;
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/admin")
	public String showAdmin()
	{
		return "Admintemplates/admin";
	}
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/adminoffice")
	public String showAdmino()
	{
		return "Admintemplates/office";
	}
	
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "admin/getUsers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UserAccountListEntity findUsers(@RequestBody UserAccountFetchEntity entity) {
		if (Strings.isNullOrEmpty(entity.getSearchParam())) {
			return userService.getBy(entity);
		} else {
			return userService.filter(entity);
		}
		
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "admin/roles", method = RequestMethod.POST)
	@ResponseBody
	public RoleEntity getRoles(@RequestBody RoleFetchEntity roleFetchEntity)
	{
		return userRoleService.getBy(roleFetchEntity);
	}
	
	@RequestMapping(value = "admin/addUserAccount", method = RequestMethod.POST)
	@ResponseBody
	public void addUserAccount(@RequestBody UserAccountCreationEntity userAccountCreationEntity) {
		//logger.debug("inside reg/add" + userAccountCreationEntity.getUserid()+userAccountCreationEntity.getPassword()+userAccountCreationEntity.getFirstName()+userAccountCreationEntity.getLastName()+userAccountCreationEntity.getEmail());
		
		if(userAccountCreationEntity.getRoles().contains("ADMIN"))
			userAccountCreationEntity.roleId = 1;
		else if(userAccountCreationEntity.getRoles().contains("ANALYST"))
			userAccountCreationEntity.roleId = 2;
		else if(userAccountCreationEntity.getRoles().contains("CUSTOMER"))
			userAccountCreationEntity.roleId = 3;
		else if(userAccountCreationEntity.getRoles().contains("SALESEXECUTIVE"))
			userAccountCreationEntity.roleId = 4;
		else if(userAccountCreationEntity.getRoles().contains("SALESMANAGER"))
			userAccountCreationEntity.roleId = 5;
		else if(userAccountCreationEntity.getRoles().contains("SUPPORT"))
			userAccountCreationEntity.roleId = 6;		
		else if(userAccountCreationEntity.getRoles().contains("WAREHOUSEMANAGER"))
			userAccountCreationEntity.roleId = 7;
		else
			userAccountCreationEntity.roleId=1;
		
		userService.insert(userAccountCreationEntity);
	}



@RequestMapping(value = "admin/updateUserAccount", method = RequestMethod.POST)
@ResponseBody
public void addEditAccount(@RequestBody UserAccountCreationEntity userAccountCreationEntity) {
	//logger.debug("inside reg/add" + userAccountCreationEntity.getUserid()+userAccountCreationEntity.getPassword()+userAccountCreationEntity.getFirstName()+userAccountCreationEntity.getLastName()+userAccountCreationEntity.getEmail());
	
	if(userAccountCreationEntity.getRoles().contains("ADMIN"))
		userAccountCreationEntity.roleId = 1;
	else if(userAccountCreationEntity.getRoles().contains("ANALYST"))
		userAccountCreationEntity.roleId = 2;
	else if(userAccountCreationEntity.getRoles().contains("CUSTOMER"))
		userAccountCreationEntity.roleId = 3;
	else if(userAccountCreationEntity.getRoles().contains("SALESEXECUTIVE"))
		userAccountCreationEntity.roleId = 4;
	else if(userAccountCreationEntity.getRoles().contains("SALESMANAGER"))
		userAccountCreationEntity.roleId = 5;
	else if(userAccountCreationEntity.getRoles().contains("SUPPORT"))
		userAccountCreationEntity.roleId = 6;		
	else if(userAccountCreationEntity.getRoles().contains("WAREHOUSEMANAGER"))
		userAccountCreationEntity.roleId = 7;
	else
		userAccountCreationEntity.roleId=1;
	
	userService.update(userAccountCreationEntity);
}


@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "admin/deleteUserAccount", method = RequestMethod.DELETE)
@ResponseBody
public void delete(@RequestParam String id)
{
	userService.deleteBy(id);
}



@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "admin/deleteOffice", method = RequestMethod.DELETE)
@ResponseBody
public boolean deleteO(@RequestParam int id)
{
	return officeService.deleteBy(id);
}


@PreAuthorize("hasAnyAuthority('ADMIN','SALESMANAGER','ANALYST','CUSTOMER','WAREHOUSEMANAGER')")
@RequestMapping(value = "admin/offices", method = RequestMethod.POST)
@ResponseBody
public OfficeEntity getOffices(
		@RequestBody OfficeFetchEntity officeFetchEntity) {
	return officeService.getBy(officeFetchEntity);
}

@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "admin/addOffice", method = RequestMethod.POST)
@ResponseBody
public void addOffice(@RequestBody OfficeCreationEntity office) throws IOException {
	officeService.insert(office.getName());
	int id=officeDao.getId(office.getName());
	t.insert(new TaxDto(id,7));
}

}


