package table;

public enum Weektime {
    q("q"),//前8周
    h("h"),//后8周
    d("d"),//单周
    s("s"),//双周
    normal("normal");//全16周

    private String text;

    Weektime(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static Weektime fromString(String text) {
        for (Weektime b : Weektime.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}