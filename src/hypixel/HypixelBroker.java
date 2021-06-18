package hypixel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HypixelBroker {
	String APIKey = "fa5c4fdd-5159-482b-a935-cb38e00a2be3";
	String productGET = "https://api.hypixel.net/skyblock/bazaar/product?key="+ APIKey + "&product=";
	String productLIST = "https://api.hypixel.net/skyblock/bazaar/products?key=" + APIKey;
	String string = "api.hypixel.net/skyblock/bazaar/product?key=YOUR_API_KEY&productId=";
	
	public static void main(String[] args) {
		HypixelBroker core = new HypixelBroker();
		core.setUp();
	}

	private void setUp() {
		//getProductLIST();
		getProductINFO("LEATHER");
	}

	private void getProductLIST() {
		try {
			String get = "https://api.hypixel.net/skyblock/bazaar/products?key=" + APIKey;
			BufferedReader br = makeGetRequest(get);
			String response = parseResponse(br);
			System.out.println(response);
		} catch (Exception e) {
			}
	}

	private void getProductINFO(String prodId) {
		try {
			String get = "https://api.hypixel.net/skyblock/bazaar/product?key=" + APIKey + "&productId=" + prodId;
			System.out.println("GET "+get);
			BufferedReader br = makeGetRequest(get);
			String response = parseResponse(br);
			System.out.println(response);
		} catch (Exception e) {
		}
	}
	private BufferedReader makeGetRequest(String get) throws IOException {
		URL url =  new URL(get);
		HttpURLConnection connector = (HttpURLConnection) url.openConnection();
		connector.setRequestMethod("GET");
		BufferedReader br = new BufferedReader(new InputStreamReader(connector.getInputStream()));
		return br;
	}
	private String parseResponse(BufferedReader br) {
		String response = "";
		String line = "";
		try {
			line = br.readLine();
			while (line != null) {
				response += line;
				line = br.readLine();
			}
			br.close();
			response = response.trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;	
	}
	private void getFriends() {
	}
}
