import java.util.HashMap;
import java.util.Map;

class Logger {
    public void log(String message) {
        System.out.println("[LOG]: " + message);
    }
}

class UserService {
    private final Logger logger;

    public UserService(Logger logger) {
        this.logger = logger;
    }

    public String getUser( ) {
        logger.log("Getting user...");
        return "{ id: 1, name: 'John Doe' }";
    }
}
public static void main(String[] args) {
    DIContainer container = new DIContainer();

    // 의존성 등록. 등록시에는 인스턴스를 저장하지는 않음
    container.register("Logger", Logger.class);
    container.register("UserService", UserService.class);

    // 의존성 해결
    UserService userService = (UserService) container.resolve("UserService");
    System.out.println(userService.getUser());
}

class DIContainer {

    private final Map<String, Object> instance;

    public DIContainer() {
        this.instance = new HashMap<>();
    }

    public void register(String string, Object o) {
        this.instance.put(string,o);
    }

    public <T> resolve(String s) {

        return this.instance.get(s);
    }
}
