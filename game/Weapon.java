
package game;

/**
 * A Weapon Token represents the token that sits in random rooms. These can be swapped when a player makes a
 * suggestion.
 *
 */
public class Weapon implements Locatable {

	private String name;

	public Weapon(String name){
		this.name = name;
	}

	@Override
	public int[] getLocation() {
		return null;
	}

	public String getName(){
		return name;
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof Weapon){
			Weapon w = (Weapon)o;
			if(w.getName().equalsIgnoreCase(this.getName())){
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString(){
		return this.name;
	}
}
