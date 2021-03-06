package jp.ac.x16g023chiba_fjb.spareplanning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by oikawa on 2017/10/17.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class PlaceData {
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Geometry{
		Location location;
	}
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Results{
		public Geometry geometry;
		public String name;
	}
	public Results[] results;
}
