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
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import ledski.util.EasyMenuBar;
import ledski.util.EasyMenuBar.ItemAction;;

@SuppressWarnings("serial")
public class MainGUI extends JFrame {
	
	// COMPONENTES GERAIS
	private JFrame thisframe;
	private Font monoFont;
	final JFileChooser fileChooser = new JFileChooser();
	private File currentFile;
	
	// PAINEL DO RUNTIME
	private JPanel panelRuntime;
	private JTextArea txaRuntime;
	private JScrollPane scrRuntime;
	private JTextArea txaInput;
	private JButton btEditor;
	private JButton btReiniciar;
	
	// PAINEL DO EDITOR	
	private JPanel panelEditor;
	private JTextArea txaEditor;
	private JScrollPane scrEditor;
	private JPanel subpanelConsole;
	private JTextArea txaConsole;
	private JScrollPane scrConsole;
	private JButton btConsole;
	private JButton btAnalisar;
	private JButton btExecutar;
	
	
	
	
	
	public MainGUI() {
		super( "Askbot" );
		configurarJanela();
		registrarFonte();
		montarLayout();
		registrarEventosDeComponentes();
	}
	
	
	
	
	
	/**
	 * Faz as configurações relacionadas ao Frame (Janela):
	 * tamanho, posicionamento e inicializa a variável thisFrame.
	 */
	private void configurarJanela() {
		setPreferredSize( new Dimension( 800, 600) );
		setMinimumSize( new Dimension( 300, 500 ) );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		thisframe = this;

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
			
			monoFont = new Font("Noto Mono", Font.PLAIN, 14);
		}
		catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * Define o contentPane, define a barra de menu do frame
	 * e faz o posicionamento dos elementos
	 */
	private void montarLayout() {
		setJMenuBar( barraDeMenu() );
		buildPanelEditor();
		buildPanelRuntime();
		viewRuntime();
	}
	
	
	
	
	
	private void buildPanelRuntime() {
		
		// PAINEL
		panelRuntime = new JPanel();
		BoxLayout panelRuntimeLayout = new BoxLayout( panelRuntime, BoxLayout.Y_AXIS );
		panelRuntime.setLayout( panelRuntimeLayout );
		panelRuntime.setBorder( new EmptyBorder( 0,5,2,5 ) );
		
		// COMPONENTES
		txaRuntime = new JTextArea(10,50);
		txaRuntime.setForeground( Color.CYAN );
		txaRuntime.setBackground( Color.DARK_GRAY );
		txaRuntime.setCaretColor( Color.WHITE );
		txaRuntime.setMargin( new Insets( 10, 10, 10, 10 ) );
		txaRuntime.setFont( monoFont );
		txaRuntime.setLineWrap( true );
		txaRuntime.setEditable( true );
		txaRuntime.setText( WelcomeText.textoInicial() );
		
		scrRuntime = new JScrollPane( txaRuntime );
		scrRuntime.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrRuntime.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrRuntime.getVerticalScrollBar().setUnitIncrement(8);
		
		txaInput = new JTextArea(2,50);
		txaInput.setForeground( Color.WHITE );
		txaInput.setBackground( new Color(100,100,100) );
		txaInput.setCaretColor( Color.WHITE );
		txaInput.setMargin( new Insets( 10, 10, 10, 10 ) );
		txaInput.setFont( monoFont );
		txaInput.setLineWrap( true );
		preventVerticalStretch( txaInput );
		
		btEditor = new JButton( "Editor" );
		btReiniciar = new JButton( "Reiniciar script" );
		
		// POSICIONAMENTO
		panelRuntime.add( scrRuntime );
		panelRuntime.add( espacamento( 1 ) );
		panelRuntime.add( txaInput );
		panelRuntime.add( espacamento( 1 ) );
		panelRuntime.add( horizontalGroup( btEditor, btReiniciar ) );
		panelRuntime.add( espacamento( 1 ) );
	}
	
	
	
	
	
	/**
	 * Faz a configuração inicial de todos os elementos da GUI
	 */
	private void buildPanelEditor() {
		
		// PAINEL GERAL DO EDITOR
		panelEditor = new JPanel();
		BoxLayout mainPanelLayout = new BoxLayout( panelEditor, BoxLayout.Y_AXIS );
		panelEditor.setLayout( mainPanelLayout );
		panelEditor.setBorder( new EmptyBorder( 0,5,2,5 ) );
		
		// COMPONENTES DO EDITOR
		txaEditor = new JTextArea(10,50);
		txaEditor.setForeground( Color.WHITE );
		txaEditor.setBackground( Color.DARK_GRAY );
		txaEditor.setCaretColor( Color.WHITE );
		txaEditor.setMargin( new Insets( 10, 10, 10, 10 ) );
		txaEditor.setFont( monoFont );
		txaEditor.setLineWrap( true );
		
		scrEditor = new JScrollPane( txaEditor );
		scrEditor.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrEditor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrEditor.getVerticalScrollBar().setUnitIncrement(8);
		
		// SUBPAINEL DO CONSOLE
		subpanelConsole = new JPanel();
		BoxLayout panelConsoleLayout = new BoxLayout( subpanelConsole, BoxLayout.Y_AXIS);
		subpanelConsole.setLayout( panelConsoleLayout );
		
		// COMPONENTES DO SUBPAINEL DO CONSOLE
		txaConsole = new JTextArea(2,50);
		txaConsole.setLineWrap( true );
		txaConsole.setEditable( true );
		txaConsole.setFont( monoFont );
		txaConsole.setForeground( Color.BLACK );
		txaConsole.setBackground( Color.LIGHT_GRAY );
		txaConsole.setCaretColor( Color.WHITE );
		txaConsole.setMargin( new Insets( 10, 10, 10, 10 ) );
		
		scrConsole = new JScrollPane( txaConsole );
		scrConsole.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrConsole.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrConsole.getVerticalScrollBar().setUnitIncrement(8);
		preventVerticalStretch( scrConsole );
		
		btConsole = new JButton("Mostrar Console");
		btAnalisar = new JButton("Analisar Código");
		btExecutar = new JButton("Executar Script");
		
		
		// POSICIONAMENTO
		panelEditor.add( scrEditor );		
		panelEditor.add( espacamento( 1 ) );
		panelEditor.add( subpanelConsole );
		panelEditor.add( horizontalGroup( btConsole, btAnalisar, btExecutar ) );
		panelEditor.add( espacamento( 1 ) );
		
		subpanelConsole.add( scrConsole );
		subpanelConsole.add( espacamento( 1 ) );
		subpanelConsole.setVisible( false );
		
		
		// CAIXAS DE DIALOGO
		File workingDirectory = new File(System.getProperty("user.dir"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de código do Askbot", "ask");
		fileChooser.setCurrentDirectory(workingDirectory);
		fileChooser.setFileFilter(filter);
	}
	
	
	
	
	
	/**
	 * Configura os eventos de input de usuário.
	 */
	private void registrarEventosDeComponentes() {
		thisframe.addWindowListener( new WindowAdapter() { public void windowOpened( WindowEvent e ){
			txaEditor.requestFocus();
		}});

		btConsole.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent e ) {
			subpanelConsole.setVisible( !subpanelConsole.isVisible() );
			btConsole.setText( subpanelConsole.isVisible() ? "Esconder Console" : "Mostrar Console" );
			thisframe.repaint();
			thisframe.revalidate();
		}});

		btAnalisar.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent e ) {
			// Analisar codigo
		}});
		
		btEditor.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent e ) {
			viewEditor();
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

		.menu( "Exibir" )
		.item( "Editor de código" ).onClick( "showEditor" )
		.item( "Terminal de execução" ).onClick( "showRuntime" )
		
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
			thisframe.setTitle( "Askbot - Novo Script" );
			viewEditor();
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
			thisframe.setTitle( "Askbot - Novo Script" );
		}})
		.action( "showEditor", new ItemAction() { public void onClick( ActionEvent e, Object[] args ) {
			viewEditor();
		}})
		.action( "showRuntime", new ItemAction() { public void onClick( ActionEvent e, Object[] args ) {
			viewRuntime();
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
	
	
	private void viewEditor() {
		thisframe.setContentPane( panelEditor );
		txaEditor.requestFocus();
		thisframe.revalidate();
		thisframe.repaint();
	}
	
	private void viewRuntime() {
		thisframe.setContentPane( panelRuntime );
		txaInput.requestFocus();
		thisframe.revalidate();
		thisframe.repaint();
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
	
	
	
	
	
	private JPanel horizontalGroup( Component ...cs ) {
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
		return pH;
	}

}
