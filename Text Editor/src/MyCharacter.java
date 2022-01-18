import javafx.scene.paint.Color;

public class MyCharacter {

    private char ascii_value;
    private Color color;

    public MyCharacter() {
    }

    public MyCharacter(char ascii_value, Color color) {
        this.ascii_value = ascii_value;
        this.color = color;
    }

    public int getAscii_value() {
        return ascii_value;
    }

    public void setAscii_value(char ascii_value) {
        this.ascii_value = ascii_value;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "MyCharacter{" +
                "ascii_value=" + ascii_value +
                ", color=" + color +
                '}';
    }
}
