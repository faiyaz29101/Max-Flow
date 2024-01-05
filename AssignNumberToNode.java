public class AssignNumberToNode {
   private String[] array;
   private int current =0;
   public AssignNumberToNode(int n){
       array = new String[n];

   }
   public void add(String s){
       array[current] = s;
       current++;
   }
   public int getNumber(String s){
       for(int i=0; i<array.length; i++){
           if(s.equals(array[i])){
               return i;
           }
       }
       return -1;
   }
   public void print(){
       System.out.println(array.length);
       for(int i =0; i<array.length; i++){
           System.out.print(array[i]+" ");
       }
   }
   public String getName(int i){
       return array[i];
   }
}
