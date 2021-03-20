package dame;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BrettTest
{

	@Test
	void testZug()
	{
		Brett brett = new Brett();
		assertThrows(IllegalMoveException.class,()->{brett.zug("A1");});
		assertThrows(IllegalMoveException.class,()->{brett.zug("A1 B2");});
		// normale Züge
		Brett brett2 = brett.zug("C3 D4"); // weiß beginnt
	// schwarz ist nicht dran im Anfangsbrett
		assertThrows(IllegalMoveException.class,()->{brett.zug("B6 C5");});
		Brett brett3 = brett2.zug("B6 C5"); // schwarz Zug 2
		// relative Zugpositionen ungültig
		assertThrows(IllegalMoveException.class,()->{brett.zug("C3 D5");});
		// Schlagen
	// weiß darf nicht sich selbst schlagen
		assertThrows(IllegalMoveException.class,()->{brett.zug("F2 H4");});
		System.out.println(brett3);
		brett3.zug("D4 B6"); // weiß schlägt schwarz
	}

}
