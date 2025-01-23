import java.util.Scanner;

public class Cow {
    private static String line = "____________________________________________________________\n";

    public static void main(String[] args) {
        String line = "____________________________________________________________\n";
        String start = line + "Hello! I'm Cow\nWhat can I do for you?\n" + line;
        String end = line + "Bye. Hope to see you again soon!\n" + line;
        System.out.println(start);

        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println(end);
                break;
            }
            System.out.println(line + input + "\n" + line);
        }

        sc.close();
    }
}
