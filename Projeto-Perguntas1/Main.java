import java.io.File;
import java.util.Scanner;
import java.io.*;

class Main {

  private static CRUD2<Usuario> arqUsuarios;
  private static Scanner ler = new Scanner(System.in);
  private static InputStreamReader isr = new InputStreamReader(System.in);       
  private static BufferedReader br = new BufferedReader(isr);

  public static void main(String[] args) {

    try {
      arqUsuarios = new CRUD2<>
      (Usuario.class.getConstructor(), "usuarios.db");
        } catch (Exception e){
          System.out.println ("ERRO: Problema na main!");
        }

    int opcao;
    do {

      System.out.println("\nPERGUNTAS 1.0\n=============");
      System.out.println("\nFEITO PELO GRUPO 07");
      System.out.println("\nACESSO\n");
      System.out.println("1) Acesso ao sistema");
      System.out.println("2) Novo usuário (primeiro acesso)");
      System.out.println("\n0) Sair");
      System.out.print("\nOpcao: ");

      opcao = MyIO.readInt();
      if (opcao == 1) {
        Login();
      }
      
      else if (opcao == 2) {
        try {
        Cadastro();
        } catch (Exception e){
          System.out.println ("ERRO: Problema durante o cadastro!");
        }
        opcao = 3;
      }

      else if (opcao == 0) {

      }

      else {
        System.out.println("\nERRO: Opcao invalida");
      }

    } while (opcao < 0 || opcao > 2);
  }

  public static void Cadastro() throws Exception{
    String email;
    String nome;
    String senha;

    Usuario user = new Usuario();
    System.out.println("\nNOVO USUARIO\n");
        
    email = MyIO.readString("E-mail: ");

    if (arqUsuarios.read(email) == null) {
      nome = MyIO.readString("Nome: ");
      senha = MyIO.readString("Senha: ");

      user.setEmail(email);
      user.setNome(nome);
      user.setSenha(senha);

      int op = MyIO.readInt("\nDigite 1 para confirmar o cadastro: ");

      if (op == 1) {
        arqUsuarios.create(user);
        System.out.println("\nUsuario cadastrado com sucesso!");
      }
      else
        System.out.println("Cadastro cancelado!");
    }

    else {
      System.out.println("ERRO: E-mail ja existe!");
    }

  }

  public static void Login(){
    String email;
    String senha;

    System.out.println("\nACESSO AO SISTEMA\n");
    System.out.println("\nEmail: ");
    
    try{
      email = br.readLine().toLowerCase();
      Usuario user = arqUsuarios.read(email);

      if(user != null){
        senha = MyIO.readString("\nDigite sua senha: ");
        //MyIO.println("senha retornada "+user.getSenha());

        if(senha.equals(user.getSenha())){

          System.out.println("Entrando");
          System.exit(0);
        
        } else {

          System.out.println("Senha Inválida!");
          System.out.println("\nPressione enter para voltar para tela inicial");
          br.read();

        }
      } else {
        
        System.out.println("Email não cadastrado.");
        System.out.println("\nPressione enter para voltar para tela inicial");
        br.read();
                
      }
    } catch(Exception e){
      e.printStackTrace();
    }
  }
}