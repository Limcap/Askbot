package ledski.askbot.runenv;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import ledski.util.EasyMenuBar;
import ledski.util.EasyMenuBar.ItemAction;;

@SuppressWarnings("serial")
public class MainGUI extends JFrame {
	
	// COMPONENTES GERAIS
	private JFrame thisframe;
	private JPanel mainPanel;
	private Font monoFont;
	final JFileChooser fileChooser = new JFileChooser();
	private File currentFile;
	
	// PAINEL DO RUNTIME
	private JPanel panelRuntime;
	private JTextArea txaRuntime;
	private JTextArea txaInput;
	private JButton btEditor;
	private JButton btReiniciar;
	
	// PAINEL DO EDITOR
	private JPanel panelEditor;
	private JTextArea txaEditor;
	private JScrollPane scrollPaneEditor;
	private JPanel panelConsole;
	private JTextArea txaConsole;
	private JScrollPane scrollPaneConsole;
	private JButton btConsole;
	private JButton btAnalisar;
	private JButton btExecutar;
	
	
	
	
	
	public MainGUI() {
		super( "Askbot" );
		configurarJanela();
		registrarFonte();
		configComponentes();
		montarLayout();
		eventosDeComponentes();
	}
	
	
	
	
	
	/**
	 * Faz as configurações relacionadas ao Frame (Janela):
	 * tamanho, posicionamento e inicializa a variável thisFrame.
	 */
	private void configurarJanela() {
		
		thisframe = this;
		setPreferredSize( new Dimension( 800, 600) );
		setMinimumSize( new Dimension( 300, 500 ) );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		pack();
		centralizarNaTela();
	}
	
	
	
	
	
	/**
	 * Registra uma fonte para poder ser usada nos componentes
	 */
	private void registrarFonte() {
		
		// como listar fontes disponíveis: for( String f : ge.getAvailableFontFamilyNames() ) System.out.println( f );
		try {
			// O jeito de carregar a fonte abaixo só funciona no projeto. Em um arquivo JAR deve ser feito através do getResourcesAsStrem
			// Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("./res/RobotoMono-Regular.ttf")).deriveFont(12f);
			
			// Carregar o arquivo de fonte. Funciona em um JAR 
			InputStream fontInputStream= getClass().getResourceAsStream("/resources/NotoMono-Regular.ttf" );
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontInputStream ).deriveFont(12f);
			
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(customFont);
		} catch (IOException e) {
			e.printStackTrace();
		} catch(FontFormatException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * Define o contentPane, define a barra de menu do frame
	 * e faz o posicionamento dos elementos
	 */
	private void montarLayout() {
		
		// JANELA
		setJMenuBar( barraDeMenu() );
		setContentPane( mainPanel );
		
		// PAINEL PRINCIPAL
		espacamento( 1 );
		mainPanel.add( scrollPaneEditor );
		
		mainPanel.add( panelConsole );
		panelConsole.add( espacamento( 1 ) );
		panelConsole.add( scrollPaneConsole );

//		adicionar( txtInput );
		mainPanel.add( espacamento( 1 ) );
		adicionar( btConsole, btAnalisar, btExecutar );
		mainPanel.add( espacamento( 1 ) );
		
		// AREA EXPANSÍVEL
//		mainPanel.add( endPanel );
	}
	
	
	
	
	
	/**
	 * Faz a configuração inicial de todos os elementos da GUI
	 */
	private void configComponentes() {
		
		// PAINEL GERAL
		mainPanel = new JPanel();
		BoxLayout mainPanelLayout = new BoxLayout( mainPanel, BoxLayout.Y_AXIS );
		mainPanel.setLayout( mainPanelLayout );
		mainPanel.setBorder( new EmptyBorder( 0,5,2,5 ) );
		monoFont = new Font("Noto Mono", Font.PLAIN, 14);
		
		
		// PAINEL DO EDITOR
		txaEditor = new JTextArea(10,50);
		txaEditor.setForeground( Color.WHITE );
		txaEditor.setBackground( Color.DARK_GRAY );
		txaEditor.setCaretColor( Color.WHITE );
		txaEditor.setMargin( new Insets( 10, 10, 10, 10 ) );
		txaEditor.setFont( monoFont );
		txaEditor.setLineWrap( true );
//		txaEditor.setEditable( false );
		txaEditor.setText( WelcomeText.textoInicial() );
		
		scrollPaneEditor = new JScrollPane( txaEditor );
		scrollPaneEditor.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneEditor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneEditor.getVerticalScrollBar().setUnitIncrement(8);
		
		
		// PAINEL DO CONSOLE
		panelConsole = new JPanel();
		BoxLayout panelConsoleLayout = new BoxLayout( panelConsole, BoxLayout.Y_AXIS);
		panelConsole.setLayout( panelConsoleLayout );
		preventVerticalStretch( panelConsole );
		
		txaConsole = new JTextArea(2,50);
		txaConsole.setLineWrap( true );
		txaConsole.setEditable( true );
		txaConsole.setFont( monoFont );
		txaConsole.setForeground( Color.BLACK );
		txaConsole.setBackground( Color.LIGHT_GRAY );
		txaConsole.setCaretColor( Color.WHITE );
		txaConsole.setMargin( new Insets( 10, 10, 10, 10 ) );
		
		scrollPaneConsole = new JScrollPane( txaConsole );
		scrollPaneConsole.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneConsole.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneConsole.getVerticalScrollBar().setUnitIncrement(8);
		
		panelConsole.setVisible( false );
		
		
		// PAINEL DOS BOTOES
		btConsole = new JButton("Mostrar Console");
		btAnalisar = new JButton("Analisar Código");
		btExecutar = new JButton("Executar Código");
		
		
		// CAIXAS DE DIALOGO
		File workingDirectory = new File(System.getProperty("user.dir"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de código do Askbot", "ask");
		fileChooser.setCurrentDirectory(workingDirectory);
		fileChooser.setFileFilter(filter);
	}
	
	
	
	
	
	/**
	 * Configura os eventos de input de usuário.
	 */
	private void eventosDeComponentes() {
		thisframe.addWindowListener( new WindowAdapter() { public void windowOpened( WindowEvent e ){
			txaEditor.requestFocus();
		}});

		btConsole.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent e ) {
			panelConsole.setVisible( !panelConsole.isVisible() );
			btConsole.setText( panelConsole.isVisible() ? "Esconder Console" : "Mostrar Console" );
			mainPanel.repaint();
			mainPanel.revalidate();
		}});

		btAnalisar.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent e ) {
			// Analisar codigo
		}});
	}
	
	
	
	
	
	
	/** 
	 * Cria a barra de menus
	 */
	private JMenuBar barraDeMenu() {
		
		EasyMenuBar menubar = new EasyMenuBar();
		
		menubar
		
		// ITENS DO MENU
		.menu( "Arquivo" )
		.item( "Criar um novo Askbot" ).onClick( "new" )
		.item( "Abrir..." ).onClick( "open" )
		.item( "Salvar" ).onClick( "save" )
		.item(  "Salvar como..." ).onClick( "saveAs" )
		.item(  "Fechar" ).onClick( "close" )

		.menu( "Código" )
		.item( "Editar" )
		.item( "Executar" )
		
		.menu( "Janela" )
		
		.submenu( "Tema do terminal" )
		.item( "Claro" )
		.item( "Escuro" )
		
		.parent()
		.submenu( "Tema do editor" )
		.item( "Claro" )
		.item( "Escuro" )
		
		.parent()
		.item( "Resetar tamanho" ).onClick( "pack" )
		
		.menu( "Ajuda" )
		.item( "Como usar" )
		.item( "Sobre" )
//		
		.action( "new", new ItemAction() { public void onClick( ActionEvent e, Object[] args ) {
			currentFile = null;
			txaConsole.setText( null );
			txaEditor.setText( null );
			thisframe.setTitle( "Askbot - Sem Nome" );
		}})
		.action( "open", new ItemAction() { public void onClick( ActionEvent e, Object[] args ) {
			int a = fileChooser.showOpenDialog( thisframe );
			if( a == JFileChooser.APPROVE_OPTION ) {
				currentFile = fileChooser.getSelectedFile();
				List<String> fileContents;
				txaEditor.setText( null );
				try {
					fileContents = Files.readAllLines( Paths.get( currentFile.toURI() ), StandardCharsets.UTF_8 );
					fileContents.forEach( line -> txaEditor.append( line + "\n" ) );
					txaEditor.setCaretPosition( 0 );
					thisframe.setTitle( "Askbot - " + currentFile.getName() );
				} catch( IOException e1 ) {
					txaEditor.append( "Não foi possível ler o aquivo selecionado. Verifique se é um arquivo de texto." );
					e1.printStackTrace();
				}
				
			}
		}})
		.action( "save", new ItemAction() { public void onClick( ActionEvent e, Object[] args ) {
			arquivoSalvar();
		}})
		.action( "saveAs", new ItemAction() { public void onClick( ActionEvent e, Object[] args ) {
			arquivoSalvarComo();
		}})
		.action( "close", new ItemAction() { public void onClick( ActionEvent e, Object[] args ) {
			currentFile = null;
			txaConsole.setText( null );
			txaEditor.setText( null );
			thisframe.setTitle( "Askbot - Sem Nome" );
		}})
//		// ACOES DO MENU
//		.action( "fill", new ItemAction() { public void onClick( ActionEvent e, Object[] args ) {
//			if( args.length > 0 && args[0] != null ) txaOut.setText( (String) args[0] );
//			if( args.length > 1 && args[1] != null ) txaRestricoes.setText( (String) args[1] );
//			if( args.length > 2 && args[2] != null ) txaSolution.setText( (String) args[2] );
//		}})
//		.action( "pack", new ItemAction() { public void onClick( ActionEvent e, Object[] args ) {
//			thisFrame.pack();
//		}})
		;
		
		return menubar;
	}
	
	
	// =================================================================================================================
	// MÉTODOS DE ABRIR E SALVAR ARQUIVO
	//==================================================================================================================
	
	
	
	private void arquivoSalvar() {
		if( currentFile == null ) {
			arquivoSalvarComo();
		} else try {
			BufferedWriter writer = new BufferedWriter( new FileWriter( currentFile ) );
			writer.write( txaEditor.getText() );
			writer.close();
		}
		catch( IOException e ) {
			txaConsole.setText( "Não foi possível salvar o arquivo" );
			e.printStackTrace();
		}
	}
	
	
	
	private void arquivoSalvarComo() {
		int selected = fileChooser.showSaveDialog( thisframe );
		if( selected == JFileChooser.APPROVE_OPTION ) {
			File f = fileChooser.getSelectedFile();
			if( !f.isDirectory() ) {
				if( !f.getAbsolutePath().endsWith( ".ask" ))
					f = new File( f.getAbsolutePath() + ".ask" );
				if( f.exists() ) {
					int i = JOptionPane.showConfirmDialog( thisframe, "O arquivo selecionado já existe. Substituí-lo?" );
					if( i == JOptionPane.YES_OPTION ) {
						currentFile = f;
						arquivoSalvar();
					}
				}
				else {
					currentFile = f;
					arquivoSalvar();
				}
				thisframe.setTitle( "Askbot - " + f.getName() );
			}
		}
	}
	
	
	
	// =================================================================================================================
	// MÉTODOS UTILITARIOS
	//==================================================================================================================
	
	
	
	public void centralizarNaTela() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
	}
	
	
	
	
	
	private Component espacamento( int s ) {
		return Box.createRigidArea(new Dimension(5*s,5*s));
	}
	
	
	
	
	
	private void preventVerticalStretch( Component c ) {
		c.setMaximumSize( new Dimension( Integer.MAX_VALUE, c.getPreferredSize().height ) );
	}
	
	
	
	
	
	private void adicionar( Component ...cs ) {
		JPanel pH = new JPanel();
		pH.setOpaque( false );
		pH.setLayout( new GridBagLayout() );
		preventVerticalStretch( pH );
		for( int i = 0; i < cs.length; i++ ) {//Component c : cs ) {
			if( cs.length == 1 ) {
				pH.add( cs[i], new GridBagConstraints(i,0,1,1,1,1,GridBagConstraints.LINE_START,GridBagConstraints.BOTH,new Insets(0,0,0,0),1,1) );	
			}
			else {
				pH.add( cs[i], new GridBagConstraints(i,0,1,1,1,0,GridBagConstraints.LINE_START,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,5),1,1) );
			}
		}
//		if( cs.length > 1 )
//			pH.add( new JLabel(), new GridBagConstraints(cs.length,0,1,1,1,1,GridBagConstraints.LINE_START,GridBagConstraints.BOTH,new Insets(0,0,0,0),1,1) );
		mainPanel.add( pH );
	}

}
