import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinesFinder extends JFrame {
    private JPanel painelPrincipal;
    private JButton jogoFacilButton;
    private JButton jogoMedioButton;
    private JButton jogoDificilButton;
    private TabelaRecordes recordesFacil;
    private TabelaRecordes recordesMedio;
    private TabelaRecordes recordesDificil;

    private JButton btnSair;
    private JLabel valueFacil;
    private JLabel jogadorFacil;
    private JLabel jogadorMedio;
    private JLabel jogadorDificil;
    private JLabel valorMedio;
    private JLabel valorDificil;

    public MinesFinder(String title) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(painelPrincipal);
        pack();
        btnSair.addActionListener(this::btnSairActionPerformed);
        jogoFacilButton.addActionListener(this::btnFacilJogoPerformed);
        jogoMedioButton.addActionListener(this::btnJogoMedioActionPerformed);
        jogoDificilButton.addActionListener(this::btnJogoDificilActionPerformed);
         recordesFacil= new TabelaRecordes();
         recordesMedio= new TabelaRecordes();
         recordesDificil=new TabelaRecordes();
        lerRecordesDoDisco();
        jogadorFacil.setText(recordesFacil.getNome());
        valueFacil.setText(Long.toString(recordesFacil.getTempoJogo()/1000));
        jogadorMedio.setText(recordesMedio.getNome());
        valorMedio.setText(Long.toString(recordesMedio.getTempoJogo()/1000));
        jogadorDificil.setText(recordesDificil.getNome());
        valorDificil.setText(Long.toString(recordesDificil.getTempoJogo()/1000));
        recordesFacil.addTabelaRecordesListener(new TabelaRecordesListener() {
            @Override
            public void recordesActualizados(TabelaRecordes recordes) {
                recordesFacilActualizado(recordes);
            }
        });
        recordesMedio.addTabelaRecordesListener(new TabelaRecordesListener() {
            @Override
            public void recordesActualizados(TabelaRecordes recordes) {
                recordesMedioActualizado(recordes);
            }
        });
        recordesDificil.addTabelaRecordesListener(new TabelaRecordesListener() {
            @Override
            public void recordesActualizados(TabelaRecordes recordes) {
                recordesDificilActualizado(recordes);
            }
        });
    }
    private void btnSairActionPerformed(ActionEvent e) {
        guardarRecordesDisco();
        System.exit(0);
    }

    private void btnFacilJogoPerformed(ActionEvent e){
        var janela = new JanelaDeJogo(new CampoMinado(9,9, 10),recordesFacil);
        janela.setVisible(true); // se não foi executado no construtor…
    }

    private void btnJogoMedioActionPerformed(ActionEvent e) {
        var janela = new JanelaDeJogo(new CampoMinado(16,16, 40),recordesMedio);
        janela.setVisible(true); // se não foi executado no construtor…
    }
    private void btnJogoDificilActionPerformed(ActionEvent e) {
        var janela = new JanelaDeJogo(new CampoMinado(16,30, 90),recordesDificil);
        janela.setVisible(true); // se não foi executado no construtor…
    }

    public static void main(String[] args) {
        new MinesFinder("Mines Finder").setVisible(true);
    }

    private void recordesFacilActualizado(TabelaRecordes recordes) {
        jogadorFacil.setText(recordes.getNome());
        valueFacil.setText(Long.toString(recordes.getTempoJogo()/1000));
        guardarRecordesDisco();
    }
    private void recordesMedioActualizado(TabelaRecordes recordes) {
        jogadorMedio.setText(recordes.getNome());
        valorMedio.setText(Long.toString(recordes.getTempoJogo()/1000));
        guardarRecordesDisco();
    }
    private void recordesDificilActualizado(TabelaRecordes recordes) {
        jogadorDificil.setText(recordes.getNome());
        valorDificil.setText(Long.toString(recordes.getTempoJogo()/1000));
        guardarRecordesDisco();
    }
    private void guardarRecordesDisco() {
        ObjectOutputStream oos = null;
        try {
            File f =new
                    File(System.getProperty("user.home")+File.separator+"minesfinder.recordes");
            oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(recordesFacil);
            oos.writeObject(recordesMedio);
            oos.writeObject(recordesDificil);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void lerRecordesDoDisco() {
        ObjectInputStream ois = null;
        File f = new
                File(System.getProperty("user.home")+File.separator+"minesfinder.recordes");
        if (f.canRead()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                recordesFacil=(TabelaRecordes) ois.readObject();
                recordesMedio=(TabelaRecordes) ois.readObject();
                recordesDificil=(TabelaRecordes) ois.readObject();
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE,
                        null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }

}
