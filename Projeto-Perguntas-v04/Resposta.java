import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;

public class Resposta implements Register{
    
    protected int idUsuario;
    protected int idPergunta;
    protected int idResposta;
    protected long criacao;
    protected short nota;
    protected String resposta;
    protected boolean ativa;

    public Resposta (int iu, int ip, int ir, long c, short n, String r, boolean a) {
        this.idUsuario = iu;
        this.idPergunta = ip;
        this.idResposta = ir;
        this.criacao = c;
        this.nota = n;
        this.resposta = r;
        this.ativa = a;
    }

    public Resposta() {
        this.idUsuario = -1;
        this.idPergunta = -1;
        this.idResposta = -1;
        this.criacao = -1;
        this.nota = -1;
        this.resposta = "";
        this.ativa = true;
    }

    public String toString() {
        String resp = "";
        SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy' 'HH':'mm':'ss");

        resp += "\nResposta: " + resposta + "\nNota: " + nota + "\nAtiva: ";
        if (this.ativa == true){
            resp += "sim";
        } else resp += "não";
        resp += "\nCriação: " + d.format(criacao);
        
        return resp;
    }

    public int getID() {
        return this.idResposta;
    }

    public void setID(int idResposta) {
        this.idResposta = idResposta;
    }

    public int getIDPergunta() {
        return this.idPergunta;
    }

    public void setIDPergunta(int idPergunta) {
        this.idPergunta = idPergunta;
    }

    public int getIDUsuario() {
        return this.idUsuario;
    }

    public void setIDUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCriacao() {
        SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy' 'HH':'mm':'ss");

        return d.format(criacao);
    }

    public void setCriacao(long c) {
        this.criacao = c;
    }

    public short getNota() {
        return this.nota;
    }

    public void setNota(short nota){
        this.nota = nota;
    }

    public String getResposta(){
        return this.resposta;
    }

    public void setResposta(String resposta){
        this.resposta = resposta;
    }

    public boolean getAtiva() {
        return this.ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeInt(idUsuario);
            dos.writeInt(idPergunta);
            dos.writeInt(idResposta);
            dos.writeLong(criacao);
            dos.writeShort(nota);
            dos.writeUTF(resposta);
            dos.writeBoolean(ativa);
        } catch (Exception e) {
        }

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        try {
            this.idUsuario = dis.readInt();
            this.idPergunta = dis.readInt();
            this.idResposta = dis.readInt();
            this.criacao = dis.readLong();
            this.nota = dis.readShort();
            this.resposta = dis.readUTF();
            this.ativa = dis.readBoolean();
            
        } catch (Exception e) {
        }

    }

    public String chaveSecundaria(){
        return null;
    }
}