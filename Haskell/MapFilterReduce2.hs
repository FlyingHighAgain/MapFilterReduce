{-
  MapFilterReduce2.hs

  説明：Map Filter Reduceのサンプルプログラム

  ファイル filelist.txtから、ファイル名とサイズの読み込み
  ファイルタイプ（拡張子）毎の合計サイズを計算し出力する。
  ただし、拡張子のないデータは除く
-}


module Main where
  import MyModule
  import Data.List(groupBy, sortBy)
  import Data.Function(on)

  -- メイン処理
  main = do

    -- ファイルを読み込みファイル名とサイズの一覧を取得
    content <- readFile "filelist.txt"
    let fileSizes = map toFileSize (lines content)

    -- ファイル名を拡張子に変換（Map）
    let fileExtSizes = map (\fs -> FileSize ((getExtension . fileName) fs) (size fs)) fileSizes

    -- 拡張子のないデータを削除(Filter)
    let fileExtSizes' = filter (\fs -> fileName fs /= "") fileExtSizes

    -- 拡張子でグループ化
    let extGroups = groupBy (on (==) fileName) $ sortBy (compare `on` fileName) fileExtSizes'

    -- グループ毎にサイズを集約（Reduce）
    let extSizes = map      (\grp -> FileSize   (fileName (grp !! 0))    (sum $ map (\fs -> size fs) grp)      )  extGroups

    -- サイズの大きい順にソート
    let extSizes' = map fromFileSize $ sortBy ((flip compare) `on` size) extSizes

    putStrLn $ unlines extSizes'


