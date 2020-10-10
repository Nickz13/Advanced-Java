package Model;

import java.io.Serializable;

public class Student implements Serializable{
    private String sid, score;

    public Student(String sid, String score){
        this.sid = sid;
        this.score = score;
    }
    public String getScore() {
        return score;
    }
    public String getSid() {
        return sid;
    }
    
}