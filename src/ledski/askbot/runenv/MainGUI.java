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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
	
	private JFrame thisframe;
	private JPanel mainPanel;
	private JTextArea txa1;
	private JScrollPane scrollPane1;
	private JTextArea txa2;
	private JScrollPane scrollPane2;
	private JTextField txtInput;
	private JButton btAvancar;
	private JPanel endPanel;
	private Font monoFont;
	
	final JFileChooser fc = new JFileChooser();
	
	public MainGUI() {
		super( "Askbot" );
		configurarJanela();
		registrarFonte();
		comfigComponentes();
		montarLayout();
		acoplarEventos();
		
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
			InputStream fontInputStream= getClass().getResourceAsStream("/resources/RobotoMono-Regular.ttf" );
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
		mainPanel.add( scrollPane1 );
		espacamento( 1 );
		mainPanel.add( scrollPane2 );
//		espacamento( 1 );
//		adicionar( txtInput );
		espacamento( 1 );
		adicionar( btAvancar );
		espacamento( 1 );
		
		// AREA EXPANSÍVEL
//		mainPanel.add( endPanel );
	}
	
	
	private void comfigComponentes() {
		
		mainPanel = new JPanel();
		BoxLayout mainPanelLayout = new BoxLayout( mainPanel, BoxLayout.Y_AXIS );
		mainPanel.setLayout( mainPanelLayout );
		mainPanel.setBorder( new EmptyBorder( 0,7,2,7 ) );
		
		
		monoFont = new Font("Roboto Mono", Font.PLAIN, 14);
		
		
		txa1 = new JTextArea(19,50);
		txa1.setLineWrap( true );
//		txaCode.setEditable( false );
		txa1.setFont( monoFont );
//		txaOut.setBorder( BorderFactory.createLineBorder( Color.BLACK, 1 ));
		txa1.setForeground( Color.WHITE );
		txa1.setBackground( Color.DARK_GRAY );
		txa1.setCaretColor( Color.WHITE );
		txa1.setMargin( new Insets( 10, 10, 10, 10 ) );
		
		scrollPane1 = new JScrollPane( txa1 );
		scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane1.getVerticalScrollBar().setUnitIncrement(8);
//		scrollPane.setViewportView( txaOut );
		
		
		txa2 = new JTextArea(2,50);
		txa2.setLineWrap( true );
//		txa2.setEditable( false );
		txa2.setFont( monoFont );
//		textAreaError.setBorder( BorderFactory.createLineBorder( Color.BLACK, 1 ));
		txa2.setForeground( Color.BLACK );
		txa2.setBackground( Color.LIGHT_GRAY );
		txa2.setCaretColor( Color.WHITE );
		txa2.setMargin( new Insets( 10, 10, 10, 10 ) );
		
		scrollPane2 = new JScrollPane( txa2 );
		scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane2.getVerticalScrollBar().setUnitIncrement(8);
//		scrollPane.setViewportView( txaOut );

		
		txtInput = new JTextField("Responda aqui");
		txtInput.setBackground( Color.DARK_GRAY );
		txtInput.setForeground( Color.WHITE );
		txtInput.setCaretColor( Color.WHITE );
		txtInput.setBorder( new EmptyBorder( new Insets( 5, 5, 5, 5 ) ) );
		txtInput.setMargin( new Insets( 5, 5, 5, 5 ) );
		
		btAvancar = new JButton("Avançar");
		
		
		// AREA EXPANSIVEL
		// ## alow for texareas to expand down when pressing enter after preventVerticalStretch has been applied
		endPanel = new JPanel( new GridBagLayout() );
		endPanel.setOpaque( false );
		//endPanel.setBackground( new Color( 0,0,0,0 ) );
		
		File workingDirectory = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(workingDirectory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de código do Askbot", "ask");
		fc.setFileFilter(filter);
		
	}
	
	
	private void acoplarEventos() {
		btAvancar.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				scrollPane2.setVisible( false );
				mainPanel.revalidate();
			}
		} );
		
		thisframe.addWindowListener( new WindowAdapter() {
			public void windowOpened( WindowEvent e ){
				txtInput.requestFocus();
			}
		}); 
	}
	
	
	/** 
	 * Cria a barra de menus
	 */
	private JMenuBar barraDeMenu() {
		
		EasyMenuBar menubar = new EasyMenuBar();
		
		menubar
		
		// ITENS DO MENU
		.menu( "Arquivo" )
		.item( "Criar um novo Askbot" )
		.item( "Abrir..." ).onClick( "abrir" )
		.item( "Abrir e executar..." )

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
		.action( "abrir", new ItemAction() { public void onClick( ActionEvent e, Object[] args ) {
			int a = fc.showOpenDialog( thisframe );
			if( a == JFileChooser.APPROVE_OPTION ) {
				File file = fc.getSelectedFile();
				List<String> fileContents;
				txa1.setText( null );
				try {
					fileContents = Files.readAllLines( Paths.get( file.toURI() ) );
					fileContents.forEach( line -> txa1.append( line + "\n" ) );
					txa1.setCaretPosition( 0 );
				} catch( IOException e1 ) {
					txa1.append( "Não foi possível ler o aquivo selecionado. Verifique se é um arquivo de texto." );
					e1.printStackTrace();
				}
				
			}
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
	
	
	// ====================================================================================================================
	// MÉTODOS UTILITARIOS
	//=====================================================================================================================
	
	
	
	public void centralizarNaTela() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
	}
	
	
	
	
	
	private void espacamento( int s ) {
		mainPanel.add( Box.createRigidArea(new Dimension(5*s,5*s)) );
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
				pH.add( cs[i], new GridBagConstraints(i,0,1,1,0,0,GridBagConstraints.LINE_START,GridBagConstraints.NONE,new Insets(0,0,0,10),1,1) );
			}
		}
		if( cs.length > 1 )
			pH.add( new JLabel(), new GridBagConstraints(cs.length,0,1,1,1,1,GridBagConstraints.LINE_START,GridBagConstraints.BOTH,new Insets(0,0,0,0),1,1) );
		mainPanel.add( pH );
	}

}
