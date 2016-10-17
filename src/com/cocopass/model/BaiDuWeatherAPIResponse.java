package com.cocopass.model;

import java.util.List;

import net.sf.ehcache.search.Results;

public class BaiDuWeatherAPIResponse {
   private String error;
	        private String status;
	        private String date;
	        private List<BaiDuWeatherResult> results;
			public String getError() 
			{
				return error;
			}
			public void setError(String error) 
			{
				this.error = error;
			}
			
			public String getStatus() 
			{
				return status;
			}
			public void setStatus(String status) 
			{
				this.status = status;
			}
			public String getDate() 
			{
				return date;
			}
			public void setDate(String date) 
			{
				this.date = date;
			}
			public List<BaiDuWeatherResult> getResults() 
			{
				return results;
			}
			public void setResults(List<BaiDuWeatherResult> results) 
			{
				this.results = results;
			}
			

}
