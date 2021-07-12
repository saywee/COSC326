import java.util.*;

public class Arithmetic {

	private ArrayList<Integer> numbers = new ArrayList<Integer>();
	private int target;
	private String order;

	public static void main(String[] args) {
		Arithmetic a = new Arithmetic();
		a.getNums();
	}

	public void getNums() {
		Scanner sc = new Scanner(System.in);
		String line[] = null;
		String tarOrd[] = null;
		String expression = "";
		while (sc.hasNextLine()) {
			line = sc.nextLine().split(" ");
			tarOrd = sc.nextLine().split(" ");
			for (int i = 0; i < line.length; i++) {
				try {
					numbers.add(Integer.parseInt(line[i]));
				} catch (Exception e) {
					System.out.println("Input has to be a number.");
				}
			}

			try {
				target = Integer.parseInt(tarOrd[0]);
				order = tarOrd[1];
			} catch (Exception e) {
				System.out.println("Target has to be a number.");
			}

			if (order.equals("L")) {
				int d = 0;
				expression = leftRight(numbers, 0, 0 + numbers.get(0), numbers.get(0).toString());
				if (!expression.equals("impossible")) {
					numbers.clear();
					System.out.println(expression);
				} else {
					numbers.clear();
					System.out.println(target + " " + order + " impossible");
				}
			} else if (order.equals("N")) {
				expression = normalOp(numbers, 0, 0, numbers.get(0), numbers.get(0).toString());
				if (!expression.equals("impossible")) {
					numbers.clear();
					System.out.println(expression);
				} else {
					numbers.clear();
					System.out.println(target + " " + order + " impossible");
				}
			}
		}
		sc.close();
	}

	public String leftRight(ArrayList<Integer> nums, int index, int sum, String exp) {
		/*System.out.println("index at : " + index);
		System.out.println(sum);*/

		if(sum > target) {
			return "impossible";
		}
		
		if(nums.get(index)>target) {
			return "impossible";
		}
		
		if (index == nums.size() - 1) {
			if (sum == target) {
				return exp;
			} else {
				return "impossible";
			}
		}

		String add = leftRight(nums, index + 1, sum + nums.get(index + 1), exp + " + " + nums.get(index + 1));
		if (!add.equals("impossible")) {
			return add;
		} else {
			String multi = leftRight(nums, index + 1, sum * nums.get(index + 1), exp + " x " + nums.get(index + 1));
			if (!multi.equals("impossible")) {
				return multi;
			}
		}
		return "impossible";
	}

	public String normalOp(ArrayList<Integer> nums, int index, int sum, int prev, String exp) {
		/*System.out.println("index at : " + index);
		System.out.println("Sum is : " + sum);*/
		if(sum+prev > target) {
			return "impossible";
		}
		
		if(nums.get(index)>target || sum > target) {
			return "impossible";
		}
		
		if (index == nums.size() - 1) {
			if (sum + prev == target) {
				return exp;
			} else {
				return "impossible";
			}
		}

		String add = normalOp(nums, index + 1, sum + prev, nums.get(index + 1), exp + " + " + nums.get(index + 1));
		if (!add.equals("impossible")) {
			return add;
		} else {
			String multi = normalOp(nums, index + 1, sum, prev * nums.get(index + 1),
					exp + " x " + nums.get(index + 1));
			if (!multi.equals("impossible")) {
				return multi;
			}
		}
		return "impossible";
	}
}