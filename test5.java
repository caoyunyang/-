import java.util.List;
import java.util.Scanner;

/**
 * 组员5：系统主入口，菜单控制台、流程调度
 * 行数：180+
 */
public class LibrarySystem {
    private static Scanner scanner = new Scanner(System.in);
    private static BookManager bookManager = new BookManager();
    private static BorrowService borrowService = new BorrowService(bookManager);
    private static User currentUser;

    public static void main(String[] args) {
        System.out.println("====== 欢迎使用图书管理系统 ======");
        login();
        showMainMenu();
    }

    // 登录
    private static void login() {
        System.out.print("请输入账号：");
        String userId = scanner.next();
        System.out.print("请输入密码：");
        String pwd = scanner.next();
        // 模拟默认用户
        User admin = new User("admin", "系统管理员", "123456", "13800001111", "admin");
        User reader = new User("reader", "张三", "123456", "13900002222", "reader");
        if (admin.getUserId().equals(userId) && admin.login(pwd)) {
            currentUser = admin;
        } else if (reader.getUserId().equals(userId) && reader.login(pwd)) {
            currentUser = reader;
        } else {
            System.out.println("账号或密码错误！");
            login();
        }
    }

    // 主菜单
    private static void showMainMenu() {
        while (true) {
            System.out.println("\n====== 主菜单 ======");
            System.out.println("1. 查看所有图书");
            System.out.println("2. 查询图书");
            System.out.println("3. 我要借书");
            System.out.println("4. 我要还书");
            System.out.println("5. 查看借阅记录");
            System.out.println("6. 退出系统");
            System.out.print("请选择操作：");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: showAllBooks(); break;
                case 2: searchBook(); break;
                case 3: borrowBook(); break;
                case 4: returnBook(); break;
                case 5: showRecords(); break;
                case 6:
                    System.out.println("谢谢使用，再见！");
                    return;
                default:
                    System.out.println("输入错误，请重新选择！");
            }
        }
    }

    private static void showAllBooks() {
        bookManager.printAllBooks();
    }

    private static void searchBook() {
        System.out.print("请输入图书名称：");
        String name = scanner.next();
        List<Book> list = bookManager.findBooksByName(name);
        if (list.isEmpty()) {
            System.out.println("未找到相关图书！");
            return;
        }
        for (Book b : list) {
            System.out.println(b);
        }
    }

    private static void borrowBook() {
        System.out.print("请输入图书编号：");
        String bookId = scanner.next();
        borrowService.borrowBook(currentUser, bookId);
    }

    private static void returnBook() {
        System.out.print("请输入图书编号：");
        String bookId = scanner.next();
        borrowService.returnBook(currentUser, bookId);
    }

    private static void showRecords() {
        List<BorrowService.BorrowRecord> list = borrowService.getRecordsByUser(currentUser.getUserId());
        if (list.isEmpty()) {
            System.out.println("暂无借阅记录！");
            return;
        }
        for (BorrowService.BorrowRecord r : list) {
            System.out.println(r);
        }
    }
}
