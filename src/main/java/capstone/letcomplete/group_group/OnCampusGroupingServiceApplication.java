package capstone.letcomplete.group_group;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OnCampusGroupingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnCampusGroupingServiceApplication.class, args);
	}

}
