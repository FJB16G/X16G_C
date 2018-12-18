package jp.ac.x16g023chiba_fjb.spareplanning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by oikawa on 2017/10/17.
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class PlaceData {
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class LatLng {
		public double lat;
		public double lng;
	}
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Geometry {
		public LatLng location;
	}
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Photo {
		public int width;
		public int height;
		public String photo_reference;
	}
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Results{
		public Geometry geometry;
		public String icon;
		public String id;
		public String name;
		public String vicinity;
		public Photo[] photos;
	}
	public Results[] results;
}
