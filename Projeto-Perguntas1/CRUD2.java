import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.io.*;

//Classe CRUD Indexado
// Responsável por realizar operações de criação, leitura, escrita e exclusão no banco de dados.

public class CRUD2<T extends Register> {
  protected Constructor<T> construtor;
  protected String fileName;
  protected String diretorio;
  protected String cestos;
  protected String arvoreB;
  private HashExtensivel hash;
  private ArvoreBMais_String_Int arvore;

  public CRUD2(Constructor<T> construtor, String fn) throws Exception {
    this.construtor = construtor;
    fileName = fn;

    // Cria os arquivos índices
    diretorio = fn;
    String[] temp = fn.split("\\.");
    diretorio = temp[0] + ".diretorio.idx";
    cestos = temp[0] + ".cestos.idx";
    arvoreB = temp[0] + ".arvore.idx";

    // apaga o arquivo anterior
    new File(diretorio).delete();
    new File(cestos).delete();

    RandomAccessFile arq = new RandomAccessFile(fileName, "rw");
    arq.writeInt(0);
    arq.close();

    // Inicia as estruturas Hash e Arvore
    hash = new HashExtensivel(10, diretorio, cestos);
    arvore = new ArvoreBMais_String_Int(10, arvoreB);

  }

  public int create(T obj) throws Exception {
    RandomAccessFile arq = new RandomAccessFile(fileName, "rw");

    // Conversao para vetor de bytes
    int id = arq.readInt() + 1;
    String chaveSecundaria = obj.chaveSecundaria();
    obj.setID(id);
    byte[] ba = obj.toByteArray();

    // Criacao do registro
    arq.seek(arq.length());
    long pos = arq.getFilePointer();
    arq.writeByte(' ');
    arq.writeInt(ba.length);
    arq.write(ba);

    // Salva ultima posicao
    arq.seek(0);
    arq.writeInt(id);

    hash.create(id, pos);
    arvore.create(chaveSecundaria, id);

    arq.close();

    return id;
  }

  public T read(int id) throws Exception {
    Long pos = hash.read(id);

    T obj = this.construtor.newInstance();

    // Posicao retornada pelo indice
    RandomAccessFile arq = new RandomAccessFile(fileName, "rw");
    boolean found = false;

    // Lê o registro
    if (pos >= 0) {
      arq.seek(pos);

      int tamR;
      byte[] ba;

      if (arq.readByte() != '*') {
        tamR = arq.readInt();
        ba = new byte[tamR];
        arq.read(ba);
        obj.fromByteArray(ba);
        if (id == obj.getID()) {
          found = true;
        }
      }
    }

    if (!found) {
      obj = null;
    }

    arq.close();

    return obj;
  }

  public T read(String chave) throws Exception {
    int id = arvore.read(chave);
    return this.read(id);
  }

  public boolean update(T obj) throws Exception {

    RandomAccessFile arq = new RandomAccessFile(fileName, "rw");
    long pos;
    T obj2 = this.read(obj.getID());
    byte[] ba, ba2;
    boolean ok = false;

    // Compara os tamanhos dos arquivos/objetos
    if (obj2 != null) {
      pos = hash.read(obj.getID());
      arq.seek(pos);

      ba = obj.toByteArray();
      ba2 = obj2.toByteArray();

      // Sobrescrever o arquivo
      if (ba2.length >= ba.length) {
        arq.seek(arq.getFilePointer() + 5);
        arq.write(ba);

        // Criacao de um novo registro no fim do arquivo
      } else {
        arq.writeByte('*');
        arq.seek(arq.length());
        pos = arq.getFilePointer();
        arq.writeByte(' ');
        arq.writeInt(ba.length);
        arq.write(ba);
        hash.update(obj.getID(), pos);
      }

      // Atualizar indice indireto
      if (obj.chaveSecundaria().compareTo(obj2.chaveSecundaria()) != 0) {
        arvore.update(obj.chaveSecundaria(), obj.getID());
      }

      ok = true;
    }

    arq.close();
    return ok;
  }

  public boolean delete(int id) throws Exception {

    long pos = hash.read(id);

    int tamR;
    T obj = construtor.newInstance();
    byte[] ba;
    boolean ok = false;

    // Posicao retornada pela tabela hash
    RandomAccessFile arq = new RandomAccessFile(fileName, "rw");
    arq.seek(pos);

    // Lê o registro
    if (arq.readByte() != '*') {
      tamR = arq.readInt();
      ba = new byte[tamR];
      arq.read(ba);
      obj.fromByteArray(ba);
      if (id == obj.getID()) {
        ok = true;
        arq.seek(pos);
        arq.writeByte('*');
        arvore.delete(obj.chaveSecundaria());
        hash.delete(id);
      }
    }

    arq.close();

    return ok;
  }
}
