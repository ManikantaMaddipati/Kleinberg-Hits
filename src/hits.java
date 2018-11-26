
import java.io.*;
import java.util.Iterator;
import java.text.DecimalFormat;
import java.util.List;

public class hits {
    
    public static void main(String[] args) throws IOException{
        
        int Numiter;
        final double errorrate = 0.00001;
        boolean isLessAut = true;
        boolean isLessHub = true;
        DecimalFormat df = new DecimalFormat("0.0000000");//Setting values ALT+SHIFT+F10, Right, E, Enter, Tab,
        
        int iterations = Integer.parseInt(args[0]);
        double initialValue = Integer.parseInt(args[1]);
        String filename = args[2];//Reading initial arguments
        
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String ln = br.readLine();//Buffer reading the file
        
        int N = Integer.parseInt(ln.split(" ")[0]);
        int E = Integer.parseInt(ln.split(" ")[1]);//Vertices and Edges are read
        
        AdjSet NodesNum = new AdjSet(N);//Adjacency Set is created
        
        int Node1, Node2;
        for(int i = 0; i < E; i++){
            ln = br.readLine();
            Node1 = Integer.parseInt(ln.split(" ")[0]);
            Node2 = Integer.parseInt(ln.split(" ")[1]);
            NodesNum.addEdge(Node1,Node2);//Adding nodes from file
        }
        
        if(iterations <1){
            Numiter = 1000000000;
        }else{
            Numiter = iterations;//Setting iterations
        }
        
        double [] aut = new double[N];
        double [] hub = new double[N];
        double [] Aut_scale = new double[aut.length];
        double [] Hub_scale = new double[hub.length];
        double [] Aut_tmp = new double[aut.length];
        double [] Hub_tmp = new double[hub.length];
        double [] Aut_diff = new double[aut.length];
        double [] Hub_diff = new double[hub.length];//initializing Hub and Authority array for the calculation
        
        
        if(initialValue == 0.0){
            initialValue = 0.0;
        }else if(initialValue == 1.0){
            initialValue = 1.0;
        }else if(initialValue == -1.0){
            initialValue = 1.0/N;
        }else if(initialValue == -2.0){
            initialValue = 1.0/(Math.sqrt(N));//Initializing initialValue
        }
        
    
        for(int i = 0; i<aut.length; i++)
        {
            aut[i] = initialValue;
            Aut_tmp[i] = initialValue;
            Aut_scale[i] = 0;
            Aut_diff[i] = 0;
        }
        
        for(int i = 0; i<hub.length; i++)
        {
            hub[i] = initialValue;
            Hub_tmp[i] = initialValue;
            Hub_scale[i] = 0;
            Hub_diff[i] = 0;//We Initialize Authority and Hub values
        }
        
        
        System.out.print("Vectors must be initialized to "+df.format(initialValue)+"\n");
        if(N < 10)
        {
            System.out.print("Base : 0 :");
            for(int i = 0; i < N; i++){
                System.out.print("A/H["+i+"] "+df.format(aut[i])+"/"+df.format(hub[i])+" ");
            }
            
            System.out.print("\n");}
        else{
            System.out.print("Base : 0 :"+"\n");
            for(int i = 0; i < N; i++){
                System.out.print("A/H["+i+"] "+df.format(aut[i])+"/"+df.format(hub[i])+" "+"\n");//Giving output of  initial vectors
            }
        }
        
        
    
        for(int y = 0; y < Numiter; y++)
        {
            for(int i = 0; i < aut.length; i ++)
            {
                aut[i] = 0;
                for(int x = 0;x< aut.length; x++)//Computing authority value
                {
                    
                    List<Integer> str = NodesNum.getVertices_Adj(x);
                    Iterator<Integer> itr = str.iterator();
                    while(itr.hasNext())
                    {
                        if(i ==itr.next())
                        {
                            aut[i] = aut[i] + hub[x];//Authority value is calculated
                        }
                    }
                }
                
            }
            
            
            for(int i = 0; i < hub.length; i++){
                hub[i] = 0;
                List<Integer> str = NodesNum.getVertices_Adj(i);
                Iterator<Integer> itr = str.iterator();//Compute Hub values
                
                while(itr.hasNext()){
                    hub[i] = hub[i] + aut[itr.next()];//Hub value is calculated
                }
                
            }
            
            
            double Aut_sum = 0;
            for(int i = 0; i < aut.length; i++ ){
                
                Aut_sum = Aut_sum + Math.pow(aut[i], 2);
                
            }
            
            for(int i = 0; i < Aut_scale.length; i++){
                Aut_scale[i] =  aut[i];
                Aut_scale[i] =  Aut_scale[i] /Math.sqrt(Aut_sum);//Compute scaled authority value
            }
            
            
            double Hub_sum = 0;
            for(int i = 0; i < aut.length; i++ ){
                Hub_sum = Hub_sum + Math.pow(hub[i], 2);
            }
            
            for(int i = 0; i < Hub_scale.length; i++){
                Hub_scale[i] = hub[i];
                Hub_scale[i] = Hub_scale[i]/Math.sqrt(Hub_sum);//Compute scaled Hub value
            }
            
            
        
            if(N < 10)
            {
                System.out.print("Itr : "+(y+1)+" :");
                for(int i = 0; i < N; i++){
                    System.out.print("A/H["+i+"] "+df.format(Aut_scale[i])+"/"+df.format(Hub_scale[i])+" ");              }
                
                System.out.print("\n");
            }else {
                System.out.print("Itr : "+(y+1)+" :"+"\n");
                for(int i = 0; i < N; i++){
                    System.out.print("A/H["+i+"] "+df.format(Aut_scale[i])+"/"+df.format(Hub_scale[i])+" "+"\n");//Generating output of Authority and Hub values
                }
                
            }
            
        
            for(int i = 0; i < Aut_diff.length; i++){
                
                Aut_diff[i] = Math.abs(Aut_tmp[i] - Aut_scale[i]);//Computation of difference of Authority values
                
                if(Aut_diff[i]< errorrate)
                {				
                    isLessAut = true;
                }
                else{
                    isLessAut = false;
                    break;//Verify whether Authority value of every vertex is less than errorrate
                }
            }
            
            
            for(int i = 0; i < Hub_diff.length; i++){
                
                Hub_diff[i] = Math.abs(Hub_tmp[i] - Hub_scale[i]);//Computation of difference of Hub values
                
                if(Hub_diff[i]< errorrate)
                {				
                    isLessHub = true;
                }
                else{
                    isLessHub = false;
                    break;//Verify whether Hub value of every vertex is less than errorrate
                }
            }
            
           
            for (int i = 0; i < Aut_tmp.length; i++){
                
                Aut_tmp[i] = Aut_scale[i];//We assign temporary Authority value
            }
            
            
            for (int i = 0; i < Hub_tmp.length; i++){
                
                Hub_tmp[i] = Hub_scale[i];//We assign temporary Hub value
            }
            
            
            if(isLessAut == true && isLessHub == true)//Verifying whether hub and authority values are less than errorate
            {
                break;
            }
        }
        
        
    }
    
    
}
