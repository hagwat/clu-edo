
package game;

public class WeaponToken implements Locatable {

	private String name;

	public WeaponToken(String name){
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
		if(o instanceof WeaponToken){
			WeaponToken w = (WeaponToken)o;
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
