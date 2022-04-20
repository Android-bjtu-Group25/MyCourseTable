package table;

public class Class {
    public String name;
    public int weekday;//这周的第几节
    public int daytime;//第几节
    public Weektime weektime;//第几周
    public String teacher;
    public String position;

    //其中daytime，name和weektime必须有，weektime默认normal，其余可以为空
    public Class(String name, int weekday, int daytime, Weektime weektime, String teacher, String position) {
        this.teacher = teacher;
        this.position = position;
        this.name = name;
        this.daytime = daytime;//第几节
        this.weekday = weekday;//一周的第几天
        if (weektime != null)
            this.weektime = weektime;
        else
            this.weektime = Weektime.normal;
    }
}