import java.io.Serializable;
import java.util.ArrayList;

public class TabelaRecordes implements Serializable {
    private String nome;
    private long tempoJogo;
    private transient ArrayList<TabelaRecordesListener> listeners;

    public TabelaRecordes(){
        listeners=new ArrayList<>();
        nome="Anónimo";
        tempoJogo=9999999;
    }

    private void notifyRecordesActualizados() {
        if (listeners != null) {
            for (TabelaRecordesListener list : listeners)
                list.recordesActualizados(this);
        }
    }

    public void addTabelaRecordesListener(TabelaRecordesListener list) {
        if (listeners == null) listeners = new ArrayList<>();
        listeners.add(list);
    }

    public void removeTabelaRecordesListener(TabelaRecordesListener list) {
        if (listeners != null) listeners.remove(list);
    }

    public void setRecorde(String nome, long tempoJogo){
        if(tempoJogo<this.tempoJogo){
            this.nome=nome;
            this.tempoJogo=tempoJogo;
            notifyRecordesActualizados();
        }
        return;
    }

    public long getTempoJogo() {
        return tempoJogo;
    }

    public String getNome() {
        return nome;
    }
}
