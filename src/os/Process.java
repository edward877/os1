package os;

import java.util.ArrayList;
import java.util.Random;

public class Process {

	//изначально квантум = 20 (можно задать другое время, например 10)
	public final static int QUANTUM = 20;

	// у процесса есть переменные - общее время процесса, список потоков и квантум(время,через которое он прерывается, и передает управление другому процессу)
	private int time_process;
	private ArrayList<Stream> list_stream = new ArrayList<>();
	private int  quantum;

	//при создании процесса, создается рандомное количество (от 1 до 3) потоков
	Process(Random r) {
		for(int i = 0; i < r.nextInt(2) + 1; i++) {
			list_stream.add(new Stream(r));
		}
		//вызываем метод setTime_process для подсчета общего времени процесса
		setTime_process();
	}

	//
	// свойства  - методы для изменения значения переменных
	//
	public ArrayList<Stream> getList_stream() {
		return list_stream;
	}

	public int getTime_process() {
		return time_process;
	}

	public void setTime_process() {
		time_process = 0;
		for(int i = 0; i < list_stream.size(); i++) {
			//время процессов = сумма времени его потоков
			time_process += list_stream.get(i).getTime_stream();
		}
	}

	public int getQuantum() {
		return quantum;
	}

	public void setQuantum(int quantum) {
		this.quantum = quantum;
	}


	// тут поток "работает",
	// из времени его работы Потока вычитается квантум (изначально это 20мс)
	// и оставшиеся значение от квантума записывается в квантум процесса
	public void action_stream(Stream stream){
		quantum = stream.setTime_stream(quantum);
	}

}
