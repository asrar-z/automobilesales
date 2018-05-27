package com.car.utility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.car.entity.jsonObj;


public class Crawler {

	public List<String> crawl(int office,int make, List<jsonObj> list)
	{
		List<String> rate=new ArrayList<String>();
		Document doc;
		try {
			System.out.println("Office"+office);
			if(office==2)
			{
			String idx="bmw.htm";
			if(make==1)
				idx="volkswagen.htm";
			else if(make==4)
				idx="ford.htm";
			else if(make==5)
				idx="toyota.htm";
			else if(make==6)
				idx="honda.htm";
			
			doc = Jsoup.connect("http://www.motortrend.in/newcars/"+idx).get();
			Elements car_list = doc.getElementsByClass("listview-text");
			System.out.println(car_list);
			for(jsonObj j:list)
			{
				int flag=0;
				for(Element e:car_list)
				{
					String name=e.getElementsByClass("title").text();
					String price=e.getElementsByClass("rght-section").eq(0).text();
					System.out.println(name + " "+ price);
					if(name.contains(j.getName()))
					{
						
						flag=1;
						String str[]=price.split("-");
						rate.add(str[1]);
						break;
					}
					
				}
				if(flag==0){
					rate.add(j.getPrice());
				}
				
			}
			
			}
			else
			{
				String m="4";
				if(make==2)
					m="4";
				else if(make==1)
					m="40";
				else if(make==3)
					m="";
				else if(make==4)
					m="12";
				else if(make==5)
					m="39";
				else if(make==6)
					m="13";
				doc = Jsoup.connect("http://www.oneshift.com/new_cars/car-dealer-price-list.php?m="+m).get();

				// get page title
				String title = doc.title();
				System.out.println("title : " + title);

				// get all links


					Elements table = doc.getElementsByClass("searchResults");
					Elements trs = table.get(0).getElementsByTag("tr");

					
					for(jsonObj j:list)
					{
						int flag=0;
					
					
					for(Element tr : trs)
					{
					
						Elements td=tr.getElementsByTag("td");
						
						String name=td.get(0).text();
						String price=td.last().text();
						
						if(name.startsWith(j.getName())){
							rate.add(price);
							flag=1;
							break;
							}
						
					
						//for(Element t:td){
						//	System.out.println("\n"+t.text());
						//}
						
					}
					if(flag==0)
						rate.add("Not Found");
					}
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return rate;
		
	}
	
	
}



