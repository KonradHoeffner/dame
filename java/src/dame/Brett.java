package dame;

import java.util.Arrays;

enum Spielstein
{
	SCHWARZ('B'), WEISS('W'), KEINER('_');
	private final char c;
	Spielstein(char c) {this.c=c;}

	@Override public String toString() {return String.valueOf(this.c);}
};

public class Brett
{
	final static int ZEILEN = 8;
	final static int SPALTEN = 8;
	final int zug;
	
	final Brett vorgaenger;

	final Spielstein[][] felder = new Spielstein[ZEILEN][SPALTEN];

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(int zeile=0;zeile<ZEILEN;zeile++)
		{
			for(int spalte=0;spalte<SPALTEN;spalte++)
			{
				sb.append(felder[zeile][spalte]);
				sb.append(' ');
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	/** Initialzustand */
	Brett()
	{
		vorgaenger = this;
		zug = 0;
		for(int zeile=0;zeile<ZEILEN;zeile++)
		{
			Arrays.fill(felder[zeile],Spielstein.KEINER);
		}
		for(int zeile=0;zeile<3;zeile++)
			for(int spalte=0;spalte<SPALTEN;spalte+=2)
			{
				felder[zeile][spalte+((zeile+1)%2)]=Spielstein.SCHWARZ;
				felder[ZEILEN-1-zeile][spalte+((zeile)%2)]=Spielstein.WEISS;
			}
	}

	/** Copy constructor. */
	public Brett(Brett alt)
	{
		this.vorgaenger=alt;
		for(int zeile=0;zeile<ZEILEN;zeile++)
		{
			this.felder[zeile]=Arrays.copyOf(alt.felder[zeile], SPALTEN);
		}
		this.zug=alt.zug+1;

	}

	public Brett zug(String s)
	{
		Brett neu = new Brett(this);
		System.out.println("zug "+neu.zug+": "+s);
		String[] ps = s.split("\\s+");
		if(ps.length!=2) {throw new IllegalMoveException(s, "Genau zwei Positionen erwartet.");}
		try
		{
			Position[] positions = {Position.fromString(ps[0]),Position.fromString(ps[1])};
			if(neu.felder[positions[0].zeile()][positions[0].spalte()]!=neu.aktiverStein())
			{
				throw new IllegalMoveException(s, "An der Startposition "+positions[0]+
						" steht nicht der aktive Spieler für Zug "+neu.zug+" ("+Spielstein.values()[neu.aktiverSpieler()]+").");
			}
			System.out.println(positions[0]+" zu "+positions[1]);

			int diffZeilen = positions[1].zeile()-positions[0].zeile();
			int diffSpalten = Math.abs(positions[1].spalte()-positions[0].spalte());
			int zeilenRichtung = (int)Math.signum(diffZeilen);
			int spielerRichtung = 1-2*neu.aktiverSpieler();

			System.out.println(zeilenRichtung+" "+spielerRichtung);
			if(zeilenRichtung!=spielerRichtung)
			{
				throw new IllegalMoveException(s, "Falsche Richtung!");
			}		
			if(diffSpalten!=Math.abs(diffZeilen))
			{
				throw new IllegalMoveException(s, "Zeilenentfernung "+diffZeilen+" ist nicht gleich Spaltenentfernung "+diffSpalten);
			}
			switch(diffSpalten)
			{
				case 1: // normaler Zug
				{
					if(neu.felder[positions[1].zeile()][positions[1].spalte()]!=Spielstein.KEINER)
					{
						throw new IllegalMoveException(s, "An der Zielposition "+positions[1]+" ist kein freies Feld.");
					}			
					neu.felder[positions[0].zeile()][positions[0].spalte()]=Spielstein.KEINER;
					neu.felder[positions[1].zeile()][positions[1].spalte()]=neu.aktiverStein();
					break;
				}
				case 2: // schlagen
				{
					if(neu.felder[positions[1].zeile()][positions[1].spalte()]!=Spielstein.KEINER)
					{
						throw new IllegalMoveException(s, "An der Zielposition "+positions[1]+" ist kein freies Feld.");
					}	
					Position zwischenPosition = new Position((positions[1].zeile()+positions[0].zeile())/2,
							(positions[1].spalte()+positions[0].spalte())/2);
					if(neu.felder[zwischenPosition.zeile()][zwischenPosition.spalte()]!=neu.passiverStein())
					{
						throw new IllegalMoveException(s, "An der Zwischenposition "+zwischenPosition+" ist kein gegnerischer Spielstein "+neu.passiverStein()+".");
					}
					neu.felder[positions[0].zeile()][positions[0].spalte()]=Spielstein.KEINER;
					neu.felder[zwischenPosition.zeile()][zwischenPosition.spalte()]=Spielstein.KEINER;
					neu.felder[positions[1].zeile()][positions[1].spalte()]=neu.aktiverStein();
					break;
				}
				default:
				{
					throw new IllegalMoveException(s,"Start und Zielspalte sind nicht genau 1 oder 2 entfernt.");
				}
			}
			return neu;
		}
		catch(IllegalPositionException pose) {throw new IllegalMoveException(s, pose.getMessage());}
	}

	public int aktiverSpieler() {return (zug)%2;}
	public int passiverSpieler() {return (zug+1)%2;}

	public Spielstein aktiverStein() {return Spielstein.values()[aktiverSpieler()];}
	public Spielstein passiverStein() {return Spielstein.values()[passiverSpieler()];}


}
