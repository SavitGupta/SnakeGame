import java.io.Serializable;
import java.util.Date;

public class Score implements Serializable{
    private Date date;
    private Integer value;
    private String name;
    public Score(Integer value , String name){
        date = new Date();
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }
}
