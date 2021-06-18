package hypixel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductList {
	@SerializedName("success")
	@Expose
	boolean success;
	@SerializedName("productIds")
	@Expose
	String[] productIds;
}