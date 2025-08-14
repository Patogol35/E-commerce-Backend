package patricio.santamaria.springgatewaycloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringGatewayCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGatewayCloudApplication.class, args);
	}

}
