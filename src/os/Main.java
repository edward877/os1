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

//������� �����
public class Main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	//������� �����
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
	//����� ���������� ����� initialize
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

		//��� ������� �� ������ "�������" ����������� ���� �����
		JButton btnCreate = new JButton("�������");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//1) ����������� ����� createProcess
				createProcess();
				//2) � ����� ���� ��������� �� ��� �������� � ������ ShowProcess
				textAreaLeft.setText( ShowProcess() );
				//3) � ������ ���� ��������� �� ��� �������� � ������ ShowActing
				textAreaRight.setText( ShowActing() );
			}
		});
		btnCreate.setBounds(223, 369, 89, 23);
		frame.getContentPane().add(btnCreate);
	}


	ArrayList<Process> list_process = new ArrayList<>();;

	//1) ��� ���������� �������� ���������
	private void createProcess() {
		Random r = new Random();
		// n = (��������� ���������� �� 0 �� 4) + 2
		int n = r.nextInt(4) + 2;
		// ������� ����� �������  ������ ������� � �������� � ������ N ���� ���������
		for(int i = 0; i < n ; i++) {
			list_process.add(new Process(r));
		}
	}

	// 2) ��� ������� ��������� ��������
	private String ShowProcess() {
		// � ���������� text ���������� �������� � ������
		String text = "";
		// ���� �� ������ �������
		for(int i = 0; i < list_process.size(); i++) {
			//����� i-�� �������
			Process p = list_process.get(i);
			//����� ��� �����
			text += "������� " + (i+1);
			//����� ������� ������ ������� ������ ��������
			text += ".  time = " + p.getTime_process() +  "\n";
			//���� �� ������ ������� � �������� (������ � ������ �������� ��������� �������)
			for(int j = 0; j < p.getList_stream().size(); j++) {
				//����� j-�� ����� ��������
				Stream s = p.getList_stream().get(j);
				//����� ������� ������ ������� ������ ������
				text += "\t����� " + (j+1) + ".  time = " +s.getTime_stream() +  "\n";
			}
			text += "\n";
		}
		return text;
	}

	//��� ������� ������ ��������� !
	// 3) ��� ������� �� ��� ���������� � ���������� ������ ���������
	private String ShowActing() {
		// � ���������� text ���������� �������� � ������
		String text = "";
		//���������� ��������� ("������ ��������"), ������� ��� �������� (������� ��� ������ ������ ����� ���������)
		int online_process = list_process.size();
		int iteration = 0;
		//���� � ��� ���� ������ ��������, �� ���� �� �����
		while(online_process>0){
			//������� 1�� ������
			text += "\t||" + (++iteration) + "�� ������||\n";

			//���� �� ������ ���� ���������
			for(int i = 0; i < list_process.size(); i++) {
				//����� i-�� �������
				Process p = list_process.get(i);
				//���� ����� �������� �� ����������� (���� ������ "������")
				if(p.getTime_process()!=0){
					//����� ��������� ������� �������
					p.setQuantum(Process.QUANTUM);
					text += "������� " + (i+1) +  "\n";

					//���� �� ������ ������� � ��������
					for(int j = 0; j < p.getList_stream().size(); j++) {
						//����� j-�� �����
						Stream s = p.getList_stream().get(j);
						//���� � ������ �������� ����� (����� "������")
						if(s.getTime_stream()!=0){
							// ���� ������� ��� �� ����������
							if(!(p.getQuantum() == 0)) {
								// �� ����� "��������"
								p.action_stream(s);
							}
							text += "\t����� " + (j+1);
							//�������� ���������� ����� ��������
							int time_stream = s.getTime_stream();
							// ���� ����� ��������, ����� ���
							if(time_stream==0){
								text +=   ".   ����������\n";
							}else text += ".   time = " + time_stream + "\n";
						}
					}

					// ���� ������� ��������, ����� ���, � ��������� ���������� ������ ���������
					p.setTime_process();
					if(p.getTime_process()==0){
						text += "������� ����������!\n";
						online_process--;
					}
					text += "\n";
				}
			}
		}

		return text;
	}
}
