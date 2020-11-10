import java.io.File;
import java.util.Scanner;
import java.io.*;

class Main {

  private static CRUD2<Pergunta> arqPergunta;
  private static Scanner ler = new Scanner(System.in);
  private static InputStreamReader isr = new InputStreamReader(System.in);
  private static BufferedReader br = new BufferedReader(isr);

  public void listar() {
    Pergunta per;
    int id = MyIO.readInt("\nQual id do usuario?: ");

    ArvoreBMais_ChaveComposta_Int_Int arvore = new ArvoreBMais_ChaveComposta_Int_Int(3, "pergunta.db");

    int[] lista = arvore.read(id);

    for (int i = 0; i < lista.length; i++) {
      per = arqPergunta.read(lista[i]);
      MyIO.println("" + i + ":");
      MyIO.println(per.data);
      MyIO.println(per.pergunta);
    }
  }

  public static void incluir() {
    System.out.println("\n       NOVA PERGUNTA");
    System.out.print("\nDigite a pergunta: ");

    String pergunta = "";

    // Pergunta per = new Pergunta();

    try {
      Pergunta per = new Pergunta();
      
      if (pergunta != "") {
        if (arqPergunta.read(pergunta) == null) {

          System.out.println("\nPergunta VALIDA!");
          per.setPergunta(pergunta);

          System.out.println("\nCONFIRME A PERGUNTA\n" + per);
          System.out.println("\n1) Confirmar\n2) Cancelar");

          byte op = -1;
          do {
            System.out.print("\nOperacao: ");
            op = ler.nextByte();
            if (op == 1) {
              arqPergunta.create(per);
            } else if (op == 2) {
              System.out.println("Pergunta cancelada!\n");
            } else {
              System.out.println("ERRO! Operacao invalida!\n");
            }

          } while (op != 1 && op != 2);
        }
      } else {
        System.out.println("ERRO! pergunta inválida!");
        System.out.println("\nPressione enter para voltar para tela inicial...");
        br.read();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /*
   * public static void incluir(Scanner entrada) throws Exception { clearScreen();
   * boolean sair = false, simounao = false;
   * System.out.println("\nPERGUNTAS 1.O");
   * System.out.println("\n===============");
   * System.out.println("\nINÍCIO > CRIAÇÃO DE PERGUNTAS > INCLUIR PERGUNTAS");
   * while(!sair){ simounao = false; String pergunta = "";
   * System.out.print("\nDigite sua pergunta (em branco para voltar): "); pergunta
   * = entrada.nextLine(); if(!pergunta.equals("")){ while(!simounao) { try { //
   * Solicita a confirmação da pergunta
   * System.out.println("\nConfirmar a inclusão dessa pergunta? [S/N]");
   * System.out.printf("Pergunta: \"%s\"", pergunta); System.out.print(" > ");
   * String resposta = entrada.nextLine();
   * 
   * if (resposta.equals("s") || resposta.equals("S")) {
   * System.out.println("\nPergunta criada com sucesso!"); sair = true; simounao =
   * true; } else if (resposta.equals("n") || resposta.equals("N")) { simounao =
   * true; } } catch(Exception err) {
   * System.out.println("Pergunta não foi criada =/"); System.err.print(err); } }
   * } else{ sair = true; } }
   */

  /*
   * public static void recuperarSenha(Scanner entrada) throws Exception{
   * System.out.println("\n==============="); System.out.println("\nNOVA SENHA");
   * 
   * String email = "", novaSenha = ""; boolean loginInvalido = true;
   * 
   * // Limpa o buffer entrada.nextLine();
   * 
   * while(loginInvalido){
   * System.out.print("\nInforme seu email (em branco para voltar): "); email =
   * entrada.nextLine();
   * 
   * if (!email.equals("")) { try{ Usuario result = crudUsuarios.read(email); if
   * (result != null) { System.out.println("Foi enviado um senha temporária para "
   * + email); loginInvalido = false; } else {
   * System.out.println("O email inserido não está cadastrado!"); } }
   * catch(Exception e){ System.err.println(e); } } else { loginInvalido = false;
   * } }
   * 
   * }
   */

  public void alterar () {

    int idUsuario = Usuario.getID();
    int idPergunta;

    ArvoreBMais_ChaveComposta_Int_Int arvore = new ArvoreBMais_ChaveComposta_Int_Int(3,"pergunta.db");    
    int[] perguntas = arvore.read(idUsuario);

    int[] pergAtivas = new int[100];
    Pergunta perg;
    for (int i = 0; i < perguntas.length; i++) {
      int contador = 1;
      perg = arqPergunta.read(perguntas[i]);

      if(perg.ativa == true)
      {
        pergAtivas[contador - 1] = i;
        MyIO.println(""+contador+":");
        MyIO.println(perg.data);
        MyIO.println(perg.pergunta);
      }
    }

    int num = MyIO.readInt("\nDigite o numero da pergunta a ser alterada:\n");

    if(num != 0){
      perg = arqPergunta.read(perguntas[pergAtivas[num]]);
      idPergunta = perg.getID();

      perg = CRUD2.read(idPergunta);

      MyIO.println(perg.data);
      MyIO.println(perg.pergunta);
    
      String novaPergunta = "";
      novaPergunta = MyIO.readLine("\nDigite a nova pergunta:\n");

      if(novaPergunta != "")
      {
        Pergunta perg2 = new Pergunta();

        perg2.setID(idPergunta);
        perg2.setPergunta(novaPergunta);

        MyIO.println("A nova perdunta sera:\n" + perg2.getPergunta() + "Deseja confirmar a alteracao?\n 1 - SIM\n 0 - NAO");

        int confirmar = MyIO.readInt();

        if(confirmar == 1)
        {
          boolean sucesso = CRUD2.update(perg2);

          if(sucesso)
          {
            MyIO.println("Pergunta alterada com sucesso!");
          }

          else MyIO.println("Erro! Nao foi possivel alterar a pergunta!");
        }
      }
    }
  }

  public void arquivar() {

  }

  public static void main(String[] args) {

    try {
      arqPergunta = new CRUD2<>(Pergunta.class.getConstructor(), "pergunta.db");
    } catch (Exception e) {
      System.out.println("ERRO: Problema na main!");
    }

    int opcao;
    do {

      System.out.println("\nPERGUNTAS 1.0\n=============");
      System.out.println("FEITO PELO GRUPO 07");
      System.out.println("\nINÍCIO\n");
      System.out.println("1) Criação de perguntas");
      System.out.println("2) Consultar/responder perguntas");
      System.out.println("3) Notificações: 0");
      System.out.println("\n0) Sair");
      System.out.print("\nOpção: ");

      opcao = MyIO.readInt();
      if (opcao == 1) {
        System.out.println("\nPERGUNTAS 1.0\n=============");
        System.out.println("FEITO PELO GRUPO 07");
        System.out.println("\nINÍCIO > CRIAÇÃO DE PERGUNTAS\n");
        System.out.println("1) Listar");
        System.out.println("2) Incluir");
        System.out.println("3) Alterar");
        System.out.println("4) Arquivar");
        System.out.println("\n0) Sair");
        System.out.print("\nOpção: ");

        opcao = MyIO.readInt();

        if (opcao == 1) {
          // listar();
        } else if (opcao == 2) {
          incluir();
        } else if (opcao == 3) {
          // alterar();
        } else if (opcao == 4) {
          // arquivar();
        }

        opcao = 3;
      }

      else if (opcao == 2) {
        try {
          System.out.println("\nERRO: Opção não implementada!");
        } catch (Exception e) {
          System.out.println("ERRO: Problema durante o cadastro!");
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
}