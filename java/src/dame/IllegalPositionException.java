package dame;

public class IllegalPositionException extends RuntimeException
{
	public IllegalPositionException(String s, String message)
	{
		super("Ungültige Positionsangabe "+s+": "+message);
	}
	
}
