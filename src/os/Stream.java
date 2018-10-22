package os;

import java.util.Random;

public class Stream {

	//время работы потока
	private int time_stream;

	//время = рандомное от 10 о 40
	Stream(Random r) {
		time_stream = r.nextInt(30)+10;
	}

	public int getTime_stream() {
		return time_stream;
	}


	//когда поток работает, от отнимает от времени потока квантум
	public int setTime_stream(int quantum) {
		// если поток длится дольше, чем квантум, то квантум = 0, а время потока остатку
		if(time_stream>=quantum){
			time_stream -= quantum;
		}else{
			//иначе (если квантум > оставшегося времени потока, то время потока =0, а квантум = оставшемуся значению
			quantum -= time_stream;
			time_stream=0;
			return quantum;
		}
		return 0;
	}
	
}
