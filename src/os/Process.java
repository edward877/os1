package os;

import java.util.ArrayList;
import java.util.Random;

public class Process {

	private int time_process;
	private ArrayList<Stream> list_stream = new ArrayList<>();
	private int  quantum = 20;


	Process(Random r) {
		for(int i = 0; i < r.nextInt(2) + 1; i++) {
			list_stream.add(new Stream(r));
		}
		setTime_process();
	}

	public ArrayList<Stream> getList_stream() {
		return list_stream;
	}

	public int getTime_process() {
		return time_process;
	}

	public void setTime_process() {
		time_process = 0;
		for(int i = 0; i < list_stream.size(); i++) {
			time_process += list_stream.get(i).getTime_stream();
		}
	}

	public void setQuantum(int quantum) {
		this.quantum = quantum;
	}

	public void setTime_stream(int j){
		quantum = list_stream.get(j).setTime_stream(quantum);
	}

}
