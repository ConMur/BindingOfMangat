package states;

public class LoadingThread implements Runnable {

private GameState state;
	
	public LoadingThread(GameState s)
	{
		state = s;
	}
	
	@Override
	public void run()
	{
		state.init();
	}

}