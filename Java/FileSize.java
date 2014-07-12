// FileSize.java

// サイズ情報用のクラス
public class FileSize {

    public FileSize(String fileName, long size) {
        this.fileName = fileName;
        this.size = size;
    }

    private String fileName;

    public String getFileName() {
        return this.fileName;
    }

    private long size;

    public long getSize() {
        return this.size;
    }

    public static FileSize parse(String line) {
        String[] items = line.split("\t");
        if (items.length < 2) {
            return null;
        }

        return new FileSize(items[0], Long.valueOf(items[1]));
    }

    @Override
    public String toString() {
        return String.format("[FileSize: FileName=%s, Size=%d]", fileName, size);
    }

}

