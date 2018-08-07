package application;

public class AAStats {

	private int AA_Pos;
	private int freq;
	
	public AAStats(int AA_Position, int frequency) {
		this.AA_Pos = AA_Position;
		this.freq = frequency;
	}
	
	public int getPosition() {
		return AA_Pos;
	}
	
	public int getFrequency() {
		return freq;
	}
	
	public void addOne() {
		freq++;
	}
	
	public String print() {
		return AA_Pos + ": " + freq;
	}
}
