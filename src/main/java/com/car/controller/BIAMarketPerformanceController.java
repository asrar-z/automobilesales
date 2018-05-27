package com.car.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.spec.SaleDao;
import com.car.dao.spec.VehicleDao;
import com.car.dto.SaleDto;
import com.car.entity.LineEntity;
import com.car.entity.SaleFetchEntity;
import com.car.entity.SaleListEntity;

@Controller
public class BIAMarketPerformanceController {
	
	@Autowired SaleDao saleDao;
@Autowired VehicleDao vehicledao;
	@PreAuthorize("hasAuthority('ANALYST')")
	@RequestMapping("/marketperformance")
	public String showFeedback()
	{
		return "BIAtemplates/marketperformance";
	}
	@PreAuthorize("hasAuthority('ANALYST')")
	@RequestMapping("/liverates")
	public String showlive()
	{
		return "BIAtemplates/liverates";
	}
	@PreAuthorize("hasAuthority('SALESMANAGER')")
	@RequestMapping("/profits")
	public String showprofits()
	{
		return "SMtemplates/profits";
	}
	
	@PreAuthorize("hasAuthority('ANALYST')")
	@RequestMapping("/marketperformance/model")
	@ResponseBody
	public List<LineEntity> showLine(@RequestParam String id,@RequestParam int office) throws IOException
	{
		return vehicledao.getsales(id, office);
	}
	
	@PreAuthorize("hasAuthority('SALESMANAGER')")
	@RequestMapping(value="/marketperformance/getprofit", method=RequestMethod.POST)
	@ResponseBody
	public SaleListEntity showprofit(@RequestBody SaleFetchEntity entity) throws IOException
	{
		if(entity.getOfficeid()!=0)
		{
			List<SaleDto> sales=saleDao.getSaleByOffice(entity.getOfficeid(),entity.getStart(),entity.getLength());
			int count=saleDao.getFilteredTotalCount(entity.getOfficeid());
			return new SaleListEntity(entity.getDraw(), count, count, sales);
		}
		else{
			
			List<SaleDto> sales=saleDao.getAll(entity.getStart(),entity.getLength());
			int count=saleDao.getTotalCount();
			return new SaleListEntity(entity.getDraw(), count, count, sales);
			
		}
	}
	
	
}
