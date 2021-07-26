
package tsp;

/**
 *
 * @author bill
 */
public class SS 
{
    public SO[] sequencia;
    
    public SS(int tamanho)
    {
        this.sequencia = new SO[tamanho]; 
    }
    
    public SS(SO[] sequencia)
    {
        this.sequencia = sequencia; 
    }
    
    public void add(SO so, int posicao)
    {
        this.sequencia[posicao] = so; 
    }
    
    public void remove(int posicao)
    {
        this.sequencia[posicao] = null;
    }
    
    public void executaSS(int[] posicao)
    {
        for (int i = 0; i < this.sequencia.length; i++)
        {
            this.sequencia[i].executaSO(posicao);
        }
    }
    
    public String info()
    {
        String s = "";
                
        for (int i=0; i < this.sequencia.length; i++)
            s = s + this.sequencia[i].info() + " ";
        
        return (s);
    }
}
