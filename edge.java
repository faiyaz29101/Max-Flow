public class edge {
    private int start;
    private int end;
    private int flow;
    private int MaxCapacity;
    private edge reverse;
    public edge(int start, int end, int flow, int maxCapacity){
        this.start = start;
        this.end = end;
        this.MaxCapacity = maxCapacity;
        this.flow = flow;
    }
    public void setReverse(edge e){
        reverse = e;
    }
    public void print(){
            System.out.println(start + " " + end + " " + flow + " " + MaxCapacity);

    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getMaxCapacity() {
        return MaxCapacity;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public edge getReverse() {
        return reverse;
    }
}
