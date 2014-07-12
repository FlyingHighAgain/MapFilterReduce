/*
  MapFilterReduce.java

  説明：Map Filter Reduceのサンプルプログラム

  ファイル filelist.txtから、ファイル名とサイズの読み込み
  ファイル名が'A'で始まるデータを抽出(Filter)
  サイズデータだけに変換(Map)
  サイズを集約する(Reduce)
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class MapFilterReduce {

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

        } catch (IOException e) {
            System.out.println(e.toString());
        }
        //printList(fileSizes);

        // ファイル名がAで始まるデータを抽出(Filter)
        List<FileSize> filteredList = fileSizes
                .stream()
                .filter(fs -> fs.getFileName().toUpperCase().startsWith("A"))
                .collect(Collectors.toList());
        printList(filteredList);


        // サイズデータだけに変換する(Map)
        List<Long> sizes = filteredList
                .stream()
                .map(fs -> fs.getSize())
                .collect(Collectors.toList());
        printList(sizes);

        // 合計サイズを計算する(Reduce)
        long totalSize = sizes.stream()
                .reduce(0L, (total, next) -> total + next);
        System.out.println("total size: " + totalSize);

    }

    public static <T> void printList(List<T> list) {
        String s = "[" + String.join(",", list.stream().map(item -> item.toString()).toArray(String[]::new)) + "]";
        System.out.println(s);
    }

}
