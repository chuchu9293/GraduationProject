package com.likeyichu.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.likeyichu.dao.Dao;

@Path("statisticsService")
public class StatisticsService {
	static Dao dao=new Dao();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public StatisticsResponse fun1(){
		return dao.statistics();
	}
}
