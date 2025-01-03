package ageria.nagefy;

import ageria.nagefy.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*@SpringBootApplication*/
public class RunnerTest implements CommandLineRunner {

    @Autowired
    ClientsService clientsService;

    public static void main(String[] arg){
        SpringApplication.run(RunnerTest.class, arg);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.clientsService.findFromName("Mirko"));
    }
}
