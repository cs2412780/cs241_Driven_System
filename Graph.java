package cs241_A3;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * A directed graph
 * @author liangdong
 *
 */
public class Graph {
	private City[] cities; // labels of cities
	private Map<String, Integer> cityLabels; // Contains the city codes and the index of the city in array 'cities'
	private Integer[][] roads;
	private boolean changed; // True if any changes are made in roads or cities
	private int source; //  Index of the soure city of the recent shortest path.
	
	/**
	 * Initializes the graph
	 * @param labels 
	 * @param distances
	 */
	public Graph(City[] cities, Integer[][] distances){
		this.roads = distances;	
		changed = true;
		this.cities = cities;
		cityLabels = new HashMap<>();
		for(int i=0; i<cities.length; i++){
			cityLabels.put(cities[i].getCityCode(), i);		
		}
		resetPath();
	}
	
	/**
	 * Inserts a road between two cities
	 * @param startingCity The source city
	 * @param endingCity The destination
	 * @param distance The length of the road
	 * @return True if insert successfully.
	 */
	public boolean insertRoad(String startingCity, String endingCity, Integer distance){
		Integer starting_city = getCityIndex(startingCity);
		Integer ending_city = getCityIndex(endingCity);
		if(starting_city != null && ending_city != null) {
			roads[starting_city][ending_city] = distance;
			changed = true;
			resetPath(); 
			return true;
		}
		return false;
	}
	
	/**
	 * Removes a road between two cities
	 * @param startingCity The source city
	 * @param endingCity The destination
	 * @return The length of the road, null if the road does not exist.
	 */
	public Integer removeRoad(String startingCity, String endingCity){
		Integer starting_city = getCityIndex(startingCity);
		Integer ending_city = getCityIndex(endingCity);
		Integer dist = null;
		if(starting_city != null && ending_city != null) {
			dist = roads[starting_city][ending_city];
			roads[starting_city][ending_city] = null;
			changed = true;
			resetPath();
		}
		return dist;
	}
	
	/**
	 * Gets the index of the city in the label array
	 * @param city_code The city code
	 * @return The index of the city, null if the city does not exist.
	 */
	private Integer getCityIndex(String city_code){
		city_code = city_code.trim().toUpperCase();
		Integer city_index = null;
		if(cityLabels.containsKey(city_code)){
			city_index = cityLabels.get(city_code);
		}
		return city_index; 
	}
	
	/**
	 * Gets the city
	 * @param city_code The city code
	 * @return The city object, null if the city does not exist
	 */
	public City queryCity(String city_code){
		city_code = city_code.trim().toUpperCase();		
		if(cityLabels.containsKey(city_code)){
			return cities[cityLabels.get(city_code)];
		}
		return null;
	}
	
	/**
	 * Finds the minimum distance and the path between two cities.
	 * @param startingCity The source city
	 * @param endingCity The destination
	 * @return An array of strings, string[0] contains the path, string[1] contains the distance. 
	 */
	private String getMinimumDistance(int starting_city, int ending_city) {
		cities[starting_city].setDistance(0);
		PriorityQueue<City> queue = new PriorityQueue<>(cities.length);
		for(int i=0; i<cities.length; i++) {
			queue.offer(cities[i]);
		}
		while(!queue.isEmpty()){
			City city = queue.peek();
			if(city.getDistance().equals(Integer.MAX_VALUE)) {
				break;
			}
			queue.remove();
			int index = getCityIndex(city.getCityCode());
			for(int i=0; i<roads.length; i++) {
				if(roads[index][i] != null){
					int dist;
					if(city.getDistance() == Integer.MAX_VALUE) {
						dist = Integer.MAX_VALUE;
					}
					else {
						dist = city.getDistance() + roads[index][i];
					}
					
					if(dist < cities[i].getDistance()){
						queue.remove(cities[i]);
						cities[i].setDistance(dist);
						cities[i].setPredecessor(city);// keeps tracks of the predecessor city
						queue.offer(cities[i]);
					}
				}
			}
		}

		changed = false;
		source = starting_city;
		return ""+ cities[ending_city].getDistance();
	}
	
	/**
	 * Tracks the path
	 * @param ending_city The destination
	 * @return A string that contains the shortest path.
	 */
	public String[] trackPath(String startingCity, String endingCity){
		Integer starting_city = getCityIndex(startingCity);
		Integer ending_city = getCityIndex(endingCity);
		if(starting_city == null || ending_city== null) {
			return null;
		}
		String[] result = new String[2];
		if(!hasChanged() && starting_city == source){
			result[0] = generatePath(ending_city);
			result[1] = "" + cities[ending_city].getDistance();				
		}
		else{
			if(!hasChanged() && starting_city != source){
				resetPath();
			}
			result[1] = getMinimumDistance(starting_city, ending_city);			
			result[0] = generatePath(ending_city);
		}
		if(result[1].equals(""+Integer.MAX_VALUE)){
			return null;
		}
		return result;
	}
	
	/**
	 * Checks if any change has been made in roads or cities array. 
	 * @return True if any change has been made.
	 */
	private boolean hasChanged(){
		return changed;
	}
	
	/**
	 * Resets the shortest path, predecessors to be null, and shortest distances to be infinity.
	 */
	private void resetPath(){
		for(int i=0; i<cities.length; i++) {
			cities[i].setDistance(Integer.MAX_VALUE);
			cities[i].setPredecessor(null);
		}
		source = -1;
	}
	
	
	/**
	 * generate The shortest path
	 * @param ending_city The destination
	 * @return A string that contains the shortest path.
	 */
	private String generatePath(int ending_city){
		String path = "";
		City current = cities[ending_city];
		if(current.getPredecessor() == null){
			return null;
		}
		while(current != null){
			path = current.getCityCode() + " " + path;
			current = current.getPredecessor();
		}
		return path;
	}


}
