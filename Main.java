import java.io.*;

public class Main {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String OUTPUT_FILE_NAME = "output.txt";
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        String line = br.readLine();
        int teams = Integer.parseInt(line);
        String[] teamName = new String[teams];
        int[] wins = new int[teams];
        int[] loses = new int[teams];
        int[] left = new int[teams];
        int[][] mat = new int[teams][teams];
        for(int i=0; i<teams; i++){
            line = br.readLine();
            String[] data = line.split(" ");
            teamName[i] = data[0];
            wins[i] = Integer.parseInt(data[1]);
            loses[i] = Integer.parseInt(data[2]);
            left[i] = Integer.parseInt(data[3]);
            for(int j = 0; j<teams; j++){
                mat[i][j] = Integer.parseInt(data[4+j]);
            }
        }
//        for(int i =0; i<teams; i++){
//            System.out.print(teamName[i]+" "+wins[i]+" "+loses[i]+" " +left[i]+" ");
//            for(int j=0; j<teams; j++){
//                System.out.print(mat[i][j]+" ");
//            }
//            System.out.println();
//        }
        baseball_elimination E = new baseball_elimination(teams, teamName, wins, loses, left, mat);
//        System.out.println(E.getString());
        bw.write(E.getString());
        bw.close();


    }

}