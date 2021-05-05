package load;

public class CpuLoader {
	public static void main(String[] args) {
		int numCores = Runtime.getRuntime().availableProcessors();
		System.out.println("Number of cpu cores:" + numCores);
	}
}
