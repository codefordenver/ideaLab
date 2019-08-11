package idealab.api.controller;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD:Backend/src/main/java/idealab/api/controller/GreetingController.java
import idealab.api.models.Greeting;
=======
import ideaLab.api.models.Greeting;
>>>>>>> add hibernate, add all models+enum files:Backend/src/main/java/ideaLab/api/controller/GreetingController.java

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
}