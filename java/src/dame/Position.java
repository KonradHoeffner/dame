package dame;

public record Position(int zeile, int spalte)
{
	static Position fromString(String s)
	{		
		// z.B. "A1"
		if(s.length()!=2)
		{
			throw new IllegalPositionException(s,"Positionsstring hat Länge "+s.length()+". 2 erwartet.");
		}
		int zeile = s.charAt(1)-'1';
		zeile = 7 - zeile;
		
		int spalte = s.charAt(0)-'A';
		if(zeile<0||zeile>=Brett.ZEILEN) {throw new IllegalPositionException(s,"Ungültige Zeile");}
		if(spalte<0||spalte>=Brett.SPALTEN) {throw new IllegalPositionException(s,"Ungültige Spalte");}
		return new Position(zeile,spalte);
	}
}
