import controller.Game;
import view.GUIView;
import view.TextView;
import view.View;

public class Battleship {

	public static void main(String[] args) {
		View view;

		if (args.length != 2 || (!args[1].equals("text") && !args[1].equals("gui"))) {
			System.out.println("Please run with argument \"text\" or \"gui\" to select view mode");
			return;
		}
		if (args[1].equals("text")) {
			view = new TextView();
		} else {
			view = new GUIView();
		}

		new Game(view);
	}
}
