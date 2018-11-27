package StandardHuff;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class StandardHuff
{
    static class Huff
    {
        public String Symbol;
        public float Probability;
        public String Code;
        public void Print()
        {
            System.out.println(Symbol + "\t\t\t " + Probability);
        }
    }
    
    static class Node
    {
        public Node Right;
        public Node Left;
        public String Code;
        
        public void Print()
        {
            System.out.println(Right + "\t\t" + Left + "\t\t" + Code);
        }
    }

    public static void main(String[] args)
    {
        try
        {
            File file = new File("Symbols.txt");
            if (!file.exists())
                file.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader("Symbols.txt"));

            String line;
            while ((line = br.readLine()) != null)
            {
                System.out.println("Symbols : " + line);

                ArrayList<Float> Probability = new ArrayList<>();
                ArrayList<String> Symbol = new ArrayList<>();

                Symbol.add(line.charAt(0) + "");

                for (int i = 1; i < line.length(); i++)
                {
                    if (!(Symbol.contains((line.charAt(i)) + "")))
                    {
                        Symbol.add(line.charAt(i) + "");
                    }
                }

                int n = 0;
                int counter = 0;
                int it = 0;

                while (n != Symbol.size())
                {
                    if ((line.charAt(it) + "").equals(Symbol.get(n)))
                    {
                        counter++;
                    }
                    if (it == line.length() - 1)
                    {
                        Probability.add((float) counter / line.length());
                        n++;
                        counter = 0;
                        it = 0;
                    }
                    it++;
                }

                ArrayList<Huff> Standard = new ArrayList<>();
                for (int i = 0; i < Symbol.size(); i++)
                {
                    Huff St = new Huff();
                    St.Symbol = Symbol.get(i);
                    St.Probability = Probability.get(i);
                    Standard.add(St);
                }
                System.out.println("\nSymbol \t\t Probability");
                for (int i = 0; i < Standard.size(); i++)
                    Standard.get(i).Print();

                Standard = Sort(Standard);
                System.out.println("\n");
                for (int i = 0; i < Standard.size(); i++)
                    Standard.get(i).Print();
                
                ArrayList<Huff> Sub_Standard = new ArrayList<>();
                Sub_Standard = Get_Sub(Standard);
                Get_Code(Sub_Standard , Standard);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static ArrayList<Huff> Sort(ArrayList<Huff> Standard)
    {
        Huff Temp = new Huff();
        for(int i=0; i<Standard.size(); i++)
        {
            for(int j =i+1; j<Standard.size(); j++)
            {
                if(Standard.get(j).Probability > Standard.get(i).Probability)
                {
                    Temp = Standard.get(j);
                    Standard.set(j , Standard.get(i));
                    Standard.set(i , Temp);
                }
            }
        }
        return Standard;
    }
    
    public static ArrayList<Huff> Get_Sub(ArrayList<Huff> Standard)
    {
        int Size = Standard.size();
        while (Size != 2)
        {
            Standard = Sort(Standard);
            float P = Standard.get(Size - 2).Probability += Standard.get(Size - 1).Probability;
            String S = Standard.get(Size - 2).Symbol  + Standard.get(Size - 1).Symbol;
        
            Huff St = new Huff();
            St.Probability = P;
            St.Symbol = S;
        
            Standard.set(Size-2 , St);
            Size--;
        }
        Standard = Sort(Standard);
        System.out.println("\n");
        for (int i = 0; i < 2; i++)
            Standard.get(i).Print();
        
        return Standard;
    }
    
    public static void Get_Code(ArrayList<Huff> Sub , ArrayList<Huff> Standard)
    {
        Sub.get(0).Code = "1";
        Sub.get(1).Code = "0";
        for(int i=0; i< 2; i++)
        {
            ArrayList<Huff> P = new ArrayList<>();
            int j = 0;
            while(P.size() != Sub.get(i).Symbol.length())
            {
                Huff Obj_p = new Huff();
                Obj_p.Symbol = Sub.get(i).Symbol.charAt(j) + "";
                for(int n = 0; n <Standard.size(); n++)
                {
                    if(Obj_p.Symbol.equals( Standard.get(n).Symbol))
                        Obj_p.Probability = Standard.get(n).Probability;
                    P.add(Obj_p);
                }
                System.out.println(Obj_p.Symbol+" hieiiei:   "+Obj_p.Probability);
              
                j++;
            }
            for(int m=0; m<P.size(); m++)
            {
                System.out.print("here: ");
                P.get(m).Print();
            }
        }
    }
}
