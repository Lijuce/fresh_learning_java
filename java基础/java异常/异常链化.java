/**
 * 异常链表Demo演示
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("请输入2个加数");
        int result;
        try
        {
            result = add();
            System.out.println("结果:"+result);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static List<Integer> getInputNumbers()
    {
        List<Integer> nums = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        try {
            int num1 = scan.nextInt();
            int num2 = scan.nextInt();
            nums.add(new Integer(num1));
            nums.add(new Integer(num2));
        }catch(InputMismatchException immExp){
            throw immExp;
        }finally {
            scan.close();
        }
        return nums;
    }

    private static int add() throws Exception{
        int result = 0;

        try {
            List<Integer> inputNumbers = getInputNumbers();
            result = inputNumbers.get(0) + inputNumbers.get(1);
        } catch (InputMismatchException e) {
            // 异常的链化：以一个异常对象为参数构造新的异常对象
            throw new Exception("计算失败", e);
        }
        return result;
    }
}
