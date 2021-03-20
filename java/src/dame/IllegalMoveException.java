package dame;

public class IllegalMoveException extends RuntimeException
{
	public IllegalMoveException(String s, String message)
	{
		super("Ungültiger Zug "+s+": "+message);
	}

}
