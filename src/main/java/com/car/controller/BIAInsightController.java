package com.car.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.impl.ChennaiDao;
import com.car.dao.impl.SingaporeDao;
import com.car.dao.spec.MakeDao;
import com.car.dao.spec.SaleDao;
import com.car.dao.spec.TaxDao;
import com.car.dao.spec.VehicleDao;
import com.car.dto.MakeDto;
import com.car.dto.Sdata;
import com.car.dto.VehicleDto;
import com.car.entity.SuggestionsEntity;
import com.car.entity.VehicleEntity;
import com.car.service.spec.VehicleService;



@Data
@AllArgsConstructor
@NoArgsConstructor
class Suggest
{
	List<SuggestionsEntity> budget;
	List<SuggestionsEntity> mid;
	List<SuggestionsEntity> high;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Suggestions
{
	List<Popover> increase;
	List<Popover> order;
	List<Popover> remove;
	String j;
}
@Controller
public class BIAInsightController {
	 public static String createJsonString(HashMap jsonMap) throws IOException {
		  System.out.println("Map:"+jsonMap);
		  Writer writer = new StringWriter();
		  JsonGenerator jsonGenerator = new JsonFactory().
		        createJsonGenerator(writer);
		  ObjectMapper mapper = new ObjectMapper();
		  mapper.writeValue(jsonGenerator, jsonMap);
		  jsonGenerator.close();
		  System.out.println(writer.toString());
		  return writer.toString();
		 }
	
	
	
	
	
	
	@Autowired VehicleService vehicleService;

	@Autowired SingaporeDao ss;
	@Autowired ChennaiDao cc;
	@Autowired SaleDao sale;
	@Autowired VehicleDao v;
	@Autowired TaxDao t;
	@Autowired
	MakeDao make;
	@PreAuthorize("hasAuthority('ANALYST')")
	@RequestMapping("/insight")
	public String showFeedback()
	{
		return "BIAtemplates/insight";
	}
	
	
	List<SuggestionsEntity> calculate(VehicleEntity vehicles_in_the_office,int office) throws NumberFormatException, IOException
	
	{
List<SuggestionsEntity> entity=new ArrayList<SuggestionsEntity>();
		
		for(VehicleDto v:vehicles_in_the_office.getVehicles())
		{
			String sell=v.getCost().replace("$","").replace(",","").replace("Rs","").replace(".","");
			
			float tax=Integer.parseInt(t.getBy(office));
			System.out.println("tax:"+tax);
			float temp=tax;
			System.out.println("temp:"+tax);
			tax=(tax/100)+1;
			System.out.println("tax:"+tax);
			float revenue=Float.parseFloat(sell)*tax*v.getSold();
			String base=v.getBp().replace("$","").replace(",","").replace("Rs","").replace(".","");
			float costofcars=Float.parseFloat(base)*(v.getQuantity()+v.getSold());
			System.out.println("Cost of cars:"+costofcars);
			float profit=(float) (revenue-costofcars);
			float taxes=Float.parseFloat(sell)*(temp/100)*v.getSold();
			System.out.println(taxes);
			float netprofit=profit-taxes;
			float profitmargin=(float) (netprofit/revenue);
			String margin=String.valueOf(profitmargin*(float)100)+"%";
			String srevenue="$ ";
			String investment="$ ";
			String taxexpense="$ ";
			String snetprofit="$ ";
			if(office==2)
				{
				srevenue="Rs ";
				taxexpense="Rs ";
				investment="Rs ";
				snetprofit="Rs ";
				}
			srevenue+=String.format("%.2f",revenue);
			taxexpense+=String.format("%.2f",taxes);
			investment+=String.format("%.2f",costofcars);
			snetprofit+=String.format("%.2f",netprofit);
			System.out.println(srevenue+"\n"+taxexpense+"\n"+investment+"\n"+snetprofit);
			SuggestionsEntity entity2=new SuggestionsEntity(v.getId(),v.getName(),srevenue,investment,taxexpense,snetprofit,margin,v.getCategory());
			entity.add(entity2);
		}
		
		return entity;
	}
	@PreAuthorize("hasAuthority('ANALYST')")
	@RequestMapping(value="/insight",method=RequestMethod.POST)
	@ResponseBody
	public Suggest get(@RequestParam int office,@RequestParam String year) throws NumberFormatException, IOException
	{
		VehicleEntity v= vehicleService.getAllsales(office,year);
		List<SuggestionsEntity> l=calculate(v,office);
		List<SuggestionsEntity> budget=new ArrayList<SuggestionsEntity>();
		List<SuggestionsEntity> mid=new ArrayList<SuggestionsEntity>();
		List<SuggestionsEntity> high=new ArrayList<SuggestionsEntity>();
		for(SuggestionsEntity ll:l)
		{
			if(ll.getCategory()==1)
				budget.add(ll);
			if(ll.getCategory()==2)
				mid.add(ll);
			if(ll.getCategory()==3)
				high.add(ll);
		}
		Collections.sort(budget,new LexicographicComparator().reversed());
		Collections.sort(mid,new LexicographicComparator().reversed());
		Collections.sort(high,new LexicographicComparator().reversed());
		budget= new ArrayList<SuggestionsEntity>(budget.subList(0,2));
		mid= new ArrayList<SuggestionsEntity>(mid.subList(0,2));
		high= new ArrayList<SuggestionsEntity>(high.subList(0,2));
		System.out.println("view" +l);
		System.out.println("low" +budget);
		System.out.println("mid" +mid);
		System.out.println("high" +high);
		return new Suggest(budget, mid, high);
		
	}

	@PreAuthorize("hasAuthority('ANALYST')")
	@RequestMapping(value="/insight/s")
	@ResponseBody	
	public List<Sdata> get(@RequestParam int office)
	{System.out.println(office);
		if(office==2)
		return cc.get();
		else
			return ss.get();
	}
	
	
	
	@PreAuthorize("hasAuthority('ANALYST')")
	@RequestMapping(value="/insight/getall",method=RequestMethod.POST)
	@ResponseBody
	public List<SuggestionsEntity> getall(@RequestParam String id,@RequestParam int office,@RequestParam String year) throws IOException
	{
		
		List<SuggestionsEntity> entity=new ArrayList<SuggestionsEntity>();
		
		VehicleEntity vehicles_in_the_office= vehicleService.getAll(Integer.parseInt((id)),office,year);
		
		entity=calculate(vehicles_in_the_office,office);
		
		return entity;

		
	}
	@PreAuthorize("hasAuthority('ANALYST')")
	@RequestMapping(value="/insight/suggestions",method=RequestMethod.POST)
	@ResponseBody
	public Suggestions getSuggestions(@RequestParam int office,@RequestParam String year) throws IOException
	{
		VehicleEntity v=vehicleService.getAllsales(office, year);
		VehicleEntity v2=vehicleService.getAllsales(office, String.valueOf(Integer.parseInt(year)-1));
		List<SuggestionsEntity> l= calculate(v,office);
		System.out.println(l);
		Collections.sort(l, new LexicographicComparator().reversed());
		System.out.println(l);
		List<Popover> increase = new ArrayList<Popover>();
		List<Popover> order = new ArrayList<Popover>();
		List<Popover> remove = new ArrayList<Popover>();
		Popover p=new Popover(l.get(0).getName(),"Stock Increase","Increase the stock of <b>"+l.get(0).getName()+"</b> . Gives you the highest profit ");
			increase.add(p);
			p=new Popover(l.get(1).getName(),"Stock Increase","Increase the stock of <b>"+l.get(1).getName()+"</b> . Gives you the second highest profit ");
			increase.add(p);
		
			p=new Popover(l.get(2).getName(),"Stock Increase","Increase the stock of <b>"+l.get(2).getName()+"</b> . Gives you the third highest profit ");
			increase.add(p);
			List<Sdata> sl=new ArrayList<Sdata>();
			List<MakeDto> makes=make.getAll();
			List<String> makenames=new ArrayList<String>();
			for(MakeDto m:makes)
			{
				makenames.add(m.getName());
			}
			sl=ss.get();
			if(office==2)
			sl=cc.get();
			for(Sdata m:sl)
			{
				if(!makenames.contains(m.getBrand()))
				{
					p=new Popover(m.getBrand(), "Order Stock", "You do not have<b> "+m.getBrand()+"</b> in your inventory. Demand for "+m.getBrand()+" is likely to increase in your area");
					order.add(p);
				}
			}
		


List<VehicleDto> list_of_prev_year=v2.getVehicles();
HashMap<String,Integer> hm=new HashMap<String,Integer >();
HashMap<String,Integer> result=new HashMap<String,Integer >();
	for(VehicleDto vehicle1:list_of_prev_year)
	{
		hm.put(vehicle1.getName(), vehicle1.getSold());
		
		
	}
	for(VehicleDto vehicle:v.getVehicles())
	{
		int difference=vehicle.getSold()+(vehicle.getSold()-hm.get(vehicle.getName()));
		result.put(vehicle.getName().trim(),difference);
	}
	
	String j= createJsonString(result);
	TreeMap<String, Integer> sortedMap = SortByValue(result);  
	System.out.println(sortedMap);
	for(String keys:sortedMap.keySet())
	{
		p=new Popover(keys,"Discontinue model", "Give discounts for <b>"+keys+"</b> Sales are decreasing");
		remove.add(p);
	}
	remove=new ArrayList<Popover>(remove.subList(0,3));
		return new Suggestions(increase,order,remove,j);
	}
	
	
	
	
	@PreAuthorize("hasAnyAuthority('ANALYST')")
	@RequestMapping(value ="/insight/graph")
	@ResponseBody
	public VehicleEntity models(@RequestParam String id,@RequestParam int office,@RequestParam String year)
	{

		VehicleEntity vehicles_in_the_office= vehicleService.getAll(Integer.parseInt((id)),office,year);
		return vehicles_in_the_office;
	}
	
	@PreAuthorize("hasAnyAuthority('ANALYST')")
	@RequestMapping(value ="/insight/getyears")
	@ResponseBody
	public List<String> models()
	{
		return v.getyears();
	}
	public static TreeMap<String, Integer> SortByValue 
	(HashMap<String, Integer> map) {
ValueComparator vc =  new ValueComparator(map);
TreeMap<String,Integer> sortedMap = new TreeMap<String,Integer>(vc);
sortedMap.putAll(map);
return sortedMap;
}
	
}


class LexicographicComparator implements Comparator<SuggestionsEntity> {

	@Override
	public int compare(SuggestionsEntity arg0, SuggestionsEntity arg1) {
		// TODO Auto-generated method stub
		float change1 = Float.parseFloat(arg0.getMargin().replace("%",""));
		float change2 = Float.parseFloat(arg1.getMargin().replace("%",""));
		if (change1 < change2) return -1;
		if (change1 > change2) return 1;
		return 0;
	}
}

class ValueComparator implements Comparator<String> {
	 
    Map<String, Integer> map;
 
    public ValueComparator(Map<String, Integer> base) {
        this.map = base;
    }
 
    public int compare(String a, String b) {
        if (map.get(a) >= map.get(b)) {
            return 1;
        } else {
            return -1;
        } // returning 0 would merge keys 
    }
}


@Data
@AllArgsConstructor
@NoArgsConstructor
class Popover
{
	String list_body;
	String pop_title;
	String pop_body;
}
