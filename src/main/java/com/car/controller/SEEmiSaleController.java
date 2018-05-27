package com.car.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.spec.CustomerDao;
import com.car.dao.spec.EMIDao;
import com.car.dao.spec.OfficeDao;
import com.car.dao.spec.SaleDao;
import com.car.dao.spec.VcostDao;
import com.car.dao.spec.VehicleDao;
import com.car.dto.EMIDto;
import com.car.dto.OfficeDto;
import com.car.dto.SaleDto;
import com.car.entity.CustomerAccountEntity;
import com.car.entity.MakeEntity;
import com.car.entity.OfficeEntity;
import com.car.entity.ScheduleEntity;
import com.car.entity.ScheduleListEntity;
import com.car.entity.VehicleEntity;
import com.car.exception.ServiceException;
import com.car.service.spec.CustomerService;
import com.car.service.spec.MakeService;
import com.car.service.spec.VehicleService;
@Data
@AllArgsConstructor
@NoArgsConstructor
class Response{
	private CustomerAccountEntity customer;
	private boolean result;
}
@Controller
public class SEEmiSaleController {
	
	@Autowired
	CustomerService customerService;
	@Autowired MakeService makeService;
	@Autowired VehicleService vehicleService;
	@Autowired OfficeDao officeDao;
	@Autowired SaleDao saleDao;
	@Autowired EMIDao emiDao;
	@Autowired VehicleDao vehicleDao;
	@Autowired VcostDao vcost;
	@PreAuthorize("hasAuthority('SALESEXECUTIVE')")
	@RequestMapping(value ="/emisale")
	public String showTodaysappointments()
	{
		return "SEtemplates/POSemi";
	}
	
	@PreAuthorize("hasAuthority('SALESEXECUTIVE')")
	@RequestMapping(value ="/emisale/customers")
	@ResponseBody
	public Response cus(@RequestParam String id)
	{
		try{
		System.out.println("asrar"+id);
		CustomerAccountEntity customer=customerService.getBy(id);
		if(customer.getEmi()=="yes") throw new Exception();
		return new Response(customer,true);
		}
		catch(Exception e)
		{
			return new Response(new CustomerAccountEntity(),false);
		}
	}
	
	@PreAuthorize("hasAnyAuthority('SALESEXECUTIVE','WAREHOUSEMANAGER','ANALYST')")
	@RequestMapping(value ="/emisale/makes")
	@ResponseBody
	public MakeEntity makes()
	{
		return makeService.getAll();

	}
	
	@PreAuthorize("hasAnyAuthority('SALESEXECUTIVE','WAREHOUSEMANAGER','ANALYST')")
	@RequestMapping(value ="/emisale/models")
	@ResponseBody
	public VehicleEntity models(@RequestParam String id,@RequestParam int office)
	{
				Calendar now = Calendar.getInstance();
		int y = now.get(Calendar.YEAR);
		String year = String.valueOf(y);
		return vehicleService.getAll(Integer.parseInt((id)),office,year);

	}

	@PreAuthorize("hasAnyAuthority('SALESEXECUTIVE')")
	@RequestMapping(value ="/emisale/getcost")
	@ResponseBody
	public String cost(@RequestParam String id,@RequestParam String office) throws IOException
	{
		
		return vcost.getCost(id,office);

	}
	
			
	@PreAuthorize("hasAuthority('SALESEXECUTIVE')")
	@RequestMapping(value ="/emisale/offices")
	@ResponseBody
	public OfficeEntity office() throws IOException
	{
		
		List<OfficeDto> offices=officeDao.get();
		return new OfficeEntity(offices);

	}
	
	@PreAuthorize("hasAuthority('SALESEXECUTIVE')")
	@RequestMapping(value ="/emisale/sale", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void recordsale(@RequestBody SaleDto sale) throws IOException
	{
		System.out.println("debug" + sale);
		Calendar now = Calendar.getInstance();
		int y = now.get(Calendar.YEAR);
		String year = String.valueOf(y);
		sale.setYear(year);
			saleDao.insert(sale);

	}
	@PreAuthorize("hasAuthority('SALESEXECUTIVE')")
	@RequestMapping(value ="/emisale/emirecord", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void emidsale(@RequestBody EMIDto sale) throws IOException
	{
		System.out.println("debug" + sale);
			emiDao.insert(sale);

	}
	@PreAuthorize("hasAuthority('SALESEXECUTIVE')")
	@RequestMapping(value = "emisale/vehiclesold")
	@ResponseBody
	public void sold(@RequestParam String id,@RequestParam int office) throws IOException {
		System.out.println("debug"+id);
			vehicleDao.sold(id,office);
	}
}
