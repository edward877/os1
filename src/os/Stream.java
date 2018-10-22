package os;

import java.util.Random;

public class Stream {
	
	private int time_stream;
	
	Stream(Random r) {
		time_stream = r.nextInt(30)+10;
	}

	public int getTime_stream() {
		return time_stream;
	}

	public int setTime_stream(int quantum) {
		if(time_stream>=quantum){
			time_stream -= quantum;
		}else{
			quantum -= time_stream;
			time_stream=0;
			return quantum;
		}
		return 0;
	}
	
}
