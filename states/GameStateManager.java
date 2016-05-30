package states;

import java.util.ArrayList;

public final class GameStateManager
{
	private static ArrayList<GameState> states;
	private static State currentStateValue;
	private static GameState currentState;

	private GameStateManager()
	{
	}

	public static void init()
	{
		states = new ArrayList<GameState>();
		// states.add(new PlayState());
		// states.add(new MenuState());
		// states.add(new CreditsState());
		// states.add(new InstructionsState());
	}

	public static void setState(State state)
	{
		if (state == currentStateValue)
			return;

		states.get(currentStateValue.ordinal()).cleanUp();

		currentState = states.get(state.ordinal());
		currentState.init();
		currentStateValue = state;

	}

}
