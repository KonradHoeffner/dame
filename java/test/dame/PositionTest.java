package dame;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PositionTest
{

	@Test
	void testPositionString()
	{
		String s = Position.fromString("A1").toString();
		System.out.println(s);
		assertThrows(IllegalPositionException.class, ()->{Position.fromString("A11");});
		assertThrows(IllegalPositionException.class, ()->{Position.fromString("Z1");});
		assertThrows(IllegalPositionException.class, ()->{Position.fromString("A9");});
	}

}
