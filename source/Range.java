package source;

import java.util.*;
import java.io.Serializable;
import java.lang.reflect.Field;

public class Range extends ArrayList<String> implements Serializable {
    
    String name;

    public Range() {}

    public Range(String name) {
        this.name = name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}
