import java.io.IOException;

// Interface Registro

public interface Register {
  public int getID();

  public void setID(int n);

  public String getEmail();

  public void setEmail(String email);

  public String getSenha();

  public void setSenha(String senha);

  public String chaveSecundaria();

  public byte[] toByteArray() throws IOException;

  public void fromByteArray(byte[] ba) throws IOException;
}