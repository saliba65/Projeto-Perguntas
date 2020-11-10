import java.io.IOException;

// Interface Registro

public interface Register {

  public int getID();

  public void setID(int ID);

  public int getIDpergunta();

  public void setIDpergunta(int ID);

  public int getIDusuario();

  public void setIDusuario(int ID);

  public long getCriacao();

  public void setCriacao(long criacao);

  public short getNota();

  public void setNota(short nota);

  public String getPergunta();

  public void setPergunta(String pergunta);

  public boolean getAtiva();

  public void setAtiva(boolean ativa);

  public byte[] toByteArray() throws IOException;

  public void fromByteArray(byte[] ba) throws IOException;

}