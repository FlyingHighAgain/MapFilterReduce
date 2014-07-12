/*
  MapFilterReduce2.java

  説明：Map Filter Reduceのサンプルプログラム

  ファイル filelist.txtから、ファイル名とサイズの読み込み
  ファイルタイプ（拡張子）毎の合計サイズを計算し出力する。
  ただし、拡張子のないデータは除く
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class MapFilterReduce2 {

    public static void main(String[] args) {

        // ファイルを読み込みファイル名とサイズの一覧を取得
        List<FileSize> fileSizes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("filelist.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {

                FileSize fs = FileSize.parse(line);
                if (fs != null) {
                    fileSizes.add(fs);
                }
            }

            br.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        //printList(fileSizes);

        // ファイル名を拡張子に変換（Map）
        List<FileSize> fileExtSizes = fileSizes
                .stream()
                .map(fs ->
                        new FileSize(getExtension(fs.getFileName()).toUpperCase(), fs.getSize()))
                .collect(Collectors.toList());
        //printList(fileExtSizes);

        // 拡張子のないデータを削除(Filter)
        List<FileSize> fileExtSizes2 = fileExtSizes
                .stream()
                .filter(fs -> fs.getFileName() != null && fs.getFileName().trim().length() > 0)
                .collect(Collectors.toList());
        //printList(fileExtSizes2);

        // 拡張子でグループ化
        Map<String, List<FileSize>> extGroups = fileExtSizes2
                .stream()
                .collect(Collectors.groupingBy(FileSize::getFileName));
        //extGroups.forEach((key, value) -> {System.out.print(key + " - "); printList(value); });

        List<FileSize> extSizes = extGroups
                .entrySet()
                .stream()
                .map(grp ->
                        new FileSize(
                                grp.getKey(),
                                grp.getValue().stream().mapToLong(fs -> fs.getSize()).sum()))
                .collect(Collectors.toList());
        //printList(extSizes);

        // サイズの大きい順にソート
        List<FileSize> extSize2 = extSizes
                .stream()
                .sorted((fs1, fs2) -> {
                    long comp = fs2.getSize() - fs1.getSize();
                    if (comp > 0) return 1;
                    if (comp < 0) return -1;
                    return 0;
                })
                .collect(Collectors.toList());
        //printList(extSize2);

        for (FileSize fs : extSize2) {
            System.out.println(String.format("%s\t%d", fs.getFileName(), fs.getSize()));
        }

    }

    public static String getExtension(String filename) {
        String s[] = filename.split("\\.");
        if (s.length < 2) {
            return "";
        }
        return s[s.length - 1];
    }

    public static <T> void printList(List<T> list) {
        String s = "[" + String.join(",", list.stream().map(item -> item.toString()).toArray(String[]::new)) + "]";
        System.out.println(s);
    }

}
