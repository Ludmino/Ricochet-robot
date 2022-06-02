package isep.godefroy.ricochet.ricochet_robot;

public class Time {
    private int second;

    public Time(int second) {
        this.second = second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public String getCurrentTime(){
        return (second+"s");
    }

    public void oneSecondPassed(){
        if (second>0){
            second--;
        }
    }

    public int getSecond(){
        return second;
    }
}
