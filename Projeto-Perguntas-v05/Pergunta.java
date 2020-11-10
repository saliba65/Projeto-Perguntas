import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.DecimalFormat;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Pergunta implements Register {
    
    protected String data;
    protected int id;
    protected int idPergunta;
    protected int idUsuario;
    protected long criacao;
    protected short nota;
    protected String pergunta;
    protected boolean ativa;
    protected String palavrasChave;

    public Pergunta (int iu, int ip, long c, short n, String p, boolean a, String pC) {
        this.idUsuario = iu;
        this.idPergunta = ip;
        this.criacao = c;
        this.nota = n;
        this.pergunta = p;
        this.ativa = a;
        this.palavrasChave = pC;
    }

    public Pergunta() {
        this.idUsuario = -1;
        this.idPergunta = -1;
        this.criacao = -1;
        this.nota = -1;
        this.pergunta = "";
        this.ativa = true;
        this.palavrasChave = "";
    }

    public String getDateTime() {
      DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      Date date = new Date();
      return dateFormat.format(date);
    }

    public int getID() {
        return this.id;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public int getIDpergunta() {
        return this.idPergunta;
    }

    public void setIDpergunta(int ID) {
        this.idPergunta = ID;
    }

    public int getIDusuario() {
        return this.idUsuario;
    }

    public void setIDusuario(int ID) {
        this.idUsuario = ID;
    }

    public long getCriacao() {
        return this.criacao;
    }

    public void setCriacao(long criacao) {
        this.criacao = criacao;
    }

    public short getNota() {
        return this.nota;
    }

    public void setNota(short nota) {
        this.nota = nota;
    }

    public String getPergunta() {
        return this.pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public boolean getAtiva() {
        return this.ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public void setPalavrasChave(String pC){
        this.palavrasChave = pC;
    }

    public String getPalavrasChave(){
        return this.palavrasChave;
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeInt(idPergunta);
            dos.writeInt(idUsuario);
            dos.writeUTF(data);
            dos.writeLong(criacao);
            dos.writeShort(nota);
            dos.writeUTF(pergunta);
            dos.writeBoolean(ativa);
            dos.writeUTF(palavrasChave);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        try {
            this.idPergunta = dis.readInt();
            this.idUsuario = dis.readInt();
            this.data = dis.readUTF();
            this.criacao = dis.readLong();
            this.nota = dis.readShort();
            this.pergunta = dis.readUTF();
            this.ativa = dis.readBoolean();
            this.palavrasChave = dis.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String chaveSecundaria(){
        return null;
    }
}