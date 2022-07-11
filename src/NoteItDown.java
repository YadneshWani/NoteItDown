import java.awt.*;
import javax.swing.*;
//import javax.swing.UIManager.*;

import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.text.BadLocationException;

import org.dom4j.DocumentException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.awt.print.*;
import javafx.print.Printer;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
//import icy.gui.component.CloseableTabbedPane;


class NoteItDown extends JFrame implements ActionListener
{	
	public
	JMenuBar mb=new JMenuBar();
	JMenu file=new JMenu("File");
	JMenu edit=new JMenu("Edit");
	JMenu help=new JMenu("Help");

	//edit menu
	JMenu align=new JMenu("Align");
	
	//file Menu
	JMenu export=new JMenu("Export");

	//file menu
	JMenuItem n=new JMenuItem("New",new ImageIcon("C:\\Users\\acer\\eclipse-workspace\\Note It Down\\Images1\\new_icon.png"));
    
	JMenuItem open=new JMenuItem("Open",new ImageIcon("C:\\Users\\acer\\eclipse-workspace\\Note It Down\\Images1\\open_icon.png"));
	JMenuItem close=new JMenuItem("Close",new ImageIcon("C:\\Users\\acer\\eclipse-workspace\\Note It Down\\Images1\\close_icon.png"));;
	JMenuItem save=new JMenuItem("Save As",new ImageIcon("C:\\Users\\acer\\eclipse-workspace\\Note It Down\\Images1\\save_icon.png"));
	JMenuItem print=new JMenuItem("Print",new ImageIcon("C:\\Users\\acer\\eclipse-workspace\\Note It Down\\Images1\\printer_icon.png"));
	//JMenuItem convert=new JMenuItem("Export to Pdf");
	JMenuItem exit=new JMenuItem("Exit",new ImageIcon("C:\\Users\\acer\\eclipse-workspace\\Note It Down\\Images1\\close_icon.png"));

	//edit menu
	JMenuItem cut=new JMenuItem("Cut",new ImageIcon("C:\\Users\\acer\\eclipse-workspace\\Note It Down\\Images1\\cut.jpg"));
	JMenuItem copy=new JMenuItem("Copy",new ImageIcon("C:\\Users\\acer\\eclipse-workspace\\Note It Down\\Images1\\copy.png"));
	JMenuItem paste=new JMenuItem("Paste",new ImageIcon("C:\\Users\\acer\\eclipse-workspace\\Note It Down\\Images1\\paste.jpg"));
	JMenuItem fullscreen=new JMenuItem("Hide Toolbar");

	//help menu
	JMenuItem about=new JMenuItem("About",new ImageIcon("C:\\Users\\acer\\eclipse-workspace\\Note It Down\\Images1\\about_icon.png"));

	//align menu
	JMenuItem left=new JMenuItem("Left");
	JMenuItem right=new JMenuItem("Right");
	JMenuItem center=new JMenuItem("Center");
	
	
	//Export Menu
	JMenuItem pdf=new JMenuItem("Export to Pdf",new ImageIcon("C:\\Users\\acer\\eclipse-workspace\\Note It Down\\Images1\\export_icon.png"));
	JMenuItem word=new JMenuItem("Export to word",new ImageIcon("C:\\Users\\acer\\eclipse-workspace\\Note It Down\\Images1\\export_icon.png"));
	//JMenuItem excel=new JMenuItem("Export to Excel");
	//JMenuItem jar=new JMenuItem("Export to zip");


	JTextArea ta=new JTextArea();
	JTabbedPane pane=new JTabbedPane(JTabbedPane.TOP);
	JToolBar tb=new JToolBar(JToolBar.VERTICAL);
	JToolBar tb1=new JToolBar();
	public JTextArea tarea[]=new JTextArea[25];
	JScrollPane p[]=new JScrollPane[25];
	File FileName[]=new File[25];
	JToolBar bottomToolbar=new JToolBar();
	JLabel BtmXY=new JLabel("Row:0,Col:0");


	String txt="Hide Toolbar",s,font;
	int c=0;
	int index;
	int length=0;
	int i;
	int size,count=0;
	Object obj;



	JComboBox <String>fontchoice=new JComboBox<String>();
	String fontsizes[]={"8","12","14","16","18","20","22","24","26","28","30","32","34","36","38","40","42","46","48"};
	JComboBox <String>fontsize=new JComboBox<String>(fontsizes);
	
	NoteItDown()
	{
		setSize(1700,1085);
		setTitle("NoteItDown");

		setLayout(null);



		mb.add(file);
		mb.add(edit);
		mb.add(help);

		file.add(n);
//		n.setIcon(new ImageIcon("C:\\Users\\acer\\eclipse-workspace\\Note It Down\\Images1\\new_icon"));
//		file.add(n);
		file.add(open);
//		file.add(close);
		file.add(save);
		file.addSeparator();
		file.add(print);
		file.add(export);
		file.add(exit);


		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.addSeparator();
		edit.add(fullscreen);
		edit.addSeparator();
		edit.add(align);

		help.add(about);

		align.add(left);
		align.add(right);
		align.add(center);
		
		export.add(pdf);
		export.add(word);
		//export.add(excel);font.get
		//export.add(jar);

		setJMenuBar(mb);

		ta.setFont(new Font("times new roman",0,20));
//		mb.setBackground(new Color(,100,0));
		mb.setBounds(0, 0, 50, 50);
		Font f = new Font(mb.getFont().getFontName(), mb.getFont().getStyle(), 12);
     	UIManager.put("Menu.font", f);
//		Font f=new Font("sans-serif", Font.PLAIN, 12);
//	    UIManager.put("Menu.font", f);
//	    UIManager.put("MenuItem.font", f);

		n.addActionListener(this);
		open.addActionListener(this);
		save.addActionListener(this);
		print.addActionListener(this);
		pdf.addActionListener(this);
		word.addActionListener(this);
		//jar.addActionListener(this);
		exit.addActionListener(this);
		about.addActionListener(this);

		try
		{
			BufferedReader fin=new BufferedReader(new FileReader("TextFontList.txt"));
			String line=fin.readLine();
			while(line!=null)
			{
				fontchoice.addItem(line);
				line=fin.readLine();
			}
			fin.close();
		}
		catch(Exception exp)
		{
			System.out.println(exp);
		}


		JButton pasteButton=new JButton(new ImageIcon("images1/paste.jpg"));
		tb.add(pasteButton);
		pasteButton.setToolTipText("Paste");
		pasteButton.addActionListener(new ActionListener()
 		{
			public void actionPerformed(ActionEvent e)
			{
				tarea[index].paste();
			}
		});

		paste.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tarea[index].paste();
			}
		});


		JButton copyButton=new JButton(new ImageIcon("images1/copy.png"));
		tb.add(copyButton);
		copyButton.setToolTipText("Copy");
		copyButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tarea[index].copy();
			}
		});

		copy.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tarea[index].copy();
			}
		});

		JButton cutButton=new JButton(new ImageIcon("images1/cut.jpg"));
		tb.add(cutButton);
		cutButton.setToolTipText("Cut");
		cutButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tarea[index].cut();
			}
		});

		cut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tarea[index].cut();
			}
		});

	    tb1.setLayout(null);
		tb1.add(fontchoice);
		fontchoice.setBounds(16,0,180,44);
		fontchoice.setToolTipText("SelectFont");
		fontchoice.setEditable(true);
		fontchoice.addItemListener(new ItemListener()
				{
					public void itemStateChanged(ItemEvent e)
					{
						obj=fontchoice.getSelectedItem();
						font=obj.toString();

						obj=fontsize.getSelectedItem();
						s=obj.toString();
						size=Integer.parseInt(s);


						System.out.println(font);
						//int index=pane.getSelectedIndex();
						//int tabCount=pane.getTabCount();
						System.out.println(index);
						tarea[index].setFont(new Font(font,0,size));
					}
		});






		tb1.add(fontsize);
		fontsize.setBounds(196,0,100,44);
		fontsize.setToolTipText("SelectFont");
		fontsize.setEditable(true);
		fontsize.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				obj=fontsize.getSelectedItem();
				s=obj.toString();
				size=Integer.parseInt(s);

				obj=fontchoice.getSelectedItem();
				font=obj.toString();

				System.out.println(size);
				//int index=pane.getSelectedIndex();
				//int tabCount=pane.getTabCount();
				System.out.println(index);
				tarea[index].setFont(new Font(font,0,size));
			}
		});

		
		

		pane.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
			index=pane.getSelectedIndex();
			System.out.println("Index = "+index);
			}
		});


		JButton plusButton=new JButton(new ImageIcon("images1/plus.jpg"));
		tb.add(plusButton);
		plusButton.setToolTipText("Maximize");
		plusButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				obj=fontsize.getSelectedItem();
				s=obj.toString();
				size=Integer.parseInt(s);

				obj=fontchoice.getSelectedItem();
				font=obj.toString();
				size=size+2;
				fontsize.setSelectedItem(size);

				//tarea[index].setFont(new Font(font,0,size));
			}
		});

		JButton minusButton=new JButton(new ImageIcon("images1/minus.png"));
		tb.add(minusButton);
		minusButton.setToolTipText("Minimize");
		minusButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				obj=fontsize.getSelectedItem();
				s=obj.toString();
				size=Integer.parseInt(s);
				size=size-2;
				obj=fontchoice.getSelectedItem();
				font=obj.toString();
				fontsize.setSelectedItem(size);

				tarea[index].setFont(new Font(font,0,size));
			}
		});




		JButton uppercaseButton=new JButton(new ImageIcon("images1/uppercase.png"));
		tb.add(uppercaseButton);
		uppercaseButton.setToolTipText("uppercase");
		uppercaseButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String text=tarea[index].getSelectedText();
				tarea[index].replaceSelection(text.toUpperCase());
			}
		});

		JButton lowercaseButton=new JButton(new ImageIcon("images1/lowercase.png"));
		tb.add(lowercaseButton);
		lowercaseButton.setToolTipText("lowercase");
		lowercaseButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String text=tarea[index].getSelectedText();
				tarea[index].replaceSelection(text.toLowerCase());
			}
		});


		JButton boldButton=new JButton(new ImageIcon("images1/bold.png"));
		tb.add(boldButton);
		boldButton.setToolTipText("Bold");
		boldButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tarea[index].setFont(new Font(font,1,size));
			}
		});

		JButton italicButton=new JButton(new ImageIcon("images1/italic.png"));
		tb.add(italicButton);
		italicButton.setToolTipText("Italic");
		italicButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tarea[index].setFont(new Font(font,2,size));
			}
		});


		JButton leftalignButton=new JButton(new ImageIcon("images1/leftalign.jpg"));
		tb.add(leftalignButton);
		leftalignButton.setToolTipText("LeftAlign");
		leftalignButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
//				StringAli gnUtils util = new StringAlignUtils(50, Alignment.LEFT);
//				System.out.println( util.format(sampleText) );
				tarea[index].setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
				//tarea[index].setAlignmentX(JTextArea.LEFT_ALIGNMENT);
				//tarea[index].setAlignmentY(JTextArea.LEFT_ALIGNMENT);
				
			}
		});

		JButton centeralignButton=new JButton(new ImageIcon("images1/centeralign.png"));
		tb.add(centeralignButton);
		centeralignButton.setToolTipText("CenterAlign");
		centeralignButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//tarea[index].setComponentOrientation(ComponentOrientation.)
				tarea[index].setAlignmentX(JTextArea.CENTER_ALIGNMENT);
				tarea[index].setAlignmentY(JTextArea.CENTER_ALIGNMENT);
			}
		});

		JButton rightalignButton=new JButton(new ImageIcon("images1/rightalign.png"));
		tb.add(rightalignButton);
		rightalignButton.setToolTipText("rightAlign");
		rightalignButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tarea[index].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
				tarea[index].setAlignmentX(JTextArea.RIGHT_ALIGNMENT);
				tarea[index].setAlignmentY(JTextArea.RIGHT_ALIGNMENT);
			}
		});

		JButton fontcolorButton=new JButton(new ImageIcon("images1/fontcolor.png"));
		tb.add(fontcolorButton);
		fontcolorButton.setToolTipText("fontcolor");
		fontcolorButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Color clr=JColorChooser.showDialog(null,"Select Color",new Color(255,0,0));
				tarea[index].setForeground(clr);
			}
		});


		JButton texthighlightButton=new JButton(new ImageIcon("images1/texthighlight.jpg"));
		tb.add(texthighlightButton);
		texthighlightButton.setToolTipText("texthighlight");
		texthighlightButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Color clr=JColorChooser.showDialog(null,"SelectHighlighter Color",new Color(255,0,0));
				tarea[index].setSelectionColor(clr);
			}
		});
		
		bottomToolbar.add(BtmXY);
		add(bottomToolbar);
//		bottomToolbar.setL
		bottomToolbar.setBounds(0,790,1530,25);
		
		add(tb);
		tb.setBounds(0,50,45,1085);
		add(tb1);
		tb1.setBounds(0,0,1085,45);
		tb.setVisible(true);
		tb1.setVisible(true);
		bottomToolbar.setVisible(true);



		
		
		fullscreen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				 txt= fullscreen.getText();

				if(txt=="Show Toolbar")
				{
					tb.setVisible(true);
					tb1.setVisible(true);
					fullscreen.setText("Hide Toolbar");
					txt="Hide Toolbar";
					pane.setBounds(50,50,1480,735);

				}
				else
				{
					tb.setVisible(false);
					tb1.setVisible(false);
					fullscreen.setText("Show Toolbar");
					txt="Show Toolbar";
					pane.setBounds(0,0,1525,783);
				}
			}
		});

	}
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				if(e.getActionCommand().equals("New"))
				{
					System.out.println("inside new");
					count=pane.getTabCount();
					System.out.println(count);
					tarea[count]=new JTextArea();
					
					tarea[count].addCaretListener(new CaretListener()
							{

								@Override
								public void caretUpdate(CaretEvent e) {
									// TODO Auto-generated method stub
										JTextArea editarea=(JTextArea)e.getSource();
										int linenum=1,columnnum=1;
									try {
										int caretpos=editarea.getCaretPosition();
										linenum=editarea.getLineOfOffset(caretpos);
										//System.out.println("Row:"+linenum);
										columnnum=caretpos-editarea.getLineStartOffset(linenum);
										linenum+=1;
										System.out.println("Row:"+linenum+"Col:"+columnnum);
										BtmXY.setText("Row:"+linenum+",Col:"+columnnum);
									}
									catch(Exception exp){
										System.out.println("Row:"+linenum+",Col:"+columnnum);
									}
								}
						
							});
					
					p[count]=new JScrollPane(tarea[count]);

					pane.addTab("Untitled",p[count]);
					JPanel pnlTab = new JPanel(new GridBagLayout());
					pnlTab.setOpaque(false);
					JLabel lblTitle = new JLabel("Untitled");
					JButton btnClose = new JButton(MetalIconFactory.getInternalFrameCloseIcon(16));
					btnClose.setMargin(new Insets(0,0,0,0));
					
					//btnClose.setVisible(false);

					GridBagConstraints gbc = new GridBagConstraints();
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.weightx = 1;

					pnlTab.add(lblTitle, gbc);

					gbc.gridx++;
					gbc.weightx = 0;
					pnlTab.add(btnClose, gbc);
                    pane.setTabComponentAt(count, pnlTab);
					add(pane);
					txt=fullscreen.getText();
					if(txt=="Show Toolbar")
						pane.setBounds(0,0,1525,783);
					else
						pane.setBounds(50,50,1480,735);
						
					count++;
					
					pane.addChangeListener(new ChangeListener()
							{
								public void stateChanged(ChangeEvent e)
								{
										Component c=(Component)e.getSource();
										//btnClose.setVisible(true);
									
										
								}
							});
					 btnClose.addMouseListener(new MouseAdapter()
					  {
					   @Override
					   public void mouseClicked(MouseEvent e)
					   {
//						   doYouWantToSave dlg=new doYouWantToSave(this);
//						   dlg.setVisible(true);
						   pane.removeTabAt(index);
						    pane.repaint();
					       length--;
					       count--;
					   }
					  });
			

				}


				else if(e.getActionCommand().equals("Open"))
				{

						//ta.setText("");
						//ta.setFont(new Font("Times new Roman",0,20));
						FileDialog dlg=new FileDialog(this,"Open");
						dlg.setMultipleMode(true);
						dlg.setVisible(true);


						FileName=dlg.getFiles();
						System.out.println("Files taken");
						File size[]=dlg.getFiles();
						System.out.println("Size array stored");
						length=length+size.length;
						System.out.println("Size of Length ="+length);

						//System.out.println("Length="+length);

						//tarea=new JTextArea[FileName.length];

						System.out.println("C="+c);
						for(i=0;i<size.length;i++)
						{
							if(i<=length)
							{

								System.out.println("-------------");
								System.out.println(FileName[i]);
								System.out.println("-------------");

								String fileContent=FileName[i].getName();
								System.out.println("-----------------------------");
								//String fContent=fileContent.toString();

								//tarea[i]=new JTextArea[FileName.length];
								tarea[count]=new JTextArea();

								tarea[count].addCaretListener(new CaretListener()
								{

									@Override
									public void caretUpdate(CaretEvent e) {
										// TODO Auto-generated method stub
											JTextArea editarea=(JTextArea)e.getSource();
											int linenum=1,columnnum=1;
										try {
											int caretpos=editarea.getCaretPosition();
											linenum=editarea.getLineOfOffset(caretpos);
											//System.out.println("Row:"+linenum);
											columnnum=caretpos-editarea.getLineStartOffset(linenum);
											linenum+=1;
											System.out.println("Row:"+linenum+"Col:"+columnnum);
											BtmXY.setText("Row:"+linenum+",Col:"+columnnum);
										}
										catch(Exception exp){
											System.out.println("Row:"+linenum+"Col:"+columnnum);
										}
									}
							
								});
								
								

								p[count]=new JScrollPane(tarea[count]);
								pane.addTab(fileContent,p[count]);
								JPanel pnlTab = new JPanel(new GridBagLayout());
								pnlTab.setOpaque(false);
								JLabel lblTitle = new JLabel(FileName[i].getName());
								JButton btnClose = new JButton(MetalIconFactory.getInternalFrameCloseIcon(16));
								btnClose.setMargin(new Insets(0,0,0,0));

								GridBagConstraints gbc = new GridBagConstraints();
								gbc.gridx = 0;
								gbc.gridy = 0;
								gbc.weightx = 1;

								pnlTab.add(lblTitle, gbc);

								gbc.gridx++;
								gbc.weightx = 0;
								pnlTab.add(btnClose, gbc);
			                    pane.setTabComponentAt(count, pnlTab);
								add(pane);
								
								/*JButton btnclose=new JButton("X");
								int count=pane.getTabCount();
								for(int j=0;j<count;j++)
								{
									pane.setTabComponentAt(j,btnclose);
								}*/
								if(txt=="Show Toolbar")
									pane.setBounds(0,0,1525,783);
								else
									pane.setBounds(50,50,1480,735);

								btnClose.addMouseListener(new MouseAdapter()
										{
											public void mouseClicked(MouseEvent e)
											{
												pane.removeTabAt(index);
												System.out.println("Inside MouseCLicked...");
												pane.repaint();
												length--;
												count--;
											}
										});
								System.out.println("heeyyyyy");
								BufferedReader fin =new BufferedReader(new FileReader(FileName[i]));
								System.out.println("-------------");
													String line=fin.readLine();
													while(line!=null)
													{
														tarea[count].append(line+"\n");
														line=fin.readLine();
													}

								fin.close();
								c++;
								count++;
							}
							else
								break;

						}




				}
				else if(e.getActionCommand().equals("Save As"))
				{
					FileDialog dlg=new FileDialog(this,"Saved As",FileDialog.SAVE);
					dlg.setMultipleMode(true);
					dlg.setVisible(true);

					File fileName[]=dlg.getFiles();
					setTitle("NoteItDown");

					for(int j=0;j<fileName.length;j++)
					{
						System.out.println("inside for");
						String text=tarea[index].getText();
						String title=fileName[j].getName();
						pane.setTitleAt(index,title);
						BufferedWriter fout =new BufferedWriter(new FileWriter(fileName[j]));
						fout.write(text);
						fout.close();
					}
				}
				else if(e.getActionCommand().equals("Print"))
				{
					try
					{
						ta.print();
					}
					catch(PrinterException exp)
					{
						System.out.println(e);
					}
				}
				else if(e.getActionCommand().equals("About"))
				{
					AboutDialog dlg=new AboutDialog(this);
					dlg.setVisible(true);
				}
				else if(e.getActionCommand().equals("Export to Pdf"))
				{
					FileDialog dlg=new FileDialog(this,"Saved As",FileDialog.SAVE);
					dlg.setMultipleMode(true);
					dlg.setVisible(true);
					File fileName[]=dlg.getFiles();
					for(int j=0;j<fileName.length;j++)
					{
						System.out.println("inside for");
						String text=tarea[index].getText();
						String title=fileName[j].getName();
					
						String Path=fileName[j]+".pdf";
						File file =new File(Path); 
						file.getParentFile().mkdirs();
						try
						{
							
							FileOutputStream fos =new FileOutputStream(Path);
							PdfWriter pdfwriter=new PdfWriter(fos);
						
							PdfDocument pdfdocument=new PdfDocument(pdfwriter); 
							pdfdocument.setDefaultPageSize(PageSize.A4);
							pdfdocument.addNewPage();
							Document pdfDoc=new Document(pdfdocument);
						
							pdfDoc.add(new Paragraph(new Text(tarea[index].getText())));
							pdfDoc.close();
						}
						catch(Exception E)
						{  
							System.out.println(E);
						}
					}
				
				}
				else if(e.getActionCommand().equals("Export to word"))
				{
					FileDialog dlg=new FileDialog(this,"Saved As",FileDialog.SAVE);
					dlg.setMultipleMode(true);
					dlg.setVisible(true);
					File fileName[]=dlg.getFiles();
					for(int j=0;j<fileName.length;j++)
					{
						try{
							FileOutputStream fs = new FileOutputStream(fileName[j]+".doc");
							OutputStreamWriter out = new OutputStreamWriter(fs);  
							out.write(tarea[index].getText());
							out.close();
						  	}
					    catch (IOException w){
						  		System.err.println(w);
						  	}
					}
				}
				else if(e.getActionCommand().equals("Export to zip"))
				{
					FileDialog dlg=new FileDialog(this,"Saved As",FileDialog.SAVE);
					dlg.setMultipleMode(true);
					dlg.setVisible(true);
					File fileName[]=dlg.getFiles();
					for(int j=0;j<fileName.length;j++)
					{
						try{
							FileOutputStream fs = new FileOutputStream(fileName[j]+".zip");
							ZipOutputStream zos = new ZipOutputStream(fs);
							zos.putNextEntry(new ZipEntry(file.getName()));
							 
				            byte[] bytes = Files.readAllBytes(Paths.get(""+fileName[j]));
				            zos.write(bytes, 0, bytes.length);
				            zos.closeEntry();
				            zos.close();
						  	}
					    catch (IOException w){
						  		System.err.println(w);
						  	}
					}

				}
				else
			  		System.exit(0);
			}
			catch(Exception exp)
			{
				System.out.println(exp);
			}

	}

	public static void main(String[]args)
	{
//		for(LookAndFeelInfo lafInfo: UIManager.getInstalledLookAndFeels())
//		{
//			System.out.println(lafInfo.getClassName());
//		}
		NoteItDown f=new NoteItDown();
		f.setDefaultCloseOperation(3);
		f.setVisible(true);
//		try {
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
		
	}
}
class AboutDialog extends JDialog
{
	AboutDialog(JFrame parent)
	{
		super(parent,"About");
		setSize(500,300);
		setLocation(600,600);
		setLayout(null);
		JLabel icon=new JLabel(new ImageIcon("images1/icon.jpg"));
		add(icon);
		icon.setBounds(15,15,50,60);
		JLabel l1=new JLabel("NoteItDown 1.0");
		l1.setFont(new Font("Cambria",Font.BOLD,14));
		add(l1);
		l1.setBounds(70,13,120,12);
		JLabel l=new JLabel("Developed By:- Yadnesh Wani");
		add(l);
		JLabel l3=new JLabel("                              Sanika Khirit");
		add(l3);
		JLabel l4=new JLabel("                              Sakshi Dherange");
		add(l4);
		JLabel l5=new JLabel("                              Samiksha Rodke");
		add(l5);
		l.setBounds(70,28,550,14);
		l.setFont(new Font("Cambria",0,14));
		l3.setBounds(70,41,550,14);
		l3.setFont(new Font("Cambria",0,14));
		l4.setBounds(70,55,550,14);
		l4.setFont(new Font("Cambria",0,14));
		l5.setBounds(70,69,550,14);
		l5.setFont(new Font("Cambria",0,14));

		
////		JLabel para=new JLabel("<html><p>\"NoteItDown is a Simple Text Editor Using Java which provide some advance feature as compare to Notepad\"</p></html>");
////		para.setBounds(5,85,490,100);
//		para.setFont(new Font("Cambira",Font.ITALIC,14));
//		add(para);
		JButton b=new JButton("OK");
		b.setBounds(380,200,65,40);
		b.setFont(new Font("Cambira",Font.BOLD,14));
		add(b);
		b.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		}); 
	}
}
//class doYouWantToSave extends JDialog
//{
//	doYouWantToSave(MouseAdapter mouseAdapter)
//	{
//		super();
//		setSize(500,300);
//		setLocation(600,600);
//		setLayout(null);
//		JLabel icon=new JLabel(new ImageIcon("images1/icon.jpg"));
//		add(icon);
//		icon.setBounds(15,15,50,60);
//		JLabel l1=new JLabel("Do You Want To Save?");
//		l1.setFont(new Font("Cambria",Font.BOLD,14));
//		add(l1);
//		l1.setBounds(100,13,200,12);
//		JButton yesButton=new JButton("YES");
//		add(yesButton);
//		yesButton.setBounds(300,200,65,40);
//		yesButton.setFont(new Font("Cambria",0,14));
//		
//		yesButton.addMouseListener(new SaveActionHandler());
//		JButton b=new JButton("NO");
//		b.setBounds(380,200,65,40);
//		b.setFont(new Font("Cambira",Font.BOLD,14));
//		add(b);
//		b.addMouseListener(new SaveActionHandler());
//		
//	}
//}
//class SaveActionHandler extends NoteItDown implements MouseListener {
//
//
//	@Override
//	public void mouseClicked(MouseEvent e) {
//		// TODO Auto-generated method stub
//		if("YES".equals(e.getSource()))
//		{
//		// TODO Auto-generated method stub
//			FileDialog dlg=new FileDialog(this,"Saved As",FileDialog.SAVE);
//			dlg.setMultipleMode(true);
//			dlg.setVisible(true);
//
//			File fileName[]=dlg.getFiles();
//			setTitle("NoteItDown");
//
//			for(int j=0;j<fileName.length;j++)
//			{
//				System.out.println("inside for index ="+index);
//				String text=tarea[index].getText();
//				String title=fileName[j].getName();
//				pane.setTitleAt(index,title);
//				try
//				{
//					BufferedWriter fout =new BufferedWriter(new FileWriter(fileName[j]));
//					fout.write(text);
//					fout.close();
//				}
//				catch(Exception exp)
//				{
//					System.out.println(""+e);
//				}
//			}
////		pane.removeTabAt(index);
////	    pane.repaint();
//		}
//		if("NO".equals(e.getActionCommand()))
//		{
//			System.out.println("Inside NO button Index="+index);
//			pane.removeTabAt(index);
//			pane.repaint();
//		}
//
//		
//	}
//
//	@Override
//	public void mousePressed(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseExited(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}	
//
//}