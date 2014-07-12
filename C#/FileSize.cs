using System;

public class FileSize
{
	public string FileName { get; set; }

	public long Size { get; set; }
	
	public static FileSize Parse (string line)
	{
		string[] items = line.Split ('\t');
		if (items.Length < 2) {
			return null;
		}
		
		return new FileSize () { FileName = items [0], Size = long.Parse (items [1]) };
	}

	public override string ToString ()
	{
		return string.Format ("[FileSize: FileName={0}, Size={1}]", FileName, Size);
	}
}

