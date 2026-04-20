import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 组员4：借书、还书、借阅记录、逾期判断
 * 行数：160+
 */
public class BorrowService {
    private List<BorrowRecord> recordList;
    private BookManager bookManager;
    private DateTimeFormatter formatter;

    public BorrowService(BookManager bookManager) {
        this.bookManager = bookManager;
        this.recordList = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    // 借书
    public boolean borrowBook(User user, String bookId) {
        if (user == null || !user.isStatus()) {
            System.out.println("用户无效或已禁用！");
            return false;
        }
        if (!user.canBorrow()) {
            System.out.println("已达到最大可借数量！");
            return false;
        }
        Book book = bookManager.findBookById(bookId);
        if (book == null) {
            System.out.println("图书不存在！");
            return false;
        }
        if (!book.isAvailable()) {
            System.out.println("图书暂无库存！");
            return false;
        }
        // 执行借书
        book.borrowBook();
        user.borrowOne();
        // 生成记录
        BorrowRecord record = new BorrowRecord();
        record.setRecordId("R" + System.currentTimeMillis());
        record.setUserId(user.getUserId());
        record.setBookId(bookId);
        record.setBookName(book.getBookName());
        record.setBorrowTime(LocalDateTime.now());
        record.setReturnTime(null);
        record.setStatus("已借出");
        recordList.add(record);
        System.out.println("借书业务完成！");
        return true;
    }

    // 还书
    public boolean returnBook(User user, String bookId) {
        Book book = bookManager.findBookById(bookId);
        if (book == null) return false;
        BorrowRecord target = null;
        for (BorrowRecord r : recordList) {
            if (r.getUserId().equals(user.getUserId())
                    && r.getBookId().equals(bookId)
                    && "已借出".equals(r.getStatus())) {
                target = r;
                break;
            }
        }
        if (target == null) {
            System.out.println("未找到该用户的借阅记录！");
            return false;
        }
        // 执行还书
        book.returnBook();
        user.returnOne();
        target.setReturnTime(LocalDateTime.now());
        target.setStatus("已归还");
        System.out.println("还书业务完成！");
        return true;
    }

    // 查询用户借阅记录
    public List<BorrowRecord> getRecordsByUser(String userId) {
        List<BorrowRecord> list = new ArrayList<>();
        for (BorrowRecord r : recordList) {
            if (r.getUserId().equals(userId)) {
                list.add(r);
            }
        }
        return list;
    }

    // 查询逾期记录
    public List<BorrowRecord> getOverdueRecords() {
        List<BorrowRecord> list = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (BorrowRecord r : recordList) {
            if ("已借出".equals(r.getStatus())) {
                LocalDateTime deadline = r.getBorrowTime().plusDays(30);
                if (now.isAfter(deadline)) {
                    list.add(r);
                }
            }
        }
        return list;
    }

    // 打印所有记录
    public void printAllRecords() {
        System.out.println("====== 借阅记录 ======");
        if (recordList.isEmpty()) {
            System.out.println("暂无记录");
            return;
        }
        for (BorrowRecord r : recordList) {
            System.out.println(r);
        }
    }

    // 内部类：借阅记录
    class BorrowRecord {
        private String recordId;
        private String userId;
        private String bookId;
        private String bookName;
        private LocalDateTime borrowTime;
        private LocalDateTime returnTime;
        private String status;

        public String getRecordId() { return recordId; }
        public void setRecordId(String recordId) { this.recordId = recordId; }
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getBookId() { return bookId; }
        public void setBookId(String bookId) { this.bookId = bookId; }
        public String getBookName() { return bookName; }
        public void setBookName(String bookName) { this.bookName = bookName; }
        public LocalDateTime getBorrowTime() { return borrowTime; }
        public void setBorrowTime(LocalDateTime borrowTime) { this.borrowTime = borrowTime; }
        public LocalDateTime getReturnTime() { return returnTime; }
        public void setReturnTime(LocalDateTime returnTime) { this.returnTime = returnTime; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        @Override
        public String toString() {
            return "借阅记录{" +
                    "记录编号='" + recordId + '\'' +
                    ", 用户账号='" + userId + '\'' +
                    ", 图书名称='" + bookName + '\'' +
                    ", 借阅时间=" + borrowTime.format(formatter) +
                    ", 归还时间=" + (returnTime == null ? "未归还" : returnTime.format(formatter)) +
                    ", 状态='" + status + '\'' +
                    '}';
        }
    }
}
