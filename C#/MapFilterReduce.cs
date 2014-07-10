/*
  MapFilterReduce.cs

  説明：Map Filter Reduceのサンプルプログラム

  ファイル filelist.txtから、ファイル名とサイズの読み込み
  ファイル名が'A'で始まるデータを抽出(Filter)
  サイズデータだけに変換(Map)
  サイズを集約する(Reduce)
*/

using System;
using System.IO;
using System.Linq;
using System.Collections.Generic;

class MapFilterReduce
{
	public static void Main (string[] args)
	{
		// ファイルを読み込みファイル名とサイズの一覧を取得
		var fileSizes = File.ReadLines("filelist.txt")
			.Select (line => FileSize.Parse(line))
			.Where (fs => fs != null);
		
		// ファイル名がAで始まるデータを抽出(Filter)
		var filteredList = fileSizes
			.Where (fs => fs.FileName.ToUpper().StartsWith("A"))
			.ToList();
		PrintList(filteredList);
		
		// サイズデータだけに変換する(Map)
		var sizes = filteredList
			.Select(fs => fs.Size)
			.ToList ();
		PrintList(sizes);
		
		// 合計サイズを計算する(Reduce)
		var totalSize = sizes.Aggregate(0L, (total, next) => total+next);
		Console.WriteLine("total size: {0}", totalSize);
		
	}

	public static void PrintList<T>(IEnumerable<T> list)
	{
		string s = "[" + string.Join (",", list.Select(item => item.ToString()).ToArray()) + "]";
		Console.WriteLine(s);
	}
	
}
