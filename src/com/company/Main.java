package com.company;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    // fluxo de 1 e salvar
    static int TAM_LISTA;
    static String[] lista;
    static int[] indices;
    static Random rand = new Random();
    static int fichasDisponiveis;
    static ArrayList<String> listacarregada = new ArrayList<String>();

    public static void atualizaFichasDisponiveis(int n){
        fichasDisponiveis = fichasDisponiveis - n;
    }
    public static void atualizaFichasDisponiveis2(){
        int usadas = 0;
        for(int i = 0; i < lista.length-1;i++){
            if(lista[i] != null){
                usadas++;
            }
        }
        atualizaFichasDisponiveis(usadas);
    }

    public static void addJogador(){
        Scanner in = new Scanner(System.in);
        do{
            if(fichasDisponiveis > 0) {
                System.out.println("Nome da pessoa: ");
                String e = in.next();
                System.out.println("Quantidade de fichas: ");
                int n = in.nextInt();
                if (fichasDisponiveis < n) {
                    System.out.println("Sem fichas disponíveis o suficiente para: " + e);
                    menu();
                }
                if (n < 0) menu();
                preenche(e, n);
                atualizaFichasDisponiveis(n);
                printa(lista);
                if(fichasDisponiveis == 0) {System.out.println("Fichas encerradas.");
                    TAM_LISTA = lista.length;
                    menu2(); }
                //salva();
            }else{
            System.out.println("Fichas encerradas.");
            menu(); }
        }while(true);
    }
    public static void preenche(String e, int n){
        for(int i = 0;i<n;i++) {
            int pos = rand.nextInt(TAM_LISTA);
            int index = indices[pos];
            indices[pos]=indices[--TAM_LISTA];
            lista[index]=e;
        }
    }
    public static void printa(String[] v){
        String str = "[";
        for(int i = 0;i<v.length;i++) {
            if(i==v.length-1)str+=v[i]+"]";
            else str+=v[i]+", ";
        }
        System.out.println(str);
    }
    public static void menu(){
        Scanner teclado = new Scanner(System.in);
        System.out.println("1-Criar novo sorteio 2-Carregar sorteio 3-Fechar Programa");
        int id = teclado.nextInt();

        if(id == 1){
            criaSorteio();
        }else if(id == 2){
            System.out.println("Digite o nome do arquivo:");
            String arq = teclado.next();
            //System.out.println("fichas disponiveis"+fichasDisponiveis);
            //atualizaFichasDisponiveis2();
            //System.out.println("fichas disponiveis"+fichasDisponiveis);
            carregaSorteio(arq);
        }else if(id == 3){
            System.exit(0);
        }
    }
    public static void menu2(){
        Scanner teclado = new Scanner(System.in);
        System.out.println("1-Adicionar jogador 2-Sortear 3-Voltar");
        int id = teclado.nextInt();

        if(id == 1){
            addJogador();
            menu2();
        }else if(id == 2){
            sortear();
        }else if(id == 3){
            menu();
        }
    }

    //------------CARREGA SORTEIO-------------
    public static ArrayList readFile(String nomeArq, ArrayList aux){
        Boolean result2=false;
        Path path1= Paths.get(nomeArq);
        try(Scanner sc= new Scanner(Files.newBufferedReader(path1, Charset.defaultCharset())))
        {
            String pal=null;
            while(sc.hasNext())
            {
                pal=sc.next();
                aux.add( pal);
            }
//            for(int i = 0; i< aux.size();i++){
//                System.out.println(aux.get(i));
//            }
            result2=true;
        }
        catch (IOException e)
        {
            System.err.format("Ocorreu um erro!\nPor favor tente novamente. \n",e);
            result2=false;
        }
        return aux;
    }

    public static void carregaSorteio(String arq){
        readFile(arq + ".txt", listacarregada); //com isso não precisa colocar a extenção do arquivo
        //Com o arq lido, coloca na lista
        String str = listacarregada.remove(0);
        int tamanho = Integer.parseInt(str);
        lista = new String[tamanho];
        int fichas = 0;
        for(int i = 0; i < listacarregada.size();i++){
            lista[i] = listacarregada.get(i);
            fichas++;
        }
        printa(lista);
        TAM_LISTA = lista.length;
        atualizaFichasDisponiveis(fichas);
        menu2();
    }
    //----------------------------------------

    //------------CRIA SORTEIO-------------
    public static void criaSorteio(){
        Scanner in = new Scanner(System.in);
        System.out.println("Digite o tamanho da lista: ");
        TAM_LISTA=in.nextInt();
        lista = new String[TAM_LISTA];
        fichasDisponiveis = TAM_LISTA;
        indices=new int[TAM_LISTA];
        for(int i = 0;i<TAM_LISTA;i++)indices[i]=i;
        addJogador();
    }
    //---------------------------------------------------------------

    public static void sortear(){
        String primeiro = lista[rand.nextInt(TAM_LISTA-1)];
        String segundo = lista[rand.nextInt(TAM_LISTA-1)];
        while(true){
            if(primeiro != segundo){
                break;
            }
            segundo = lista[rand.nextInt(TAM_LISTA-1)];
        }
        System.out.println("Primeiro: " + primeiro);
        System.out.println("Segundo: " + segundo);

        menu2();
    }

    public static void main(String[] args) {
        menu();
    }
}
