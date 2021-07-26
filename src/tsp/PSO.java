
package tsp;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;


/**
 *
 * @author bill
 */
public class PSO 
{
    public static final int MAX_INTERACOES = 50000;
    public static final int MAX_TROCAS = 5;
    public Particula[] populacao;
    public static int vizinhanca = 2;
    public double[][] matrizDistancias;
    public int numCidades;
        
    public Particula gbest;
    
    public PSO(int tamanhoPopulacao, int numCidades, double[][] matriz)
    {
        this.populacao        =  new Particula[tamanhoPopulacao];
        this.numCidades       =  numCidades;
        this.matrizDistancias =  matriz;
    }
    
    public PSO(int tamanhoPopulacao, int espacoBusca, int numCidades, double[][] matriz)
    {
        populacao  = new Particula[tamanhoPopulacao];
        vizinhanca = espacoBusca;
        this.numCidades       =  numCidades;
        this.matrizDistancias =  matriz;
    }
    
    public int[] geraPosicaoInicial()
    {
        int[] posicaoSequencial = null;
        // gera uma posicaoInicial com um circuito sequencial 
        
        posicaoSequencial = new int[this.numCidades];

        // preenche a posicaoInicial com os valores sequenciais
        for (int k = 0; k < this.numCidades; k++)
        {
            posicaoSequencial[k] = k+1;
        }
        
        return (posicaoSequencial);
    }
    
    public int[] geraPosicaoInicialGulosa()
    {
        int[] posicaoInicial = new int[this.numCidades];
        int[] cidadesVisitadas = new int[this.numCidades];
        int indCidAtual;
        int indProxCidade;
        
        double menorDistancia;
        
        

        indCidAtual   = 0;
        indProxCidade = 1;
        
        posicaoInicial[0] = indCidAtual+1;
        menorDistancia = this.matrizDistancias[indCidAtual][indProxCidade];
        cidadesVisitadas[indCidAtual] = 1;
        
        for (int i=1; i < this.numCidades; i++)
        {   
            // seleciona a proxima cidade com menor distancia da cidade atual
            for (indProxCidade = 0; indProxCidade < this.numCidades; indProxCidade++)
            {
                if ( ((this.matrizDistancias[indCidAtual][indProxCidade] < menorDistancia) || (menorDistancia == -1)) && (indProxCidade != indCidAtual) && (cidadesVisitadas[indProxCidade] != 1))
                {
                    menorDistancia    = this.matrizDistancias[indCidAtual][indProxCidade];
                    posicaoInicial[i] = indProxCidade + 1;
                }    
            }
           
            cidadesVisitadas[indCidAtual] = 1;
            indCidAtual = posicaoInicial[i]-1;
            menorDistancia = -1;
            
        }
        return (posicaoInicial);
    }
    
    public void geraPopulacaoInicial()
    {
        int qdtTrocas = MAX_TROCAS;
        int indice = 0;
        int[] posicaoSequencial = null;
        
        posicaoSequencial = geraPosicaoInicial();
        //posicaoSequencial = geraPosicaoInicialGulosa();
                    
            populacao[0] = new Particula(geraPosicaoInicialGulosa());
            populacao[0].setCusto(calculaCusto(this.matrizDistancias, geraPosicaoInicialGulosa()));
            populacao[0].setXt_1(geraPosicaoInicialGulosa());            
            populacao[0].setVelocidade(null);
            populacao[0].setMelhorVizinho(populacao[0]);
        
            populacao[populacao.length-1] = new Particula(geraPosicaoInicialGulosa());
            populacao[populacao.length-1].setCusto(calculaCusto(this.matrizDistancias, geraPosicaoInicialGulosa()));
            populacao[populacao.length-1].setXt_1(geraPosicaoInicialGulosa());            
            populacao[populacao.length-1].setVelocidade(null);
            populacao[populacao.length-1].setMelhorVizinho(populacao[populacao.length-1]);
            
            System.out.println("populacao["+0+"] \n" + populacao[0].info());
            System.out.println("populacao[" + (populacao.length-1) + "] \n" + populacao[populacao.length-1].info());
            
        for (int i=1; i < (populacao.length-1); i++)
        {
            
            SS seqAleatoria = geraSSAleatoria(qdtTrocas, this.numCidades);
            
            int[] posicaoInicial = posicaoSequencial.clone();
            move(posicaoInicial, seqAleatoria);
            
            populacao[i] = new Particula(posicaoInicial);
            
           /*********************************************************************************/     
          /*  if (i==1)
            {
                    int[] pos = new int[this.numCidades];
                    posicaoInicial[0] = 1;
                    posicaoInicial[1] = 14;
                    posicaoInicial[2] = 13;
                    posicaoInicial[3] = 12;
                    posicaoInicial[4] = 7;
                    posicaoInicial[5] = 6;
                    posicaoInicial[6] = 15;
                    posicaoInicial[7] = 5;
                    posicaoInicial[8] = 11;
                    posicaoInicial[9] = 9;
                    posicaoInicial[10] = 10;
                    posicaoInicial[11] = 19;
                    posicaoInicial[12] = 20;
                    posicaoInicial[13] = 21;
                    posicaoInicial[14] = 16;
                    posicaoInicial[15] = 3;
                    posicaoInicial[16] = 2;
                    posicaoInicial[17] = 17;
                    posicaoInicial[18] = 22;
                    posicaoInicial[19] = 4;
                    posicaoInicial[20] = 18;
                    posicaoInicial[21] = 8;

                    populacao[1].setPosicao(posicaoInicial);
                   // populacao[0].setVelocidade(null);            
            }
        /*********************************************************************************/    
            
            double custo = calculaCusto(this.matrizDistancias, posicaoInicial);            
            populacao[i].setCusto(custo);
            populacao[i].setXt_1(posicaoSequencial);            
            populacao[i].setVelocidade(null);
            populacao[i].setMelhorVizinho(populacao[i]);
            
            System.out.println("populacao["+i+"] \n" + populacao[i].info());
            
            if ((gbest != null))
            {
                if (custo < gbest.getCusto())
                {
                    gbest = populacao[i].clona();
                }
            }
            else
            {
               gbest = populacao[i].clona();
            }            
        }
        
        // percorre toda a população para determinar os melhores locais de cada particula
        for (int i=0; i < populacao.length; i++)
        {
            double menorCusto = 0;
          /*              
            if (i == (populacao.length-1))
            {
                menorCusto = populacao[i-1].getCusto();
                populacao[i].setMelhorVizinho(populacao[indice]);
            }
            else
            {
                menorCusto = populacao[i+1].getCusto();
                populacao[i].setMelhorVizinho(populacao[indice]);
            }  */
            //System.out.println("(i) = " + i);
            // Obs.: a busca do melhor local inclui tambem a propria particula
            for (int k = 0; k < vizinhanca; k++)
            {
                indice = ((i-k) < 0 ? (populacao.length + (i-k)/* - Math.abs(i-k)*/) : (i-k));
                if (((populacao[indice].getCusto() < menorCusto)) || (menorCusto == 0))
                {   
                    populacao[i].setMelhorVizinho(populacao[indice]);
                    menorCusto = /*(int)*/ populacao[indice].getCusto();                   
                }
              
                indice = ((i+k) >= populacao.length ? ((i+k)%populacao.length) : (i+k));
                if (((populacao[indice].getCusto() < menorCusto)) || (menorCusto == 0))
                {                    
                    populacao[i].setMelhorVizinho(populacao[indice]);
                    menorCusto = /*(int)*/ populacao[indice].getCusto();
                }              
            }
        }

    }
    
    public void atualizaMelhorLocal(int indiceParticula)
    {
        double menorCusto = populacao[indiceParticula].getCusto();
        populacao[indiceParticula].setMelhorVizinho(populacao[indiceParticula]);
        
        int indice;
        
        // Obs.: a busca pelo melhor vizinho inclui a propria particula
        for (int k = 0; k < vizinhanca; k++)
        {
            indice = ((indiceParticula-k) < 0 ? (populacao.length + (indiceParticula-k)/*- Math.abs(indiceParticula-k)*/) : (indiceParticula-k));
            if ((populacao[indice].getCusto() < menorCusto))
            {   
                populacao[indiceParticula].setMelhorVizinho(populacao[indice]);
                menorCusto = (int) populacao[indice].getCusto();
            }

            indice = ((indiceParticula+k) >= populacao.length ? ((indiceParticula+k)%populacao.length) : (indiceParticula+k));
            if ((populacao[indice].getCusto() < menorCusto))
            {                    
                populacao[indiceParticula].setMelhorVizinho(populacao[indice]);
                menorCusto = (int) populacao[indice].getCusto();
            }
        }
    }
    // calculo o custo de um circuito baseado na matriz de distancias passada
    public static double calculaCusto(double matriz[][], int passeio[])
    {
        double custo = 0.0;
        int cidadeAnterior, cidadeAtual;
        //int k = 0;
        
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
    
    // gera uma sequencia de trocas aleatoria de tamanho qtdTrocas
    private int[] geraPosicaoAleatoria(int qtdTrocas, int maxCidade)
    {        
        int indice1, indice2;
        SS sequenciaAleatoria = new SS(qtdTrocas);
        
        int[] posicaoAleatoria = new int[maxCidade];
        
        // preenche a posicaoInicial com os valores sequenciais
        for (int i = 0; i < maxCidade; i++)
        {
            posicaoAleatoria[i] = i+1;
        }
        
        for (int i = 0; i < qtdTrocas; i++)
        {
            indice1 = geraNumeroAleatorio(maxCidade);            
            indice2 = geraNumeroAleatorio(maxCidade);
            
            SO so = new SO(indice1, indice2);
            
            sequenciaAleatoria.add(so, i);            
        }
        
        move(posicaoAleatoria, sequenciaAleatoria);
        
        return (posicaoAleatoria);        
    }
    
    private SS geraSSAleatoria(int qtdTrocas, int maxCidade)
    {        
        int indice1, indice2;
        SS sequenciaAleatoria = new SS(qtdTrocas);
        
        for (int i = 0; i < qtdTrocas; i++)
        {
            indice1 = geraNumeroAleatorio(maxCidade);            
            indice2 = geraNumeroAleatorio(maxCidade);
            
            SO so = new SO(indice1, indice2);
            
            if ((!so.eEstatica()) && (!so.existeIgual(sequenciaAleatoria.sequencia)) && (!so.existeReversa(sequenciaAleatoria.sequencia)))
                sequenciaAleatoria.add(so, i);            
            else
                i--;
        }
        
        // avalia se não existem trocas repetidas dentro da solucao
        
        
        return (sequenciaAleatoria);        
    }
    
    
    private int geraNumeroAleatorio(int valorMax)
    {
        long semente;
        
        semente = System.nanoTime();
        
        Random r = new Random();
        r.setSeed(semente);        
        
        return (r.nextInt(valorMax));
    }
    
    private double geraNumeroAleatorio()
    {
        long semente;
        
        semente = System.nanoTime();
        
        Random r = new Random();
        r.setSeed(semente);        
        
        return (r.nextDouble());
    }
    
    // aplica uma determinada SS em uma posicaoInicial passada
    // x' =  x + v
    private void move(int[] posicaoAtual, SS ss)
    {
        ss.executaSS(posicaoAtual);
    }
    
    
    // retorna a distancia entre duas posicoes 
    // calcula e retorna a sequencia de troca necessária para posicao2 se tornar posicao1
    private SS subtrai(int[] posicao1, int[] posicao2)
    {
        
        Vector v = new Vector();
        SO[]   a = null;
        
        if ((posicao1 == null) || (posicao2 == null))
        {
            System.out.println("Impossivel calcular distância para uma posição nula!");
            System.exit(0);
            //return (null);
        }
        
        for (int i = 0; i < posicao1.length; i++)
        {
            for (int k = (i+1); k < posicao2.length; k++)
            {
                if (posicao1[i] == posicao2[k])
                {
                    SO so = new SO(i,k);
                    v.add(so);
                }
            }
        }
       
        // retira os objetos do vector e poe dentro de um array
        Iterator i = v.iterator();        
        int k = 0;
        a = new SO[v.size()];
        
        while (i.hasNext())
        {
          a[k++] = (SO) i.next();  
        }
        
        SS ss = new SS(a);
        
        return (ss);
    }
    
    // adiciona duas velocidades gerando a sequencia menor possivel apos a concatenacao
    // das mesmas
    private SS adiciona(SS velocidade1, SS velocidade2)
    {
        Vector v = new Vector();
        
             if (velocidade1 == null) return (velocidade2);
        else if (velocidade2 == null) return (velocidade1);
        
        for (int i = 0; i < velocidade1.sequencia.length; i++)
        {
            for (int k = 0; k < velocidade2.sequencia.length; k++)
            {
                if (velocidade1.sequencia[i].eIgual(velocidade2.sequencia[k]))
                    velocidade2.sequencia[k] = null;                
            }            
        }
               
        
        for (int i = 0; i < velocidade1.sequencia.length; i++)
        {
            if ((velocidade1.sequencia[i] != null) || (!velocidade1.sequencia[i].eEstatica()))
                v.add(velocidade1.sequencia[i]);
        }
            
        
        for (int i = 0; i < velocidade2.sequencia.length; i++)
        {
            if (velocidade2.sequencia[i] != null)
            {
                if (!velocidade2.sequencia[i].eEstatica())
                    v.add(velocidade2.sequencia[i]);
            }    
        }
       
        // retira os objetos do vector e poe dentro de um array
        Iterator i = v.iterator();        
        int k = 0;
        SO[] a = new SO[v.size()];
        
        while (i.hasNext())
        {
          a[k++] = (SO) i.next();  
        }
        
        SS ss = new SS(a);
        
        return (ss);
    }
    
    //  realiza a multiplica de uma SS por um coeficiente real
    private SS multiplica(double coeficiente, SS velocidade)
    {
        if (velocidade != null)
        {
        
            if (coeficiente == 0)
                return (null);
            else if (coeficiente == 1)
                return velocidade;
            else
            {
                return (multiplica(Math.round(coeficiente), velocidade));
            }
        }
        else
        {
            return (null);
        }
    }
    
    // Devolve o circuito de menor custo
    public int[] buscaSolucao()
    {
        int     numIteracoes = 0;
        boolean continua     = true; 
        int     custoIgual   = 0;
        
        this.geraPopulacaoInicial();
        
        while (continua)  // condicao de parada????
        {
            numIteracoes++;
           // System.out.println("iteracao - " + numIteracoes );
            for (int i = 0; i < populacao.length; i++)
            {
                // atualiza a velocidade de cada particula                
                SS parcela1, parcela2, parcela3;
                double c1, c2, c3;
                
                c1 = geraNumeroAleatorio();
                c2 = geraNumeroAleatorio();
                c3 = geraNumeroAleatorio();
                
                c1 = c1 > 0.5 ? 1 : 0; 
                c2 = c2 > 0.5 ? 0 : 1; 
                c3 = c3 > 0.5 ? 0 : 1; 
                
                //parcela1 = multiplica(c1, populacao[i].getVelocidade());
                parcela1 = populacao[i].getVelocidade();
                
                //Particula melhorVizinho
                parcela2 = subtrai(populacao[i].getMelhorVizinho().getPosicao(), populacao[i].getPosicao());
                parcela2 = multiplica(c2, parcela2);
                
                // Particula melhorGlobal
                parcela3 = subtrai(gbest.getPosicao(), populacao[i].getPosicao());
                parcela3 = multiplica(c3, parcela3);
                
                SS v = adiciona(parcela1, adiciona(parcela2, parcela3));
                
                if (v != null)
                {
                    // atualiza a posicaoInicial de todos os agentes                    
                    populacao[i].setXt_1(populacao[i].getPosicao().clone());
                    move(populacao[i].getPosicao(), v);
                    
                    v = subtrai(populacao[i].getPosicao(), populacao[i].getXt_1());
                    
                    populacao[i].setVelocidade(v);
                }
                
                // atualiza o custo de todos os agentes
                double custo = calculaCusto(matrizDistancias, populacao[i].getPosicao());
                populacao[i].setCusto(custo);
                
                /**************** APAGAR ****************/
              /*  if (i==0)
                {
                   System.out.println("Iteracao: " + numIteracoes); 
                   System.out.println(populacao[i].info());
                }
                /**************** APAGAR ****************/
                
                // atualiza a melhor solucao global
                Particula gbestAnterior = gbest;
                
                if (custo < gbest.getCusto())
                {
                    gbest = populacao[i].clona();                    
                }
                else if (custo == gbest.getCusto())
                {
                    //System.out.println("Custo igual!");   
                    custoIgual++;
                }
                
                if (numIteracoes > MAX_INTERACOES)
                {
                    //continua = false;
                    
                    if (custoIgual > populacao.length)  // criterio de convergencia (eu acho?!)
                    {
                        continua = false;
                        System.out.println("Total de iteracoes realizadas: " + numIteracoes);   
                    }
                }
                
                if (gbest != gbestAnterior)                
                {
                    System.out.println("***************************************************************************************\n");
                    System.out.println(gbest.info());   
                    System.out.println("***************************************************************************************\n");
                }
                
                // atuliza a melhor solucao local
                //atualizaMelhorLocal(i); 
            }
            
            for (int i = 0; i < populacao.length; i++)
            {
                // atuliza a melhor solucao local
                atualizaMelhorLocal(i); 
            }
        }
        
        
        
        return (gbest.getPosicao());
    }
}
