
package tsp;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bill
 */
public class Particula 
{
    private int[] Xt;          // posicao atual  
    private int[] Xt_1;        // posicao anterior
    private SS velocidade;     // velocidade da particula
    private double custo = 0;  // custo de Xt
    
    private Particula lbest;   // melhor vizinho 
    
    public Particula(int[] posicaoAtual)
    {
        this.Xt   = posicaoAtual;
        this.Xt_1 = null;
        velocidade = null;
    }
    
    public void setPosicao(int[] posicao)
    {
        this.Xt = posicao;
    }
    
    public int[] getPosicao()
    {
        return (Xt);
    }
    
    public void setXt_1(int[] Xt_1)
    {
        this.Xt_1 = Xt_1;
    }
    
    public int[] getXt_1()
    {
        return (this.Xt_1);
    }
    
    public void setCusto(double custo)
    {
        this.custo = custo;
    }
    
    public double getCusto()
    {
        return (this.custo);
    }
    
    public void setMelhorVizinho(Particula p)
    {
        this.lbest = p;
    }
    
    public Particula getMelhorVizinho()
    {
        return (this.lbest);
    }
    
    public void setVelocidade(SS v)
    {
        this.velocidade = v;
    }
    
    public SS getVelocidade()
    {
        return (this.velocidade);
    }
    
    public Particula clona()
    {
        Particula p = null;
        p = new Particula(this.Xt.clone());
        
        p.Xt_1       = this.Xt_1.clone();
        p.custo      = this.getCusto();
        p.lbest      = this.getMelhorVizinho();
        p.velocidade = this.getVelocidade();
                
        return (p);
    } 
    
    public String info()
    {
        String s = "Posicao Atual:   ";
        for (int i=0; i < this.Xt.length; i++)
            s = s + this.Xt[i] + " ";
        s = s + "\n";
        
        s = s +    "Posicao Anterior: ";
        for (int i=0; i < this.Xt_1.length; i++)
            s = s + this.Xt_1[i] + " ";
        s = s + "\n";
        
        s = s + "Velocidade: ";
        s = s + (velocidade==null ? "null" : this.velocidade.info());
        s = s + "\n";
        
        s = s + "Custo: ";
        s = s + this.getCusto();
        s = s + "\n";       
        
        if (this.lbest == null)
            s = s + "MELHOR VIZINHO EH NULO!";
        else
        {
            s = s +    "Melhor Vizinho: ";
            for (int i=0; i < this.Xt.length; i++)
                s = s + this.getMelhorVizinho().getPosicao()[i] + " ";
            s = s + "\n";
        }
        return(s);
    }
}
