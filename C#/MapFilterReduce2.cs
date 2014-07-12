/*
  MapFilterReduce2.cs

  説明：Map Filter Reduceのサンプルプログラム

  ファイル filelist.txtから、ファイル名とサイズの読み込み
  ファイルタイプ（拡張子）毎の合計サイズを計算し出力する。
  ただし、拡張子のないデータは除く
*/
using System;
using System.IO;
using System.Linq;
using System.Collections.Generic;

class MapFilterReduce2
{
	public static void Main (string[] args)
	{
		// ファイルを読み込みファイル名とサイズの一覧を取得
		var fileSizes = File.ReadLines ("filelist.txt")
			.Select (line => FileSize.Parse (line))
			.Where (fs => fs != null);
		
		// ファイル名を拡張子に変換（Map）
		var fileExtSizes = fileSizes
			.Select (fs => 
			       new FileSize ()
			       {
						FileName = Path.GetExtension (fs.FileName).ToUpper (),
						Size = fs.Size
					})
			.ToList ();
		//PrintList(fileExtSizes);
		
		// 拡張子のないデータを削除(Filter)
		var fileExtSizes2 = fileExtSizes
			.Where (fs => !string.IsNullOrWhiteSpace (fs.FileName))
			.ToList ();
		//PrintList(fileExtSizes2);
		
		// 拡張子でグループ化
		var extGroups = fileExtSizes2
			.GroupBy (fs => fs.FileName)
			.ToList ();
		//PrintList(extGroups);
		
		// グループ毎にサイズを集約（Reduce）
		var extSizes = extGroups
			.Select (grp => 
			        new FileSize () {
						FileName = grp.Key,
						Size = grp.Sum (fs => fs.Size)
					})
			.ToList ();
		//PrintList(extSizes);
		
		// サイズの大きい順にソート
		var extSize2 = extSizes
			.OrderByDescending (fs => fs.Size)
			.ToList ();
		//PrintList(extSize2);
		foreach (FileSize fs in extSize2) {
			Console.WriteLine ("{0}\t{1}", fs.FileName, fs.Size);	
		}
	}

	public static void PrintList<T> (IEnumerable<T> list)
	{
		string s = "[" + string.Join (",", list.Select (item => item.ToString ()).ToArray ()) + "]";
		Console.WriteLine (s);
	}
	
}
