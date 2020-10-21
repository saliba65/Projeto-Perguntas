import java.lang.reflect.Constructor;
import java.io.RandomAccessFile;
import java.io.IOException;

public class CRUD2 <T extends Register> {

    Constructor<T> construtor;
    String nomeDoArquivo; 
    RandomAccessFile arquivo;
    HashExtensivel indexDireto;
    ArvoreBMais indexIndireto;

    public CRUD2(Constructor<T> construtor, String nomeDoArquivo) throws IOException {
      this.nomeDoArquivo = nomeDoArquivo;
      this.construtor = construtor;
      arquivo = new RandomAccessFile(nomeDoArquivo, "rw");
      try {
        indexDireto = new HashExtensivel(4,"diretorio.db","cestos.db");
        indexIndireto = new ArvoreBMais(5,"arvorebmais.db");
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
      if(arquivo.length()<4){
        arquivo.writeInt(0);
      }
    }

    public int create(T objeto) throws IOException { 

      arquivo.seek(0);
      int id = arquivo.readInt() + 1;
      objeto.setID(id);

      try {
          
        long pos = arquivo.length();
        arquivo.seek(pos);
        
        byte ba[] = objeto.toByteArray();
        arquivo.writeByte(0);
        arquivo.writeInt(ba.length);
        arquivo.write(ba);

        arquivo.seek(0);
        arquivo.writeInt(id);
        
        indexDireto.create(objeto.getID(),pos);
        indexIndireto.create(objeto.chaveSecundaria(),objeto.getID());

      } catch ( Exception e){
          System.out.println(e.getMessage());
      }

      return id;
    }
    
    public T read(int idChave) throws Exception {
      T objeto = construtor.newInstance();

      try {
        long pos = indexDireto.read(idChave);

        if (pos == -1) {
          objeto = null;
          throw new Exception("ERRO! Registro não encontrado\n");
        }

        arquivo.seek(pos);
        byte lapide = arquivo.readByte();
        
        if (lapide == 1){
          System.out.println("ERRO! Registro Excluído!\n");
        }

        int tamCampo = arquivo.readInt();
        byte[] ba = new byte[tamCampo];
        arquivo.read(ba);
        objeto.fromByteArray(ba);

      } catch(Exception e) {
          System.out.println(e.getMessage());
      }

      return objeto;
    }

    public T read(String chaveSecundaria) throws Exception {
      try {
        int ID = indexIndireto.read(chaveSecundaria); 
        if (ID!=-1) return read(ID);
      }catch(Exception e){}
      
      return null;
    }
  
    public boolean update(T objetoNovo) throws IOException {
      boolean ok = false;

    try {
        long pos = indexDireto.read(objetoNovo.getID());

        if (pos != -1){
            arquivo.seek(pos);

            byte lapide = arquivo.readByte();
            int tamAntigo = arquivo.readInt();
            byte[] baAntigo= new byte[tamAntigo];
            arquivo.read(baAntigo);

            T objetoAntigo = construtor.newInstance(); 
            objetoAntigo.fromByteArray(baAntigo);
      
            byte baNovo[] = objetoNovo.toByteArray();
            int tamNovo = baNovo.length;

            if (tamAntigo < tamNovo){
            arquivo.seek(pos);
            arquivo.writeByte(1);
    
            objetoNovo.setID(objetoAntigo.getID());

            pos = arquivo.length();
            arquivo.seek(pos);
        
            byte ba[] = objetoNovo.toByteArray();
            arquivo.writeByte(0);
            arquivo.writeInt(ba.length);
            arquivo.write(ba);

            indexDireto.update(objetoNovo.getID(),pos);
            ok = true;
          
            }else {
                arquivo.seek(pos+1);
                arquivo.writeInt(baNovo.length);
                arquivo.write(baNovo);
        
                ok = true;
            }

            if (!objetoNovo.chaveSecundaria().equals(objetoAntigo.chaveSecundaria())) {
            
                indexIndireto.update(objetoNovo.chaveSecundaria(),objetoNovo.getID());
            }

        } else {
            System.out.println("Registro não encontrado!");
        }
    } catch (Exception e) {
          System.out.println(e.getMessage());
    }

      return ok;
    }

    public boolean delete(int ID) throws IOException {
      
      boolean ok = false; // controle
      try {

        long pos = indexDireto.read(ID);
          
        arquivo.seek(pos);
        arquivo.writeByte(1);

        indexDireto.delete(ID);
        T objeto = read(ID);
        indexIndireto.delete(objeto.chaveSecundaria());

      } catch (Exception e){
          System.out.println(e.getMessage());
      }
      
      return ok;
    }
    
  }