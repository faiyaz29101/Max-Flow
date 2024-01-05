import java.util.ArrayList;

public class baseball_elimination {
    private int teams = 0;
    private String[] teamName = null;
    private int[] wins = null;
    private int[] loses = null;
    private int[] left = null;
    private int[][] mat = null;
    private int INF = 1000000;
    private AssignNumberToNode a;
    private StringBuffer s = new StringBuffer();

    public baseball_elimination(int teams, String[] teamName, int[] wins, int[] loses, int[] left, int[][] mat){
        this.teams = teams;
        this.teamName = teamName;
        this.wins = wins;
        this.loses = loses;
        this.left = left;
        this.mat = mat;
        for(int i=0; i<teams; i++){
            isEliminated(i);
        }
//        System.out.println(getString());

    }
    private void isEliminated(int num){
        for(int i=0; i<teams; i++){
            if(i!=num) {
                if ((wins[num] + left[num] < wins[i])) {
                    s.append(teamName[num] + " is eliminated\n");
                    s.append("They can win at most "+wins[num]+" + "+left[num]+" = "+(wins[num]+left[num])+" games.\n");
                    s.append("But "+teamName[i]+" already has "+wins[i]+" wins.\n");
                    s.append("So, "+teamName[num]+" mathematically can never be at first position.\n\n");
                    return;
                }
            }
        }
        int nodes = teams + (teams*(teams-1)/2) +2;
        a = new AssignNumberToNode(nodes);
        a.add("s");
        for(int i=0; i<teams-1; i++){
            for(int j=i+1; j<teams; j++){
                StringBuffer sb = new StringBuffer();
                sb.append(i);
                sb.append(j);
                a.add(sb.toString());
            }
        }
        for(int i =0; i<teams; i++){
            a.add(Integer.toString(i));
        }
        a.add("t");
        int currentTeam = num;
        int source = a.getNumber("s");
        int sink = a.getNumber("t");
        Node[] graph = new Node[nodes];
        for (int i = 0; i < nodes; i++)
            graph[i] = new Node();
        for(int i=0; i<teams-1; i++){
            for(int j=i+1; j<teams; j++){
                StringBuffer sb = new StringBuffer();
                sb.append(i);
                sb.append(j);
                int mC;
                if(i!=currentTeam && j!=currentTeam){
                    mC = mat[i][j];
                }
                else{
                    mC=0;
                }
                edge u = new edge(source, a.getNumber(sb.toString()), 0, mC);
                edge v = new edge(source, a.getNumber(sb.toString()), 0,0);
                u.setReverse(v);
                v.setReverse(u);
                graph[source].edges.add(u);
                graph[a.getNumber(sb.toString())].edges.add(v);
            }

        }
        for(int i=0; i<teams-1; i++){
            for(int j = i+1; j<teams; j++){
                StringBuffer sb = new StringBuffer();
                sb.append(i);
                sb.append(j);
                edge u = new edge(a.getNumber(sb.toString()), a.getNumber(Integer.toString(i)), 0, INF);
                edge v = new edge(a.getNumber(sb.toString()), a.getNumber(Integer.toString(i)), 0,0);
                u.setReverse(v);
                v.setReverse(u);
               // System.out.println(sb.toString());
                graph[a.getNumber(sb.toString())].edges.add(u);
                graph[a.getNumber(Integer.toString(i))].edges.add(v);
                u = new edge(a.getNumber(sb.toString()), a.getNumber(Integer.toString(j)), 0, INF);
                v = new edge(a.getNumber(sb.toString()), a.getNumber(Integer.toString(j)), 0,0);
                u.setReverse(v);
                v.setReverse(u);
                graph[a.getNumber(sb.toString())].edges.add(u);
                graph[a.getNumber(Integer.toString(j))].edges.add(v);
            }
        }
        for(int i=0; i<teams; i++){

            edge u = new edge(a.getNumber(Integer.toString(i)), sink, 0, findmax((wins[currentTeam]+left[currentTeam]-wins[i]), 0));
            edge v = new edge(a.getNumber(Integer.toString(i)), sink, 0,0);
            u.setReverse(v);
            v.setReverse(u);
            graph[a.getNumber(Integer.toString(i))].edges.add(u);
            graph[sink].edges.add(v);
        }
//        for(int i =0; i<nodes-1; i++){
//            graph[i].print();
//        }

//        int maxFlow = 0;

        while (true) {
            edge[] parent = new edge[nodes];
            ArrayList<Node> q = new ArrayList<>();
            q.add(graph[source]);

            while (!q.isEmpty()) {
                Node current = q.remove(0);

                for (edge e : current.edges)
                    if (parent[e.getEnd()] == null && e.getEnd() != source && e.getMaxCapacity() > e.getFlow()) {
                        parent[e.getEnd()] = e;
                        q.add(graph[e.getEnd()]);
                    }
            }

            if (parent[sink] == null)
                break;

            int pushFlow = INF;

            for (edge e = parent[sink]; e != null; e = parent[e.getStart()])
                pushFlow = min(pushFlow , e.getMaxCapacity() - e.getFlow());

            for (edge e = parent[sink]; e != null; e = parent[e.getStart()]) {
                e.setFlow(e.getFlow() + pushFlow);
                e.getReverse().setFlow(e.getReverse().getFlow() - pushFlow);
            }

//            maxFlow += pushFlow;
        }
//        for(int i =0; i<nodes-1; i++){
//            graph[i].print();
//        }
        for(int i = 0; i<graph[0].edges.size(); i++){
            if(graph[0].edges.get(i).getFlow() != graph[0].edges.get(i).getMaxCapacity()){
                s.append(teamName[currentTeam] + " is eliminated\n");
                s.append("They can win at most "+wins[num]+" + "+left[num]+" = "+(wins[num]+left[num])+" games.\n");
                String[] nums = a.getName(graph[0].edges.get(i).getEnd()).split("");
                int x = Integer.parseInt(nums[0]);
                int y = Integer.parseInt(nums[1]);
                int t = x+y+graph[0].edges.get(i).getMaxCapacity()-graph[0].edges.get(i).getFlow();
                s.append(teamName[x]+" and "+teamName[y]+ " have won a total of "+(wins[x]+wins[y])+" games.\n");
                s.append("They play each other "+mat[x][y]+" times.\n");
                float tot = (wins[x]+ wins[y]+ mat[x][y]);
                s.append("So, on average, each of the team wins "+tot+"/2 ="+tot/2+" games.\n\n");
                return;
            }
        }

    }
    private int findmax(int a, int b){
        if(a>b){
            return a;
        }
        return b;
    }
    public String getString(){
        return s.toString();
    }
    private int min(int a, int b){
        if(a<b){
            return a;
        }
        return b;
    }


}
