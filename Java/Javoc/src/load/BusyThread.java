package load;

import java.time.LocalDateTime;

public class BusyThread extends Thread{
	private LocalDateTime endtime;

	public BusyThread(String name, LocalDateTime endtime) {
		super(name);
		this.endtime = endtime;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		System.out.println("Thread: " + this.getName());
		while(LocalDateTime.now().isBefore(endtime)) {
		}
	}
}
