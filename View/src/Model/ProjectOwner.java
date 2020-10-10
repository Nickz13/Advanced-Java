package Model;

import java.io.Serializable;

public class ProjectOwner implements Serializable{
    String fname, sname, pid, role, email, eid;
    
    public ProjectOwner(String fname, String sname, String pid, String role, String email, String eid){
        this.fname = fname;
        this.sname = sname;
        this.pid = pid;
        this.role = role;
        this.email = email;
        this.eid = eid;
    }

    public String getEid() {
        return eid;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getPid() {
        return pid;
    }

    public String getRole() {
        return role;
    }

    public String getSname() {
        return sname;
    }

}