package cs241_A3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * 
 * @author liangdong
 *
 */
public class App {

	public static void main(String[] args) {
			
		try {
			int counter = 0;
			Scanner scanner  = new Scanner(new File("city.dat"));
			while (scanner.hasNextLine()) {						
	            if(scanner.nextLine().equals("")){
	            		break;
	            }
	            counter++;
	        }
	        City[] cities = new City[counter];
	        for(int i=0; i<counter;i++){
	        		cities[i] = new City();
	        }
	        Integer[][] roads = new Integer[counter][counter];
	        scanner = new Scanner(new File("city.dat"));
	        int i = 0;
	        while (i < counter && scanner.hasNextLine()) {
	            String st = scanner.nextLine(); 
	            st = st.trim();
	            Scanner sc = new Scanner(st);
	            cities[i].setCityNumber(sc.nextInt());
	            cities[i].setCityCode(sc.next());
	            String name = sc.next();
	            cities[i].setFullCityName(name);
	            String str = sc.next();
	            if(str.charAt(0) > 57 ){
	            		cities[i].setFullCityName(name +" "+str);
	            		cities[i].setPopulation(sc.nextInt());
	            }
	            else{
	            		cities[i].setPopulation(Integer.parseInt(str));
	            }
	            cities[i].setElevation(sc.nextInt());
	            sc.close();
	            i++;
	        }
	        scanner = new Scanner(new File("road.dat"));
	        while (scanner.hasNext()) {
        		String st = scanner.nextLine(); 
            st = st.trim();
            Scanner sc = new Scanner(st);
            int starting = sc.nextInt();
            int ending = sc.nextInt();
            int dist = sc.nextInt();
            roads[starting - 1][ending - 1] = dist;
            sc.close();
	        }
        
	        Graph graph = new Graph(cities, roads);
	        String source;
	        String destination;
	        System.out.println("Java Project3");
	        while(true){
	        		System.out.print("Command? ");
	        		scanner = new Scanner(System.in);
	        		String choice = scanner.next();
	        		choice = choice.toLowerCase();
		        switch(choice){
		        	case "h" :
		        		System.out.print(" Q Query the city information by entering the city code."
		        				+ "\n D Find the minimum distance between two cities."
		        				+ "\n I Insert a road by entering two city codes and distance."
		        				+ "\n R Remove an existing road by entering two city codes."
		        				+ "\n H Display this message."
		        				+ "\n E Exit.\n");
		        		break;
		        	case "q" :       
		        		System.out.print("City code: ");
		        		source = scanner.next();
		        		City city = graph.queryCity(source);
		        		if(city != null) {
		        			System.out.println(city);
		        		}
		        		else{
		        			System.out.println("The city code does not exist!");
		        		}
		        		break;
		        	case "d" : 
		        		System.out.print("City codes: ");
		        		source = scanner.next();
		        		destination = scanner.next();
		        		String result[] = graph.trackPath(source, destination); 	
		        		if(result == null){
		        			System.out.println("No Such Path!");
		        		}
		        		else {
			        		System.out.println("The minimum distance between "
			        				+ graph.queryCity(source).getFullCityName() +" and " 
			        				+ graph.queryCity(destination).getFullCityName() + " is "
			        				+ result[1] +" through the route: " + result[0]);
		        		}
		        		break;
		        	case "i":
		        		System.out.print("City codes and distance: ");
		        		source = scanner.next();
		        		destination = scanner.next();
		        		try{
		        			Integer dist = scanner.nextInt();
		        			if(dist < 0) {
		        				System.out.println("Invalid Input!!");
		        				break;
		        			}
		        			if(graph.insertRoad(source, destination, dist)){
			        			System.out.println("You have inserted a road from "
				        				+ graph.queryCity(source).getFullCityName() +" to " 
				        				+ graph.queryCity(destination).getFullCityName() + " with a distance of "
				        				+ dist);
		        			}else{
			        			System.out.println("At least one of the cities does not exist");
			        		}
		        		}catch(InputMismatchException e){
		        			System.out.println("Invalid Input!!");
		        		}	
		        		break;
		        	case "r":
		        		System.out.print("City codes: ");
		        		source = scanner.next();
		        		destination = scanner.next();
		        		if(graph.removeRoad(source, destination) != null){
		        			System.out.println("The road from " 
		        					+ graph.queryCity(source).getFullCityName() +" to " 
			        				+ graph.queryCity(destination).getFullCityName() 
			        				+ " has been removed.");
		        		}
		        		else{
		        			try{
		        			System.out.println("The road from " 
		        					+ graph.queryCity(source).getFullCityName() +" to " 
			        				+ graph.queryCity(destination).getFullCityName() 
			        				+ " doesn't exist.");
		        			}catch(NullPointerException e){
		        				System.out.println("At least one of the cities does not exist");
		        			}
		        		}
		        		break;
		        	case "e":
		        		scanner.close();
		        		System.exit(0);
		        	default:
		        		System.out.println("Invalid Input!!"); 		
		        }
	
	        }
		}catch (FileNotFoundException e) {
			System.out.println("File Not Found!");
			System.exit(1);
		}catch(InputMismatchException e){
			System.out.println("Invalid Input!!");
		}
	}
}
