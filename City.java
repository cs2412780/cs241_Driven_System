package cs241_A3;

/**
 * A city class
 * @author liangdong
 *
 */
public class City implements Comparable<City> {
	private int city_number;
	private String city_code;
	private String full_city_name;
	private int population;
	private int elevation;
	private Integer distance;
	private City predecessor;
	
	public City(int city_number, String city_code, 
				String full_city_name, int population,
										int elevation){
		this.city_number = city_number;
		this.city_code = city_code;
		this.full_city_name = full_city_name;
		this.population = population;
		this.elevation = elevation;
	}
	public City(){
		this(-1, null, null, -1, -1);
	}
	/**
	 * @return the city_number
	 */
	public int getCityNumber() {
		return city_number;
	}
	/**
	 * @param city_number the city_number to set
	 */
	public void setCityNumber(int city_number) {
		this.city_number = city_number;
	}
	/**
	 * @return the city_code
	 */
	public String getCityCode() {
		return city_code;
	}
	/**
	 * @param city_code the city_code to set
	 */
	public void setCityCode(String city_code) {
		this.city_code = city_code;
	}
	/**
	 * @return the full_city_name
	 */
	public String getFullCityName() {
		return full_city_name;
	}
	/**
	 * @param full_city_name the full_city_name to set
	 */
	public void setFullCityName(String full_city_name) {
		this.full_city_name = full_city_name;
	}
	/**
	 * @return the population
	 */
	public int getPopulation() {
		return population;
	}
	/**
	 * @param population the population to set
	 */
	public void setPopulation(int population) {
		this.population = population;
	}
	/**
	 * @return the elevation
	 */
	public int getElevation() {
		return elevation;
	}
	/**
	 * @param elevation the elevation to set
	 */
	public void setElevation(int elevation) {
		this.elevation = elevation;
	}
	
	public String toString(){
		return city_number +" "+ city_code +" "+ full_city_name +" "+ population +" "+ elevation;
	}
	/**
	 * @return the distance
	 */
	public Integer getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	@Override
	public int compareTo(City o) {
		if(this.distance < o.distance) {
			return -1;
		}
		else if(this.distance > o.distance) {
			return 1;
		}
		else
			return 0;
	}
	/**
	 * @return The predecessor of the calling city in the recent shortest path.
	 */
	public City getPredecessor() {
		return predecessor;
	}
	/**
	 * Sets the predecessor of the calling city in the recent shortest path.
	 * @param predecessor the predecessor to set
	 */
	public void setPredecessor(City predecessor) {
		this.predecessor = predecessor;
	}
}
