package game;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class TextClient {

	private Game game;

	public TextClient(Game game) {
		this.game = game;
		startup();
	}

	public static String readString(String msg){
		System.out.println(msg);
		Scanner sc = new Scanner(System.in);
		String s = sc.next();
		sc.close();
		return s;
	}

	public static int readInt(String msg){
		System.out.println("");
		System.out.println(msg);
		Scanner sc = new Scanner(System.in);
		try{
			int i = sc.nextInt();
			sc.close();
			return i;
		}
		catch(InputMismatchException e){
			System.out.println("Must be a number!");
			return readInt(msg);
		}
		finally{
			sc.close();
		}
	}

	public void startup() {
		System.out.println("********************************");
		System.out.println("       Welcome to Cluedo");
		System.out.println("********************************");
		//setPlayers();
	}
}
