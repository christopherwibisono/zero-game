package zerogame;

public class playerStatus {
	
	protected String Name;
	protected Integer Zerc, Exp, totala = 0;
	
	public String getName() {
		return Name;
	}

	public Integer getTotala() {
		return totala;
	}

	public void setTotala(Integer totala) {
		this.totala = totala;
	}

	public void setName(String name) {
		Name = name;
	}

	public Integer getZerc() {
		return Zerc;
	}

	public void setGold(Integer zerc) {
		Zerc = zerc;
	}

	public Integer getExp() {
		return Exp;
	}

	public void setExp(Integer exp) {
		Exp = exp;
	}
	
	public playerStatus(String name, Integer zerc, Integer exp) {
		super();
		Name = name;
		Zerc = zerc;
		Exp = exp;
	}
}