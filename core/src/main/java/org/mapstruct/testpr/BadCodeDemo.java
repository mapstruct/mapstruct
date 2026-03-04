package org.mapstruct.testpr;

/**
 * test pr-agent
 *
 * @author jinzh
 * @date 2026/3/4 14:28
 * @since V1.0
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BadCodeDemo {

    private static String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static String DB_USER = "root";
    private static String DB_PWD = "123456";

    private static int MAX_COUNT = 999;
    private static double RATE = 0.05;

    public static void main(String[] args) {
        BadCodeDemo demo = new BadCodeDemo();
        demo.doBusinessLogic();
    }

    public void doBusinessLogic() {
        List<User> userList = getUserListFromDB();
        Map<String, Object> resultMap = new HashMap<>();

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if (user.getAge() > 18) {
                resultMap.put(user.getName(), user);
            }
        }

        String content = readFileContent(null);
        System.out.println(content);

        double money = calculateFee(null, 1000);
        System.out.println("fee: " + money);

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public List<User> getUserListFromDB() {
        List<User> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
            String sql = "select * from user where status = 1";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
                list.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        return list;
    }

    public String readFileContent(String path) {
        BufferedReader br = null;
        String content = "";
        try {
            br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                content += line;
            }
        } catch (IOException e) {
        }
        return content;
    }

    public double calculateFee(User user, int amount) {
        if (amount > MAX_COUNT) {
            return amount * RATE;
        } else {
            return 0;
        }
    }

    public void uselessMethod1() {}
    public void uselessMethod2() {}
    public void uselessMethod3() {}
    public void uselessMethod4() {}
    public void uselessMethod5() {}
}

class User {
    public int id;
    public String name;
    public int age;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}