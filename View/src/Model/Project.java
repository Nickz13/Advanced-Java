package Model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Project implements Serializable{
    String title, desc, pid, oid, skill;
    int p, n, a, w;

    public Project(String title, String desc, String pid, String oid, int p, int n, int a, int w){
        this.title = title;
        this.desc = desc;
        this.pid = pid;
        this.oid = oid;
        this.p = p;
        this.n = n;
        this.a = a;
        this.w = w;
        this.skill = compare();
    }

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<String, Integer> > list = 
               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
            public int compare(Map.Entry<String, Integer> o1,  
                               Map.Entry<String, Integer> o2) 
            { 
                return (o1.getValue()).compareTo(o2.getValue());
            } 
        }); 
        Collections.reverse(list);
        // put data from sorted list to hashmap  
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
        for (Map.Entry<String, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    }

    public String compare(){
        HashMap<String, Integer> skill = new HashMap<String, Integer>();
        skill.put("P", p);
        skill.put("N", n);
        skill.put("A", a);
        skill.put("W", w);
        Map<String, Integer> skills = sortByValue(skill);

        String skillset = "";
        for (Map.Entry<String, Integer> en : skills.entrySet()) { 
            skillset = skillset.concat(en.getKey() + " " + en.getValue()+" ");
        }
        return skillset;
    }

    public String getSkill() {
        return skill;
    }

    public String getOid() {
        return oid;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getPid() {
        return pid;
    }

    public int getP() {
        return p;
    }

    public int getN() {
        return n;
    }

    public int getA() {
        return a;
    }

    public int getW() {
        return w;
    }
    
}