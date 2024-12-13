import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * see: https://adventofcode.com/2024/day/13
 *
 */
public class Y24Day13 {
	
	
	record Machine(double xa, double ya, double xb, double yb, double xp, double yp) {

		
		public boolean checkLinear() {
			return Math.abs(xa - ya/yb*xb) < 0.0000001;
		}

		public boolean checkINT(double r) {
			return Math.abs(r - Math.floor(r+0.0000001)) < 0.0000001;
		}

		// a*xa + b*xb = xp
		// a*ya + b*yb = yp
		// 
		// a*xa + b*xb = xp
		// b*yb = yp - a*ya
		//
		// a*xa + b*xb = xp
		// b = (yp - a*ya)/yb
		//
		// a*xa + ((yp - a*ya)/yb)*xb = xp
		//
		// a*xa + (yp/yb - a*ya/yb)*xb = xp
		//
		// a*xa + yp/yb*xb - a*ya/yb*xb = xp
		//
		// a*xa - a*ya/yb*xb = xp - yp/yb*xb 
		//
		// a*(xa - ya/yb*xb) = xp - yp/yb*xb 
		//
		// a = (xp - yp/yb*xb)/(xa - ya/yb*xb)
		public double calcA() {
			return (xp - yp/yb*xb)/(xa - ya/yb*xb);
		}
		
		// b = (yp - a*ya)/yb
		public double calcB() {
			return calcB(calcA());
		}
		// b = (yp - a*ya)/yb
		public double calcB(double a) {
			return (yp - a*ya)/yb;
		}
		
		public long calcCost() {
			if (checkLinear()) {
				throw new RuntimeException("linear");
			}
			else {
				double a = calcA();
				double b = calcB();
				double c = 3*a+b;
				System.out.println("A: "+a+", B: "+b+", COST: "+c);
				if ((a<0) || (b<0) || (!checkINT(a)) || (!checkINT(b))) {
					System.out.println("INVALID");
					return 0;
				}
				return Math.round(c);
			}
		}
	}
	
	private static final String BUTTON_RX = "^Button (A|B): X[+]([0-9]+), Y[+]([0-9]+)$"; 
	private static final String PRIZE_RX = "^Prize: X=([0-9]+), Y=([0-9]+)$";
	
	public static void mainPart1(String inputfile) throws FileNotFoundException {
			
		List<Machine> machines = new ArrayList<>();
		long tokens = 0;
		try (Scanner scanner = new Scanner(new File(inputfile))) {
			while (scanner.hasNext()) {
				String line1 = scanner.nextLine().trim();
				if (line1.isBlank()) {
					continue;
				}
				String line2 = scanner.nextLine().trim();
				String line3 = scanner.nextLine().trim();
				int xa = Integer.parseInt(line1.replaceFirst(BUTTON_RX, "$2"));
				int ya = Integer.parseInt(line1.replaceFirst(BUTTON_RX, "$3"));
				int xb = Integer.parseInt(line2.replaceFirst(BUTTON_RX, "$2"));
				int yb = Integer.parseInt(line2.replaceFirst(BUTTON_RX, "$3"));
				int xprize = Integer.parseInt(line3.replaceFirst(PRIZE_RX, "$1"));
				int yprize = Integer.parseInt(line3.replaceFirst(PRIZE_RX, "$2"));
				Machine machine = new Machine(xa,ya,xb,yb,xprize,yprize);
				long costs = machine.calcCost();
				if (costs>0) {
					tokens += costs;
				}
			}
		}
		System.out.println("TOKENS: "+tokens);    // > 24038
	}


	public static void mainPart2(String inputfile) throws FileNotFoundException {
	}


	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("--- PART I  ---");
//		mainPart1("exchange/day13/feri/input-example.txt");
//		mainPart1("exchange/day13/feri/input-example-2.txt");
		mainPart1("exchange/day13/feri/input.txt");     
		System.out.println("---------------");
		System.out.println();
		System.out.println("--- PART II ---");
		mainPart2("exchange/day13/feri/input-example.txt");
//		mainPart2("exchange/day13/feri/input.txt");
		System.out.println("---------------");
	}

	
}
