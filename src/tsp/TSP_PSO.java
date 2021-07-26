
package tsp;

import javax.swing.JOptionPane;

/**
 *
 * @author bill
 */
public class TSP_PSO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception 
    {
        int numParticulas;
        int numCidades;
        int tamVizinhanca;
        double[][] matriz;
        
        int[] menorCircuito;
        
        TSPLib ulysses  = new TSPLib("./files/ulysses22.tsp");
        TSPLib circuito = new TSPLib("./files/ulysses22.opt.tour"); 
        
        // determina o tamanho da populacao
        numParticulas = 2500;
        tamVizinhanca = 1;
        
        ulysses.carregaArquivo();
        circuito.carregaArquivo();
        
        numCidades = circuito.tour.length;
        matriz = ulysses.matriz;
        
        
        
        System.out.println(ulysses.info());
        System.out.println(circuito.info());
        System.out.println("Menor Custo: " + calculaCusto(ulysses.matriz, circuito.tour));
        
        System.out.println("\n BUSCA O MELHOR CAMINHO ATRAVES DE PSO \n");
        
        PSO pso = new PSO(numParticulas, tamVizinhanca, numCidades, matriz);
        
        menorCircuito = pso.buscaSolucao();
        
        
        System.out.println("Menor Custo ACHADO PELO PSO: " + calculaCusto(matriz, menorCircuito));
        
        for (int i = 0; i < menorCircuito.length; i++)
        {
            System.out.println(menorCircuito[i]);
        }
        
        /*TSPLib ulysses2  = new TSPLib("/media/disk/files/tsp225.tsp");
        TSPLib circuito2 = new TSPLib("/media/disk/files/tsp225.opt.tour"); 
        
        ulysses2.carregaArquivo();
        circuito2.carregaArquivo();
        
        System.out.println(ulysses2.info());
        System.out.println(circuito2.info());
        System.out.println("Menor Custo: " + calculaCusto(ulysses2.matriz, circuito2.tour));*/
        
    }

    public static double calculaCusto(double matriz[][], int passeio[])
    {
        double custo = 0.0;
        int cidadeAnterior, cidadeAtual;
        int k = 0;
        
        cidadeAnterior = passeio[0];
        for (int i = 1; i < passeio.length; i++)
        {
            cidadeAtual = passeio[i];
            custo = custo + matriz[cidadeAnterior-1][cidadeAtual-1];
            
            cidadeAnterior = passeio[i];
        }    
        
        cidadeAtual = passeio[0];
        custo = custo + matriz[cidadeAnterior-1][cidadeAtual-1];        
        
        return (custo);
    }
    
    
}
