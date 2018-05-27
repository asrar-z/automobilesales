package com.car.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.dao.spec.MakeDao;
import com.car.dto.MakeDto;
import com.car.entity.MakeEntity;
import com.car.exception.ServiceException;
import com.car.service.spec.MakeService;

@Service
public class MakeServiceImpl implements MakeService{

	
	@Autowired
	MakeDao makeDao;
	
	@Override
	public String getBy(int id) throws ServiceException {
				MakeDto make=null;
				try{
					make=makeDao.getBy(id);
					
				}catch (IOException e) {
					throw new ServiceException("Cannot find office for office Id: "
							+ id, e);
					}
				String name=null;
				if(make!=null){
					name=make.getName();
					}
				return name;
	}
	
	
	
	
	
	@Override
	public MakeEntity getAll() throws ServiceException {
		List<MakeDto> makes=null;
		try
		{
			makes=makeDao.getAll();
			Collections.sort(makes,new LexicographicComparator());
		}catch(IOException e)
		{
			throw new ServiceException("Couldn't get makes",e);
		}
		return new MakeEntity(makes);
	}

	@Override
	public void insert(String name) throws ServiceException {
		try
		{
			makeDao.insert(name);
		}catch(IOException e)
		{
			throw new ServiceException("Couldn't insert make",e);
		}
		
	}

	@Override
	public void deleteBy(int id) throws ServiceException {
		try
		{
			 makeDao.deleteBy(id);
		}catch(IOException e)
		{
			throw new ServiceException("Couldn't insert make",e);
		}
	}

}
class LexicographicComparator implements Comparator<MakeDto> {

	@Override
	public int compare(MakeDto arg0, MakeDto arg1) {
		// TODO Auto-generated method stub
		return arg0.getName().compareToIgnoreCase(arg1.getName());
	}
}