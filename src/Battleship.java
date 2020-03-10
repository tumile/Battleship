import controller.Game;
import view.GUIView;
import view.TextView;
import view.View;

public class Battleship {

	public static void main(String[] args) {
		View view;

		if (args.length != 1 || (!args[0].equals("text") && !args[0].equals("gui"))) {
			System.out.println("Please run with argument \"text\" or \"gui\" to select view mode");
			return;
		}
		if (args[0].equals("text")) {
			view = new TextView();
		} else {
			view = new GUIView();
		}

		new Game(view);
	}
}
