package load;

import java.time.LocalDateTime;

public class BusyThread extends Thread{
	private LocalDateTime endtime;
	private double utilization;

	public BusyThread(String name, LocalDateTime endtime, double utilization) {
		super(name);
		this.endtime = endtime;
		this.utilization = utilization / 100;
	}
	
	@Override
	public void run() {
		super.run();
		System.out.println(this.getName());
		while(LocalDateTime.now().isBefore(endtime)) {
			if (System.currentTimeMillis() % 100 == 0) {
                try {
					Thread.sleep((long) Math.floor((1 - this.utilization) * 100));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
		}
	}
}
