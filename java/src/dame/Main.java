package dame;

import java.util.Scanner;

public class Main
{

	public static void main(String[] args)
	{
		Brett brett = new Brett();
		String[] spielerNamen = {"Schwarz","Weiß"};
		Scanner in = new Scanner(System.in);
		while(true)
		{
			System.out.println(brett);
			System.out.print("Zug "+(brett.zug+1)+ " Spieler "+spielerNamen[brett.passiverSpieler()]+": ");
			String input = in.nextLine().trim();
			try
			{
				if(input.equals("_"))
				{
					brett = brett.vorgaenger;
					continue;
				}
				brett = brett.zug(input);
			}
			catch(IllegalMoveException e)
			{
				System.err.println("Bitte Zug erneut eingeben. "+e.getMessage());
			}
		}
	}

}
