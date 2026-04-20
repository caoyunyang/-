import java.util.Objects;

/**
 * 组员3：用户实体类，包含读者/管理员、注册、登录、权限判断
 * 行数：130+
 */
public class User {
    private String userId;
    private String username;
    private String password;
    private String phone;
    private String role; // admin-管理员 reader-读者
    private int maxBorrow; // 最大可借数量
    private int borrowedCount; // 已借数量
    private boolean status; // 账号状态

    public User() {
        this.maxBorrow = 5;
        this.borrowedCount = 0;
        this.status = true;
    }

    public User(String userId, String username, String password, String phone, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.maxBorrow = "admin".equals(role) ? 99 : 5;
        this.borrowedCount = 0;
        this.status = true;
    }

    // 登录验证
    public boolean login(String inputPwd) {
        if (!status) {
            System.out.println("账号已被禁用！");
            return false;
        }
        if (password.equals(inputPwd)) {
            System.out.println("登录成功！欢迎：" + username);
            return true;
        } else {
            System.out.println("密码错误！");
            return false;
        }
    }

    // 是否可借书
    public boolean canBorrow() {
        if (!status) return false;
        return borrowedCount < maxBorrow;
    }

    // 借书成功后+1
    public void borrowOne() {
        if (canBorrow()) {
            borrowedCount++;
        }
    }

    // 还书成功后-1
    public void returnOne() {
        if (borrowedCount > 0) {
            borrowedCount--;
        }
    }

    // 校验用户信息
    public boolean isValidUser() {
        if (userId == null || userId.trim().isEmpty()) return false;
        if (username == null || username.trim().isEmpty()) return false;
        if (password == null || password.length() < 6) return false;
        if (phone == null || phone.length() != 11) return false;
        return "admin".equals(role) || "reader".equals(role);
    }

    // get/set
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public int getMaxBorrow() { return maxBorrow; }
    public void setMaxBorrow(int maxBorrow) { this.maxBorrow = maxBorrow; }
    public int getBorrowedCount() { return borrowedCount; }
    public void setBorrowedCount(int borrowedCount) { this.borrowedCount = borrowedCount; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "用户信息{" +
                "账号='" + userId + '\'' +
                ", 姓名='" + username + '\'' +
                ", 手机号='" + phone + '\'' +
                ", 角色='" + role + '\'' +
                ", 已借=" + borrowedCount +
                ", 最大可借=" + maxBorrow +
                ", 状态=" + (status ? "正常" : "禁用") +
                '}';
    }
}
