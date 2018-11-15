package jp.ac.x16g023chiba_fjb.spareplanning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RouteData{

	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Legs{
		public String start_address;
		public jp.ac.x16g023chiba_fjb.spareplanning.Location start_location;
		public String end_address;
		public jp.ac.x16g023chiba_fjb.spareplanning.Location end_location;
	}
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Routes{
		public Legs[] legs;
	}

	public Routes[] routes;
	public String error_message;
}
