package cb.fm.backtowork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        //ConnectionManager connMgr = ConnectionManager.getConnManager();
        SpringApplication.run(App.class, args);
    }
}