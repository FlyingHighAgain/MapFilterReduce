{-
  MapFilterReduce.hs

  説明：Map Filter Reduceのサンプルプログラム

  ファイル filelist.txtから、ファイル名とサイズの読み込み
  ファイル名が'A'で始まるデータを抽出(Filter)
  サイズデータだけに変換(Map)
  サイズを集約する(Reduce)
-}


module Main where
  import MyModule
  import Data.Char(toUpper)
  import Data.List(groupBy, sortBy)
  import Data.Function(on)

  -- メイン処理
  main = do

    -- ファイルを読み込みファイル名とサイズの一覧を取得
    content <- readFile "filelist.txt"
    let fileSizes = map toFileSize (lines content)

    -- ファイル名がAで始まるデータを抽出(Filter)
    let filteredList = filter (\f -> (head . map toUpper . fileName) f == 'A') fileSizes
    print filteredList

    -- サイズデータだけに変換する(Map)
    let sizes = map (\f -> size f) filteredList
    print sizes

    -- 合計サイズを計算する(Reduce)
    let totalSize = foldl (+) 0 sizes
    print totalSize


