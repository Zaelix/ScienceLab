package territorialio;

public enum Maps {
	WHITE_ARENA(51984), BLACK_ARENA(636804),ISLANDS(38000);
	int totalLand;
	Maps(int totalLand) {
		this.totalLand = totalLand;
	}
}
