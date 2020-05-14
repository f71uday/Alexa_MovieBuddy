package io.github.f71uday.OMDB;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;




public class OpenDBAPIFetch  {
	
	 public String fetchMovieDetails(String slotValue)
	 {
		 String plot = null;
		 MovieDetails movieDetails = new MovieDetails();
		 HttpClient httpClient = new DefaultHttpClient();
		 try
		 {
			 URIBuilder uriBuilder = new URIBuilder(Constants.OpenDBAPIURL);
			 uriBuilder.addParameter("apikey", Constants.APIKey);
			 uriBuilder.addParameter("t", slotValue);
			 uriBuilder.addParameter("plot", Constants.Plot);
			 uriBuilder.addParameter("type", Constants.SearchType);
			 uriBuilder.addParameter("r", "json");
			 HttpGet getRequest = new HttpGet(uriBuilder.build());
			 HttpResponse response  = httpClient.execute(getRequest);
			 
			 ObjectMapper objectMapper = new ObjectMapper();
			 movieDetails = objectMapper.readValue(response.getEntity().getContent(), MovieDetails.class);
			 System.out.println(movieDetails.getPlot());
			 
			// JsonObject jsonObject =  new JsonObject(response.getEntity().toString());
			 
		 }catch(Exception e)
		 {
			 System.out.println(e.getMessage());
		 }finally
		 {
			 
		 }
		 return movieDetails.getPlot();
	 }
}
