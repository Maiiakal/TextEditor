public class BitMap {

    private boolean[][] bitmap = new boolean[8][8];

    public BitMap() {
    }

    public BitMap(boolean[][] bitmap) {
        for(int i=0; i < 8; i++)
            for(int j=0; j < 8; j++)
                this.bitmap[i][j]=bitmap[i][j];
    }

    public boolean[][] getBitmap() {
        return bitmap;
    }

    public void setBitmap(boolean[][] bitmap) {
        for(int i=0; i < 8; i++)
            for(int j=0; j < 8; j++)
                this.bitmap[i][j]=bitmap[i][j];
    }

    @Override
    public String toString() {
        String s="";
        for(int i=0; i < 8; i++) {
            for (int j = 0; j < 8; j++)
                s = s + bitmap[i][j] +" ";
            s=s+"\n";
        }
        return s;
    }
}
