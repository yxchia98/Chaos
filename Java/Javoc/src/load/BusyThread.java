package load;

public class BusyThread extends Thread{
	private double load;
	private long duration;
	
	public BusyThread(String name, double load, long duration) {
		super(name);
		this.load = load;
		this.duration = duration;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}
}
