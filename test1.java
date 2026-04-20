import java.util.Objects;

public class Book {
    private String bookId;       // 图书编号
    private String bookName;     // 书名
    private String author;       // 作者
    private String publisher;    // 出版社
    private int publishYear;     // 出版年份
    private int total;           // 总册数
    private int stock;           // 可借库存
    private boolean isAvailable; // 是否可借

    // 无参构造
    public Book() {}

    // 全参构造
    public Book(String bookId, String bookName, String author, String publisher,
                int publishYear, int total) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.total = total;
        this.stock = total;
        this.isAvailable = stock > 0;
    }

    // 借书方法
    public boolean borrowBook() {
        if (stock > 0) {
            stock--;
            isAvailable = stock > 0;
            System.out.println("借书成功：" + bookName);
            return true;
        } else {
            System.out.println("该书已无库存！");
            return false;
        }
    }

    // 还书方法
    public boolean returnBook() {
        if (stock < total) {
            stock++;
            isAvailable = true;
            System.out.println("还书成功：" + bookName);
            return true;
        } else {
            System.out.println("该书库存已满，无需归还！");
            return false;
        }
    }

    // 信息校验
    public boolean isValidBook() {
        if (bookId == null || bookId.trim().isEmpty()) return false;
        if (bookName == null || bookName.trim().isEmpty()) return false;
        if (author == null || author.trim().isEmpty()) return false;
        if (publisher == null || publisher.trim().isEmpty()) return false;
        if (publishYear < 1000 || publishYear > 2026) return false;
        if (total <= 0) return false;
        return stock >= 0 && stock <= total;
    }

    // get/set方法
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public int getPublishYear() { return publishYear; }
    public void setPublishYear(int publishYear) { this.publishYear = publishYear; }
    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    // equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(bookId, book.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId);
    }

    // 打印信息
    @Override
    public String toString() {
        return "图书信息{" +
                "编号='" + bookId + '\'' +
                ", 书名='" + bookName + '\'' +
                ", 作者='" + author + '\'' +
                ", 出版社='" + publisher + '\'' +
                ", 出版年份=" + publishYear +
                ", 总册数=" + total +
                ", 可借库存=" + stock +
                ", 是否可借=" + isAvailable +
                '}';
    }

    // 新增：Java程序主入口方法，解决无main方法报错
    public static void main(String[] args) {
        // 测试图书类功能
        Book testBook = new Book("B001", "Java编程思想", "Bruce Eckel", "机械工业出版社", 2007, 5);
        
        // 打印初始图书信息
        System.out.println("=====初始图书信息=====");
        System.out.println(testBook);
        
        // 校验图书信息合法性
        System.out.println("图书信息是否有效：" + testBook.isValidBook());
        
        // 测试借书功能
        System.out.println("\n=====测试借书=====");
        testBook.borrowBook();
        System.out.println("借书后库存：" + testBook.getStock());
        
        // 测试还书功能
        System.out.println("\n=====测试还书=====");
        testBook.returnBook();
        System.out.println("还书后库存：" + testBook.getStock());
    }
}
