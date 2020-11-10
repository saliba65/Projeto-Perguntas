import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.DecimalFormat;

public class Usuario //implements Register 
{
    
    protected int idUsuario;
    protected String nome;
    protected String email;
    protected String senha;

    public Usuario (int i, String n, String e, String s) {
        this.idUsuario = i;
        this.nome = n;
        this.email = e;
        this.senha = s;
    }

    public Usuario() {
        this.idUsuario = -1;
        this.nome = "";
        this.email = "";
        this.senha = "";
    }

    public String toString() {
        return "\nNome de Usuario: " + nome + "\nEmail: " + email + "\nSenha: " + senha;
    }

    public int getID() {
        return this.idUsuario;
    }

    public void setID(int ID) {
        this.idUsuario = ID;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getSenha(){
        return this.senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeInt(idUsuario);
            dos.writeUTF(nome);
            dos.writeUTF(email);
            dos.writeUTF(senha);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        try {
            this.idUsuario = dis.readInt();
            this.nome = dis.readUTF();
            this.email = dis.readUTF();
            this.senha = dis.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String chaveSecundaria(){
        return this.email;
    }
}