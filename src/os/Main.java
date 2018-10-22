package os;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

//главный класс
public class Main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	//главный метод
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	//здесь вызывается метод initialize
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 579, 459);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(22, 11, 229, 329);
		frame.getContentPane().add(scrollPane_1);

		JTextArea textAreaLeft = new JTextArea();
		scrollPane_1.setViewportView(textAreaLeft);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(278, 10, 275, 329);
		frame.getContentPane().add(scrollPane);

		JTextArea textAreaRight = new JTextArea();
		scrollPane.setViewportView(textAreaRight);
		textAreaRight.setWrapStyleWord(true);
		textAreaRight.setLineWrap(true);
		textAreaRight.setLineWrap(true);
		textAreaRight.setWrapStyleWord(true);

		//при нажатии на кнопку "создать" срабатывает этот метод
		JButton btnCreate = new JButton("создать");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//1) срабатывает метод createProcess
				createProcess();
				//2) в левое окно выводится то что написано в методе ShowProcess
				textAreaLeft.setText( ShowProcess() );
				//3) в правое окно выводится то что написано в методе ShowActing
				textAreaRight.setText( ShowActing() );
			}
		});
		btnCreate.setBounds(223, 369, 89, 23);
		frame.getContentPane().add(btnCreate);
	}


	ArrayList<Process> list_process = new ArrayList<>();;

	//1) тут происходит создание процессов
	private void createProcess() {
		Random r = new Random();
		// n = (рандомное количество от 0 до 4) + 2
		int n = r.nextInt(4) + 2;
		// создаем новые обеъкты  класса процесс и помещаем в список N штук процессов
		for(int i = 0; i < n ; i++) {
			list_process.add(new Process(r));
		}
	}

	// 2) тут выводим созданные процессы
	private String ShowProcess() {
		// в переменную text записываем процессы и потоки
		String text = "";
		// идем по списку потоков
		for(int i = 0; i < list_process.size(); i++) {
			//берем i-ый процесс
			Process p = list_process.get(i);
			//пишем его номер
			text += "процесс " + (i+1);
			//пишем сколько займет времени работа процесса
			text += ".  time = " + p.getTime_process() +  "\n";
			//идем по списку потоков у процесса (обычно у одного процесса несколько потоков)
			for(int j = 0; j < p.getList_stream().size(); j++) {
				//берем j-ый поток процесса
				Stream s = p.getList_stream().get(j);
				//пишем сколько займет времени работа потока
				text += "\tПоток " + (j+1) + ".  time = " +s.getTime_stream() +  "\n";
			}
			text += "\n";
		}
		return text;
	}

	//тут главная работа программы !
	// 3) тут выводим то что происходит в результате работы программы
	private String ShowActing() {
		// в переменную text записываем процессы и потоки
		String text = "";
		//количество процессов ("онлайн процессы"), которые еще работают (сначала это полный список наших процессов)
		int online_process = list_process.size();
		int iteration = 0;
		//пока у нас есть онлайн процессы, то идем по циклу
		while(online_process>0){
			//сначала 1ый проход
			text += "\t||" + (++iteration) + "ый проход||\n";

			//идем по списку всех процессов
			for(int i = 0; i < list_process.size(); i++) {
				//берем i-ый процесс
				Process p = list_process.get(i);
				//если время процесса не закончилось (если процес "онлайн")
				if(p.getTime_process()!=0){
					//берем начальный квантум времени
					p.setQuantum(Process.QUANTUM);
					text += "процесс " + (i+1) +  "\n";

					//идем по списку потоков у процесса
					for(int j = 0; j < p.getList_stream().size(); j++) {
						//берем j-ый поток
						Stream s = p.getList_stream().get(j);
						//если у потока осталось время (поток "онлайн")
						if(s.getTime_stream()!=0){
							// если квантум еще не закончился
							if(!(p.getQuantum() == 0)) {
								// то поток "работает"
								p.action_stream(s);
							}
							text += "\tПоток " + (j+1);
							//получаем оставшиеся время процесса
							int time_stream = s.getTime_stream();
							// если поток завершен, пишем это
							if(time_stream==0){
								text +=   ".   закончился\n";
							}else text += ".   time = " + time_stream + "\n";
						}
					}

					// если процесс завершен, пишем это, и уменьшаем количество онлайн процессов
					p.setTime_process();
					if(p.getTime_process()==0){
						text += "процесс закончился!\n";
						online_process--;
					}
					text += "\n";
				}
			}
		}

		return text;
	}
}
