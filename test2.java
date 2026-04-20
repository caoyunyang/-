import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookManager {
    private List<Book> bookList;

    public BookManager() {
        bookList = new ArrayList<>();
        initDefaultBooks();
    }

    // 初始化默认数据
    private void initDefaultBooks() {
        bookList.add(new Book("B001", "Java编程思想", "Bruce Eckel", "机械工业出版社", 2018, 5));
        bookList.add(new Book("B002", "数据结构与算法", "严蔚敏", "清华大学出版社", 2019, 3));
        bookList.add(new Book("B003", "计算机网络", "谢希仁", "电子工业出版社", 2021, 4));
    }

    // 添加图书
    public boolean addBook(Book book) {
        if (book == null || !book.isValidBook()) {
            System.out.println("图书信息不合法，添加失败！");
            return false;
        }
        for (Book b : bookList) {
            if (b.getBookId().equals(book.getBookId())) {
                System.out.println("图书编号已存在，添加失败！");
                return false;
            }
        }
        bookList.add(book);
        System.out.println("图书添加成功：" + book.getBookName());
        return true;
    }

    // 根据ID删除图书
    public boolean deleteBookById(String bookId) {
        if (bookId == null || bookId.trim().isEmpty()) {
            System.out.println("图书编号不能为空！");
            return false;
        }
        Book target = null;
        for (Book book : bookList) {
            if (book.getBookId().equals(bookId)) {
                target = book;
                break;
            }
        }
        if (target == null) {
            System.out.println("未找到该编号图书！");
            return false;
        }
        bookList.remove(target);
        System.out.println("删除成功：" + target.getBookName());
        return true;
    }

    // 根据ID查询图书
    public Book findBookById(String bookId) {
        if (bookId == null || bookId.trim().isEmpty()) return null;
        for (Book book : bookList) {
            if (book.getBookId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    // 根据名称模糊查询
    public List<Book> findBooksByName(String name) {
        if (name == null || name.trim().isEmpty()) return new ArrayList<>();
        return bookList.stream()
                .filter(book -> book.getBookName().contains(name))
                .collect(Collectors.toList());
    }

    // 修改图书信息
    public boolean updateBook(Book book) {
        if (book == null || !book.isValidBook()) return false;
        Book old = findBookById(book.getBookId());
        if (old == null) return false;
        old.setBookName(book.getBookName());
        old.setAuthor(book.getAuthor());
        old.setPublisher(book.getPublisher());
        old.setPublishYear(book.getPublishYear());
        old.setTotal(book.getTotal());
        old.setStock(book.getStock());
        old.setAvailable(book.isAvailable());
        System.out.println("图书信息修改成功！");
        return true;
    }

    // 查询所有图书
    public List<Book> findAllBooks() {
        return new ArrayList<>(bookList);
    }

    // 统计可借图书数量
    public int countAvailableBooks() {
        int count = 0;
        for (Book book : bookList) {
            if (book.isAvailable()) count++;
        }
        return count;
    }

    // 打印所有图书
    public void printAllBooks() {
        System.out.println("====== 所有图书列表 ======");
        if (bookList.isEmpty()) {
            System.out.println("暂无图书！");
            return;
        }
        for (Book book : bookList) {
            System.out.println(book);
        }
    }
}
