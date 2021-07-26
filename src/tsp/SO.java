
package tsp;

/**
 *
 * @author bill
 */
public class SO 
{
    public int a;
    public int b;

    public SO(int m, int n)
    {
        this.a = m;
        this.b = n;
    }
    
    public boolean eIgual(SO so)
    {
        if (so != null)
        {
            if ((this.a == so.a) && (this.b == so.b))
            {
                return (true);
            }
        }
        return (false);
    }
    
    public boolean eEstatica()
    {        
        if (this.a == this.b)
            return (true);
        
        return (false);
    }
    
    public boolean eReversa(SO so)
    {
        if (so != null)
        {
            if ((this.a == so.b) && (this.b == so.a))
            {
                return (true);
            }
        }
        return (false);
    }
    
    public boolean existeIgual(SO[] lista)
    {
        if (lista != null)
        {
            for (SO so: lista)
            {
                if (this.eIgual(so))
                {
                    return (true);
                }
                /*if (so != null)
                {
                    if ((this.a == so.a) && (this.b == so.b))
                    {
                        return (true);
                    }
                } */   
            }
        }
        return (false);        
    }
    
    
    public static boolean existeEstatica(SO[] lista)
    {
        if (lista != null)
        {
            for (SO so: lista)
            {
                if (so.eEstatica())
                {
                    return (true);
                }
                /*if (so != null)
                {
                    if ((this.a == so.a) && (this.b == so.b))
                    {
                        return (true);
                    }
                } */   
            }
        }
        return (false);        
    }
    
    public boolean existeReversa(SO[] lista)
    {
        if (lista != null)
        {
            for (SO so: lista)
            {
                if (this.eReversa(so))
                {
                    return (true);
                }
                /*if (so != null)
                {
                    if ((this.a == so.b) && (this.b == so.a))
                    {
                        return (true);
                    }
                }*/
            }
        }
        
        return (false);
    }
    
    public void executaSO(int[] posicao)
    {
        int aux;
        
        aux = posicao[this.a];
        posicao[this.a] = posicao[this.b];
        posicao[this.b] = aux;
    }
    
    public String info()
    {
        String s = "";
        s = "("+ this.a + "," + this.b + ")";
        return (s);
    }
}
