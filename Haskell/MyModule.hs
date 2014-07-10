-- MyModule.hs

module MyModule where
  import Data.Char(toUpper)
  import Data.List(elemIndex)
  import Data.List.Split(splitOn)


  -- サイズ情報用レコードの定義
  data FileSize = FileSize {
      fileName :: String
    , size :: Integer
  } deriving (Show)


  -- 文字列からFileSize型へ変換
  toFileSize :: String -> FileSize
  toFileSize line = FileSize filename size
    where
      xs = splitOn "\t" line
      filename = xs !! 0
      size = read (xs !! 1) :: Integer

  -- FileSize型から文字列へ変換
  fromFileSize :: FileSize -> String
  fromFileSize fs = (fileName fs) ++ "\t" ++ show (size fs)


  -- ファイル名の拡張子取得
  getExtension s = if elemIndex '.' s == Nothing then ""
                   else (map toUpper . last . splitOn ".") s


